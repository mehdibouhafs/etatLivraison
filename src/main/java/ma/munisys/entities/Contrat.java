package ma.munisys.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contrat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String codeProjet;
	
	private String codeSap;
	
	private String pilote;
	
	private String bu;
	
	private String numMarcheContrat;
	
	private String type;
	
	private String statut;
	
	private Double montantCmd;
	
	private Double montantPrevisionnel;
	
	private String moisFact;
	
	private Date dateFact;
	
	private String numFact;
	
	private Double montantFact;
	
	private String periode;
	
	private Date du;
	
	private Date au;
	
	private boolean sousTraiter;
	
	private String nomSousTraitant;
	
	private String numCmdFournisseur;
	
	private Double montantStock;
	
	private String dureeContrat;
	
	private Date dateDebut;
	private Date dateFin;
	
	private boolean retardFacture;
	
	
	
	
	
	public Contrat() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeProjet() {
		return codeProjet;
	}

	public void setCodeProjet(String codeProjet) {
		this.codeProjet = codeProjet;
	}

	public String getCodeSap() {
		return codeSap;
	}

	public void setCodeSap(String codeSap) {
		this.codeSap = codeSap;
	}

	public String getPilote() {
		return pilote;
	}

	public void setPilote(String pilote) {
		this.pilote = pilote;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getNumMarcheContrat() {
		return numMarcheContrat;
	}

	public void setNumMarcheContrat(String numMarcheContrat) {
		this.numMarcheContrat = numMarcheContrat;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Double getMontantCmd() {
		return montantCmd;
	}

	public void setMontantCmd(Double montantCmd) {
		this.montantCmd = montantCmd;
	}

	public Double getMontantPrevisionnel() {
		return montantPrevisionnel;
	}

	public void setMontantPrevisionnel(Double montantPrevisionnel) {
		this.montantPrevisionnel = montantPrevisionnel;
	}

	public String getMoisFact() {
		return moisFact;
	}

	public void setMoisFact(String moisFact) {
		this.moisFact = moisFact;
	}

	public Date getDateFact() {
		return dateFact;
	}

	public void setDateFact(Date dateFact) {
		this.dateFact = dateFact;
	}

	public String getNumFact() {
		return numFact;
	}

	public void setNumFact(String numFact) {
		this.numFact = numFact;
	}

	public Double getMontantFact() {
		return montantFact;
	}

	public void setMontantFact(Double montantFact) {
		this.montantFact = montantFact;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public Date getDu() {
		return du;
	}

	public void setDu(Date du) {
		this.du = du;
	}

	public Date getAu() {
		return au;
	}

	public void setAu(Date au) {
		this.au = au;
	}

	public boolean isSousTraiter() {
		return sousTraiter;
	}

	public void setSousTraiter(boolean sousTraiter) {
		this.sousTraiter = sousTraiter;
	}

	public String getNomSousTraitant() {
		return nomSousTraitant;
	}

	public void setNomSousTraitant(String nomSousTraitant) {
		this.nomSousTraitant = nomSousTraitant;
	}

	public String getNumCmdFournisseur() {
		return numCmdFournisseur;
	}

	public void setNumCmdFournisseur(String numCmdFournisseur) {
		this.numCmdFournisseur = numCmdFournisseur;
	}

	public Double getMontantStock() {
		return montantStock;
	}

	public void setMontantStock(Double montantStock) {
		this.montantStock = montantStock;
	}

	public String getDureeContrat() {
		return dureeContrat;
	}

	public void setDureeContrat(String dureeContrat) {
		this.dureeContrat = dureeContrat;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public boolean isRetardFacture() {
		return retardFacture;
	}

	public void setRetardFacture(boolean retardFacture) {
		this.retardFacture = retardFacture;
	}

	

	
	
	
	
	

}
