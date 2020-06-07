package ma.munisys.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.util.Log;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.util.GregorianCalendar;

import ma.munisys.dao.AuthRepository;
import ma.munisys.dao.CommentaireContratRepository;
import ma.munisys.dao.ContratRepository;
import ma.munisys.dao.ContratSpecification;
import ma.munisys.dao.EcheanceRepository;
import ma.munisys.dao.FactureEcheanceRepository;
import ma.munisys.dao.FactureRepository;
import ma.munisys.dao.PieceRepository;
import ma.munisys.dao.ProfileRepository;
import ma.munisys.dao.UserRepository;
import ma.munisys.dto.ContratSearch;
import ma.munisys.entities.AppProfile;
import ma.munisys.entities.AppUser;
import ma.munisys.entities.Authorisation;
import ma.munisys.entities.CommandeFournisseur;
import ma.munisys.entities.CommentaireContrat;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.ContratModel;
import ma.munisys.entities.Echeance;
import ma.munisys.entities.Event;
import ma.munisys.entities.Facture;
import ma.munisys.entities.FactureEcheance;
import ma.munisys.entities.OccurenceFacturation;
import ma.munisys.entities.PeriodeFacturation;
import ma.munisys.entities.Piece;
import ma.munisys.sap.dao.DBA;

@Service
@Transactional // spring pas javax
public class ContratServiceImp implements ContratService {
	
	private static final Logger LOGGER = LogManager.getLogger(ContratServiceImp.class);

	@Autowired
	private ContratRepository contratRepository;
	
	@Autowired
	private EcheanceRepository echeanceRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PieceRepository pieceRepository;
	
	@Autowired
	private FactureEcheanceRepository factureEcheanceRepository;
	
	@Autowired
	private CommentaireContratRepository commentaireContratRepository;

	@Override
	public Contrat saveContrat(Contrat contrat) {
		// TODO Auto-generated method stub
		LOGGER.info("saving contrat");
		return contratRepository.save(contrat);
	}

	@Override
	//@Async
	//@javax.transaction.Transactional
	public void loadContratFromSap() {
		LOGGER.info("Loading  contrat from SAP");
		Collection<Contrat> contrats2 =contratRepository.findAll();
		for(Contrat c : contrats2) {
			
			c.setCloture(true);
			
		}
		
		contratRepository.saveAll(contrats2);
		
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_ECH_CM\"";
			rs1 = DBA.request(req1);
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
			Map<Long, Contrat> contrats = new HashMap<Long, Contrat>();
			while (rs1.next()) {
				
				final Contrat contrat = new Contrat();
				
				if (rs1.getString(1) == null) {
					continue;
				}
				if (rs1.getString(1) != null ) {

					contrat.setNumContrat(rs1.getLong(1));
				}
				if (rs1.getString(2) != null ) {

					contrat.setCodePartenaire(rs1.getString(2));
				}
				if (rs1.getString(3) != null ) {

					contrat.setNomPartenaire(rs1.getString(3));
				}
				if (rs1.getString(4) != null ) {

					contrat.setStatut(rs1.getString(4));
				}
				if (rs1.getString(5) != null ) {

					contrat.setDu(sp.parse(rs1.getString(5).split("\\s+")[0]));
				}
				if (rs1.getString(6) != null ) {

					contrat.setAu(sp.parse(rs1.getString(6).split("\\s+")[0]));
				}
				if (rs1.getString(7) != null) {
					contrat.setDescription(rs1.getString(7));
				}
				if (rs1.getString(8) != null ) {

					contrat.setNomSousTraitant(rs1.getString(8));
					if(contrat.getNomSousTraitant()!=null && !contrat.getNomSousTraitant().isEmpty()) {
						contrat.setSousTraiter(true);
					}
					
				}
				if (rs1.getString(9) != null ) {

					if (rs1.getString(9).toUpperCase().equals("OUI")) {
						contrat.setContratSigne(true);
					} else {
						contrat.setContratSigne(false);
					}

				}
				if (rs1.getString(10) != null ) {
					contrat.setCodeProjet(rs1.getString(10));
				}
				if (rs1.getString(11) != null) {
					contrat.setNumMarche(rs1.getString(11));
				}
				if (rs1.getString(12) != null ) {
					contrat.setPilote(rs1.getString(12));
				}
				if (rs1.getString(13) != null ) {
					contrat.setMontantContrat(Double.valueOf(rs1.getDouble(13)));
					contrat.setMontantFactureAn(0.0);
					contrat.setMontantRestFactureAn(contrat.getMontantContrat());
				}
				if (rs1.getString(14) != null ) {

					PeriodeFacturation periodeFacturation;
					try {
						periodeFacturation = PeriodeFacturation.valueOf(rs1.getString(14).toUpperCase());
					} catch (Exception e) {
						periodeFacturation = PeriodeFacturation.UNKNOWN;
					}
					contrat.setPeriodeFacturationLabel(rs1.getString(14));
					contrat.setPeriodeFacturation(periodeFacturation);
				}
				if (rs1.getString(15) != null ) {

					OccurenceFacturation c;

					switch (rs1.getString(15).toUpperCase()) {
					case "LE 31":
						c = OccurenceFacturation.FINPERIODE;
						break;
					case "LE 1":
						c = OccurenceFacturation.DEBUTPERIODE;
						break;
					default:
						c = OccurenceFacturation.UNKNOWNPERIODE;
					}
					contrat.setOccurenceFacturationLabel(rs1.getString(15));
					contrat.setOccurenceFacturation(c);
				}
				
			
				if (!contrats.containsKey(contrat.getNumContrat()) && contrat.getNumContrat() != null) {
					
					contrats.put(contrat.getNumContrat(), contrat);

				} else {
					LOGGER.error("double contrat " + contrat.getNumMarche());
				}
			}
			
			for (Map.Entry<Long, Contrat> entry : contrats.entrySet()) {
				Contrat c =updateContratFromSap(entry.getValue());
				contratRepository.save(c);
			}
			/*
			LOGGER.info("Loading modele contrat from SAP");
			
		
				String req2 = "SELECT * FROM DB_MUNISYS.\"V_CM_MOD\"";
				rs2 = DBA.request(req2);
				java.sql.ResultSetMetaData rsmd1 = rs2.getMetaData();
				for (int columnCount = rsmd1.getColumnCount(), i = 1; i <= columnCount; ++i) {
					final String name = rsmd1.getColumnName(i);
					System.out.println("column Name " + name);
				}
				
				while (rs2.next()) {
					
					ContratModel contratModel = new ContratModel();
					 Contrat currentContrat=null;
					
					 
					if (rs2.getString(1) != null  ) {
						  currentContrat = contratRepository.findById(rs2.getLong(1)).orElse(null);
						 if(currentContrat==null) {
							 continue;
						 }
		
							
						}
				
					if(rs2.getString(1) != null && rs2.getString(2)!=null) {
						
						contratModel.setId(rs2.getLong(1)+"-"+rs2.getString(2));
					}
					
					if (rs2.getString(4) != null) {

						PeriodeFacturation periodeFacturation;
						try {
							periodeFacturation = PeriodeFacturation.valueOf(rs2.getString(4).toUpperCase());
						} catch (Exception e) {
							periodeFacturation = PeriodeFacturation.UNKNOWN;
						}
						
						contratModel.setPeriodeFacturationLabel(rs2.getString(4));
						contratModel.setPeriodeFacturation(periodeFacturation);
					
					}
					if (rs2.getString(3) != null) {
						contratModel.setMontant(rs2.getDouble(3));
					}
					
					
					if (rs2.getString(5) != null ) {

						contratModel.setDu(sp.parse(rs2.getString(5).split("\\s+")[0]));
					}
					
					if (rs2.getString(6) != null) {

						contratModel.setAu(sp.parse(rs2.getString(6).split("\\s+")[0]));
					}
					
					contratModel.setContrat(currentContrat);
					
					boolean found = false;
					ContratModel lastContratModelFound=null;
					for(ContratModel lastContratModel : currentContrat.getContratsModel()) {
						if(contratModel.getId().equals(lastContratModel.getId())) {
							lastContratModel.setCloture(false);
							
							for(Echeance e : lastContratModel.getEcheances()) {
								if(!e.isDeletedByUser()) {
									e.setCloture(false);
								}
							}
							contratRepository.save(currentContrat);
							found =true;
							lastContratModelFound = lastContratModel;
							break;
						}
					}
					
					if(!found) {
						currentContrat.getContratsModel().add(contratModel);
						currentContrat.getEcheances().addAll(generateEcheanceModele(contratModel));
						contratRepository.save(currentContrat);
					}else {
						
						if(lastContratModelFound.getAu()!=null && contratModel.getAu()!=null && lastContratModelFound.getAu().before(contratModel.getAu())) {
							
						Set<Echeance> echeances = generateEcheanceModele( contratModel);
						
						for(Echeance e : echeances) {
							boolean exist = false;
							for(Echeance e1: lastContratModelFound.getEcheances()) {
								if(e.equals(e1)) {
									exist = true;
									break;
								}
							}
							if(!exist) {
								currentContrat.getEcheances().add(e);
							}
						}
						
						contratRepository.save(currentContrat);
						}
					}
								
				}*/
				
			LOGGER.info("done synchro contrat");
		
		}

		catch (Exception e) {
			LOGGER.error("Error loading contrat from Sap " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(rs2!=null) {
				try {
					rs2.close();
					DBA.getConnection().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Error closing connextion  to Sap " + e.getMessage());
					e.printStackTrace();
				}
			}
			
			if (rs1 != null) {
				try {
					rs1.close();
					DBA.getConnection().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Error closing connextion  to Sap " + e.getMessage());
					e.printStackTrace();
				}
			}

		}
		
		//return CompletableFuture.completedFuture("loading Contrats");
	}
	

	public Contrat updateContratFromSap(Contrat contrat) {
		
			Contrat lastContrat = contratRepository.findById(contrat.getNumContrat()).orElse(null);
			if(lastContrat!=null) {
				lastContrat.setPeriodeFacturation(contrat.getPeriodeFacturation());
				lastContrat.setCodeProjet(contrat.getCodeProjet());
				lastContrat.setMontantContrat(contrat.getMontantContrat());
				lastContrat.setPilote(contrat.getPilote());
				lastContrat.setOccurenceFacturation(contrat.getOccurenceFacturation());
				lastContrat.setLastUpdate(contrat.getLastUpdate());
				lastContrat.setCloture(false);
				return lastContrat;
			}else {
				
				contrat.setCloture(false);
				return contrat;
			}

	}
	
	

	
	@Override
	public CommentaireContrat addCommentaire(Long numContrat,CommentaireContrat commentaire) {
		LOGGER.info("adding commentaire to contrat " + numContrat);
		
		Contrat c =new Contrat();
		c.setNumContrat(numContrat);
		commentaire.setContrat(c);
		
		return commentaireContratRepository.save(commentaire);
		
		
	}

	@Override
	public Collection<Contrat> getContratByPredicate(ContratSearch contratSearch) {
		LOGGER.info("getContratByPredicate ");
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int startYear=year-1;
		int endYear = year;
		ContratSpecification contratSpecification = new ContratSpecification(contratSearch);
		Collection<Contrat> contrats = contratRepository.findAll(contratSpecification);
		
		return sortingContratInPeriode(contrats,startYear,endYear);
	}

	@Override
	public Collection<Contrat> getAllContrats() {
		LOGGER.info("Getting all contrats");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int startYear=year-1;
		int endYear = year;
		
		Collection<Contrat> contrats = contratRepository.findAll();
		
		return sortingContratInPeriode(contrats,startYear,endYear);
	}
	
	private Collection<Contrat> sortingContratInPeriode(Collection<Contrat> contrats,int startDate,int endDate){
		LOGGER.info("Sorting contrats in periode");
		Collection<Contrat> contratsRes = new ArrayList<Contrat>();
		int addingId=0;
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		try {
		for(Contrat c : contrats) {
			if(!c.getCloture()) {
				Contrat c1 = (Contrat)c.clone();
				SortedSet<Echeance> echeances =new TreeSet<Echeance>(new Echeance());
				SortedSet<FactureEcheance> factureEcheances =new TreeSet<FactureEcheance>(new FactureEcheance());
				Collection<FactureEcheance> facturesEcheancess = new ArrayList<FactureEcheance>();
				SortedSet<CommandeFournisseur> commandeFournisseurs =new TreeSet<CommandeFournisseur>(new CommandeFournisseur());
				commandeFournisseurs.addAll( c.getCommandesFournisseurs());
				
				for(Echeance  e:c1.getEcheances()) {
					
					Calendar cAu = Calendar.getInstance();
					Calendar cDu = Calendar.getInstance();
					cAu.setTime(e.getAu());
					cDu.setTime(e.getDu());
					
					if(!e.getCloture() && (cAu.get(Calendar.YEAR) == currentYear || cDu.get(Calendar.YEAR)== currentYear )) {
						System.out.println("echeance sort "+ e.getId()+" du " + e.getDu() +" au "+e.getAu());
						Calendar calendarDu = new java.util.GregorianCalendar();
						calendarDu.setTime(e.getDu());
						Calendar calendarEnd = new java.util.GregorianCalendar();
						calendarEnd.setTime(e.getAu());
						if(calendarDu.get(Calendar.YEAR)==startDate || calendarEnd.get(Calendar.YEAR)==startDate || calendarDu.get(Calendar.YEAR)==endDate || calendarEnd.get(Calendar.YEAR)==endDate  ) {
								echeances.add(e);
							for(FactureEcheance fe :e.getFactureEcheances()) {
								//System.out.println("numFacture " +fe.getFacture().getNumFacture());
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(fe.getFacture().getDateEnregistrement());
								if(calendar.get(Calendar.YEAR)==currentYear) {
									FactureEcheance fe2 = (FactureEcheance) fe.clone();
									addingId = addingId+1;
									fe2.setId(fe2.getId()+""+addingId);
									facturesEcheancess.add(fe2);
									factureEcheances.add(fe2);
								}
							}
						}
					}
				}
				
				for(FactureEcheance fe :c1.getFactureEcheances()) {
					//System.out.println("numFacture " +fe.getFacture().getNumFacture());
					if(fe.getEcheance()==null) {
						System.out.println("fe "  + fe.getId());
						FactureEcheance fe2 = (FactureEcheance) fe.clone();
						addingId= addingId+1;
						fe2.setId(fe2.getId()+""+addingId);
						facturesEcheancess.add(fe2);
						factureEcheances.add(fe2);
					}
				}
				
				c1.setEcheances(echeances);
				c1.setFactureEcheances(factureEcheances);
				c1.setCommandesFournisseurs(commandeFournisseurs);
				contratsRes.add(c1);
			}
		}
		
		}catch (CloneNotSupportedException e) {
			LOGGER.error("error clone object " , e);
			e.printStackTrace();
		}
		
		return contratsRes;
	}

	@Override
	//@Async
	public void loadContratPieceSap() {
		LOGGER.info("load piece contrat from SAP");
		
		Collection<Contrat> allContrats = this.contratRepository.findAll();
		
		Map<Long, Contrat> contrats2 = 
				allContrats.stream().collect(Collectors.toMap(Contrat::getNumContrat, contrat -> contrat));

		
		ResultSet rs1 = null;
		try {
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_CM_PJ\"";
			rs1 = DBA.request(req1);
			java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
			for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
				final String name = rsmd.getColumnName(i);
				//System.out.println("column Name " + name);
			}
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
			Map<Long, Contrat> contrats = new HashMap<Long, Contrat>();
			while (rs1.next()) {
				Piece p = new Piece();
				final Contrat contrat = new Contrat();
				
				// chemin nomfichier extension

				if (rs1.getString(2) == null || !contrats2.containsKey(rs1.getLong(2))) {
					continue;
				}else {
					Contrat c = contrats2.get(rs1.getLong(2));
					p.setContrat(c);
					
				}
				if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {

					p.setType(rs1.getString(1));
					//System.out.println("rs1.getString(1) " + rs1.getString(1));
				}
				if (rs1.getString(3) != null && rs1.getString(4)!=null && rs1.getString(5)!=null) {
				
					p.setName(rs1.getString(4));
					p.setFullPath(rs1.getString(3)+"\\"+rs1.getString(4)+"."+rs1.getString(5));
					
				}else {
					continue;
				}
				
				pieceRepository.save(p);
				
			}

		
		}

		catch (Exception e) {
			LOGGER.info("erorr loading piece de contrat from SAP" +e.getMessage());
			e.printStackTrace();
		} finally {
			if (rs1 != null) {
				try {
					rs1.close();
					DBA.getConnection().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					LOGGER.info("erorr closing connection sap " +e.getMessage());
					e.printStackTrace();
				}
			}

		}
		
		LOGGER.info("End load piece contrat from SAP");
		//return CompletableFuture.completedFuture("loaded Piece de Contrat");
		
	}

	@Override
	public Page<Contrat> getAllContrats(int page, int size) {
		
		Page<Contrat> contrats= contratRepository.getAllContrats(PageRequest.of(page-1, size));
		
		return contrats;
		//System.out.println("contrats " + contrats.getTotalElements());
		
		/*List<Contrat> contratsCloned = new ArrayList<>();
		
		for(Contrat c : contrats) {
			Contrat c1;
			try {
				c1 = (Contrat) c.clone();
				c1.setEcheances(null);
				c1.setFactureEcheances(null);
				c1.setFactures(null); 
				c1.setContratsModel(null);
				c1.setCommandesFournisseurs(null);
				c1.setPieces(null);
				contratsCloned.add(c1);
			} catch (CloneNotSupportedException e) {
				Log.error(e.getMessage(),e);
			}
			
		}*/
		
		//System.out.println("contrats " + contrats.getTotalElements());
		
		//return  new PageImpl<>(contratsCloned,PageRequest.of(page, size),contrats.getTotalElements());
	}

	@Override
	public Contrat getContrat(Long numContrat) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		Contrat c = contratRepository.findById(numContrat).orElse(null);
		
		
		if(c!=null) {
			Contrat contratClone;
			try {
				contratClone = (Contrat) c.clone();
				List<Echeance> echeancesToRemove = new ArrayList<Echeance>();
				List<FactureEcheance> factureEcheancesToRemove = new ArrayList<FactureEcheance>();
				
					for(Echeance e : contratClone.getEcheances()) {
						Calendar calendarEnd = Calendar.getInstance();
						Calendar calendarDu = Calendar.getInstance();
						calendarEnd.setTime(e.getAu());
						calendarDu.setTime(e.getDu());
						
					
						if(calendarDu.get(Calendar.YEAR)!=year && calendarEnd.get(Calendar.YEAR)!=year &&  calendarDu.get(Calendar.YEAR)!=year-1 &&  calendarEnd.get(Calendar.YEAR)!=year-1  ) {
							echeancesToRemove.add(e);
							
						}
						
					}
					
					for(FactureEcheance fe : c.getFactureEcheances()) {
						if(fe.getEcheance()!=null) {
							if(echeancesToRemove.contains(fe.getEcheance())) {
								factureEcheancesToRemove.add(fe);
							}
						}
					}
								
				contratClone.getEcheances().removeAll(echeancesToRemove);
				contratClone.getFactureEcheances().removeAll(factureEcheancesToRemove);
				contratClone.setFactures(new HashSet<Facture>());
				
				SortedSet<Echeance> echeances =new TreeSet<Echeance>(contratClone.getEcheances());
				SortedSet<FactureEcheance> factureEcheances =new TreeSet<FactureEcheance>(contratClone.getFactureEcheances());
				SortedSet<CommandeFournisseur> commandeFournisseurs =new TreeSet<CommandeFournisseur>(contratClone.getCommandesFournisseurs());
				
				contratClone.setEcheances(echeances);
				contratClone.setFactureEcheances(factureEcheances);
				contratClone.setCommandesFournisseurs(commandeFournisseurs);
				
			} catch (CloneNotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		
	}

	return c;
	}

	

	@Override
	public Page<Contrat> getContratByPredicate2(ContratSearch contratSearch,int page,int size,String sortBy,String sortType) {
		LOGGER.info("getContratByPredicate2 ");
	
		ContratSpecification contratSpecification = new ContratSpecification(contratSearch);
		
		if(sortBy!=null) {
			
			if("asc".equals(sortType)) {
				return contratRepository.findAll(contratSpecification,PageRequest.of(page-1, size,Sort.by(sortBy).ascending()));
				
			}else {
				return contratRepository.findAll(contratSpecification,PageRequest.of(page-1, size,Sort.by(sortBy).descending()));
			}
			
		}else {
			return contratRepository.findAll(contratSpecification,PageRequest.of(page-1, size));
		}
				
	    
		
		
	}

	@Override
	public Collection<Contrat> getAllContratsWithPredicate(ContratSearch contratSearch) {
		ContratSpecification contratSpecification = new ContratSpecification(contratSearch);
		return contratRepository.findAll(contratSpecification);
	}

	@Override
	public void deleteCommentaire(Long idCommentaire) {
		commentaireContratRepository.deleteById(idCommentaire);
		
	}

}
