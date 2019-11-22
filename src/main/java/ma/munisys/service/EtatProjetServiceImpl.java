package ma.munisys.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.sap.db.jdbcext.wrapper.ResultSetMetaData;

import ma.munisys.dao.EtatProjetRepository;
import ma.munisys.dao.ProjetRepository;
import ma.munisys.dao.ServiceRepository;
import ma.munisys.entities.Detail;
import ma.munisys.entities.Employer;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Header;
import ma.munisys.entities.Projet;
import ma.munisys.entities.Service;
import ma.munisys.sap.dao.DBA;
import ma.munisys.utils.Constants;

@org.springframework.stereotype.Service // spring pas javax
@Transactional
public class EtatProjetServiceImpl implements EtatProjetService {

	private static final Logger logger = LoggerFactory.getLogger(EtatProjetServiceImpl.class);

	@Autowired
	private ProjetRepository projetRepository;

	public Collection<Projet> getProjetsByBuAndCommercial(Boolean cloturer, String bu1, String bu2, String commercial) {
		return projetRepository.getProjetsByBuAndCommercial(cloturer, bu1, bu2, commercial);
	}

	public Collection<Projet> getProjetsByBuAndChefProjet(Boolean cloturer, String bu1, String bu2, String chefProjet) {
		return projetRepository.getProjetsByBuAndChefProjet(cloturer, bu1, bu2, chefProjet);
	}

	public Collection<Projet> getProjetsByStatutAndCommercial(Boolean cloturer, String statut, String commercial) {
		return projetRepository.getProjetsByStatutAndCommercial(cloturer, statut, commercial);
	}

	public Collection<Projet> getProjetsByStatutAndChefProjet(Boolean cloturer, String statut, String chefProjet) {
		return projetRepository.getProjetsByStatutAndChefProjet(cloturer, statut, chefProjet);
	}

	public Collection<Projet> getProjetsByBuAndStatutAndCommercial(Boolean cloturer, String bu1, String bu2,
			String statut, String commercial) {
		return projetRepository.getProjetsByBuAndStatutAndCommercial(cloturer, bu1, bu2, statut, commercial);
	}

	public Collection<Projet> getProjetsByBuAndStatutAndChefProjet(Boolean cloturer, String bu1, String bu2,
			String statut, String chefProjet) {
		return projetRepository.getProjetsByBuAndStatutAndchefProjet(cloturer, bu1, bu2, statut, chefProjet);
	}

	public Collection<Projet> getAllProjetsByCommercialOrChefProjet(Boolean cloturer, String commercialOrChefProjet) {
		return projetRepository.getAllProjetsByCommercialOrChefProjet(cloturer, commercialOrChefProjet);
	}

	@Autowired
	private EtatProjetRepository etatProjetRepository;

	private final Path rootLocation = Paths.get("upload-dir");
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private EmployeService employeService;

	/*
	 * @Override public void checkIfProjetClotured(Projet projet) {
	 * 
	 * if(projet.getRal() == 0 && projet.getLnf() == 0 &&
	 * projet.getLfp()==projet.getLiv()) { projet.setCloture(true); }
	 * 
	 * projet.setCloture(true); }
	 */

	@Override
	public Page<Projet> getProjetsFromEtatProjet(Boolean cloture, int page, int size) {
		// TODO Auto-generated method stub
		return projetRepository.getProjets( cloture, new PageRequest(page, size));
	}

	@Override
	public Set<Projet> getProjetsFromInputFile(String fileName) {
		// TODO Auto-generated method stub

		System.out.println("filename " + fileName);
		logger.debug(" Input File  " + fileName.toString());

		Set<Projet> projets = new HashSet<>();

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
			EtatProjet newEtatProjet = new EtatProjet();
			newEtatProjet.setId(1L);
			List<Header> headers = new ArrayList<Header>();

			while (rowIterator.hasNext()) {
				Projet p = new Projet();

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
						newEtatProjet.addHeader(header);
						headers.add(header);
						System.out.println("row header contain " + cellValue);
					} else {
						System.out.println("row Collone contain " + cellValue);

						Detail detail = new Detail();
						detail.setHeader(headers.get(cell.getColumnIndex()));
						detail.setValue(cellValue);
						p.addDetail(detail);
						newEtatProjet.addProjet(p);
						// p.getProperties().put(etatProjet.getHeader().get(cell.getColumnIndex()),
						// cellValue);

					}

				}

				System.out.println();
			}

			System.out.println("new2 EtatProjet " + newEtatProjet);
			addOrUpdateEtatProjet(newEtatProjet);

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			logger.debug("Encrypted document !" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("InvalidFormatException document !" + e.getMessage());
			e.printStackTrace();
		}

		//
		return projets;

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
	public void addOrUpdateEtatProjet(EtatProjet newEtatProjet) {
		boolean firstCreation = false;
		System.out.println("adorUpdate");
		EtatProjet lastEtatProjet = etatProjetRepository.findById(1L).orElse(null);

		if (lastEtatProjet != null && lastEtatProjet.getProjets() != null && !lastEtatProjet.getProjets().isEmpty()) {
			for (Projet projet : lastEtatProjet.getProjets()) {
				projet.setCloture(true);
			}
		}

		// checking projet header
		// checkRequiredHidders(newEtatProjet);

		if (lastEtatProjet == null) {
			System.out.println("new projets " +newEtatProjet.getProjets());
			// create date projet
			for(Projet p : newEtatProjet.getProjets() ) {
				p.setCreation(new Date());
				System.out.println("codeProjet " + p.getCodeProjet());
				
				/*Employer commercial= checkAndAddEmployer(p.getCodeCommercial(),p.getNomCommercial(),"Commercial");
				System.out.println("commercial " + commercial);
				p.setCommercial(commercial);*/
				
				
				//p.setLastUpdate(new Date());
			}
			newEtatProjet.setDateCreation(new Date());
			newEtatProjet.setLastUpdate(new Date());
			etatProjetRepository.save(newEtatProjet);
		} else {
			System.out.println("here");
			boolean found = false;
			System.out.println("lastEtatProjet " + lastEtatProjet.toString());
			System.out.println("newEtatProjet " + newEtatProjet.toString());
			/*
			List<Header> headerToDelete = new ArrayList<Header>();

			List<Header> headerToAdd = new ArrayList<Header>();

			for (Header lastHeader : lastEtatProjet.getHeaders()) {

				found = false;
				for (Header newHeader : newEtatProjet.getHeaders()) {

					if (lastHeader.getLabel().equals(newHeader.getLabel())) {
						found = true;
					}
				}
				if (!found) {
					System.out.println("delete head " + lastHeader.getLabel());
					headerToDelete.add(new Header(lastHeader.getLabel()));
					// lastEtatProjet.getHeaders().remove(lastHeader);
				}
			}

			for (Header header : headerToDelete) {
				deleteHeaderFromEtatProjet(1L, header);
			}

			

			for (Header newheader : newEtatProjet.getHeaders()) {
				found = false;
				for (Header lastHeader : lastEtatProjet.getHeaders()) {
					if (newheader.getLabel().equals(lastHeader.getLabel())) {
						found = true;
					}
				}
				if (!found) {
					System.out.println("adding header " + newheader.getLabel());
					headerToAdd.add(new Header(newheader.getLabel().replace("% ", "")));
				}

			}

			for (Header header : headerToAdd) {
				lastEtatProjet.addHeader(header);
			}*/

			// System.out.println("lastEtatProjet header " + lastEtatProjet.getHeaders());

			for (Projet projet : newEtatProjet.getProjets()) {
				projet.setCloture(false);
				
				addOrUpdateProjet(lastEtatProjet, projet);
			}

			System.out.println("lastEtatProjet to update " + lastEtatProjet);
			lastEtatProjet.setLastUpdate(new Date());
			etatProjetRepository.save(lastEtatProjet);
		}

	}

	@Override
	@javax.transaction.Transactional
	public void addOrUpdateProjet(EtatProjet lastEtatProjet, Projet projet) {

		Projet lastProjet = findProjetFromEtatProjet(lastEtatProjet, projet.getCodeProjet());
		
		/*if(projet.getCodeCommercial()!=null && !projet.getCodeCommercial().isEmpty() &&
				projet.getNomCommercial()!=null && !projet.getNomCommercial().isEmpty()	) {
			Employer commercial= checkAndAddEmployer(projet.getCodeCommercial(),projet.getNomCommercial(),"Commercial");	
			System.out.println("not equals commercial");
			System.out.println("newCom " + commercial);
			
			projet.setCommercial(commercial);	
		}*/
		if (lastProjet != null) {
			projet.setCommentaireDirection(lastProjet.getCommentaireDirection());
			projet.setCommentaires(lastProjet.getCommentaires());
			projet.setAction(lastProjet.getAction());
			projet.setDateFinProjet(lastProjet.getDateFinProjet());
			projet.setCondPaiement(lastProjet.getCondPaiement());
			projet.setGarantie(lastProjet.getGarantie());
			projet.setDesignProjet(lastProjet.getDesignProjet());
			projet.setDateFinProjet(lastProjet.getDateFinProjet());
			projet.setCreation(lastProjet.getCreation());
			projet.setMaintenance(lastProjet.getMaintenance());
			projet.setLivraison(lastProjet.getLivraison());
			projet.setPreRequis(lastProjet.getPreRequis());
			projet.setLastUpdate(new Date());
			projet.setIntervenantPrincipal(lastProjet.getIntervenantPrincipal());
			projet.setAvantVente(lastProjet.getAvantVente());
			projet.setSyntheseProjet(lastProjet.getSyntheseProjet());
			projet.setRisque(lastProjet.getRisque());
			projet.setSuivre(lastProjet.getSuivre());
			projet.setPerimetreProjet(lastProjet.getPerimetreProjet());
			projet.setInfoClient(lastProjet.getInfoClient());
			projet.setInfoFournisseur(lastProjet.getInfoFournisseur());
			projet.setInfoProjet(lastProjet.getInfoProjet());
					
			lastEtatProjet.getProjets().remove(lastProjet);
			System.out.println("lastEtatProjet.getProjets() " + lastEtatProjet.getProjets());
			System.out.println("projet comm must be " + projet.getCommercial());
			lastEtatProjet.addProjet(projet);
		} else {
			projet.setCreation(new Date());
			projet.setSuivre(false);
			lastEtatProjet.addProjet(projet);
		}

	}

	@Override
	public Projet findProjetFromEtatProjet(EtatProjet etatProjet, String codeProjet) {

		for (Projet p : etatProjet.getProjets()) {
			if (p.getCodeProjet().equals(codeProjet)) {
				return p;
			}
		}
		return null;

	}

	@Override
	@javax.transaction.Transactional
	public void deleteHeaderFromEtatProjet(Long idEtatProjet, Header header) {
		EtatProjet etatProjet = etatProjetRepository.getOne(1L);

		List<Detail> detailsToRemove = new ArrayList<Detail>();

		/*for (Projet p : etatProjet.getProjets()) {
			for (Detail d : p.getDetails()) {
				if (d.getHeader().getLabel().equals(header.getLabel())) {
					detailsToRemove.add(d);

				}
			}
		}

		for (Projet p : etatProjet.getProjets()) {

			for (Detail d1 : detailsToRemove) {
				p.getDetails().remove(d1);
			}

		}*/

		//etatProjet.getHeaders().removeIf(h -> h.getLabel().equals(header.getLabel()));

	}

	public void checkRequiredHidders(EtatProjet etatProjet) {

		System.out.println("required headers " + Constants.getRequiredHeaders());
		List<String> requiredHeaders = Constants.getRequiredHeaders();
		boolean found = false;
		for (String requiredHeader : requiredHeaders) {
			found = false;
			for (Header header : etatProjet.getHeaders()) {
				if (header.getLabel().equals(requiredHeader)) {
					found = true;
				}
			}

			if (!found) {
				throw new RuntimeException("the header name " + requiredHeader + " is required on the list of headers");
			}
		}
	}

	@Override
	public Collection<Projet> getProjetFromEtatProjet(Boolean cloture,String bu1,String bu2) {
		return projetRepository.getProjetsByBu( cloture,bu1,bu2);
	}

	@Override
	public Projet updateProjet(String idProjet, Projet projet) {

		projet.setCodeProjet(idProjet);
		return projetRepository.save(projet);
	}

	@Override
	public List<Projet> findAllProjetsByDateSup(Boolean cloturer, Date dateCmd) {
		
		return projetRepository.findAllProjetsByDateSup(cloturer,dateCmd);
	}
	
	@Override
	public List<Projet> findAllProjetsByDateInf(Boolean cloturer, Date dateCmd) {
		
		return projetRepository.findAllProjetsByDateInf(cloturer,dateCmd);
	}
	
	public List<Projet> findAllProjet(){
		return projetRepository.findAllProjetsByDate();
	}

	@Override
	public Double getTotalLnf() {
		Double totalLnf = 0.0;
		
		
		String sDate1="31/12/1960";  
	    Date d = null;
		try {
			d = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
		for(Projet p : findAllProjetsByDateSup(false, d)) {
			totalLnf = totalLnf + p.getLivrerNonFacture();
		}
		
		return totalLnf;
	}

	@Override
	public Double getTotalRal() {
		// TODO Auto-generated method stub
		Double totalRal = 0.0;

		String sDate1="31/12/1960";  
	    Date d = null;
		try {
			d = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
		for(Projet p : findAllProjetsByDateSup(false,  d)) {
			totalRal = totalRal + p.getRestAlivrer();
		}
		
		return totalRal;
	}

	@Override
	public Double getTotalRalPlusLnfBeforeSevenMonth() {
		Double totalRal = 0.0;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -6);
		Date result = cal.getTime();
		
		for(Projet p : findAllProjetsByDateInf(false,  result)) {
			totalRal = totalRal + p.getLivrerNonFacture();
		}
		
		return totalRal;
	}

	@Override
	public EtatProjet getEtatProjet() {
		return etatProjetRepository.findEtatProjet();
		
	}

	public EmployeService getEmployeService() {
		return employeService;
	}

	public void setEmployeService(EmployeService employeService) {
		this.employeService = employeService;
	}

	public ServiceRepository getServiceRepository() {
		return serviceRepository;
	}

	public void setServiceRepository(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}
	
	private Employer checkAndAddEmployer(String codeEmployer,String nomEmployer, String serviceName) {
		
		if(employeService.getEmployer(codeEmployer)==null &&  codeEmployer!=null && !codeEmployer.isEmpty()) {
			Employer employer = new Employer();
			employer.setCode(codeEmployer);
			employer.setName(nomEmployer);
			Service service = serviceRepository.getService(serviceName);
			employer.setService(service);
			employeService.saveEmployer(employer);
			return employer;
		}else {
			
			return employeService.getEmployer(codeEmployer);	
		}
	}

	@Override
	public Collection<Projet> getProjets(Boolean cloturer) {
		// TODO Auto-generated method stub
		return projetRepository.getProjets( cloturer);
	}
	
	@javax.transaction.Transactional
	 public void updateProjetsFromSAp(EtatProjet newEtatProjet) {
	        final EtatProjet lastEtatProjet = this.etatProjetRepository.findById(1L).orElse(null);
	        if (lastEtatProjet != null && lastEtatProjet.getProjets() != null && !lastEtatProjet.getProjets().isEmpty()) {
	            for (final Projet projet : lastEtatProjet.getProjets()) {
	                projet.setCloture(true);
	            }
	        }
	        if (lastEtatProjet == null) {
	            System.out.println("new projets " + newEtatProjet.getProjets());
	            for (final Projet p : newEtatProjet.getProjets()) {
	                p.setCreation(new Date());
	            }
	            newEtatProjet.setDateCreation(new Date());
	            newEtatProjet.setLastUpdate(new Date());
	            this.etatProjetRepository.save(newEtatProjet);
	        }
	        else {
	            System.out.println("here");
	            final boolean found = false;
	            System.out.println("lastEtatProjet " + lastEtatProjet.toString());
	            System.out.println("newEtatProjet " + newEtatProjet.toString());
	            for (final Projet projet2 : newEtatProjet.getProjets()) {
	                projet2.setCloture(false);
	                this.addOrUpdateProjet(lastEtatProjet, projet2);
	            }
	            System.out.println("lastEtatProjet to update " + lastEtatProjet);
	            lastEtatProjet.setLastUpdate(new Date());
	            this.etatProjetRepository.save(lastEtatProjet);
	        }
	    }

	@Override
	public Collection<Projet> getAllProjets() {
		// TODO Auto-generated method stub
		return projetRepository.getAllProjets();
	}
	
	@Override
	public  void loadProjetsFromSap() {
    	System.out.println("load projet from SAP");
         Set<Projet> projets = new HashSet<Projet>();
         EtatProjet etatProjet = new EtatProjet();
        etatProjet.setId(Long.valueOf(1L));
        etatProjet.setLastUpdate(new Date());
        try {
            final String req1 = "SELECT * FROM DB_MUNISYS.\"V_OPEN_PRJ\"";
            final ResultSet rs1 = DBA.request(req1);
            final java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
            for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
                final String name = rsmd.getColumnName(i);
                System.out.println("column Name " + name);
            }
            while (rs1.next()) {
                final Projet p = new Projet();
                if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {
                    p.setCodeProjet(rs1.getString(1));
                }
                if (rs1.getString(2) != null && !rs1.getString(2).equals("null")) {
                    p.setProjet(rs1.getString(2));
                }
                if (rs1.getString(3) != null && !rs1.getString(3).equals("null")) {
                	System.out.println("codeProjet "+ rs1.getString(1)+ " dateCMD "+rs1.getString(3));
                     SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                    p.setDateCmd(sp.parse(rs1.getString(3).split("\\s+")[0]));
                }
                if (rs1.getString(4) != null && !rs1.getString(4).equals("null")) {
                    p.setAge(Double.valueOf(rs1.getDouble(4)));
                }
                if (rs1.getString(5) != null && !rs1.getString(5).equals("null")) {
                    p.setCodeClient(rs1.getString(5));
                }
                if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {
                    p.setClient(rs1.getString(6));
                }
                if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {
                    p.setCodeCommercial(rs1.getString(7));
                }
                if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
                    p.setCommercial(rs1.getString(8));
                }
                if (rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
                    p.setChefProjet(rs1.getString(9));
                }
                if (rs1.getString(10) != null && !rs1.getString(10).equals("null")) {
                    p.setBu(rs1.getString(10));
                }
                if (rs1.getString(11) != null && !rs1.getString(11).equals("null")) {
                    p.setRefCom(rs1.getString(11));
                }
                if (rs1.getString(12) != null && !rs1.getString(12).equals("null")) {
                    p.setStatut(rs1.getString(12));
                }
                if (rs1.getString(13) != null && !rs1.getString(13).equals("null")) {
                    p.setMontantCmd(Double.valueOf(rs1.getDouble(13)));
                }
                if (rs1.getString(14) != null && !rs1.getString(14).equals("null")) {
                    p.setRestAlivrer(Double.valueOf(rs1.getDouble(14)));
                }
                if (rs1.getString(15) != null && !rs1.getString(15).equals("null")) {
                    p.setLivrer(Double.valueOf(rs1.getDouble(15)));
                }
                if (rs1.getString(16) != null && !rs1.getString(16).equals("null")) {
                    p.setLivrerNonFacture(Double.valueOf(rs1.getDouble(16)));
                }
                if (rs1.getString(17) != null && !rs1.getString(17).equals("null")) {
                    p.setLivreFacturePayer(Double.valueOf(rs1.getDouble(17)));
                }
                if (rs1.getString(18) != null && !rs1.getString(18).equals("null")) {
                    p.setMontantPayer(Double.valueOf(rs1.getDouble(18)));
                }
                if (rs1.getString(19) != null && !rs1.getString(19).equals("null")) {
                    p.setFacturation(rs1.getDouble(19));
                }
                if (rs1.getString(20) != null && !rs1.getString(20).equals("null")) {
                    p.setFactEncours(rs1.getString(20));
                }
                if (rs1.getString(21) != null && !rs1.getString(21).equals("null")) {
                    p.setMontantStock(Double.valueOf(rs1.getDouble(21)));
                }
                if (rs1.getString(22) != null && !rs1.getString(22).equals("null")) {
                    p.setPrestationCommande(Double.valueOf(rs1.getDouble(22)));
                }
                if (rs1.getString(23) != null && !rs1.getString(23).equals("null")) {
                    p.setRalJrsPrestCalc(Double.valueOf(rs1.getDouble(23)));
                }
                
                p.setEtatProjet(etatProjet);
                projets.add(p);
                
            }
           // System.out.println("projets " + projets.size());
            etatProjet.setProjets(projets);
            updateProjetsFromSAp(etatProjet);
            
            //System.out.println("projets1 " + etatProjetServiceStatic.getProjetFromEtatProjet( false, "undefined"));
            
            //System.out.println("projets2 " + etatProjetServiceStatic.getAllProjets());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBuAndStatut(Boolean cloturer, String bu1, String bu2,
			String statut1) {
		return projetRepository.getProjetsByChefDeProjetNotNullAndBuAndStatut(cloturer, bu1, bu2, statut1);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBuAndStatut(Boolean cloturer, String bu1, String bu2,
			String statut1) {
		return projetRepository.getProjetsByChefDeProjetIsNullAndBuAndStatut(cloturer, bu1, bu2, statut1);
	}

	@Override
	public Collection<Projet> getProjetFromEtatProjet(Boolean cloture, String bu1, String bu2, String statut1) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByBuAndStatut( cloture,  bu1,  bu2,  statut1);
	}

	@Override
	public Collection<Projet> getProjetsByBu(Boolean cloturer, String bu1, String bu2) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByBu(cloturer, bu1, bu2);
	}

	@Override
	public Collection<Projet> getProjetsByStatut(Boolean cloturer, String statut) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByStatut(cloturer, statut);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetIsNull(Boolean cloturer) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByChefDeProjetIsNull(cloturer);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetNotNull(Boolean cloturer) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByChefDeProjetNotNull(cloturer);
	}

	@Override
	public Collection<Projet> getProjetsByBuAndStatut(Boolean cloturer, String bu1, String bu2, String statut
			) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByBuAndStatut(cloturer, bu1, bu2, statut);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBu(Boolean cloturer, String bu1, String bu2
			) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByChefDeProjetNotNullAndBu(cloturer, bu1, bu2);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBu(Boolean cloturer, String bu,String bu2) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByChefDeProjetIsNullAndBu(cloturer, bu,bu2);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndStatut(Boolean cloturer, String statut1) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByChefDeProjetIsNullAndStatut(cloturer, statut1);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndStatut(Boolean cloturer, String statut1) {
		// TODO Auto-generated method stub
		return projetRepository.getProjetsByChefDeProjetNotNullAndStatut(cloturer, statut1);
	}

	
	
	
	
	

}
