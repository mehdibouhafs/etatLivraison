package ma.munisys.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ma.munisys.dao.FactureRepository;
import ma.munisys.dao.ContratRepository;
import ma.munisys.dao.EcheanceRepository;
import ma.munisys.dao.FactureEcheanceRepository;
import ma.munisys.entities.Facture;
import ma.munisys.entities.FactureEcheance;
import ma.munisys.entities.Contrat;
import ma.munisys.entities.Echeance;
import ma.munisys.sap.dao.DBA;

@Service
//@Transactional // spring pas javax
public class FactureImpl implements FactureService {

	private static final Logger LOGGER = LogManager.getLogger(FactureImpl.class);

	@Autowired
	private FactureRepository factureRepository;

	@Autowired
	private ContratRepository contratRepository;

	@Autowired
	private EcheanceRepository echeanceRepository;

	@Autowired
	private FactureEcheanceRepository factureEcheanceRepository;

	@Autowired
	private ContratService contratService;

	@Override
	public Facture saveFacutre(Facture facture) {
		// TODO Auto-generated method stub
		return factureRepository.save(facture);
	}

	@Override
	// @Async
	@javax.transaction.Transactional
	public void loadFactureFromSap() {

		LOGGER.info("load facture from SAP");

		Collection<Contrat> allContrats = this.contratRepository.findAll();

		Map<Long, Contrat> contrats = allContrats.stream()
				.collect(Collectors.toMap(Contrat::getNumContrat, contrat -> contrat));
		ResultSet rs1 = null;
		Set<Facture> factures = new HashSet<Facture>();

		try {
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_FACT_MAINT\"";
			rs1 = DBA.request(req1);
			java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
			for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
				final String name = rsmd.getColumnName(i);
				//System.out.println("column Name " + name);
			}
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
			Contrat contrat = null;
			while (rs1.next()) {
				final Facture facture = new Facture();

				if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {

					facture.setNumFacture(rs1.getLong(1));
				} else {
					continue;
				}
				if (rs1.getString(2) != null && !rs1.getString(2).equals("null")) {

					if (contrats.containsKey(rs1.getLong(2))) {
						contrat = contrats.get(rs1.getLong(2));
						facture.setContrat(contrat);
					} else {
						continue;
					}

				}
				if (rs1.getString(3) != null && !rs1.getString(3).equals("null")) {

					facture.setDateEnregistrement(sp.parse(rs1.getString(3).split("\\s+")[0]));
				}
				if (rs1.getString(4) != null && !rs1.getString(4).equals("null")) {

					facture.setMontantTTC(rs1.getDouble(4));
				} else {
					// System.out.println("nulllll");
				}

				if (rs1.getString(5) != null && !rs1.getString(5).equals("null")) {

					facture.setMontantHT(rs1.getDouble(5));
				}

				if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {

					facture.setMontantRestant(rs1.getDouble(6));
				}

				if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {

					facture.setDebutPeriode(sp.parse(rs1.getString(7).split("\\s+")[0]));
				}
				if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
					facture.setFinPeriode(sp.parse(rs1.getString(8).split("\\s+")[0]));
				}

				factures.add(facture);

			}

			factureRepository.saveAll(factures);

			for (Facture f : factures) {
				//System.out.println("numFacture " + f.getNumFacture());
			
				Collection<FactureEcheance> factureEcheances = factureEcheanceRepository.getFactureEcheance(f.getContrat().getNumContrat(), f.getNumFacture());
				
				List<String> factureEcheancesAffectedByUser = new ArrayList<String>();
				
				for(FactureEcheance fe:factureEcheances) {
					if(fe.isAffectedByUser()) {
						factureEcheancesAffectedByUser.add(fe.getId());
					}
				}
				
				FactureEcheance lastFactureEchanceNotLinked = factureEcheanceRepository
						.findById(f.getNumFacture() + "/" + f.getContrat().getNumContrat()).orElse(null);
				if (lastFactureEchanceNotLinked != null && !lastFactureEchanceNotLinked.isAffectedByUser()) {
					factureEcheanceRepository.delete(lastFactureEchanceNotLinked);
				}
				
					List<Echeance> ecs = affecterEcheance(f);
					if (ecs != null && !ecs.isEmpty()) {

						for (Echeance e : ecs) {

							FactureEcheance fe = new FactureEcheance();
							
							fe.setId(f.getNumFacture() + "/" + e.getId() + "/" + f.getContrat().getNumContrat());
							fe.setContrat(f.getContrat());
							fe.setFacture(f);
							fe.setMontant(f.getMontantHT() / ecs.size());
							fe.setEcheance(e);
							fe.setCloture(false);
							
							if(!factureEcheancesAffectedByUser.contains(fe.getId())) {
								factureEcheanceRepository.saveAndFlush(fe);
							}
							

						}
					} else {
							FactureEcheance fe = new FactureEcheance();
							fe.setId(f.getNumFacture() + "/" + f.getContrat().getNumContrat());
							fe.setContrat(f.getContrat());
							fe.setFacture(f);
							fe.setCloture(false);
							fe.setMontant(f.getMontantHT());
							
							boolean found=false;
							for(Echeance e : f.getContrat().getEcheances()) {
								String idFactEcheance = f.getNumFacture() + "/" + e.getId() + "/" + f.getContrat().getNumContrat();
								if(factureEcheancesAffectedByUser.contains(idFactEcheance)) {
									found=true;
									break;
								}
							}
							
							if(!found) {
								factureEcheanceRepository.saveAndFlush(fe);
							}
						
					}
				}
	
				

			int currentYear = Calendar.getInstance(TimeZone.getTimeZone("africa/Casablanca")).get(Calendar.YEAR);

			for (Contrat c : contratRepository.findAll()) {

				for (FactureEcheance fe : c.getFactureEcheances()) {
					
					if(fe.getEcheance()!=null && !fe.getEcheance().getCloture()) {
						Collection<FactureEcheance> factureEcheances = factureEcheanceRepository.getFactureEcheance(fe.getEcheance().getId());
						if(factureEcheances!=null) {
							fe.getEcheance().setFactureEcheances(new HashSet<>(factureEcheances));
							fe.getEcheance().calculMontantFacture();
							echeanceRepository.saveAndFlush(fe.getEcheance());
						}
					
					}
					
					
					
				}

				Double montantFactureAn = factureRepository.sumAmountContrat(c.getNumContrat(), currentYear);

				if (montantFactureAn != null) {
					c.setMontantFactureAn(montantFactureAn);
				} else {
					c.setMontantFactureAn(0.0);
				}

				Double montantProvisonFacture = factureRepository.sumAmountContratInfAn(c.getNumContrat(), currentYear);
				Double montantRestAFactureInfAn = echeanceRepository.sumAmountRestAFacture(c.getNumContrat(),
						currentYear);
				if (montantProvisonFacture != null) {
					c.setMontantProvisionFactureInfAnneeEnCours(montantProvisonFacture);
					if (montantRestAFactureInfAn != null && montantRestAFactureInfAn - montantProvisonFacture > 0) {
						c.setMontantProvisionAFactureInfAnneeEnCours(montantRestAFactureInfAn - montantProvisonFacture);
					} else {
						c.setMontantProvisionAFactureInfAnneeEnCours(0.00);
					}
				} else {
					c.setMontantProvisionFactureInfAnneeEnCours(0.0);
					if (montantRestAFactureInfAn != null) {
						c.setMontantProvisionAFactureInfAnneeEnCours(montantRestAFactureInfAn);
					} else {
						c.setMontantProvisionAFactureInfAnneeEnCours(0.0);
					}
				}

				contratRepository.save(c);

			}

			LOGGER.info("end loading facture ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs1 != null) {
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

	public List<Echeance> genereateEcheance(Facture f) {

		List<Echeance> echeances = new ArrayList<>();

		for (Echeance e : f.getContrat().getEcheances()) {
			if (f.getDebutPeriode() != null && f.getFinPeriode() != null && f.getDebutPeriode().before(e.getAu())
					&& e.getDu().before(f.getFinPeriode())) {
				echeances.add(e);
			}
		}
		return echeances;

	}

	public FactureEcheanceRepository getFactureEcheanceRepository() {
		return factureEcheanceRepository;
	}

	public void setFactureEcheanceRepository(FactureEcheanceRepository factureEcheanceRepository) {
		this.factureEcheanceRepository = factureEcheanceRepository;
	}

	public List<Echeance> affecterEcheance(Facture f) {

		List<Echeance> echeances = new ArrayList<>();

		for (Echeance e : f.getContrat().getEcheances()) {
			if (!e.getCloture() && !e.isAddedByUser()) {

				if (f.getDebutPeriode() != null && f.getFinPeriode() != null
						&& new DateTime(f.getDebutPeriode()).toLocalDate()
								.compareTo(new DateTime(e.getDu()).toLocalDate()) == 0
						&& f.getFinPeriode() != null && new DateTime(f.getFinPeriode()).toLocalDate()
								.compareTo(new DateTime(e.getAu()).toLocalDate()) == 0) {

					if (e.getMontant() != null) {
						if (f.getMontantHT() >= e.getMontant()
								&& f.getMontantHT() <= (e.getMontant() + (e.getMontant() * 0.05))) {
							echeances.add(e);
						}
					} else {
						if (e.getMontantPrevision() != null && f.getMontantHT() >= e.getMontantPrevision()
								&& f.getMontantHT() <= (e.getMontantPrevision() + (e.getMontantPrevision() * 0.05))) {
							echeances.add(e);
						}
					}

				}
			}
		}
		return echeances;

	}

	@Override
	public FactureEcheance updateFactureEcheance(Long numContrat, FactureEcheance factureEcheance) {

		FactureEcheance fe=null;
		Contrat c = new Contrat();
		c.setNumContrat(numContrat);
		factureEcheance.setContrat(c);
		
		factureEcheance.setMontant(factureEcheance.getMontant());
		factureEcheance.setAffectedByUser(true);
		
		FactureEcheance lastFactureEcheance = factureEcheanceRepository.findById(factureEcheance.getId()).orElse(null);
		if(lastFactureEcheance!=null) {
			
			lastFactureEcheance.setCloture(true);
			
			factureEcheanceRepository.delete(lastFactureEcheance);
			if(lastFactureEcheance.getEcheance()!=null) {
				Long idLastEcheance = lastFactureEcheance.getEcheance().getId();
				
				Echeance e = echeanceRepository.findById(idLastEcheance).orElse(null);
				
				e.calculMontantFacture();
				
				echeanceRepository.save(e);
			}
			
			
		}
			
			lastFactureEcheance.setCloture(false);
			lastFactureEcheance.setAffectedByUser(true);
			
			factureEcheance.setMontant(factureEcheance.getFacture().getMontantHT());
			
			if(factureEcheance.getEcheance()!=null ) {
				
				factureEcheance.setId(factureEcheance.getFacture().getNumFacture()
						+ "/"+ factureEcheance.getEcheance().getId()+ "/" + factureEcheance.getContrat().getNumContrat());	
				
				
				
			}else {
				
				factureEcheance.setId(factureEcheance.getFacture().getNumFacture() + "/"
						 + factureEcheance.getContrat().getNumContrat());	
				
			}
			
			fe =factureEcheanceRepository.save(factureEcheance);
			
			
			if(factureEcheance.getEcheance()!=null) {
				Echeance e2 = echeanceRepository.findById(factureEcheance.getEcheance().getId()).orElse(null);

				if (e2 != null) {

					e2.getFactureEcheances().add(factureEcheance);
					e2.calculMontantFacture();
					factureEcheance.setEcheance(e2);
					echeanceRepository.save(e2);
				}
			}
			
			
		
		return fe;
	}

	@Override
	public Page<FactureEcheance> getFactureEcheance(Long numContrat, int page, int size, String sortBy,
			String sortType) {
		if (sortBy != null) {

			if ("asc".equals(sortType)) {
				return factureEcheanceRepository.getFactureEcheanceWithoutOrder(numContrat,
						PageRequest.of(page, size, Sort.by(sortBy).ascending()));

			} else {
				return factureEcheanceRepository.getFactureEcheanceWithoutOrder(numContrat,
						PageRequest.of(page, size, Sort.by(sortBy).descending()));
			}

		} else {
			return factureEcheanceRepository.getFactureEcheance(numContrat, PageRequest.of(page, size));
		}
	}

	@Override
	public void loadFactureFromSapByContrat(Long numContrat) {

		LOGGER.info("load facture from SAP for contrat " + numContrat);

		ResultSet rs1 = null;
		Set<Facture> factures = new HashSet<Facture>();

		try {
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_FACT_MAINT\" where \"ContractID\"= " + numContrat;
			rs1 = DBA.request(req1);
			java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
			for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
				final String name = rsmd.getColumnName(i);
				System.out.println("column Name " + name);
			}
			SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
			Contrat contrat = null;
			while (rs1.next()) {
				final Facture facture = new Facture();

				if (rs1.getString(1) != null && !rs1.getString(1).equals("null")) {

					facture.setNumFacture(rs1.getLong(1));
				} else {
					continue;
				}
				if (rs1.getString(2) != null && !rs1.getString(2).equals("null")) {

					contrat = contratRepository.findById(rs1.getLong(2)).orElse(null);

					if (contrat != null) {
						facture.setContrat(contrat);
					} else {
						continue;
					}

				}
				if (rs1.getString(3) != null && !rs1.getString(3).equals("null")) {

					facture.setDateEnregistrement(sp.parse(rs1.getString(3).split("\\s+")[0]));
				}
				if (rs1.getString(4) != null && !rs1.getString(4).equals("null")) {

					facture.setMontantTTC(rs1.getDouble(4));
				} else {
					// System.out.println("nulllll");
				}

				if (rs1.getString(5) != null && !rs1.getString(5).equals("null")) {

					facture.setMontantHT(rs1.getDouble(5));
				}

				if (rs1.getString(6) != null && !rs1.getString(6).equals("null")) {

					facture.setMontantRestant(rs1.getDouble(6));
				}

				if (rs1.getString(7) != null && !rs1.getString(7).equals("null")) {

					facture.setDebutPeriode(sp.parse(rs1.getString(7).split("\\s+")[0]));
				}
				if (rs1.getString(8) != null && !rs1.getString(8).equals("null")) {
					facture.setFinPeriode(sp.parse(rs1.getString(8).split("\\s+")[0]));
				}

				factures.add(facture);

			}

			for (Facture f : factures) {
				// if(f.getNumFacture()==2000426L) {
				factureRepository.saveAndFlush(f);

				List<Echeance> ecs = affecterEcheance(f);
				if (ecs != null && !ecs.isEmpty()) {

					FactureEcheance lastFactureEchanceNotLinked = factureEcheanceRepository
							.findById(f.getNumFacture() + "/" + f.getContrat().getNumContrat()).orElse(null);
					if (lastFactureEchanceNotLinked != null) {
						factureEcheanceRepository.delete(lastFactureEchanceNotLinked);
					}

					for (Echeance e : ecs) {

						FactureEcheance lastFactureEcheance = factureEcheanceRepository
								.findById(f.getNumFacture() + "/" + e.getId() + "/" + f.getContrat().getNumContrat())
								.orElse(null);

						FactureEcheance fe = new FactureEcheance();
						;
						fe.setId(f.getNumFacture() + "/" + e.getId() + "/" + f.getContrat().getNumContrat());
						fe.setContrat(f.getContrat());
						fe.setFacture(f);
						fe.setMontant(f.getMontantHT());
						fe.setEcheance(e);
						fe.setCloture(false);

						if (lastFactureEcheance == null || !lastFactureEcheance.isAffectedByUser()) {
							factureEcheanceRepository.saveAndFlush(fe);
						}

					}
				} else {

					Collection<FactureEcheance> factureEcheances = factureEcheanceRepository
							.getFactureEcheance(f.getContrat().getNumContrat(), f.getNumFacture());

					boolean affectedByUser = false;

					if (!factureEcheances.isEmpty()) {
						for (FactureEcheance factureEcheance : factureEcheances) {
							if (factureEcheance.isAffectedByUser()) {
								affectedByUser = true;
								break;
							}
						}
					}

					if (!affectedByUser) {
						FactureEcheance fe = new FactureEcheance();
						fe.setId(f.getNumFacture() + "/" + f.getContrat().getNumContrat());
						fe.setContrat(f.getContrat());
						fe.setFacture(f);
						fe.setCloture(false);
						fe.setMontant(f.getMontantHT());
						factureEcheanceRepository.saveAndFlush(fe);
					}

				}
			}

			if (contrat != null) {
				int currentYear = Calendar.getInstance(TimeZone.getTimeZone("africa/Casablanca")).get(Calendar.YEAR);

				Contrat c = contratRepository.findById(contrat.getNumContrat()).orElse(null);

				for (FactureEcheance fe : c.getFactureEcheances()) {
					if(fe.getEcheance()!=null) {
						Collection<FactureEcheance> factureEcheances = factureEcheanceRepository.getFactureEcheance(fe.getEcheance().getId());
						if(factureEcheances!=null && !fe.getEcheance().getCloture()) {
							fe.getEcheance().setFactureEcheances(new HashSet<>(factureEcheances));
							fe.getEcheance().calculMontantFacture();
							echeanceRepository.saveAndFlush(fe.getEcheance());
						}
					
					}
				}
						
					
				Double montantFactureAn = factureRepository.sumAmountContrat(c.getNumContrat(), currentYear);

				if (montantFactureAn != null) {
					c.setMontantFactureAn(montantFactureAn);
				} else {
					c.setMontantFactureAn(0.0);
				}

				Double montantProvisonFacture = factureRepository.sumAmountContratInfAn(c.getNumContrat(), currentYear);
				Double montantRestAFactureInfAn = echeanceRepository.sumAmountRestAFacture(c.getNumContrat(),
						currentYear);
				if (montantProvisonFacture != null) {
					c.setMontantProvisionFactureInfAnneeEnCours(montantProvisonFacture);
					if (montantRestAFactureInfAn != null && montantRestAFactureInfAn - montantProvisonFacture > 0) {
						c.setMontantProvisionAFactureInfAnneeEnCours(montantRestAFactureInfAn - montantProvisonFacture);
					} else {
						c.setMontantProvisionAFactureInfAnneeEnCours(0.00);
					}
				} else {
					c.setMontantProvisionFactureInfAnneeEnCours(0.0);
					if (montantRestAFactureInfAn != null) {
						c.setMontantProvisionAFactureInfAnneeEnCours(montantRestAFactureInfAn);
					} else {
						c.setMontantProvisionAFactureInfAnneeEnCours(0.0);
					}
				}

				contratRepository.saveAndFlush(c);
			}
			// }

			LOGGER.info("end loading facture ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs1 != null) {
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

}
