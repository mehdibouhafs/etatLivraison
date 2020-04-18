package ma.munisys.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetInstancesFromServiceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.munisys.dao.EtatRecouvrementRepository;
import ma.munisys.dao.EventRepository;
import ma.munisys.dao.ProjetRepository;
import ma.munisys.dao.UserRepository;
import ma.munisys.dto.DocumentAnalyse;
import ma.munisys.dto.EtatEncaissement;
import ma.munisys.dao.DocumentRepository;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Detail;
import ma.munisys.entities.EtatRecouvrement;
import ma.munisys.entities.Event;
import ma.munisys.entities.Header;
import ma.munisys.entities.Projet;
import ma.munisys.entities.Document;
import ma.munisys.utils.Constants;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service // spring pas javax
@Transactional
public class EtatRecouvrementServiceImpl implements EtatRecouvrementService {

	private static final Logger logger = LogManager.getLogger(EtatRecouvrementServiceImpl.class);

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private ProjetRepository ProjetRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private EtatRecouvrementRepository etatRecouvrementRepository;

	private final Path rootLocation = Paths.get("upload-dir");

	/*
	 * @Override public void checkIfDocumentClotured(Document document) {
	 * 
	 * if(document.getRal() == 0 && document.getLnf() == 0 &&
	 * document.getLfp()==document.getLiv()) { document.setCloture(true); }
	 * 
	 * document.setCloture(true); }
	 */

	@Override
	public Page<Document> getDocumentsFromEtatRecouvrement(Long idEtatRecouvrement, Boolean cloture, int page,
			int size) {
		// TODO Auto-generated method stub
		return documentRepository.getDocuments(idEtatRecouvrement, cloture, new PageRequest(page, size));
	}

	@Override
	public Set<Document> getDocumentsFromInputFile(String fileName) {
		// TODO Auto-generated method stub

		//System.out.println("filename " + fileName);
		logger.debug(" Input File  " + fileName.toString());

		Set<Document> documents = new HashSet<>();

		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(new File(rootLocation.toString() + "/" + fileName));

			//System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
			// Getting the Sheet at index zero
			Sheet sheet = workbook.getSheetAt(0);

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Row> rowIterator = sheet.rowIterator();
			boolean firstRow = false;
			EtatRecouvrement newEtatRecouvrement = new EtatRecouvrement();
			newEtatRecouvrement.setId(1L);
			List<Header> headers = new ArrayList<Header>();

			while (rowIterator.hasNext()) {
				Document p = new Document();

				Row row = rowIterator.next();

				//System.out.println("row  " + row.getRowNum());

				if (row.getRowNum() == 0) {
					firstRow = true;
				} else {
					firstRow = false;
				}
				// Now let's iterate over the columns of the current row
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);
					if (firstRow) {
						Header header = new Header(cellValue);
						newEtatRecouvrement.addHeader(header);
						headers.add(header);
						//System.out.println("row header contain " + cellValue);
					} else {
						//System.out.println("row Collone contain " + cellValue);

						Detail detail = new Detail();
						detail.setHeader(headers.get(cell.getColumnIndex()));
						detail.setValue(cellValue);
						p.addDetail(detail);
						newEtatRecouvrement.addDocument(p);
						// p.getProperties().put(EtatRecouvrement.getHeader().get(cell.getColumnIndex()),
						// cellValue);

					}

				}

				//System.out.println();
			}

			//System.out.println("new EtatRecouvrement " + newEtatRecouvrement);
			addOrUpdateEtatRecouvrement(newEtatRecouvrement);

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			logger.debug("Encrypted document !" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("InvalidFormatException document !" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//
		return documents;

		/*
		 * for(int i=1; i<=lastRow;i++) { CtrctCustomer ctrctCustomer = new
		 * CtrctCustomer(); //System.out.println("id " +
		 * (long)workbook.getSheetAt(0).getRow(i).getCell(0).getNumericCellValue());
		 * ctrctCustomer.setId((long)workbook.getSheetAt(0).getRow(i).getCell(0).
		 * getNumericCellValue());
		 * ctrctCustomer.setDteStrt(workbook.getSheetAt(0).getRow(i).getCell(3).
		 * getDateCellValue());
		 * ctrctCustomer.setDteEnd(workbook.getSheetAt(0).getRow(i).getCell(4).
		 * getDateCellValue()); ctrctCustomer.setCustomer(new
		 * Customer(workbook.getSheetAt(0).getRow(i).getCell(5).getStringCellValue()));
		 * ctrctCustomer.setPilote(workbook.getSheetAt(0).getRow(i).getCell(2).
		 * getStringCellValue());
		 * ctrctCustomer.setCtrtName(workbook.getSheetAt(0).getRow(i).getCell(1).
		 * getStringCellValue());
		 * 
		 * ctrctCustomers.add(ctrctCustomer); }
		 */

		// return ctrctCustomers;
	}

	private static void printCellValue(Cell cell) {
		switch (cell.getCellTypeEnum()) {
		case BOOLEAN:
			//System.out.print(cell.getBooleanCellValue());
			break;
		case STRING:
			//System.out.print(cell.getRichStringCellValue().getString());
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				//System.out.print(cell.getDateCellValue());
			} else {
				//System.out.print(cell.getNumericCellValue());
			}
			break;
		case FORMULA:
			//System.out.print(cell.getCellFormula());
			break;
		case BLANK:
			//System.out.print("");
			break;
		default:
			//System.out.print("");
		}

		//System.out.print("\t");
	}

	@Override
	@javax.transaction.Transactional
	public void addOrUpdateEtatRecouvrement(EtatRecouvrement newEtatRecouvrement) {
		boolean firstCreation = false;
		//System.out.println("adorUpdate");
		EtatRecouvrement lastEtatRecouvrement = etatRecouvrementRepository.findById(1L).orElse(null);

		if (lastEtatRecouvrement != null && lastEtatRecouvrement.getDocuments() != null
				&& !lastEtatRecouvrement.getDocuments().isEmpty()) {
			for (Document document : lastEtatRecouvrement.getDocuments()) {
				document.setCloture(true);
			}
		}

		// checking document header
		// checkRequiredHidders(newEtatRecouvrement);

		if (lastEtatRecouvrement == null) {

			// create date document
			for (Document p : newEtatRecouvrement.getDocuments()) {
				p.setCreation(new Date());
				// p.setLastUpdate(new Date());
			}
			newEtatRecouvrement.setDateCreation(new Date());
			etatRecouvrementRepository.save(newEtatRecouvrement);
		} else {
			//System.out.println("here");
			boolean found = false;
			//System.out.println("lastEtatRecouvrement " + lastEtatRecouvrement.toString());
			//System.out.println("newEtatRecouvrement " + newEtatRecouvrement.toString());

			List<Header> headerToDelete = new ArrayList<Header>();

			List<Header> headerToAdd = new ArrayList<Header>();

			/*
			 * for (Header lastHeader : lastEtatRecouvrement.getHeaders()) {
			 * 
			 * found = false; for (Header newHeader : newEtatRecouvrement.getHeaders()) {
			 * 
			 * if (lastHeader.getLabel().equals(newHeader.getLabel())) { found = true; } }
			 * if (!found) { //System.out.println("delete head " + lastHeader.getLabel());
			 * headerToDelete.add(new Header(lastHeader.getLabel())); //
			 * lastEtatRecouvrement.getHeaders().remove(lastHeader); } }
			 * 
			 * for (Header header : headerToDelete) { deleteHeaderFromEtatRecouvrement(1L,
			 * header); }
			 */

			/*
			 * for (Iterator<Header> iterator =
			 * lastEtatRecouvrement.getHeaders().iterator(); iterator.hasNext(); ) { Header
			 * lastHeader = iterator.next(); found = false; for (Header newHeader :
			 * newEtatRecouvrement.getHeaders()) {
			 * 
			 * if (lastHeader.getLabel().equals(newHeader.getLabel())) { found = true; } }
			 * if (!found) { //System.out.println("delete head " + lastHeader.getLabel());
			 * deleteHeaderFromEtatRecouvrement(1L, lastHeader); //iterator.remove();
			 * //lastEtatRecouvrement.getHeaders().remove(lastHeader); }
			 * 
			 * 
			 * }
			 */

			/*
			 * for (Header newheader : newEtatRecouvrement.getHeaders()) { found = false;
			 * for (Header lastHeader : lastEtatRecouvrement.getHeaders()) { if
			 * (newheader.getLabel().equals(lastHeader.getLabel())) { found = true; } } if
			 * (!found) { //System.out.println("adding header " + newheader.getLabel());
			 * headerToAdd.add(new Header(newheader.getLabel())); }
			 * 
			 * }
			 * 
			 * for (Header header : headerToAdd) { lastEtatRecouvrement.addHeader(header); }
			 */

			// //System.out.println("lastEtatRecouvrement header " +
			// lastEtatRecouvrement.getHeaders());

			for (Document document : newEtatRecouvrement.getDocuments()) {
				document.setCloture(false);
				addOrUpdateDocument(lastEtatRecouvrement, document);
			}

			//System.out.println("lastEtatRecouvrement to update " + lastEtatRecouvrement);
			lastEtatRecouvrement.setLastUpdate(new Date());
			etatRecouvrementRepository.saveAndFlush(lastEtatRecouvrement);
		}

	}

	@Override
	@javax.transaction.Transactional
	public void addOrUpdateDocument(EtatRecouvrement lastEtatRecouvrement, Document document) {

		Document lastDocument = findDocumentFromEtatRecouvrement(lastEtatRecouvrement,
				document.getNumPieceByTypeDocument());
		if (lastDocument != null) {
			// document.setCodePiece(lastDocument.getCodePiece());
			if (lastDocument.getStatut() != null) {
				document.setStatut(lastDocument.getStatut());
			}
			
			if(lastDocument.isDisableUpdateChefProjet()) {
				document.setCodeChefProjet(lastDocument.getCodeChefProjet());
				document.setChefProjet(lastDocument.getChefProjet());
			}

			document.setMotif(lastDocument.getMotif());
			document.setMontantGarantie(lastDocument.getMontantGarantie());
			document.setMontantProvision(lastDocument.getMontantProvision());
			document.setDateFinGarantie(lastDocument.getDateFinGarantie());
			if (lastDocument.getDatePrevuEncaissement() != null)
				document.setDatePrevuEncaissement(lastDocument.getDatePrevuEncaissement());
			document.setDureeGarantie(lastDocument.getDureeGarantie());
			document.setAction(lastDocument.getAction());
			document.setResponsable(lastDocument.getResponsable());

			if (lastDocument.getDateDepot() != null)
				document.setDateDepot(lastDocument.getDateDepot());

			document.setMotifChangementDate(lastDocument.getMotifChangementDate());
			document.setCommentaires(lastDocument.getCommentaires());
			document.setDatePvProvisoire(lastDocument.getDatePvProvisoire());
			;
			document.setDatePrevuReceptionDefinitive(lastDocument.getDatePrevuReceptionDefinitive());
			document.setLastUpdate(new Date());
			document.setInfoChefProjetOrCommercial(lastDocument.getInfoChefProjetOrCommercial());
			document.setInfoClient(lastDocument.getInfoClient());
			document.setInfoProjet(lastDocument.getInfoProjet());
			document.setPriorite(lastDocument.getPriorite());
			document.setTypeBloquage(lastDocument.getTypeBloquage());

			lastEtatRecouvrement.getDocuments().remove(lastDocument);
			//System.out.println("document must be " + document);
			lastEtatRecouvrement.addDocument(document);
		} else {
			document.setCreation(new Date());
			lastEtatRecouvrement.addDocument(document);
		}

	}

	@Override
	public Document findDocumentFromEtatRecouvrement(EtatRecouvrement EtatRecouvrement, String numDocument) {

		for (Document p : EtatRecouvrement.getDocuments()) {
			if (p.getNumPieceByTypeDocument().equals(numDocument)) {
				return p;
			}
		}
		return null;

	}

	@Override
	@javax.transaction.Transactional
	public void deleteHeaderFromEtatRecouvrement(Long idEtatRecouvrement, Header header) {
		EtatRecouvrement EtatRecouvrement = etatRecouvrementRepository.getOne(1L);

		List<Detail> detailsToRemove = new ArrayList<Detail>();

		/*
		 * for (Document p : EtatRecouvrement.getDocuments()) { for (Detail d :
		 * p.getDetails()) { if (d.getHeader().getLabel().equals(header.getLabel())) {
		 * detailsToRemove.add(d);
		 * 
		 * } } }
		 * 
		 * for (Document p : EtatRecouvrement.getDocuments()) {
		 * 
		 * for (Detail d1 : detailsToRemove) { p.getDetails().remove(d1); }
		 * 
		 * }
		 */

		// EtatRecouvrement.getHeaders().removeIf(h ->
		// h.getLabel().equals(header.getLabel()));

	}

	public void checkRequiredHidders(EtatRecouvrement EtatRecouvrement) {

		//System.out.println("required headers " + Constants.getRequiredHeaders());
		List<String> requiredHeaders = Constants.getRequiredHeaders();
		boolean found = false;
		/*
		 * for (String requiredHeader : requiredHeaders) { found = false; for (Header
		 * header : EtatRecouvrement.getHeaders()) { if
		 * (header.getLabel().equals(requiredHeader)) { found = true; } }
		 * 
		 * if (!found) { throw new RuntimeException("the header name " + requiredHeader
		 * + " is required on the list of headers"); } }
		 */
	}

	@Override
	public Collection<Document> getDocumentsFromEtatRecouvrement(Long idEtatRecouvrement, Boolean cloturer,
			String chargeRecouvrement, String commercial, String chefProjet, String client, String statut,
			String annee) {

		// aucun filtre
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.getDocuments(idEtatRecouvrement, cloturer);

		}

		// filtre par chargeRecouvrement uniquement. A
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {

			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))));
		}

		// filtre par statut uniquement B
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository
					.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byStatut(statut)));
		}

		// filtre par chefProjet uniquement C
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(
					DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byChefProjet(chefProjet)));
		}

		// filtre par commercial uniquement D
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(
					DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byCommercial(commercial)));
		}

		// filtre par client uniquement E
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository
					.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byClient(client)));
		}

		// filtre par annee uniquement F
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository
					.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byAnnee(annee)));
		}

		// filtre par chargeRecouvrement et statut uniquement. AB
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut))));
		}

		// filtre par chargeRecouvrement et chefProjet uniquement. AC
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byChefProjet(chefProjet))));
		}

		// filtre par chargeRecouvrement et commercial uniquement. AD
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byCommercial(commercial))));

		}
		// filtre par chargeRecouvrement et client uniquement. AE
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byClient(client))));
		}

		// filtre par chargeRecouvrement et annee uniquement. AF
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byAnnee(annee))));
		}

		// filtre par statut et chefProjet uniquement. BC
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byChefProjet(chefProjet))));
		}

		// filtre par statut et commercial uniquement. BD
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byCommercial(commercial))));
		}

		// filtre par statut et client uniquement. BE
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byClient(client))));
		}

		// filtre par statut et client uniquement. BF
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byAnnee(annee))));
		}

		// filtre par chefProjet et commercial uniquement. CD
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byChefProjet(chefProjet).and(DocumentSpecification.byCommercial(commercial))));
		}

		// filtre par chefProjet et client uniquement. CE
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification.byClient(client))));
		}

		// filtre par chefProjet et client uniquement. CF
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification.byAnnee(annee))));
		}

		// filtre par commercial et client uniquement. DE
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byCommercial(commercial).and(DocumentSpecification.byClient(client))));
		}

		// filtre par commercial et annee uniquement. DF
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee))));
		}

		// filtre par client et annee uniquement. FE
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byClient(client).and(DocumentSpecification.byAnnee(annee))));
		}

		// filtre par chargeRecouvrement et statut et chefProjet uniquement. ABC
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byChargeRecouvrement(chargeRecouvrement)
					.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
					.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byChefProjet(chefProjet)))));

		}

		// filtre par chargeRecouvrement et statut et commercial uniquement. ABD
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byChargeRecouvrement(chargeRecouvrement)
					.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
					.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byCommercial(commercial)))));
		}

		// filtre par chargeRecouvrement et statut et client uniquement. ABE
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byClient(client)))));
		}

		// filtre par chargeRecouvrement et statut et annee uniquement. ABF
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byAnnee(annee)))));
		}

		// filtre par chargeRecouvrement et chefProjet et client uniquement. ACD
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byChefProjet(chefProjet)
									.and(DocumentSpecification.byCommercial(commercial)))));
		}

		// filtre par chargeRecouvrement et chefProjet et commercial uniquement. ACE
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byChefProjet(chefProjet)
									.and(DocumentSpecification.byCommercial(commercial)))));
		}

		// filtre par chargeRecouvrement et chefProjet et annee uniquement. ACF
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byChargeRecouvrement(chargeRecouvrement)
					.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
					.and(DocumentSpecification.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee)))));
		}

		// filtre par chargeRecouvrement et commercial et client uniquement. ADE
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byChargeRecouvrement(chargeRecouvrement)
					.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
					.and(DocumentSpecification.byCommercial(commercial).and(DocumentSpecification.byClient(client)))));
		}

		// filtre par chargeRecouvrement et commercial et annee uniquement. ADF
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byChargeRecouvrement(chargeRecouvrement)
					.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
					.and(DocumentSpecification.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee)))));
		}
		

		// filtre par chargeRecouvrement et client et annee uniquement. AEF
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byChargeRecouvrement(chargeRecouvrement)
					.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
					.and(DocumentSpecification.byClient(client).and(DocumentSpecification.byAnnee(annee)))));
		}

		// filtre par statut et chefProjet et commercial uniquement. BCD
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification.byChefProjet(chefProjet)
							.and(DocumentSpecification.byCommercial(commercial)))));
		}

		// filtre par statut et chefProjet et client uniquement. BCE
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byStatut(statut)
					.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification.byClient(client)))));

		}

		// filtre par statut et chefProjet et annee uniquement. BCF
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository
					.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byStatut(statut).and(
							DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification.byAnnee(annee)))));

		}

		// filtre par statut et commercial et client uniquement. BDE
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byStatut(statut)
					.and(DocumentSpecification.byCommercial(commercial).and(DocumentSpecification.byClient(client)))));

		}

		// filtre par statut et commercial et annee uniquement. BDF
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository
					.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byStatut(statut).and(
							DocumentSpecification.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee)))));
		}
		
		// filtre par statut et client et annee uniquement. BEF
				if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
						&& commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
					return documentRepository
							.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byStatut(statut).and(
									DocumentSpecification.byClient(client).and(DocumentSpecification.byAnnee(annee)))));

				}

		// filtre par chefProjet et commercial et client uniquement. CDE
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer).and(DocumentSpecification
					.byChefProjet(chefProjet)
					.and(DocumentSpecification.byClient(client).and(DocumentSpecification.byCommercial(commercial)))));

		}

		// filtre par chefProjet et commercial et anee uniquement. CDF
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(
					DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byChefProjet(chefProjet).and(
							DocumentSpecification.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee)))));

		}

		// filtre par commercialet client et anee uniquement. DFE
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(
					DocumentSpecification.isCloture(cloturer).and(DocumentSpecification.byCommercial(commercial)
							.and(DocumentSpecification.byClient(client).and(DocumentSpecification.byAnnee(annee)))));

		}

		// filtre par chargeRecouvrement et statut et chefProjet et commercial
		// uniquement. ABCD
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification
									.byChefProjet(chefProjet).and(DocumentSpecification.byCommercial(commercial))))));

		}

		// filtre par chargeRecouvrement et statut et chefProjet et client uniquement.
		// ABCE
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification
									.byChefProjet(chefProjet).and(DocumentSpecification.byClient(client))))));
		}

		// filtre par chargeRecouvrement et statut et chefProjet et annee uniquement.
		// ABCF
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification
									.byChefProjet(chefProjet).and(DocumentSpecification.byAnnee(annee))))));
		}

		// filtre par chargeRecouvrement et statut et commercial et client uniquement.
		// ABDE
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification
									.byCommercial(commercial).and(DocumentSpecification.byClient(client))))));
		}

		// filtre par chargeRecouvrement et statut et commercial et annee uniquement.
		// ABDF
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut).and(DocumentSpecification
									.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee))))));
		}

		// filtre par chargeRecouvrement et chefProjet et commercial et client
		// uniquement. ACDE
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification
									.byCommercial(commercial).and(DocumentSpecification.byClient(client))))));
		}

		// filtre par chargeRecouvrement et chefProjet et commercial et client
		// uniquement. ACDF
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification
									.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee))))));
		}

		// filtre par statut et chefProjet et commercial et client uniquement. BCDE
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byStatut(statut)
							.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification
									.byCommercial(commercial).and(DocumentSpecification.byClient(client))))));

		}
		
		// filtre par statut et chefProjet et client et annee uniquement. BCDE
				if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
						&& commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
					return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
							.and(DocumentSpecification.byStatut(statut)
									.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification
											.byClient(client).and(DocumentSpecification.byAnnee(annee))))));

				}

		// filtre par statut et chefProjet et commercial et annee uniquement. BCDF
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byStatut(statut)
							.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification
									.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee))))));

		}
		
		// filtre par statut et client et commercial et annee uniquement. BDEF
				if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
						&& !commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
					return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
							.and(DocumentSpecification.byStatut(statut)
									.and(DocumentSpecification.byClient(client).and(DocumentSpecification
											.byCommercial(commercial).and(DocumentSpecification.byAnnee(annee))))));
				}

		// filtre par chefProjet et commercial client et annee uniquement. CDFE
		if (chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification
							.byCommercial(commercial)
							.and(DocumentSpecification.byClient(client).and(DocumentSpecification.byAnnee(annee))))));

		}
		
	
		// filtre par chargeRecouvrement et statut et chefProjet et commercial et client
		// uniquement. ABCDE
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut)
									.and(DocumentSpecification.byChefProjet(chefProjet).and(DocumentSpecification
											.byCommercial(commercial).and(DocumentSpecification.byClient(client)))))));

		}

		// filtre par chargeRecouvrement et statut et chefProjet et commercial et annee
		// uniquement. ABCDF
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut)
									.and(DocumentSpecification.byChefProjet(chefProjet).and(
											DocumentSpecification.byCommercial(commercial).and(DocumentSpecification
													.byClient(client).and(DocumentSpecification.byAnnee(annee))))))));

		}

		// filtre par chargeRecouvrement et chefProjet et commercial et client et annee
		// uniquement. ACDEF
		if (!chargeRecouvrement.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut)
									.and(DocumentSpecification.byChefProjet(chefProjet).and(
											DocumentSpecification.byCommercial(commercial).and(DocumentSpecification
													.byClient(client).and(DocumentSpecification.byAnnee(annee))))))));

		}

		// filtre par statut et chefProjet et commercial et client et annee uniquement.
		// BCDEF
		if (chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut)
									.and(DocumentSpecification.byChefProjet(chefProjet).and(
											DocumentSpecification.byCommercial(commercial).and(DocumentSpecification
													.byClient(client).and(DocumentSpecification.byAnnee(annee))))))));

		}

		// filtre par chargeRecouvrement et statut et chefProjet et commercial et client
		// et annee uniquement. ABCDEF
		if (!chargeRecouvrement.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
				&& !commercial.equals("undefined") && !client.equals("undefined") && !annee.equals("undefined")) {
			return documentRepository.findAll(DocumentSpecification.isCloture(cloturer)
					.and(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement)
							.or(DocumentSpecification.byChargeRecouvrement(chargeRecouvrement))
							.and(DocumentSpecification.byStatut(statut)
									.and(DocumentSpecification.byChefProjet(chefProjet).and(
											DocumentSpecification.byCommercial(commercial).and(DocumentSpecification
													.byClient(client).and(DocumentSpecification.byAnnee(annee))))))));

		}
		return null;

	}

	@Override
	@Transactional
	public Document updateDocument(String idDocument, Document document) {
		
		logger.info("update doucment " + idDocument);

		document.setCodePiece(idDocument);

		Document lastDocument = documentRepository.findById(idDocument).get();

		StringJoiner stringJoiner = new StringJoiner(" || ");

		if (!lastDocument.getCommentaires().equals(document.getCommentaires())) {
			stringJoiner.add("Ajout de commentaires");
		}

		if (stringJoiner.length() > 0) {
			Collection<AppUser> users = userRepository
					.findUserByServices(Arrays.asList("Commercial", "Chef Projet", "SI", "Direction"));
			//System.out.println("size user " + users.size());
			//System.out.println("users : " + users);

			Date date = new Date();
			for (AppUser appUser : users) {

				boolean addNotification = false;

				if (appUser.getService().getServName().equals("Commercial")
						|| appUser.getService().getServName().equals("Chef Projet")) {
					if ((document.getCommercial() != null && document.getCommercial().equals(appUser.getLastName())
							|| (document.getChefProjet() != null
									&& document.getChefProjet().equals(appUser.getUsername())))) {
						addNotification = true;
					}

				} else {
					addNotification = true;
				}

				if (addNotification) {

					Event event = new Event();
					Document c = new Document();
					c.setCodePiece(idDocument);
					event.setDocument(c);
					event.setStatut(false);
					event.setCreatedBy(document.getUpdatedBy());
					event.setActions(stringJoiner.toString());
					event.setDate(date);
					event.setUser(appUser);

					Collection<Event> events = eventRepository.getEventDocument(appUser.getUsername(),
							document.getCodePiece());
					if (events != null && events.size() > 0)
						eventRepository.deleteAll(events);
					eventRepository.save(event);
				}
			}
		}

		Document rs = documentRepository.save(document);
		if (document.getClient() != null && document.getInfoClient() != null && !document.getInfoClient().isEmpty()) {
			for (Document doc : getDocumentsByClient(1L, false, document.getClient())) {
				doc.setInfoClient(document.getInfoClient());
				documentRepository.save(doc);
			}
		}

		return rs;
	}

	@Override
	public List<Document> findAllDocuments() {
		return documentRepository.findAllDocuments();
	}

	@Override
	public EtatRecouvrement getEtatRecouvrement() {
		// TODO Auto-generated method stub
		return etatRecouvrementRepository.findEtatRecouvrement();
	}

	@Override
	public Collection<Document> getDocumentsByCommercial(Long idEtatFacture, Boolean cloturer, String codeCommercial) {
		// TODO Auto-generated method stub
		return documentRepository.getDocumentsByCommercial(idEtatFacture, cloturer, codeCommercial);
	}

	@Override
	public Collection<Document> getDocumentsByChefProjet(Long idEtatFacture, Boolean cloturer, String codeChefProjet) {
		// TODO Auto-generated method stub
		return documentRepository.getDocumentsByChefProjet(idEtatFacture, cloturer, codeChefProjet);
	}

	@Override
	public Collection<Document> getDocumentsByClient(Long idEtatFacture, Boolean cloturer, String codeClient) {
		// TODO Auto-generated method stub
		return documentRepository.getDocumentsByClient(idEtatFacture, cloturer, codeClient);
	}

	@Override
	@javax.transaction.Transactional
	public void updateDocumentsFromSap(EtatRecouvrement etatRecouvrement) {
		EtatRecouvrement lastEtatRecouvrement = this.etatRecouvrementRepository.findById(1L).orElse(null);
		if (lastEtatRecouvrement != null && lastEtatRecouvrement.getDocuments() != null
				&& !lastEtatRecouvrement.getDocuments().isEmpty()) {
			for (Document document : lastEtatRecouvrement.getDocuments()) {
				document.setNumPieceByTypeDocument(document.getNumPiece() + document.getTypeDocument());
				document.setCloture(true);
			}
		}
		if (lastEtatRecouvrement == null) {

			for (Document p : etatRecouvrement.getDocuments()) {
				p.setCreation(new Date());
				p.setNumPieceByTypeDocument(p.getNumPiece() + p.getTypeDocument());
			}
			etatRecouvrement.setDateCreation(new Date());
			etatRecouvrement.setLastUpdate(new Date());
			this.etatRecouvrementRepository.save(etatRecouvrement);
		} else {
			for (final Document document : etatRecouvrement.getDocuments()) {
				// document.setCloture(false);
				document.setNumPieceByTypeDocument(document.getNumPiece() + document.getTypeDocument());
				document.setCodePiece(document.getNumPieceByTypeDocument());
				this.addOrUpdateDocument(lastEtatRecouvrement, document);
			}

			lastEtatRecouvrement.setLastUpdate(new Date());
			this.etatRecouvrementRepository.save(lastEtatRecouvrement);
		}
	}

	@Override
	public Collection<Document> getDocumentsByCodeProjet(Long idEtatFacture, Boolean cloturer, String codeProjet) {
		// TODO Auto-generated method stub
		return documentRepository.getDocumentsByCodeProjet(idEtatFacture, cloturer, codeProjet);
	}

	@Override
	public Collection<Document> getDocumentsByChargeRecouvrement(Long idEtatFacture, Boolean cloturer,
			String chargeRecouvrement) {
		// TODO Auto-generated method stub
		return documentRepository.getDocumentsByChargeRecouvrement(idEtatFacture, cloturer, chargeRecouvrement);
	}

	@Override
	public Collection<Document> getDocumentsByCommercialOrChefProjet(Long idEtatFacture, Boolean cloturer,
			String commercialOrChefProjet) {
		// TODO Auto-generated method stub
		return documentRepository.getDocumentsByCommercialOrChefProjet(idEtatFacture, cloturer, commercialOrChefProjet);
	}

	@Override
	public List<String> getDistinctAnneePiece() {
		// TODO Auto-generated method stub
		return documentRepository.getDistinctAnneePiece();
	}

	@Override
	public List<String> getDistinctClient() {
		// TODO Auto-generated method stub
		return documentRepository.getDistinctClient();
	}

	@Override
	public List<String> getDistinctCommercial() {
		// TODO Auto-generated method stub
		return documentRepository.getDistinctCommercial();
	}

	@Override
	public List<String> getDistinctChefProjet() {
		// TODO Auto-generated method stub
		return documentRepository.getDistinctChefProjet();
	}

	@Override
	public Resource getReleveClient(String client) {
		// TODO Auto-generated method stub
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

		String nameFile = "releve_" + client + "_" + localDate.format(formatter) + ".pdf";
		String outPutFile = "rapport/" + nameFile;
		List<Document> documentsClient = documentRepository
				.findAll(DocumentSpecification.isCloture(false).and(DocumentSpecification.byClient(client)));

		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(documentsClient);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("collectionDataSource", jrBeanCollectionDataSource);
		parameters.put("client", client);
		FileOutputStream fileOutputStream = null;
		InputStream inputS = null;
		try {
			inputS = new FileInputStream("rapport/releveClient.jrxml");
			JasperDesign jD = JRXmlLoader.load(inputS);
			JasperReport jasperReport = JasperCompileManager.compileReport(jD);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			/* Create PDF */

			fileOutputStream = new FileOutputStream(new File(outPutFile));

			JasperExportManager.exportReportToPdfStream(jasperPrint, fileOutputStream);

			return loadFile(nameFile);

		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			logger.info("error file or JRE " + e.getMessage());
		} finally {
			try {
				if (fileOutputStream != null)
					fileOutputStream.close();
				if (inputS != null) {
					inputS.close();
				}
			} catch (IOException e) {
				logger.info("error closing fileInputStream " + e.getMessage());
			}
		}

		return null;
	}

	private final Path rootLocation2 = Paths.get("rapport");

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation2.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public List<DocumentAnalyse> getCountSumDocumentsByClient(Boolean cloturer, String client) {
		// TODO Auto-generated method stub
		return documentRepository.getCountSumDocumentsByClient(cloturer, client);
	}

	@Override
	public Collection<Document> getDocumentByClientOnDate(Boolean cloturer, String client, int month, int year) {
		// TODO Auto-generated method stub
		return documentRepository.getDocumentByClientOnDate(cloturer, client, month, year);
	}

	@Override
	public Resource getSituationDocumentsByClient(Boolean cloturer, String client) {
		// TODO Auto-generated method stub
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

		String nameFile = "releveStatut_" + client + "_" + localDate.format(formatter) + ".pdf";
		String outPutFile = "rapport/" + nameFile;
		List<DocumentAnalyse> documentsClientAnalyse = documentRepository.getCountSumDocumentsByClient(false, client);

		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(documentsClientAnalyse);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("collectionDataSource", jrBeanCollectionDataSource);
		parameters.put("client", client);
		FileOutputStream fileOutputStream = null;
		InputStream inputS = null;
		try {
			inputS = new FileInputStream("rapport/situationByStatut.jrxml");
			JasperDesign jD = JRXmlLoader.load(inputS);
			JasperReport jasperReport = JasperCompileManager.compileReport(jD);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			/* Create PDF */

			fileOutputStream = new FileOutputStream(new File(outPutFile));

			JasperExportManager.exportReportToPdfStream(jasperPrint, fileOutputStream);

			return loadFile(nameFile);

		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("error " + e.getMessage());
		} finally {
			try {
				if (fileOutputStream != null)
					fileOutputStream.close();
				if (inputS != null) {
					inputS.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("error " + e.getMessage());
			}
		}

		return null;
	}

	@Override
	public Resource getSituationDocumentsByClient(Boolean cloturer, String client, int month, int year) {
		// TODO Auto-generated method stub
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

		String nameFile = "releveSituation_" + client + "_" + localDate.format(formatter) + ".pdf";
		String outPutFile = "rapport/" + nameFile;
		Collection<Document> documentsClient = documentRepository.getDocumentByClientOnDate(cloturer, client, month,
				year);

		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(documentsClient);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("collectionDataSource", jrBeanCollectionDataSource);
		parameters.put("client", client);
		parameters.put("Mois", month + "/" + year);
		FileOutputStream fileOutputStream = null;
		InputStream inputS = null;
		try {
			inputS = new FileInputStream("rapport/situationEncaissement.jrxml");
			JasperDesign jD = JRXmlLoader.load(inputS);
			JasperReport jasperReport = JasperCompileManager.compileReport(jD);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			/* Create PDF */

			fileOutputStream = new FileOutputStream(new File(outPutFile));

			JasperExportManager.exportReportToPdfStream(jasperPrint, fileOutputStream);

			return loadFile(nameFile);

		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("error " + e.getMessage());
		} finally {
			try {
				if (fileOutputStream != null)
					fileOutputStream.close();
				if (inputS != null) {
					inputS.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("error " + e.getMessage());
			}
		}

		return null;
	}

	@Override
	public Collection<EtatEncaissement> getEncaissementNextMonth() {
		return documentRepository.getEncaissemenForNextMonths();
	}

	@Override
	public Resource getRapportEncaissementNextMonths() {
		// TODO Auto-generated method stub
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

		String nameFile = "EtatEncaissementsPrevu_" + localDate.format(formatter) + ".pdf";
		String outPutFile = "rapport/" + nameFile;
		Collection<EtatEncaissement> etatEncaisements = getEncaissementNextMonth();

		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(etatEncaisements);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("collectionDataSource", jrBeanCollectionDataSource);
		parameters.put("objet", "Etat d'encaissement pour les 4 prochains mois");

		FileOutputStream fileOutputStream = null;
		InputStream inputS = null;
		try {
			inputS = new FileInputStream("rapport/situationEncaissementClient.jrxml");
			JasperDesign jD = JRXmlLoader.load(inputS);
			JasperReport jasperReport = JasperCompileManager.compileReport(jD);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			/* Create PDF */

			fileOutputStream = new FileOutputStream(new File(outPutFile));

			JasperExportManager.exportReportToPdfStream(jasperPrint, fileOutputStream);

			return loadFile(nameFile);

		} catch (FileNotFoundException | JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("error " + e.getMessage());
		} finally {
			try {
				if (fileOutputStream != null)
					fileOutputStream.close();
				if (inputS != null) {
					inputS.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("error " + e.getMessage());
			}
		}

		return null;
	}

	@Override
	public Collection<Document> getDocumentsByNumDocument(Long idEtatFacture, Boolean cloturer, String numDocument) {
		// TODO Auto-generated method stub
		
		return documentRepository.findAll(DocumentSpecification.byNumDocument(numDocument));
	}

	@Override
	public Collection<Document> getDocumentsByCodeProjet(String codeProjet) {
		// TODO Auto-generated method stub
		return documentRepository.getDocumentsByCodeProjet(codeProjet);
	}
}
