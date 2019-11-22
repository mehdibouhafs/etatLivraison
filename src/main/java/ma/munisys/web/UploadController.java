package ma.munisys.web;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ma.munisys.entities.Document;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Projet;
import ma.munisys.service.EtatProjetService;
import ma.munisys.service.EtatRecouvrementService;
import ma.munisys.service.StorageServiceImpl;

@Controller
@CrossOrigin(origins="*")
public class UploadController {
	
	@Autowired
	StorageServiceImpl storageService;
	
	@Autowired
	EtatProjetService etatProjetService;
	
	@Autowired
	EtatRecouvrementService etatRecouvrementService;
 
	List<String> files = new ArrayList<String>();
 
	@PostMapping("/post")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			//storageService.deleteAll();
	
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			String newFileName= storageService.store(file);
			files.add(newFileName);
			System.out.println("file "+ file.getOriginalFilename());
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
			//storageService.deleteAll();
	
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			String newFileName= storageService.store(file);
			files.add(newFileName);
			System.out.println("file "+ file.getOriginalFilename());
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
	public ResponseEntity<Resource> exportExcel(@RequestBody List<Projet> projets)  {
		
		System.out.println("projets " + projets);
	         
	    XSSFWorkbook workbook = null;
	    Resource file = null;
	    String fileName = null;
	    /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
	    //vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);
	    try{
	        /* Logic to Export Excel */
	        LocalDateTime localDate = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm"); 
	         fileName = "EtatProjet"+ "-" + localDate.format(formatter) + ".xlsx";
	        //response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	        
	        OutputStream out;
	        workbook = (XSSFWorkbook) storageService.generateWorkBook(projets);
	        
	        
	        FileOutputStream fileOut = new FileOutputStream("upload-dir/"+fileName);
	        workbook.write(fileOut);
	        fileOut.close();
	        workbook.close();
	         file = storageService.loadFile(fileName);
	        
	        /* Export Excel logic end */
	        
	             
	        } catch (Exception ecx) {
	        	ecx.printStackTrace();
	           // vehicleLastSeenByCampaignReport vehicleCampaignReport = new VehicleLastSeenByCampaignReport();
	            //vehicleCampaignReport.setErrorMessage("Campaign Not Found");
	            //return  ResponseEntity.;
	        }/*finally {
	            if (null != workbook) {
	                try {
	                    workbook.close();
	                     //file.getFile().delete(); 
	                } catch (Exception e) {
	                	e.printStackTrace();
	                  //  logger.error("Error Occurred while exporting to XLS ", eio);
	                }
	            }
	        }*/
	    return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);
	         
	       
	    }
	
	
	@PostMapping("/exportDocumentsExcel")
	@ResponseBody
	public ResponseEntity<Resource> exportDocumentsExcel(@RequestBody List<Document> documents)  {
		     
	    XSSFWorkbook workbook = null;
	    Resource file = null;
	    String fileName = null;
	    /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
	    //vehicleLastSeenByCampaignReport.setCvsSummary(cvsSummary);
	    try{
	        /* Logic to Export Excel */
	        LocalDateTime localDate = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm"); 
	         fileName = "EtatDocuments"+ "-" + localDate.format(formatter) + ".xlsx";
	        //response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	        
	        OutputStream out;
	        workbook = (XSSFWorkbook) storageService.generateWorkBookDocuments(documents);
	        
	        
	        FileOutputStream fileOut = new FileOutputStream("upload-dir/"+fileName);
	        workbook.write(fileOut);
	        fileOut.close();
	        workbook.close();
	         file = storageService.loadFile(fileName);
	        
	        /* Export Excel logic end */
	        
	             
	        } catch (Exception ecx) {
	        	ecx.printStackTrace();
	           // vehicleLastSeenByCampaignReport vehicleCampaignReport = new VehicleLastSeenByCampaignReport();
	            //vehicleCampaignReport.setErrorMessage("Campaign Not Found");
	            //return  ResponseEntity.;
	        }/*finally {
	            if (null != workbook) {
	                try {
	                    workbook.close();
	                     //file.getFile().delete(); 
	                } catch (Exception e) {
	                	e.printStackTrace();
	                  //  logger.error("Error Occurred while exporting to XLS ", eio);
	                }
	            }
	        }*/
	    return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(file);
	         
	       
	    }
	

	
	
     
   
	

}
