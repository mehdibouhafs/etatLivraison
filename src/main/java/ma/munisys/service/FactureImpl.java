package ma.munisys.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.util.Log;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public Facture saveFacutre(Facture facture) {
		// TODO Auto-generated method stub
		return factureRepository.save(facture);
	}

	@Override
	@Async
	public CompletableFuture<String> loadFactureFromSap() {
		LOGGER.info("load facture from SAP");

		// factureRepository.deleteAll();

		ResultSet rs1 = null;
		Set<Facture> factures = new HashSet<Facture>();

		try {
			String req1 = "SELECT * FROM DB_MUNISYS.\"V_FACT_MAINT\"";
			rs1 = DBA.request(req1);
			java.sql.ResultSetMetaData rsmd = rs1.getMetaData();
			for (int columnCount = rsmd.getColumnCount(), i = 1; i <= columnCount; ++i) {
				final String name = rsmd.getColumnName(i);
				// System.out.println("column Name " + name);
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

					try {
						contrat = contratRepository.findById(rs1.getLong(2)).get();
						facture.setContrat(contrat);
					} catch (Exception e) {
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
						f.getFactureEcheances().add(fe);
					}
				} else {
					FactureEcheance fe = new FactureEcheance();
					fe.setId(f.getNumFacture() + "/" + f.getContrat().getNumContrat());
					fe.setContrat(f.getContrat());
					fe.setFacture(f);
					fe.setCloture(false);
					fe.setMontant(f.getMontantHT());
					f.getFactureEcheances().add(fe);
				}
			}

			factureRepository.saveAll(factures);

			int currentYear = Calendar.getInstance(TimeZone.getTimeZone("africa/Casablanca")).get(Calendar.YEAR);

			for (Contrat c : contratRepository.findAll()) {
				
					for (FactureEcheance fe : c.getFactureEcheances()) {

						Echeance e = fe.getEcheance();
						if (e != null && !e.getCloture()) {
							e.calculMontantFacture();
							echeanceRepository.save(e);
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

		return CompletableFuture.completedFuture("loaded Factures");
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
			if(!e.getCloture()) {
				if (f.getDebutPeriode() != null && f.getFinPeriode() != null
						&& new DateTime(f.getDebutPeriode()).toLocalDate()
								.compareTo(new DateTime(e.getDu()).toLocalDate()) == 0
						&& f.getFinPeriode() != null && new DateTime(f.getFinPeriode()).toLocalDate()
								.compareTo(new DateTime(e.getAu()).toLocalDate()) == 0) {
					echeances.add(e);
				}
			}
		}
		return echeances;

	}

}
