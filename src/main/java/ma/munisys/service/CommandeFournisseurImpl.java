package ma.munisys.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.munisys.dao.AuthRepository;
import ma.munisys.dao.CommandeFournisseurRepository;
import ma.munisys.dao.ContratRepository;
import ma.munisys.dao.ProfileRepository;
import ma.munisys.dao.UserRepository;

import ma.munisys.entities.AppProfile;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Authorisation;
import ma.munisys.entities.CommandeFournisseur;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.Echeance;
import ma.munisys.entities.OccurenceFacturation;
import ma.munisys.entities.PeriodeFacturation;
import ma.munisys.sap.dao.DBA;

@Service
@Transactional // spring pas javax
public class CommandeFournisseurImpl implements CommandeFournisseurService {
	
	private static final Logger logger = LogManager.getLogger(CommandeFournisseurImpl.class);

	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	
	@Autowired
	private ContratRepository contratRepository;

	@Override
	public CommandeFournisseur saveCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
		// TODO Auto-generated method stub
		logger.info("Saving commande fournisseur Service");
		return commandeFournisseurRepository.save(commandeFournisseur);
	}

	@Async
	@Override
	@javax.transaction.Transactional
	public  CompletableFuture<String> loadCommandeFournisseurFromSap() {
    	//System.out.println("load commandeFournisseur from SAP");
		logger.info("loading CommandeFournisseur From Sap");
        ResultSet rs1 = null ;
        Set<CommandeFournisseur> commandeFournisseurs =new HashSet<CommandeFournisseur>();
        
        try {
             String req1 = "SELECT * FROM DB_MUNISYS.\"V_CM_DETACH\"";
              rs1 = DBA.request(req1);
             java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
            for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
                final String name = rsmd.getColumnName(i);
               // System.out.println("column Name " + name);
            }
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
           while (rs1.next()) {
                final CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
                Collection<Contrat> c =contratRepository.getContratByCodeProjet(rs1.getString(1));
                if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {
                 
                	if(c!=null) {
                		
                      	commandeFournisseur.setContrats(new HashSet<Contrat>(c));
                      	for(Contrat c1 : c) {
                      		c1.setSousTraiter(true);
                      		contratRepository.save(c1);
                      	}
                	}
                	
                }
                if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {
                   
                	commandeFournisseur.setNumeroDocument(rs1.getLong(6) );
                }
                if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {
                	
                	commandeFournisseur.setDateEnregistrement(sp.parse(rs1.getString(7).split("\\s+")[0]));
                }
                if (rs1.getString(9) != null && !rs1.getString(9).equals("null")) {
                    
                	commandeFournisseur.setFournisseur(rs1.getString(9) );
                }
                if (rs1.getString(10) != null && !rs1.getString(10).equals("null")) {
                    
                	commandeFournisseur.setNumReference(rs1.getString(10) );
                }
                if (rs1.getString(11) != null && !rs1.getString(11).equals("null")) {
                    
                	commandeFournisseur.setRemarque(rs1.getString(11) );
                }
                if (rs1.getString(14) != null && !rs1.getString(14).equals("null")) {
                    commandeFournisseur.setNumArticle(rs1.getString(14));
                }
                if (rs1.getString(15) != null && !rs1.getString(15).equals("null")) {
                    
                    commandeFournisseur.setDescriptionArticle(rs1.getString(15) );
                }
                if (rs1.getString(20) != null && !rs1.getString(20).equals("null")) {
                   
                	commandeFournisseur.setQte(rs1.getInt(20) );
                    
                    
                }
                if (rs1.getString(21) != null && !rs1.getString(21).equals("null")) {
                	commandeFournisseur.setPrix(rs1.getDouble(21) );
                }
                if (rs1.getString(22) != null && !rs1.getString(22).equals("null")) {
                	commandeFournisseur.setTotalCmd(rs1.getDouble(22) );
                }
                if (rs1.getString(23) != null && !rs1.getString(23).equals("null")) {
                	commandeFournisseur.setQteLiv(rs1.getInt(23));
                }
                if (rs1.getString(24) != null && !rs1.getString(24).equals("null")) {
                	commandeFournisseur.setTotalLiv(rs1.getDouble(24));
                }
                if (rs1.getString(25) != null && !rs1.getString(25).equals("null")) {
                	
                	commandeFournisseur.setQteEnCours(rs1.getInt(25));
                }
                if (rs1.getString(26) != null && !rs1.getString(26).equals("null")) {
                	
                	commandeFournisseur.setTotalRal(rs1.getDouble(26));
                }
				if (rs1.getString(27) != null && !rs1.getString(27).equals("null")) {
				                	
				          commandeFournisseur.setQteFacture(rs1.getInt(27));
				                }
				if (rs1.getString(28) != null && !rs1.getString(28).equals("null")) {
					
					commandeFournisseur.setMontantFacture(rs1.getDouble(28));
				}
				if (rs1.getString(29) != null && !rs1.getString(29).equals("null")) {
					
					commandeFournisseur.setQteRnf(rs1.getInt(29));
				}
				if (rs1.getString(30) != null && !rs1.getString(30).equals("null")) {
					
					commandeFournisseur.setMontantRnf(rs1.getDouble(30));
				}
                      
                commandeFournisseurs.add(commandeFournisseur);
                
            }
           
           commandeFournisseurRepository.deleteAll();
           commandeFournisseurRepository.saveAll(commandeFournisseurs);
           logger.info("end synchro commande fournisseur");
            
            
            //System.out.println("projets1 " + etatProjetServiceStatic.getProjetFromEtatProjet( false, "undefined"));
            
            //System.out.println("projets2 " + etatProjetServiceStatic.getAllProjets());
        }
        catch (Exception e) {
        	logger.error("error loading commande fournisseur "+e.getMessage());
            e.printStackTrace();
        }finally {
        	if(rs1!=null) {
        		try {
					rs1.close();
					DBA.getConnection().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.error("error closing connection sap "+e.getMessage());
					e.printStackTrace();
				}
        	}
        	
		}
        
        return CompletableFuture.completedFuture("loaded Commande fournisseur");
    }

}
