package ma.munisys.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.munisys.dao.EtatRecouvrementRepository;
import ma.munisys.dao.DocumentRepository;
import ma.munisys.entities.Detail;
import ma.munisys.entities.EtatRecouvrement;
import ma.munisys.entities.Header;
import ma.munisys.entities.Document;
import ma.munisys.utils.Constants;

@Service // spring pas javax
@Transactional
public class EtatRecouvrementServiceImpl implements EtatRecouvrementService {

	private static final Logger logger = LoggerFactory.getLogger(EtatRecouvrementServiceImpl.class);

	@Autowired
	private DocumentRepository documentRepository;

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
	public Page<Document> getDocumentsFromEtatRecouvrement(Long idEtatRecouvrement, Boolean cloture, int page, int size) {
		// TODO Auto-generated method stub
		return documentRepository.getDocuments(idEtatRecouvrement, cloture, new PageRequest(page, size));
	}

	@Override
	public Set<Document> getDocumentsFromInputFile(String fileName) {
		// TODO Auto-generated method stub

		System.out.println("filename " + fileName);
		logger.debug(" Input File  " + fileName.toString());

		Set<Document> documents = new HashSet<>();

		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(new File(rootLocation.toString() + "/" + fileName));

			System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
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

				System.out.println("row  " + row.getRowNum());

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
						System.out.println("row header contain " + cellValue);
					} else {
						System.out.println("row Collone contain " + cellValue);

						Detail detail = new Detail();
						detail.setHeader(headers.get(cell.getColumnIndex()));
						detail.setValue(cellValue);
						p.addDetail(detail);
						newEtatRecouvrement.addDocument(p);
						// p.getProperties().put(EtatRecouvrement.getHeader().get(cell.getColumnIndex()),
						// cellValue);

					}

				}

				System.out.println();
			}

			System.out.println("new EtatRecouvrement " + newEtatRecouvrement);
			addOrUpdateEtatRecouvrement(newEtatRecouvrement);

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			logger.debug("Encrypted document !" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("InvalidFormatException document !" + e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		//
		return documents;

		/*
		 * for(int i=1; i<=lastRow;i++) { CtrctCustomer ctrctCustomer = new
		 * CtrctCustomer(); System.out.println("id " +
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
			System.out.print(cell.getBooleanCellValue());
			break;
		case STRING:
			System.out.print(cell.getRichStringCellValue().getString());
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				System.out.print(cell.getDateCellValue());
			} else {
				System.out.print(cell.getNumericCellValue());
			}
			break;
		case FORMULA:
			System.out.print(cell.getCellFormula());
			break;
		case BLANK:
			System.out.print("");
			break;
		default:
			System.out.print("");
		}

		System.out.print("\t");
	}

	@Override
	@javax.transaction.Transactional
	public void addOrUpdateEtatRecouvrement(EtatRecouvrement newEtatRecouvrement) {
		boolean firstCreation = false;
		System.out.println("adorUpdate");
		EtatRecouvrement lastEtatRecouvrement = etatRecouvrementRepository.findById(1L).orElse(null);

		if (lastEtatRecouvrement != null && lastEtatRecouvrement.getDocuments() != null && !lastEtatRecouvrement.getDocuments().isEmpty()) {
			for (Document document : lastEtatRecouvrement.getDocuments()) {
				document.setCloture(true);
			}
		}

		// checking document header
		// checkRequiredHidders(newEtatRecouvrement);

		if (lastEtatRecouvrement == null) {
			
			// create date document
			for(Document p : newEtatRecouvrement.getDocuments() ) {
				p.setCreation(new Date());
				//p.setLastUpdate(new Date());
			}
			newEtatRecouvrement.setDateCreation(new Date());
			etatRecouvrementRepository.save(newEtatRecouvrement);
		} else {
			System.out.println("here");
			boolean found = false;
			System.out.println("lastEtatRecouvrement " + lastEtatRecouvrement.toString());
			System.out.println("newEtatRecouvrement " + newEtatRecouvrement.toString());

			List<Header> headerToDelete = new ArrayList<Header>();

			List<Header> headerToAdd = new ArrayList<Header>();

			/*for (Header lastHeader : lastEtatRecouvrement.getHeaders()) {

				found = false;
				for (Header newHeader : newEtatRecouvrement.getHeaders()) {

					if (lastHeader.getLabel().equals(newHeader.getLabel())) {
						found = true;
					}
				}
				if (!found) {
					System.out.println("delete head " + lastHeader.getLabel());
					headerToDelete.add(new Header(lastHeader.getLabel()));
					// lastEtatRecouvrement.getHeaders().remove(lastHeader);
				}
			}

			for (Header header : headerToDelete) {
				deleteHeaderFromEtatRecouvrement(1L, header);
			}*/

			/*
			 * for (Iterator<Header> iterator = lastEtatRecouvrement.getHeaders().iterator();
			 * iterator.hasNext(); ) { Header lastHeader = iterator.next(); found = false;
			 * for (Header newHeader : newEtatRecouvrement.getHeaders()) {
			 * 
			 * if (lastHeader.getLabel().equals(newHeader.getLabel())) { found = true; } }
			 * if (!found) { System.out.println("delete head " + lastHeader.getLabel());
			 * deleteHeaderFromEtatRecouvrement(1L, lastHeader); //iterator.remove();
			 * //lastEtatRecouvrement.getHeaders().remove(lastHeader); }
			 * 
			 * 
			 * }
			 */

			/*for (Header newheader : newEtatRecouvrement.getHeaders()) {
				found = false;
				for (Header lastHeader : lastEtatRecouvrement.getHeaders()) {
					if (newheader.getLabel().equals(lastHeader.getLabel())) {
						found = true;
					}
				}
				if (!found) {
					System.out.println("adding header " + newheader.getLabel());
					headerToAdd.add(new Header(newheader.getLabel()));
				}

			}

			for (Header header : headerToAdd) {
				lastEtatRecouvrement.addHeader(header);
			}*/

			// System.out.println("lastEtatRecouvrement header " + lastEtatRecouvrement.getHeaders());

			for (Document document : newEtatRecouvrement.getDocuments()) {
				document.setCloture(false);
				addOrUpdateDocument(lastEtatRecouvrement, document);
			}

			System.out.println("lastEtatRecouvrement to update " + lastEtatRecouvrement);
			lastEtatRecouvrement.setLastUpdate(new Date());
			etatRecouvrementRepository.saveAndFlush(lastEtatRecouvrement);
		}

	}

	@Override
	@javax.transaction.Transactional
	public void addOrUpdateDocument(EtatRecouvrement lastEtatRecouvrement, Document document) {

		Document lastDocument = findDocumentFromEtatRecouvrement(lastEtatRecouvrement, document.getNumPieceByTypeDocument());
		if (lastDocument != null) {
			//document.setCodePiece(lastDocument.getCodePiece());
			document.setStatut(lastDocument.getStatut());
			document.setMotif(lastDocument.getMotif());
			document.setMontantGarantie(lastDocument.getMontantGarantie());
			document.setMontantProvision(lastDocument.getMontantProvision());
			document.setDateFinGarantie(lastDocument.getDateFinGarantie());
			document.setDatePrevuEncaissement(lastDocument.getDatePrevuEncaissement());
			document.setDureeGarantie(lastDocument.getDureeGarantie());
			document.setAction(lastDocument.getAction());
			document.setResponsable(lastDocument.getResponsable());
			document.setDateDepot(lastDocument.getDateDepot());
			document.setMotifChangementDate(lastDocument.getMotifChangementDate());
			document.setCommentaires(lastDocument.getCommentaires());
			document.setDatePvProvisoire(lastDocument.getDatePvProvisoire()); ;
			document.setDatePrevuReceptionDefinitive(lastDocument.getDatePrevuReceptionDefinitive());
			document.setLastUpdate(new Date());
			document.setInfoChefProjetOrCommercial(lastDocument.getInfoChefProjetOrCommercial());
			document.setInfoClient(lastDocument.getInfoClient());
			document.setInfoProjet(lastDocument.getInfoProjet());
			
			lastEtatRecouvrement.getDocuments().remove(lastDocument);
			System.out.println("document must be " + document);
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

		/*for (Document p : EtatRecouvrement.getDocuments()) {
			for (Detail d : p.getDetails()) {
				if (d.getHeader().getLabel().equals(header.getLabel())) {
					detailsToRemove.add(d);

				}
			}
		}

		for (Document p : EtatRecouvrement.getDocuments()) {

			for (Detail d1 : detailsToRemove) {
				p.getDetails().remove(d1);
			}

		}*/

		//EtatRecouvrement.getHeaders().removeIf(h -> h.getLabel().equals(header.getLabel()));

	}

	public void checkRequiredHidders(EtatRecouvrement EtatRecouvrement) {

		System.out.println("required headers " + Constants.getRequiredHeaders());
		List<String> requiredHeaders = Constants.getRequiredHeaders();
		boolean found = false;
		/*for (String requiredHeader : requiredHeaders) {
			found = false;
			for (Header header : EtatRecouvrement.getHeaders()) {
				if (header.getLabel().equals(requiredHeader)) {
					found = true;
				}
			}

			if (!found) {
				throw new RuntimeException("the header name " + requiredHeader + " is required on the list of headers");
			}
		}*/
	}

	@Override
	public Collection<Document> getDocumentsFromEtatRecouvrement(Long idEtatRecouvrement, Boolean cloture) {
		return documentRepository.getDocuments(idEtatRecouvrement, cloture);
	}

	@Override
	public Document updateDocument(String idDocument, Document document) {

		document.setNumPiece(idDocument);
		return documentRepository.save(document);
	}


	@Override
	public List<Document> findAllDocuments(){
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
        if (lastEtatRecouvrement != null && lastEtatRecouvrement.getDocuments() != null && !lastEtatRecouvrement.getDocuments().isEmpty()) {
            for (Document document : lastEtatRecouvrement.getDocuments()) {
            	document.setNumPieceByTypeDocument(document.getNumPiece()+document.getTypeDocument());
            	document.setCloture(true);
            }
        }
        if (lastEtatRecouvrement == null) {
            
            for ( Document p : etatRecouvrement.getDocuments()) {
                p.setCreation(new Date());
                p.setNumPieceByTypeDocument(p.getNumPiece()+p.getTypeDocument());
            }
            etatRecouvrement.setDateCreation(new Date());
            etatRecouvrement.setLastUpdate(new Date());
            this.etatRecouvrementRepository.save(etatRecouvrement);
        }
        else {
            for (final Document document : etatRecouvrement.getDocuments()) {
                //document.setCloture(false);
            	document.setNumPieceByTypeDocument(document.getNumPiece()+document.getTypeDocument());
                document.setCodePiece(document.getNumPieceByTypeDocument());
            	this.addOrUpdateDocument(lastEtatRecouvrement, document);
            }
            
            lastEtatRecouvrement.setLastUpdate(new Date());
            this.etatRecouvrementRepository.save(lastEtatRecouvrement);
        }
    }

	@Override
	public Collection<Document> getDocumentsByCodeProjet(Long idEtatFacture, Boolean cloturer,
			String codeProjet) {
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

}
