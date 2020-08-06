package ma.munisys.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.map.HashedMap;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ma.munisys.dao.DocumentRepository;
import ma.munisys.dao.EtatProjetRepository;
import ma.munisys.dao.EventRepository;
import ma.munisys.dao.ProjetRepository;
import ma.munisys.dao.ServiceRepository;
import ma.munisys.dao.UserRepository;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Detail;
import ma.munisys.entities.DetailRdv;
import ma.munisys.entities.Document;
import ma.munisys.entities.Employer;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.Event;
import ma.munisys.entities.Header;
import ma.munisys.entities.Projet;
import ma.munisys.entities.Service;
import ma.munisys.sap.dao.DBA;
import ma.munisys.utils.Constants;

@org.springframework.stereotype.Service // spring pas javax
@Transactional
public class EtatProjetServiceImpl implements EtatProjetService {

	private static final Logger logger = LogManager.getLogger(EtatProjetServiceImpl.class);

	@Autowired
	private ProjetRepository projetRepository;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/*public Collection<Projet> Collection<Projet> projets){
		
		
		for(Projet p : projets) {
			
			Double sumMontantOuvert = documentRepository.getMontantStock(p.getCodeProjet());
			if(sumMontantOuvert!=null)
			p.setMontantStock(sumMontantOuvert);
		}
		
		return projets;
		
		
	}*/

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
			String statut, 
			String chefProjet) {
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
		
		return projetRepository.getProjets( cloture, new PageRequest(page, size));
	}

	@Override
	public Set<Projet> getProjetsFromInputFile(String fileName) {
		

		//System.out.println("filename " + fileName);
		logger.debug(" Input File  " + fileName.toString());

		Set<Projet> projets = new HashSet<>();

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
			EtatProjet newEtatProjet = new EtatProjet();
			newEtatProjet.setId(1L);
			List<Header> headers = new ArrayList<Header>();

			while (rowIterator.hasNext()) {
				Projet p = new Projet();

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
						newEtatProjet.addHeader(header);
						headers.add(header);
						//System.out.println("row header contain " + cellValue);
					} else {
						//System.out.println("row Collone contain " + cellValue);

						Detail detail = new Detail();
						detail.setHeader(headers.get(cell.getColumnIndex()));
						detail.setValue(cellValue);
						p.addDetail(detail);
						newEtatProjet.addProjet(p);
						// p.getProperties().put(etatProjet.getHeader().get(cell.getColumnIndex()),
						// cellValue);

					}

				}

				//System.out.println();
			}

			//System.out.println("new2 EtatProjet " + newEtatProjet);
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
			//	System.out.print(cell.getDateCellValue());
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

		System.out.print("\t");
	}

	@Override
	@javax.transaction.Transactional
	public void addOrUpdateEtatProjet(EtatProjet newEtatProjet) {
		boolean firstCreation = false;
		//System.out.println("adorUpdate");
		EtatProjet lastEtatProjet = etatProjetRepository.findById(1L).orElse(null);

		if (lastEtatProjet != null && lastEtatProjet.getProjets() != null && !lastEtatProjet.getProjets().isEmpty()) {
			for (Projet projet : lastEtatProjet.getProjets()) {
				if(!projet.isDecloturedByUser()) {
					projet.setCloture(true);
				}
				
			}
		}

		// checking projet header
		// checkRequiredHidders(newEtatProjet);

		if (lastEtatProjet == null) {
			//System.out.println("new projets " +newEtatProjet.getProjets());
			// create date projet
			for(Projet p : newEtatProjet.getProjets() ) {
				p.setCreation(new Date());
				//System.out.println("codeProjet " + p.getCodeProjet());
				
				/*Employer commercial= checkAndAddEmployer(p.getCodeCommercial(),p.getNomCommercial(),"Commercial");
				System.out.println("commercial " + commercial);
				p.setCommercial(commercial);*/
				
				
				//p.setLastUpdate(new Date());
			}
			newEtatProjet.setDateCreation(new Date());
			newEtatProjet.setLastUpdate(new Date());
			etatProjetRepository.save(newEtatProjet);
		} else {
			boolean found = false;
			
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
			projet.setInfoProjet(lastProjet.getInfoProjet());
			projet.setPriorite(lastProjet.getPriorite());
			projet.setDatePvReceptionDefinitive(lastProjet.getDatePvReceptionDefinitive());
			projet.setDatePvReceptionProvisoire(lastProjet.getDatePvReceptionProvisoire());
			projet.setStatutProjet(lastProjet.getStatutProjet());
			projet.setTauxAvancement(lastProjet.getTauxAvancement());
			lastEtatProjet.getProjets().remove(lastProjet);
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
	@Transactional
	public Projet updateProjet(String idProjet, Projet projet) {

		logger.info("update de Projet " + idProjet);
		
		projet.setCodeProjet(idProjet);
		
		
		
		Projet lastProjet = projetRepository.findById(idProjet).get();
		
		
		StringJoiner stringJoiner = new StringJoiner(" || ");
		if(lastProjet.getDateFinProjet()==null ) {
			
			if(projet.getDateFinProjet()!=null) {
				 
				stringJoiner.add("Modification de la date de Fin du projet");
			}
			
			
		}else {
			
			if(projet.getDateFinProjet()!=null ) {
				if(!lastProjet.getDateFinProjet().equals(projet.getDateFinProjet())){
					stringJoiner.add("Modification de la date de Fin du projet");
				}
				
			}else {
				stringJoiner.add("Modification de la date de Fin du projet");
			}
			
		}
		
		if(!lastProjet.getCommentaires().equals(projet.getCommentaires())) {
			stringJoiner.add("Ajout de commentaires");
		}
			
		if(stringJoiner.length()>0) {
			Collection<AppUser> users = userRepository.findUserByServices(Arrays.asList("Commercial","Chef Projet","SI","Direction"));
		
			Date date =new Date();
			for(AppUser appUser : users) {
				
				boolean addNotification=false;
				
				if( appUser.getService().getServName().equals("Commercial") || appUser.getService().getServName().equals("Chef Projet")) {
					if( (projet.getCommercial()!=null && projet.getCommercial().equals(appUser.getLastName()) || (projet.getChefProjet()!=null && projet.getChefProjet().equals(appUser.getUsername())) )) {
						addNotification = true;	
					}
					
				}else {
					addNotification = true;
				}
				
				if(addNotification) {
					Event event=new Event();
					event.setProjet(projet);
					event.setStatut(false);
					event.setCreatedBy(projet.getUpdatedBy());
					event.setActions(stringJoiner.toString());
					event.setDate(date);
					event.setUser(appUser);
					
					
					Collection<Event> events = eventRepository.getEventProjet(appUser.getUsername(), projet.getCodeProjet());
					if(events!=null && events.size()>0)
					eventRepository.deleteAll(events);
					
					
					eventRepository.save(event);
				}
				
				
				
			}
		}
			
		
		Projet p = projetRepository.save(projet);
		Collection<Document> documents = documentRepository.getDocumentsByCodeProjet( projet.getCodeProjet());
		if(documents!=null) {
			for(Document d : documents ) {
				d.setDatePvProvisoire(projet.getDatePvReceptionProvisoire());
				d.setDatePrevuReceptionDefinitive(projet.getDatePvReceptionDefinitive());
				documentRepository.save(d);
			}
		}
		return p;
	}

	@Override
	public List<Projet> findAllProjetsByDateSup(Boolean cloturer, Date dateCmd) {
		
		return  (List<Projet>)  projetRepository.findAllProjetsByDateSup(cloturer,dateCmd);
	}
	
	@Override
	public List<Projet> findAllProjetsByDateInf(Boolean cloturer, Date dateCmd) {
		
		return (List<Projet>) projetRepository.findAllProjetsByDateInf(cloturer,dateCmd);
	}
	
	public List<Projet> findAllProjet(){
		return (List<Projet>) projetRepository.findAllProjetsByDate();
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
		
		Double totalRal = 0.0;

		String sDate1="31/12/1960";  
	    Date d = null;
		try {
			d = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("error parse exception " + e.getMessage());
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
		
		return projetRepository.getProjets( cloturer);
	}
	

	
	
	@Override
	@javax.transaction.Transactional
	public  void loadProjetsDepFromSap() {
		ProjetRepository projetRepo;
		logger.info("load Projets From Sap");
        Set<Projet> projets = new HashSet<Projet>();
        EtatProjet etatProjet = new EtatProjet();
       etatProjet.setId(Long.valueOf(1L));
       etatProjet.setLastUpdate(new Date());
       
       Map<String,String> commentairesInfoProjet =  this.importInfoFournisseurFromSAP();
       ResultSet rs1 = null;
       try {
    	   System.out.println("OPEN DEP PROJECT");
            String req1 = "SELECT * FROM DB_MUNISYS.\"V_OPEN_DEP_PRJ\"";
             rs1 = DBA.request(req1);
            java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
           for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
               final String name = rsmd.getColumnName(i);
            
           }
           while (rs1.next()) {
               final Projet p = new Projet();
               if(projetRepository.existsById(rs1.getString(1))) {
            	   System.out.println("project already commercial "+ rs1.getString(1));
            	   projetRepository.updateFlag(rs1.getString(1));
               }
               
              /* else {
               if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {
                   p.setCodeProjet(rs1.getString(1));
               }
               if (rs1.getString(2) != null && !rs1.getString(2).equals("null")) {
                   p.setProjet(rs1.getString(2));
               }
               if (rs1.getString(4) != null && !rs1.getString(4).equals("null")) {
               	
                    SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                   p.setDateCmd(sp.parse(rs1.getString(4).split("\\s+")[0]));
               }
               if (rs1.getString(5) != null && !rs1.getString(5).equals("null")) {
                   p.setAge(Double.valueOf(rs1.getDouble(5)));
               }
               if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {
                   p.setCodeClient(rs1.getString(6));
               }
               if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {
                   p.setClient(rs1.getString(7));
               }
               if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
                   p.setCodeCommercial(rs1.getString(8));
               }
               if (rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
                   p.setCommercial(rs1.getString(9));
               }
               if (rs1.getString(10) != null && !rs1.getString(10).equals("null")) {
                   p.setChefProjet(rs1.getString(10));
               }
               if (rs1.getString(11) != null && !rs1.getString(11).equals("null")) {
                   p.setBu(rs1.getString(11));
               }
               if (rs1.getString(12) != null && !rs1.getString(12).equals("null")) {
                   p.setRefCom(rs1.getString(12));
               }
               if (rs1.getString(13) != null && !rs1.getString(13).equals("null")) {
                   p.setStatut(rs1.getString(13));
               }


               
               if(commentairesInfoProjet.get(p.getCodeProjet())!=null){
               	p.setInfoFournisseur(commentairesInfoProjet.get(p.getCodeProjet()));
               }
               
               p.setFlag("Normal");
               p.setType("Déploiement");
               p.setEtatProjet(etatProjet);
               projetRepository.save(p);
               
           
          // System.out.println("projets " + projets.size());
           //etatProjet.setProjets(projets);
          // updateProjetsFromSAp(etatProjet);
           }*/
               
           }
           
           
           //System.out.println("projets1 " + etatProjetServiceStatic.getProjetFromEtatProjet( false, "undefined"));
           
           //System.out.println("projets2 " + etatProjetServiceStatic.getAllProjets());
       }
       catch (Exception e) {
           e.printStackTrace();
           logger.info("error " + e.getMessage());
       }finally {
       	if(rs1!=null) {
       		try {
					rs1.close();
					DBA.getConnection().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.info("cannot close connection SAP " + e.getMessage());
				}
       	}
       	
		}
		
	}
	
	
	@javax.transaction.Transactional
	 public void updateProjetsFromSAp(EtatProjet newEtatProjet) {
		
	        final EtatProjet lastEtatProjet = this.etatProjetRepository.findById(1L).orElse(null);
	        if (lastEtatProjet != null && lastEtatProjet.getProjets() != null && !lastEtatProjet.getProjets().isEmpty()) {
	            for (final Projet projet : lastEtatProjet.getProjets()) {
	                
	            	
	            	
	    			if(!projet.isDecloturedByUser()) 
	            		projet.setCloture(true);
	            		//projet.setDecloturedByUser(false);
	            		//projet.setCloturedByUser(false);
	            		projet.setFacturation(100.00);
	            		projet.setRestAlivrer(0.0);
	            		projet.setLivrerNonFacture(0.0);
	            		projet.setLivreFacturePayer(projet.getMontantCmd());
	            	
	            	
	            }
	        }
	        if (lastEtatProjet == null) {
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
	            
	            for (final Projet projet2 : newEtatProjet.getProjets()) {
	                projet2.setCloture(false);
	                this.addOrUpdateProjet(lastEtatProjet, projet2);
	            }
	            
	          	lastEtatProjet.setLastUpdate(new Date());

	            this.etatProjetRepository.save(lastEtatProjet);
	        }
	    }

	@Override
	public List<Projet> getAllProjets() {
		
		return (List<Projet>)projetRepository.getAllProjets();
	}
	
	public Map<String,String> importInfoFournisseurFromSAP() {
		
		Map<String,String> commentaireInfoFour = new HashedMap<String, String>();
 		ResultSet rs1 = null;
		try {
            final String req1 = "SELECT * FROM DB_MUNISYS.\"V_INFO_ACH\"";
             rs1 = DBA.request(req1);
            final java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
            for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
                final String name = rsmd.getColumnName(i);
                logger.info("colNam Info Achat " + name);
            }
            
            while (rs1.next()) {
            	
                 if (rs1.getString(1)!=null && !rs1.getString(1).equals("null") && rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
                	 
                	 String newInfoFourniseur = rs1.getString(9);
                	 
                	 if(commentaireInfoFour.get(rs1.getString(1))!=null) {
                		 
                		 String lastInfos = commentaireInfoFour.get(rs1.getString(1));
                		 String newInfo = lastInfos + "\n"+newInfoFourniseur;
                		 
                		 commentaireInfoFour.put(rs1.getString(1), newInfo);
                		 
                		 
                	 }else {
                		 commentaireInfoFour.put(rs1.getString(1), newInfoFourniseur);
                	 }
                	 
                 }
            		
            }
            
           
		} catch (Exception e) {
			logger.error("error  " + e.getMessage());
         }finally {
			if(rs1!=null) {
				try {
					rs1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error("error  " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		 return commentaireInfoFour;
		
	}
	
	@Override
	@javax.transaction.Transactional
	public  void loadProjetsFromSap() {
		logger.info("load Projets From Sap");
         Set<Projet> projets = new HashSet<Projet>();
         EtatProjet etatProjet = new EtatProjet();
        etatProjet.setId(Long.valueOf(1L));
        etatProjet.setLastUpdate(new Date());
        Map<String,String> commentairesInfoProjet =  this.importInfoFournisseurFromSAP();
        ResultSet rs1 = null ;
        try {
             String req1 = "SELECT * FROM DB_MUNISYS.\"V_OPEN_PRJ\"";
              rs1 = DBA.request(req1);
             java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
            for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
                final String name = rsmd.getColumnName(i);
             System.out.println("name " + name);
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
                
                if (rs1.getString(24) != null && !rs1.getString(24).equals("null")) {
                    p.setConditionFacturation(rs1.getString(24));
                }
                
                if(commentairesInfoProjet.get(p.getCodeProjet())!=null){
                	p.setInfoFournisseur(commentairesInfoProjet.get(p.getCodeProjet()));
                }
                
                p.setEtatProjet(etatProjet);
                p.setType("Commercial");
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
            logger.info("error " + e.getMessage());
        }finally {
        	if(rs1!=null) {
        		try {
					rs1.close();
				} catch (SQLException e) {
					logger.info("cannot close connection SAP " + e.getMessage());
				}
        	}
        	try {
				DBA.getConnection().close();
			} catch (SQLException e) {
				logger.info("cannot close connection SAP " + e.getMessage());
			}
		}
    }
	
	@Override
	@javax.transaction.Transactional
	public Projet loadSingleProjetFromSap(String codeProjet) {
		logger.info("load projet : {} From Sap",codeProjet);
		  Projet p=null;
        Map<String,String> commentairesInfoProjet =  this.importInfoFournisseurFromSAP();
        ResultSet rs1 = null ;
        try {
             String req1 = "SELECT * FROM DB_MUNISYS.\"V_OPEN_PRJ\" where \"Code projet\"="+"'"+codeProjet+"'";
              rs1 = DBA.request(req1);
            
            while (rs1.next()) {
               
                p = projetRepository.findById(rs1.getString(1)).orElse(null);
               
               if(p!=null) {
            	   if (rs1.getString(2) != null && !rs1.getString(2).equals("null")) {
                       p.setProjet(rs1.getString(2));
                   }
                   if (rs1.getString(3) != null && !rs1.getString(3).equals("null")) {
                   	
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
                   
                   if (rs1.getString(24) != null && !rs1.getString(24).equals("null")) {
                       p.setConditionFacturation(rs1.getString(24));
                   }
                   
                   if(commentairesInfoProjet.get(p.getCodeProjet())!=null){
                   	p.setInfoFournisseur(commentairesInfoProjet.get(p.getCodeProjet()));
                   }
                   
                   projetRepository.save(p);
               }
            
               
              
            }
           
           
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info("error " + e.getMessage());
        }finally {
        	if(rs1!=null) {
        		try {
					rs1.close();
					
				} catch (SQLException e) {
					logger.info("cannot close connection SAP " + e.getMessage());
				}
        	}
        	
        	try {
				DBA.getConnection().close();
			} catch (SQLException e) {
				logger.info("cannot close connection SAP " + e.getMessage());
			}
        	
		}
        return p;
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
		
		return projetRepository.getProjetsByBuAndStatut( cloture,  bu1,  bu2,  statut1);
	}

	@Override
	public Collection<Projet> getProjetsByBu(Boolean cloturer, String bu1, String bu2) {
		
		return projetRepository.getProjetsByBu(cloturer, bu1, bu2);
	}

	@Override
	public Collection<Projet> getProjetsByStatut(Boolean cloturer, String statut) {
		
		return projetRepository.getProjetsByStatut(cloturer, statut);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetIsNull(Boolean cloturer) {
		
		return projetRepository.getProjetsByChefDeProjetIsNull(cloturer);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetNotNull(Boolean cloturer) {
		
		return projetRepository.getProjetsByChefDeProjetNotNull(cloturer);
	}

	@Override
	public Collection<Projet> getProjetsByBuAndStatut(Boolean cloturer, String bu1, String bu2, String statut
			) {
		
		return projetRepository.getProjetsByBuAndStatut(cloturer, bu1, bu2, statut);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndBu(Boolean cloturer, String bu1, String bu2
			) {
		
		return projetRepository.getProjetsByChefDeProjetNotNullAndBu(cloturer, bu1, bu2);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndBu(Boolean cloturer, String bu,String bu2) {
		
		return projetRepository.getProjetsByChefDeProjetIsNullAndBu(cloturer, bu,bu2);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetIsNullAndStatut(Boolean cloturer, String statut1) {
		
		return projetRepository.getProjetsByChefDeProjetIsNullAndStatut(cloturer, statut1);
	}

	@Override
	public Collection<Projet> getProjetsByChefDeProjetNotNullAndStatut(Boolean cloturer, String statut1) {
		
		return projetRepository.getProjetsByChefDeProjetNotNullAndStatut(cloturer, statut1);
	}

	
	/*@Override
	public Collection<Projet> getProjetsFromEtatProjetDep(Long idEtatProjet,
			Boolean cloturer,
			 String bu1, String bu2,
			 String statut,
			 String commercial,String chefProjet,String client,String affectationChefProjet) {
		// TODO Auto-generated method stub
		
		if(bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){
			return projetRepository.getProjetsDep();
		     	
		     }
		
		if(!commercial.equals("undefined") && affectationChefProjet.equals("true")) {
			projetRepository.getProjetsByChefDeProjetNotNullAndCommercialDep(cloturer, commercial);
		}
		
		if(!commercial.equals("undefined") && affectationChefProjet.equals("false")) {
			projetRepository.getProjetsByChefDeProjetIsNullAndCommercialDep(cloturer, commercial);
		}
		
		if(affectationChefProjet.equals("true")){
			return projetRepository.getProjetsByChefDeProjetNotNullDep(cloturer);
		}
		
		if(affectationChefProjet.equals("false")){
			return projetRepository.getProjetsByChefDeProjetIsNullDep(cloturer);
		}
		
	     // filtre par bu uniquement.  A
	     if(!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
		      && commercial.equals("undefined") && client.equals("undefined") ){

	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))).and(ProjetSpecification.byType("DEP")));
	     }

	 	 // filtre par statut uniquement B 
	     if(bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
		      && commercial.equals("undefined") && client.equals("undefined") ){
	    	 return 	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut)).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par chefProjet uniquement C 
	     if(bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
		      && commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byChefProjet(chefProjet)).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par commercial uniquement D 
	     if(bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && client.equals("undefined") ){
	    	 return 	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byCommercial(commercial)).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par client uniquement E
	     if(bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
		      && commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byClient(client)).and(ProjetSpecification.byType("DEP")));
	     }


	 	// filtre par bu et statut uniquement.  AB
	     if(!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
		      && commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byStatut(statut))).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par bu et chefProjet uniquement.  AC
	     if(!bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
		      && commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byChefProjet(chefProjet))).and(ProjetSpecification.byType("DEP")));
	     }

	 // filtre par bu et commercial uniquement.  AD
	     if(!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byCommercial(commercial))).and(ProjetSpecification.byType("DEP")));

	     }
	 // filtre par bu et client uniquement.  AE
	     if(!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
		      && commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byClient(client))).and(ProjetSpecification.byType("DEP")));
	     }
	     
	  // filtre par statut et chefProjet uniquement.  BC
	     if(bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
		      && commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet))).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par statut et commercial uniquement.  BD
	     if(bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && client.equals("undefined") ){
	    	 return	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byCommercial(commercial))).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par statut et client uniquement.  BE
	     if(bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
		      && commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byClient(client))).and(ProjetSpecification.byType("DEP")));
	     }


	 	// filtre par chefProjet et commercial uniquement.  CD
	     if(bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial))).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par chefProjet et client uniquement.  CE
	     if(bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
		      && commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byClient(client))).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par commercial et client uniquement.  DE
	     if(bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client))).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par bu et statut et chefProjet uniquement.  ABC
	     if(!bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
		      && commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet)))).and(ProjetSpecification.byType("DEP")));

	     }

	 // filtre par bu et statut et commercial uniquement.  ABD
	     if(!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byCommercial(commercial)))).and(ProjetSpecification.byType("DEP")));
	     }

	  // filtre par bu et statut et client uniquement.  ABE
	     if(!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
		      && commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byClient(client)))).and(ProjetSpecification.byType("DEP")));
	     }
	  // filtre par bu et chefProjet et client uniquement.  ACD
	     if(!bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial)))).and(ProjetSpecification.byType("DEP")));
	     }


	 // filtre par bu et chefProjet et client uniquement.  ACE
	     if(!bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
		      && commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byClient(client)))).and(ProjetSpecification.byType("DEP")));
	     }
	 
	 
	 // filtre par bu et commercial et client uniquement.  ADE
	     if(!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client)))).and(ProjetSpecification.byType("DEP")));
	     }


	 	// filtre par statut et chefProjet et commercial uniquement.  BCD
	     if(bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial)))).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par statut et chefProjet et client uniquement.  BCE
	     if(bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
		      && commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byClient(client)))).and(ProjetSpecification.byType("DEP")));
	    
	     }
	     
	  // filtre par statut et client et commercial uniquement.  BDE
	     if(bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && !client.equals("undefined") ){
	     		projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client)))).and(ProjetSpecification.byType("DEP")));
	    
	     }

	 	// filtre par chefProjet et commercial et client uniquement.  CDE
	     if(bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client)))).and(ProjetSpecification.byType("DEP")));
	    
	     }


	 	// filtre par bu et statut et chefProjet et commercial uniquement.  ABCD
	     if(!bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
	     		.and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial))))).and(ProjetSpecification.byType("DEP")));
	    

	     }

	 	// filtre par bu et statut et chefProjet et client uniquement.  ABCE
	     if(!bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
		      && commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
	     		.and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byClient(client))))).and(ProjetSpecification.byType("DEP")));
	     }

	 	// filtre par bu et statut et commercial et client uniquement.  ABDE
	     if(!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
	     		.and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client))))).and(ProjetSpecification.byType("DEP")));
	     }

	 	
	 	// filtre par bu et chefProjet et commercial et client uniquement.  ACDE
	     if(!bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
	     		.and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client))))).and(ProjetSpecification.byType("DEP")));
	     }



	 	// filtre par statut et chefProjet et commercial  et client uniquement.  BCDE
	     if(bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && !client.equals("undefined") ){
	    	 return	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut)
	     		.and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client))))));
	     

	     }

	     // filtre par bu et statut et chefProjet et commercial et client uniquement.  ABCDE
	     if(!bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
		      && !commercial.equals("undefined") && !client.equals("undefined") ){
	     return	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
	     		.and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client)))))).and(ProjetSpecification.byType("DEP")));

	     }
	     
		
		
		return null;
	}*/
	
	
	public Collection<Projet> getProjetsByPredicate( Long idEtatProjet,
			Boolean cloturer,
			 String bu1, String bu2,
			 String statut,
			 String commercial,String chefProjet,String client,String affectationChefProjet){        


		if(!commercial.equals("undefined") && affectationChefProjet.equals("true")) {
			projetRepository.getProjetsByChefDeProjetNotNullAndCommercial(cloturer, commercial);
		}
		
		if(!commercial.equals("undefined") && affectationChefProjet.equals("false")) {
			projetRepository.getProjetsByChefDeProjetIsNullAndCommercial(cloturer, commercial);
		}
		
		if(affectationChefProjet.equals("true")){
			return this.getProjetsByChefDeProjetNotNull(cloturer);
		}
		
		if(affectationChefProjet.equals("false")){
			return this.getProjetsByChefDeProjetIsNull(cloturer);
		}
		
		
		
		
		if(bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){
		     	return this.getProjets(cloturer);
		     	
		     }
		
		
		//filtre par type projet
		
		

		     // filtre par bu uniquement.  A
		     if(!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){

		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))));
		     }

		 	 // filtre par statut uniquement B 
		     if(bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){
		    	 return 	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut)));
		     }

		 	// filtre par chefProjet uniquement C 
		     if(bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byChefProjet(chefProjet)));
		     }

		 	// filtre par commercial uniquement D 
		     if(bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && client.equals("undefined") ){
		    	 return 	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byCommercial(commercial)));
		     }

		 	// filtre par client uniquement E
		     if(bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byClient(client)));
		     }


		 	// filtre par bu et statut uniquement.  AB
		     if(!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byStatut(statut))));
		     }

		 	// filtre par bu et chefProjet uniquement.  AC
		     if(!bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byChefProjet(chefProjet))));
		     }

		 // filtre par bu et commercial uniquement.  AD
		     if(!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byCommercial(commercial))));

		     }
		 // filtre par bu et client uniquement.  AE
		     if(!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byClient(client))));
		     }




		 	// filtre par statut et chefProjet uniquement.  BC
		     if(bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet))));
		     }

		 	// filtre par statut et commercial uniquement.  BD
		     if(bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && client.equals("undefined") ){
		    	 return	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byCommercial(commercial))));
		     }

		 	// filtre par statut et client uniquement.  BE
		     if(bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byClient(client))));
		     }


		 	// filtre par chefProjet et commercial uniquement.  CD
		     if(bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial))));
		     }

		 	// filtre par chefProjet et client uniquement.  CE
		     if(bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
			      && commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byClient(client))));
		     }

		 	// filtre par commercial et client uniquement.  DE
		     if(bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client))));
		     }

		 	// filtre par bu et statut et chefProjet uniquement.  ABC
		     if(!bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
			      && commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet)))));

		     }

		 // filtre par bu et statut et commercial uniquement.  ABD
		     if(!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byCommercial(commercial)))));
		     }

		  // filtre par bu et statut et client uniquement.  ABE
		     if(!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
			      && commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byClient(client)))));
		     }
		  // filtre par bu et chefProjet et client uniquement.  ACD
		     if(!bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial)))));
		     }


		 // filtre par bu et chefProjet et client uniquement.  ACE
		     if(!bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
			      && commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byClient(client)))));
		     }
		 
		 
		 // filtre par bu et commercial et client uniquement.  ADE
		     if(!bu1.equals("undefined") && statut.equals("undefined") && chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2)).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client)))));
		     }


		 	// filtre par statut et chefProjet et commercial uniquement.  BCD
		     if(bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial)))));
		     }

		 	// filtre par statut et chefProjet et client uniquement.  BCE
		     if(bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
			      && commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byClient(client)))));
		    
		     }
		     
		  // filtre par statut et client et commercial uniquement.  BDE
		     if(bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && !client.equals("undefined") ){
		     		projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client)))));
		    
		     }

		 	// filtre par chefProjet et commercial et client uniquement.  CDE
		     if(bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client)))));
		    
		     }


		 	// filtre par bu et statut et chefProjet et commercial uniquement.  ABCD
		     if(!bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
		     		.and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial))))));
		    

		     }

		 	// filtre par bu et statut et chefProjet et client uniquement.  ABCE
		     if(!bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
			      && commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
		     		.and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byClient(client))))));
		     }

		 	// filtre par bu et statut et commercial et client uniquement.  ABDE
		     if(!bu1.equals("undefined") && !statut.equals("undefined") && chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
		     		.and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client))))));
		     }

		 	
		 	// filtre par bu et chefProjet et commercial et client uniquement.  ACDE
		     if(!bu1.equals("undefined") && statut.equals("undefined") && !chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
		     		.and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client))))));
		     }



		 	// filtre par statut et chefProjet et commercial  et client uniquement.  BCDE
		     if(bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && !client.equals("undefined") ){
		    	 return	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byStatut(statut)
		     		.and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client))))));
		     

		     }

		     // filtre par bu et statut et chefProjet et commercial et client uniquement.  ABCDE
		     if(!bu1.equals("undefined") && !statut.equals("undefined") && !chefProjet.equals("undefined")
			      && !commercial.equals("undefined") && !client.equals("undefined") ){
		     return	projetRepository.findAll(ProjetSpecification.isCloture(cloturer).and(ProjetSpecification.byBu(bu1).or(ProjetSpecification.byBu(bu2))
		     		.and(ProjetSpecification.byStatut(statut).and(ProjetSpecification.byChefProjet(chefProjet).and(ProjetSpecification.byCommercial(commercial).and(ProjetSpecification.byClient(client)))))));

		     }
			return null;
		
		
		
	}
	
	

	@Override
	public List<String> getDistinctClient() {
		
		return projetRepository.getDistinctClient();
	}

	@Override
	public List<String> getDistinctCommercial() {
		
		return projetRepository.getDistinctCommercial();
	}

	@Override
	public List<String> getDistinctChefProjet() {
		
		return projetRepository.getDistinctChefProjet();
	}

	@Override
	
	public Projet declotureProjet(Projet p) {
		
		logger.info("decloture de Projet " + p.getCodeProjet());
		
		int i = projetRepository.updateStatutProjet(false, false, true, p.getCodeProjet());
			
		return projetRepository.findById(p.getCodeProjet()).get();
		 
		
	}
	
	@Override
	@Transactional
	public Projet clotureProjet(Projet p) {
		logger.info("cloture de Projet " + p.getCodeProjet());
		
		EtatProjet etatProjet = new EtatProjet();
		etatProjet.setId(1L);
		p.setEtatProjet(etatProjet);
		
		
		int i = projetRepository.updateStatutProjetMontant(true, true, false, 100.00, 0.00, p.getMontantCmd(), 0.00, p.getCodeProjet());
		
		
		return projetRepository.findById(p.getProjet()).get();
		
		/*p.setCloture(true);
		p.setDecloturedByUser(false);
		p.setCloturedByUser(true);
		p.setFacturation(100.00);
		p.setRestAlivrer(0.0);
		p.setLivrerNonFacture(0.0);
		p.setLivreFacturePayer(p.getMontantCmd());
		Projet c = projetRepository.save(p);
		//projetRepository.flush();
		//System.out.println("cloture projet " + c.isCloture());
		return c;*/
		
	}

	public EventRepository getEventRepository() {
		return eventRepository;
	}

	public void setEventRepository(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public Collection<DetailRdv> getDetailRdvByCodeProjet(String codeProjet) {
		Collection<DetailRdv> detailRdvs = new ArrayList<DetailRdv>();
		ResultSet rs1 = null ;
		
        try {
             String req1 = "SELECT * FROM DB_MUNISYS.\"V_EXPORT_RDV\" where \"Code Projet\"= "+"'"+codeProjet+"'";
              rs1 = DBA.request(req1);
              
                 
            while (rs1.next()) {
               
              DetailRdv detailRdv = new DetailRdv();
               
            	   
                       detailRdv.setCodeProjet(rs1.getString(1));
                   
                  
                       detailRdv.setItemCode(rs1.getString(2));
                   
                       detailRdv.setDesignation(rs1.getString(3));                    
                       
                       detailRdv.setNature(rs1.getString(4));
                       
                       detailRdv.setSousNature(rs1.getString(5));
                       
                       detailRdv.setDomaine(rs1.getString(6));
                       
                       detailRdv.setSousDomaine(rs1.getString(7));
                       
                       detailRdv.setMarque(rs1.getString(8));
                       
                       detailRdv.setQte(rs1.getFloat(9));
                       
                       detailRdv.setQteLiv(rs1.getFloat(10));
                       
                       detailRdv.setQteRAL(rs1.getFloat(11));
                       detailRdv.setMontantRAL(rs1.getDouble(12));
                       
                       detailRdv.setPrixVente(rs1.getDouble(13));
                       detailRdv.setMontantVente(rs1.getDouble(14));
                       
                       detailRdv.setPrixAchat(rs1.getDouble(15));
                       detailRdv.setMontantAchat(rs1.getDouble(16));
                       

                       

                       
                      
                       
                       detailRdv.setQteLNF(rs1.getFloat(17));
                       detailRdv.setMontantLNF(rs1.getDouble(18));
                       detailRdv.setCodeFrs(rs1.getString(19));
                       detailRdv.setFrs(rs1.getString(20));
                       
                       
                       
           
                       
                       
                       detailRdvs.add(detailRdv);
                   
               }
           
            }
              
             
        catch (Exception e) {
            e.printStackTrace();
            logger.info("error " + e.getMessage());
        }finally {
        	if(rs1!=null) {
        		try {
					rs1.close();
					
				} catch (SQLException e) {
					logger.info("cannot close connection SAP " + e.getMessage());
				}
        	}
        	
        	try {
				DBA.getConnection().close();
			} catch (SQLException e) {
				logger.info("cannot close connection SAP " + e.getMessage());
			}
        	
		}
        return detailRdvs;
	}
	
	@Override
	public Collection<DetailRdv> getDetailRdvDEPByCodeProjet(String codeProjet) {
		Collection<DetailRdv> detailRdvsDep = new ArrayList<DetailRdv>();
		ResultSet rs2 = null ;
		
        try {
             String req2 = "SELECT * FROM DB_MUNISYS.\"V_RDV_DEP\" where \"Code Projet\"= "+"'"+codeProjet+"'";
              rs2 = DBA.request(req2);
              
                 
            while (rs2.next()) {
               
              DetailRdv detailRdvDep = new DetailRdv();
               
            	   
              detailRdvDep.setCodeProjet(rs2.getString(1));
                   
                  
              detailRdvDep.setItemCode(rs2.getString(2));
                   
              detailRdvDep.setDesignation(rs2.getString(3));                    
                       
              detailRdvDep.setNature(rs2.getString(4));
                       
              detailRdvDep.setSousNature(rs2.getString(5));
                       
              detailRdvDep.setDomaine(rs2.getString(6));
                       
              detailRdvDep.setSousDomaine(rs2.getString(7));
                       
              detailRdvDep.setMarque(rs2.getString(8));
                       
              detailRdvDep.setQte(rs2.getFloat(9));
                       
              detailRdvDep.setQteLiv(rs2.getFloat(10));
                       
              detailRdvDep.setQteRAL(rs2.getFloat(11));
                       
                       
                       
           
                       
                       
              detailRdvsDep.add(detailRdvDep);
                   
               }
           
            }
              
             
        catch (Exception e) {
            e.printStackTrace();
            logger.info("error " + e.getMessage());
        }finally {
        	if(rs2!=null) {
        		try {
					rs2.close();
					
				} catch (SQLException e) {
					logger.info("cannot close connection SAP " + e.getMessage());
				}
        	}
        	
        	try {
				DBA.getConnection().close();
			} catch (SQLException e) {
				logger.info("cannot close connection SAP " + e.getMessage());
			}
        	
		}
        return detailRdvsDep;
	}
	
	
	
	
	
	
	

}
