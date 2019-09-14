package ma.munisys.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Projet implements Serializable {
	
	@Id 
	private String codeProjet;
	
	private String projet;
	
	private String dateCmd;
	
	private String refCom;
	
	private int age;
	
	private String codeClient;
	
	private String client;
	
	private String commercial;
	
	private String chefProjet;
	
	private String bu;
	
	private int mntCmd;
	
	private int ral;
	
	private int liv;
	
	private int lnf;
	
	private int lfp;
	
	private int mntPaye;
	
	private double pourcentFacturation;
	
	private int ralJrsPrestCom;
	
	private int ralJrsPrestMuis;
	
	private String factEc;
	
	private String risque;
	
	@ManyToOne
	private EtatProjet etatProjet;
	
	private Commentaire firstCommentaire;
	
	private Commentaire secondCommentaire;
	
	//private Commentaire commentMerged;
	
	
	private Date dateFinProjet;
	
	private String condPaiement;
	
	private String preRequis;
	
	private String livraison;
	
	private String designProjet;
	
	private String intervenant;
	
	private String action;
	
	private String garantie;
	
	private String maintenance;
	
	//private String ralAndLnf; 
	
	
	
	
	
	public Projet() {
		// TODO Auto-generated constructor stub
	}



	public String getCodeProjet() {
		return codeProjet;
	}



	public void setCodeProjet(String codeProjet) {
		this.codeProjet = codeProjet;
	}



	public String getProjet() {
		return projet;
	}



	public void setProjet(String projet) {
		this.projet = projet;
	}



	public String getDateCmd() {
		return dateCmd;
	}



	public void setDateCmd(String dateCmd) {
		this.dateCmd = dateCmd;
	}



	public String getRefCom() {
		return refCom;
	}



	public void setRefCom(String refCom) {
		this.refCom = refCom;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public String getCodeClient() {
		return codeClient;
	}



	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}



	public String getClient() {
		return client;
	}



	public void setClient(String client) {
		this.client = client;
	}



	public String getCommercial() {
		return commercial;
	}



	public void setCommercial(String commercial) {
		this.commercial = commercial;
	}



	public String getChefProjet() {
		return chefProjet;
	}



	public void setChefProjet(String chefProjet) {
		this.chefProjet = chefProjet;
	}



	public String getBu() {
		return bu;
	}



	public void setBu(String bu) {
		this.bu = bu;
	}



	public int getMntCmd() {
		return mntCmd;
	}



	public void setMntCmd(int mntCmd) {
		this.mntCmd = mntCmd;
	}



	public int getRal() {
		return ral;
	}



	public void setRal(int ral) {
		this.ral = ral;
	}



	public int getLiv() {
		return liv;
	}



	public void setLiv(int liv) {
		this.liv = liv;
	}



	public int getLnf() {
		return lnf;
	}



	public void setLnf(int lnf) {
		this.lnf = lnf;
	}



	public int getLfp() {
		return lfp;
	}



	public void setLfp(int lfp) {
		this.lfp = lfp;
	}



	public int getMntPaye() {
		return mntPaye;
	}



	public void setMntPaye(int mntPaye) {
		this.mntPaye = mntPaye;
	}



	public EtatProjet getEtatProjet() {
		return etatProjet;
	}



	public void setEtatProjet(EtatProjet etatProjet) {
		this.etatProjet = etatProjet;
	}



	public double getPourcentFacturation() {
		return pourcentFacturation;
	}



	public void setPourcentFacturation(double pourcentFacturation) {
		this.pourcentFacturation = pourcentFacturation;
	}



	public int getRalJrsPrestCom() {
		return ralJrsPrestCom;
	}



	public void setRalJrsPrestCom(int ralJrsPrestCom) {
		this.ralJrsPrestCom = ralJrsPrestCom;
	}



	public int getRalJrsPrestMuis() {
		return ralJrsPrestMuis;
	}



	public void setRalJrsPrestMuis(int ralJrsPrestMuis) {
		this.ralJrsPrestMuis = ralJrsPrestMuis;
	}



	public String getFactEc() {
		return factEc;
	}



	public void setFactEc(String factEc) {
		this.factEc = factEc;
	}



	public String getRisque() {
		return risque;
	}



	public void setRisque(String risque) {
		this.risque = risque;
	}



	public Commentaire getFirstCommentaire() {
		return firstCommentaire;
	}



	public void setFirstCommentaire(Commentaire firstCommentaire) {
		this.firstCommentaire = firstCommentaire;
	}



	public Commentaire getSecondCommentaire() {
		return secondCommentaire;
	}



	public void setSecondCommentaire(Commentaire secondCommentaire) {
		this.secondCommentaire = secondCommentaire;
	}


	public Date getDateFinProjet() {
		return dateFinProjet;
	}



	public void setDateFinProjet(Date dateFinProjet) {
		this.dateFinProjet = dateFinProjet;
	}



	public String getCondPaiement() {
		return condPaiement;
	}



	public void setCondPaiement(String condPaiement) {
		this.condPaiement = condPaiement;
	}



	public String getPreRequis() {
		return preRequis;
	}



	public void setPreRequis(String preRequis) {
		this.preRequis = preRequis;
	}



	public String getLivraison() {
		return livraison;
	}



	public void setLivraison(String livraison) {
		this.livraison = livraison;
	}



	public String getDesignProjet() {
		return designProjet;
	}



	public void setDesignProjet(String designProjet) {
		this.designProjet = designProjet;
	}



	public String getIntervenant() {
		return intervenant;
	}



	public void setIntervenant(String intervenant) {
		this.intervenant = intervenant;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public String getGarantie() {
		return garantie;
	}



	public void setGarantie(String garantie) {
		this.garantie = garantie;
	}



	public String getMaintenance() {
		return maintenance;
	}



	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
	}

	
	
	
	


	
	
	

}
