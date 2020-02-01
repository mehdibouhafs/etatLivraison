package ma.munisys;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Set;
import java.text.SimpleDateFormat;
import ma.munisys.sap.dao.DBA;

import java.util.Calendar;
import java.util.Date;

import ma.munisys.entities.AppUser;
import ma.munisys.entities.Authorisation;
import ma.munisys.entities.CommandeFournisseur;
import ma.munisys.entities.Document;
import ma.munisys.entities.EtatProjet;
import ma.munisys.entities.EtatRecouvrement;
import ma.munisys.entities.Projet;
import ma.munisys.entities.Service;

import java.util.HashSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import ma.munisys.dao.ServiceRepository;
import ma.munisys.dao.CommentaireRepository;
import ma.munisys.dao.EmployerRepository;
import ma.munisys.service.ReunionService;
import ma.munisys.service.StorageService;
import ma.munisys.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import ma.munisys.service.CommandeFournisseurService;
import ma.munisys.service.ContratService;
import ma.munisys.service.EtatProjetService;
import ma.munisys.service.EtatRecouvrementService;
import ma.munisys.service.ProduitService;
import ma.munisys.dao.EtatProjetRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableScheduling
public class EtatLvSvcApplication extends SpringBootServletInitializer implements CommandLineRunner
{
    private static EtatProjetRepository etatProjetRepositoryStatic;
    
    private static EtatProjetService etatProjetServiceStatic;
    
    private static ProduitService produitServiceStatic;
    
    private static EtatRecouvrementService etatRecouvrementServiceStatic;
    
    private static ContratService contratServiceStatic;
    
    private static CommandeFournisseurService commandeFournisseurServiceStatic;
    @Autowired
    EtatProjetRepository etatProjetRepository;
    @Autowired
    EtatRecouvrementService etatRecouvrementService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StorageService storageService;
    @Autowired
    EtatProjetService etatProjetService;
    @Autowired
    ReunionService reunionService;
    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    CommentaireRepository commentaireRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    ProduitService produitService;
    @Autowired
    ContratService contratService;
    
    @Autowired
    CommandeFournisseurService commandeFournisseurService;
    
    
    public static void main(final String[] args) {
        SpringApplication.run((Class)EtatLvSvcApplication.class, args);
         etatProjetServiceStatic.loadProjetsFromSap();
        //loadFromSap();
        //loadDocumentsFromSap();
        //etatProjetServiceStatic.importInfoFournisseurFromSAP();
         //3 produitServiceStatic.loadProduitFromSap();
    }
    
    @PostConstruct
    public void init() {
        EtatLvSvcApplication.etatProjetRepositoryStatic = this.etatProjetRepository;
        EtatLvSvcApplication.etatProjetServiceStatic = this.etatProjetService;
        EtatLvSvcApplication.etatRecouvrementServiceStatic = this.etatRecouvrementService;
        EtatLvSvcApplication.produitServiceStatic = this.produitService;
        EtatLvSvcApplication.contratServiceStatic = this.contratService;
        EtatLvSvcApplication.commandeFournisseurServiceStatic = this.commandeFournisseurService;
    }
    
    @Bean // ce bean sera utilise n'importe ou
	public BCryptPasswordEncoder getBCPE() { 
		return new BCryptPasswordEncoder();
	}
	
    
    public void run(final String... args) throws Exception {
        this.storageService.init();
        //contratService.loadContratFromSap();
       // commandeFournisseurService.loadCommandeFournisseurFromSap();
       /* 
        Service service1= new Service();
        service1.setServName("SI");
        serviceRepository.save(service1);
        
        AppUser appUser = new AppUser("stage01", "lastStage01", "stage01", null, service1, null);
        userRepository.save(appUser);
        
        Authorisation authorisation = new Authorisation();
        authorisation.setAuthName("ReadEtatProjet");
        
        appUser.getAuthorities().add(authorisation);
        
        
        
        Authorisation authorisation1 = new Authorisation();
        authorisation1.setAuthName("ReadALLEtatRecouvrement");
        
        Authorisation authorisation2 = new Authorisation();
        authorisation2.setAuthName("ReadOnlyMineEtatRecouvrement");*/
        
    }
    
    @Scheduled(cron = "0 0 0 * * *")
    public static void loadFromSap() {
        System.out.println("STARTING TASK Projetcts CRON ");
        //loadProjetsFromSap();
        etatProjetServiceStatic.loadProjetsFromSap();
        //loadDocumentsFromSap();
        System.out.println("ENDING TASK Projects CRON ");
    }
    
    @Scheduled(cron = "0 0 23 * * *")
    public static void loadFromSap2() {
        System.out.println("STARTING TASK Docuemtns CRON ");
        //loadProjetsFromSap();
        //etatProjetServiceStatic.loadProjetsFromSap();
        loadDocumentsFromSap();
        System.out.println("ENDING TASK Documents CRON ");
    }
    
    
    @Scheduled(cron = "0 0 22 * * *")
    public static void loadCommandeFournisseur() {
        System.out.println("STARTING TASK commande fournisseur CRON ");
        EtatLvSvcApplication.commandeFournisseurServiceStatic.loadCommandeFournisseurFromSap();
        System.out.println("ENDING TASK comande fournisseur CRON ");
    }
    
    @Scheduled(cron = "0 0 21 * * *")
    public static void loadContrat() {
        System.out.println("STARTING TASK contrat fournisseur CRON ");
        EtatLvSvcApplication.contratServiceStatic.loadContratFromSap();
        System.out.println("ENDING TASK contrat fournisseur CRON ");
    }
    
    
    @Scheduled(cron = "0 0 1 * * *")
    public static void loadProduitFromSap() {
        System.out.println("STARTING TASK Produits CRON ");
        //loadProjetsFromSap();
        //etatProjetServiceStatic.loadProjetsFromSap();
        //loadDocumentsFromSap();
        EtatLvSvcApplication.produitServiceStatic.loadProduitFromSap();
        System.out.println("ENDING TASK Produits CRON ");
    }
    
    @Scheduled(cron = "0 0 13 * * *")
    public static void loadProduitFromSap2() {
        System.out.println("STARTING TASK Produits CRON ");
        //loadProjetsFromSap();
        //etatProjetServiceStatic.loadProjetsFromSap();
        //loadDocumentsFromSap();
        EtatLvSvcApplication.produitServiceStatic.loadProduitFromSap();
        System.out.println("ENDING TASK Produits CRON ");
    }
    
    public static void loadDocumentsFromSap() {
        Set<Document> documents = new HashSet<Document>();
        EtatRecouvrement etatRecouvrement = new EtatRecouvrement();
        etatRecouvrement.setId(Long.valueOf(1L));
        etatRecouvrement.setLastUpdate(new Date());
         ResultSet rs1 = null;
       try {
           final String req1 = "SELECT * FROM DB_MUNISYS.\"V_BALANCE\"";
            rs1 = DBA.request(req1);
           final ResultSetMetaData rsmd = rs1.getMetaData();
           for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
                String name = rsmd.getColumnName(i);
               System.out.println("column Name " + name);
           }
           SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
           while (rs1.next()) {
                Document d = new Document();
                
              
               if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {
                   d.setNumPiece(rs1.getString(1));
               }
               if (rs1.getString(2) != null && !rs1.getString(2).equals("null")) {
            	   
                   d.setDatePiece(sp.parse(rs1.getString(2).split("\\s+")[0]));
                   d.setDateDepot(d.getDatePiece());
                   
               }
               if (rs1.getString(3) != null && !rs1.getString(3).equals("null")) {
                    d.setTypeDocument(rs1.getString(3));
                   
               }
               
               if(rs1.getString(1) != null && rs1.getString(3)!=null) {
            	   d.setNumPieceByTypeDocument(rs1.getString(1)+rs1.getString(3));
            	   d.setCodePiece(rs1.getString(1)+rs1.getString(3));
               }
               
               if (rs1.getString(4) != null && !rs1.getString(4).equals("null")) {
                   d.setCodeClient(rs1.getString(4));
               }
               if (rs1.getString(5) != null && !rs1.getString(5).equals("null")) {
                   d.setClient(rs1.getString(5));
               }
               if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {
                   d.setRefClient(rs1.getString(6));
               }
               if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {
                   d.setCodeProjet(rs1.getString(7));
               }
               if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
                   d.setProjet(rs1.getString(8));
               }
               if (rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
                   d.setCodeCommercial(rs1.getString(9));
               }
               if (rs1.getString(10) != null && !rs1.getString(10).equals("null")) {
                   d.setCommercial(rs1.getString(10));
               }
               if (rs1.getString(11) != null && !rs1.getString(11).equals("null")) {
                   d.setChefProjet(rs1.getString(11));
               }
               if (rs1.getString(12) != null && !rs1.getString(12).equals("null")) {
                   d.setMontantPiece(rs1.getDouble(12));
               }
               
               if (rs1.getString(13) != null && !rs1.getString(13).equals("null")) {
            	   d.setMontantOuvert(rs1.getDouble(13));
            	   
            	   if( rs1.getDouble(13) <= 200000) {
            		   d.setCategorie("C");
            	   }
            	   if( rs1.getDouble(13) > 200000 && rs1.getDouble(13) <1000000) {
            		   d.setCategorie("B");
            	   }
            	   
            	   if(rs1.getDouble(13) >= 1000000) {
            		   d.setCategorie("A");
            	   }
            	   
            	   
               }
               if (rs1.getString(14) != null && !rs1.getString(14).equals("null")) {
                  d.setChargerRecouvrement( rs1.getString(14));
               }
               if (rs1.getString(15) != null && !rs1.getString(15).equals("null")) {
                   d.setAnneePiece(rs1.getInt(15));
               }
               if (rs1.getString(16) != null && !rs1.getString(16).equals("null")) {
            	   d.setAgePiece(rs1.getInt(16));
               }
               if (rs1.getString(17) != null && !rs1.getString(17).equals("null")) {
                  d.setAge(rs1.getString(17));
               }
               
               if (rs1.getString(18) != null && !rs1.getString(18).equals("null")) {
                  
                  
                  
                  d.setDateEcheance(sp.parse(rs1.getString(18).split("\\s+")[0]));
                  
               }
               
               if (rs1.getString(19) != null && !rs1.getString(19).equals("null")) {
                   d.setConditionDePaiement(rs1.getString(19));
                   
                  
                   
                }
               
               if(d.getTypeDocument()!=null && d.getTypeDocument().equals("Facture")) {
            	   
            	   d.setDateDepot(d.getDatePiece());
            	   
            	   if(d.getConditionDePaiement()!=null && d.getDatePiece()!=null && d.getConditionDePaiement().equals("- Base de paiement -")) {
            		   
            		   Calendar c = Calendar.getInstance();
            		   c.setTime(d.getDatePiece());
            		   
            		   c.add(Calendar.MONTH,3);
            		   
            		   d.setDatePrevuEncaissement(c.getTime());
            		   
            	   }else {
            		   if(d.getDateEcheance()!=null)
            		   d.setDatePrevuEncaissement(d.getDateEcheance());
            	   }
               }
               	
               if (rs1.getString(20) != null && !rs1.getString(20).equals("null")) {
                  
                  if(rs1.getString(20).toLowerCase().equals("oui"))
            	    d.setCaution(true);
                }
               if (rs1.getString(21) != null && !rs1.getString(21).equals("null")) {
                   d.setNumCaution(rs1.getString(21));
                }
               
               if (rs1.getString(22) != null && !rs1.getString(22).equals("null")) {
    
            	   d.setTypeCaution(rs1.getString(22));
                }
               if (rs1.getString(23) != null && !rs1.getString(23).equals("null")) {
                   d.setDateLiberationCaution(sp.parse(rs1.getString(23).split("\\s+")[0]));
                }
               if (rs1.getString(24) != null && !rs1.getString(24).equals("null")) {
                   d.setMontantCaution(rs1.getDouble(24));
                }
               
               if(d.getStatut()==null) {
            	   d.setStatut("Non Traitée par CR");
               }
               
               d.setEtatRecouvrement(etatRecouvrement);
               documents.add(d);
               
           }
           
           System.out.println("documents " + documents.size());
           System.out.println("documents " + documents);
           etatRecouvrement.setDocuments(documents);
           EtatLvSvcApplication.etatRecouvrementServiceStatic.updateDocumentsFromSap(etatRecouvrement);
           
           //System.out.println("projets1 " + etatProjetServiceStatic.getProjetFromEtatProjet( false, "undefined"));
           
           //System.out.println("projets2 " + etatProjetServiceStatic.getAllProjets());
       }
       catch (Exception e) {
           e.printStackTrace();
       }finally {
       	if(rs1!=null) {
    		try {
				rs1.close();
				DBA.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
	}
   }

	public ProduitService getProduitService() {
		return produitService;
	}

	public void setProduitService(ProduitService produitService) {
		this.produitService = produitService;
	}
    
    

	
    
}