package ma.munisys.web;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ma.munisys.dao.ContratRepository;
import ma.munisys.dao.ContratSpecification;
import ma.munisys.dao.EcheanceRepository;
import ma.munisys.dto.ContratSearch;
import ma.munisys.dto.EcheanceDto;
import ma.munisys.entities.BalanceAgee;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.DetailRdv;
import ma.munisys.entities.Document;
import ma.munisys.entities.Echeance;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Produit;
import ma.munisys.entities.Projet;
import ma.munisys.entities.StockProjet;
import ma.munisys.service.EtatProjetService;
import ma.munisys.service.EtatRecouvrementService;
import ma.munisys.service.StorageServiceImpl;

@Controller
@CrossOrigin(origins = "*")
public class UploadController {

	private static final Logger LOGGER = LogManager.getLogger(UploadController.class);

	@Autowired
	StorageServiceImpl storageService;

	@Autowired
	EtatProjetService etatProjetService;

	@Autowired
	EtatRecouvrementService etatRecouvrementService;

	@Autowired
	ContratRepository contratRepository;

	@Autowired
	EcheanceRepository echeanceRepository;

	List<String> files = new ArrayList<String>();

	@PostMapping("/post")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			// storageService.deleteAll();

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			String newFileName = storageService.store(file);
			files.add(newFileName);
			System.out.println("file " + file.getOriginalFilename());
			etatProjetService.getProjetsFromInputFile(newFileName);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/post2")
	public ResponseEntity<String> handleFileUpload2(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			// storageService.deleteAll();

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			String newFileName = storageService.store(file);
			files.add(newFileName);
			System.out.println("file " + file.getOriginalFilename());
			etatRecouvrementService.getDocumentsFromInputFile(newFileName);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@GetMapping("/getallfiles")
	public ResponseEntity<List<String>> getListFiles(Model model) {
		List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(fileNames);
	}

	@GetMapping("/files2/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/exportExcel")
	@ResponseBody
	public ResponseEntity<Resource> exportExcel(@RequestBody List<Projet> projets) {

		System.out.println("projets " + projets);

		XSSFWorkbook workbook = null;
		Resource file = null;
		String fileName = null;
		/*
		 * Here I got the object structure (pulling it from DAO layer) that I want to be
		 * export as part of Excel.
		 */
		// vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);
		try {
			/* Logic to Export Excel */
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
			fileName = "EtatProjet" + "-" + localDate.format(formatter) + ".xlsx";
			// response.setHeader("Content-Disposition", "attachment; filename=" +
			// fileName);

			OutputStream out;
			workbook = (XSSFWorkbook) storageService.generateWorkBook(projets);

			FileOutputStream fileOut = new FileOutputStream("upload-dir/" + fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = storageService.loadFile(fileName);

			/* Export Excel logic end */

		} catch (Exception ecx) {
			ecx.printStackTrace();
			// vehicleLastSeenByCampaignReport vehicleCampaignReport = new
			// VehicleLastSeenByCampaignReport();
			// vehicleCampaignReport.setErrorMessage("Campaign Not Found");
			// return ResponseEntity.;
		} /*
			 * finally { if (null != workbook) { try { workbook.close();
			 * //file.getFile().delete(); } catch (Exception e) { e.printStackTrace(); //
			 * logger.error("Error Occurred while exporting to XLS ", eio); } } }
			 */
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);

	}

	@PostMapping("/exportDetailRdv")
	@ResponseBody
	public ResponseEntity<Resource> exportDetailRdv(@RequestBody String codeProjet) {

		XSSFWorkbook workbook = null;
		Resource file = null;
		String fileName = null;

		try {
			/* Logic to Export Excel */
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
			fileName = "RDV" + "-" + codeProjet.replace("/", "-") + "-" + localDate.format(formatter) + ".xlsx";

			Collection<DetailRdv> detailRdvs = etatProjetService.getDetailRdvByCodeProjet(codeProjet);

			Collection<DetailRdv> detailRdvsDep = etatProjetService.getDetailRdvDEPByCodeProjet(codeProjet);

			
			OutputStream out;
			workbook = (XSSFWorkbook) storageService.generateWorkBookRdv(detailRdvs,detailRdvsDep);


			FileOutputStream fileOut = new FileOutputStream("upload-dir/" + fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = storageService.loadFile(fileName);

		} catch (Exception ecx) {
			ecx.printStackTrace();
		} finally {
			if (null != workbook) {
				try {
					workbook.close();
					// file.getFile().delete();
				} catch (Exception e) {
					e.printStackTrace();
					// logger.error("Error Occurred while exporting to XLS ", eio);
				}
			}
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);

	}

	@PostMapping("/exportDocumentsExcel")
	@ResponseBody
	public ResponseEntity<Resource> exportDocumentsExcel(@RequestBody List<Document> documents) {

		XSSFWorkbook workbook = null;
		Resource file = null;
		String fileName = null;
		/*
		 * Here I got the object structure (pulling it from DAO layer) that I want to be
		 * export as part of Excel.
		 */
		// vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);
		try {
			/* Logic to Export Excel */
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
			fileName = "EtatDocuments" + "-" + localDate.format(formatter) + ".xlsx";
			// response.setHeader("Content-Disposition", "attachment; filename=" +
			// fileName);

			OutputStream out;
			workbook = (XSSFWorkbook) storageService.generateWorkBookDocuments(documents);

			FileOutputStream fileOut = new FileOutputStream("upload-dir/" + fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = storageService.loadFile(fileName);

			/* Export Excel logic end */

		} catch (Exception ecx) {
			ecx.printStackTrace();
			// vehicleLastSeenByCampaignReport vehicleCampaignReport = new
			// VehicleLastSeenByCampaignReport();
			// vehicleCampaignReport.setErrorMessage("Campaign Not Found");
			// return ResponseEntity.;
		} /*
			 * finally { if (null != workbook) { try { workbook.close();
			 * //file.getFile().delete(); } catch (Exception e) { e.printStackTrace(); //
			 * logger.error("Error Occurred while exporting to XLS ", eio); } } }
			 */
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);

	}

	@PostMapping("/exportProduitsExcel")
	@ResponseBody
	public ResponseEntity<Resource> exportProduitsExcel(@RequestBody List<Produit> produits) {

		XSSFWorkbook workbook = null;
		Resource file = null;
		String fileName = null;
		/*
		 * Here I got the object structure (pulling it from DAO layer) that I want to be
		 * export as part of Excel.
		 */
		// vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);
		try {
			/* Logic to Export Excel */
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
			fileName = "EtatStock" + "-" + localDate.format(formatter) + ".xlsx";
			// response.setHeader("Content-Disposition", "attachment; filename=" +
			// fileName);

			OutputStream out;
			workbook = (XSSFWorkbook) storageService.generateWorkBookStock(produits);

			FileOutputStream fileOut = new FileOutputStream("upload-dir/" + fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = storageService.loadFile(fileName);

			/* Export Excel logic end */

		} catch (Exception ecx) {
			ecx.printStackTrace();
			// vehicleLastSeenByCampaignReport vehicleCampaignReport = new
			// VehicleLastSeenByCampaignReport();
			// vehicleCampaignReport.setErrorMessage("Campaign Not Found");
			// return ResponseEntity.;
		} /*
			 * finally { if (null != workbook) { try { workbook.close();
			 * //file.getFile().delete(); } catch (Exception e) { e.printStackTrace(); //
			 * logger.error("Error Occurred while exporting to XLS ", eio); } } }
			 */
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);

	}

	@RequestMapping(value = "/exportContratExcel", method = RequestMethod.GET)
	public ResponseEntity<Resource> exportContratExcel(
			@RequestParam(name = "numContrat", required = false) String numContrat,
			@RequestParam(name = "mc", required = false) String motCle,
			@RequestParam(name = "numMarche", required = false) String numMarche,
			@RequestParam(name = "pilote", required = false) String pilote,
			@RequestParam(name = "nomPartenaire", required = false) String nomPartenaire,
			@RequestParam(name = "sousTraiter", required = false) Boolean sousTraiter) {

		ContratSearch cs = new ContratSearch();
		cs.setMotCle(motCle);
		cs.setNomPartenaire(nomPartenaire);
		cs.setPilote(pilote);
		cs.setSousTraiter(sousTraiter);
		cs.setNumMarche(numMarche);

		ContratSpecification contratSpecification = new ContratSpecification(cs);

		Collection<Contrat> contrats = contratRepository.findAll(contratSpecification);

		XSSFWorkbook workbook = null;
		Resource file = null;
		String fileName = null;
		/*
		 * Here I got the object structure (pulling it from DAO layer) that I want to be
		 * export as part of Excel.
		 */
		// vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);
		try {
			/* Logic to Export Excel */
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
			fileName = "EtatContrat" + "-" + localDate.format(formatter) + ".xlsx";
			// response.setHeader("Content-Disposition", "attachment; filename=" +
			// fileName);

			OutputStream out;
			workbook = (XSSFWorkbook) storageService.generateWorkBookContrat(new ArrayList<>(contrats));

			FileOutputStream fileOut = new FileOutputStream("upload-dir/" + fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = storageService.loadFile(fileName);

			/* Export Excel logic end */

		} catch (Exception ecx) {
			ecx.printStackTrace();
			// vehicleLastSeenByCampaignReport vehicleCampaignReport = new
			// VehicleLastSeenByCampaignReport();
			// vehicleCampaignReport.setErrorMessage("Campaign Not Found");
			// return ResponseEntity.;
		} /*
			 * finally { if (null != workbook) { try { workbook.close();
			 * //file.getFile().delete(); } catch (Exception e) { e.printStackTrace(); //
			 * logger.error("Error Occurred while exporting to XLS ", eio); } } }
			 */
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);

	}

	@RequestMapping(value = "/exportEcheancesNotLinked", method = RequestMethod.GET)
	public ResponseEntity<Resource> exportEcheanceNotLinked(
			@RequestParam(name = "date", required = false) String date) {

		DateFormat sourceFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");

		Calendar c = Calendar.getInstance();

		Collection<Echeance> echeances = null;
		try {

			if (date != null && date != "") {

				Date date1 = sourceFormat.parse(date);
				echeances = echeanceRepository.getEcheancesNotLinkedDateParam(date1);

			} else {
				echeances = echeanceRepository.getEcheancesNotLinked(c.get(Calendar.YEAR));
			}

			XSSFWorkbook workbook = null;
			Resource file = null;
			String fileName = null;
			/*
			 * Here I got the object structure (pulling it from DAO layer) that I want to be
			 * export as part of Excel.
			 */
			// vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);

			/* Logic to Export Excel */
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
			fileName = "EtatContrat" + "-" + localDate.format(formatter) + ".xlsx";
			// response.setHeader("Content-Disposition", "attachment; filename=" +
			// fileName);

			OutputStream out;
			workbook = (XSSFWorkbook) storageService.generateWorkBookEcheance(new ArrayList<>(echeances));

			FileOutputStream fileOut = new FileOutputStream("upload-dir/" + fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = storageService.loadFile(fileName);

			/* Export Excel logic end */

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(file);

		} catch (ParseException | IOException e) {
			LOGGER.error(e);
		}

		return null;

	}

	@PostMapping("/exportStockExcel")
	@ResponseBody
	public ResponseEntity<Resource> exportStockExcel(@RequestBody List<StockProjet> produit) {

		XSSFWorkbook workbook = null;
		Resource file = null;
		String fileName = null;
		/*
		 * Here I got the object structure (pulling it from DAO layer) that I want to be
		 * export as part of Excel.
		 */
		// vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);
		try {
			/* Logic to Export Excel */
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
			fileName = "Etat_StockProjet" + "-" + localDate.format(formatter) + ".xlsx";
			// response.setHeader("Content-Disposition", "attachment; filename=" +
			// fileName);
			for (StockProjet p : produit) {
				System.out.println("CONTROLLER " + p.getId_stock());
			}
			OutputStream out;
			workbook = (XSSFWorkbook) storageService.generateWorkBookStockProjet(produit);

			FileOutputStream fileOut = new FileOutputStream("upload-dir/" + fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = storageService.loadFile(fileName);

			/* Export Excel logic end */

		} catch (Exception ecx) {
			ecx.printStackTrace();
			// vehicleLastSeenByCampaignReport vehicleCampaignReport = new
			// VehicleLastSeenByCampaignReport();
			// vehicleCampaignReport.setErrorMessage("Campaign Not Found");
			// return ResponseEntity.;
		} /*
			 * finally { if (null != workbook) { try { workbook.close();
			 * //file.getFile().delete(); } catch (Exception e) { e.printStackTrace(); //
			 * logger.error("Error Occurred while exporting to XLS ", eio); } } }
			 */
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);

	}

	@PostMapping("/exportBalance")
	@ResponseBody
	public ResponseEntity<Resource> exportBalance(@RequestBody List<BalanceAgee> balance) {

		XSSFWorkbook workbook = null;
		Resource file = null;
		String fileName = null;
		/*
		 * Here I got the object structure (pulling it from DAO layer) that I want to be
		 * export as part of Excel.
		 */
		// vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);
		try {
			/* Logic to Export Excel */
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
			fileName = "Balance_Agee" + "-" + localDate.format(formatter) + ".xlsx";
			// response.setHeader("Content-Disposition", "attachment; filename=" +
			// fileName);

			OutputStream out;
			workbook = (XSSFWorkbook) storageService.generateWorkBookBalance(balance);

			FileOutputStream fileOut = new FileOutputStream("upload-dir/" + fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			file = storageService.loadFile(fileName);

			/* Export Excel logic end */

		} catch (Exception ecx) {
			ecx.printStackTrace();
			// vehicleLastSeenByCampaignReport vehicleCampaignReport = new
			// VehicleLastSeenByCampaignReport();
			// vehicleCampaignReport.setErrorMessage("Campaign Not Found");
			// return ResponseEntity.;
		} /*
			 * finally { if (null != workbook) { try { workbook.close();
			 * //file.getFile().delete(); } catch (Exception e) { e.printStackTrace(); //
			 * logger.error("Error Occurred while exporting to XLS ", eio); } } }
			 */
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);

	}

}
