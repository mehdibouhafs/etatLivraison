package ma.munisys.dto;

import java.util.Date;

public class ContratSearch {
	
	private String numContrat;
	
	private String motCle;
	
	private String nomPartenaire;
	
	private String numMarche;
	
	
	private String pilote;
	
	private Boolean sousTraiter;
	
	private String bu;
	
	private Date dateFinContrat;
	
	
	

	

	public String getNomPartenaire() {
		return nomPartenaire;
	}

	public void setNomPartenaire(String nomPartenaire) {
		this.nomPartenaire = nomPartenaire;
	}

	public String getPilote() {
		return pilote;
	}

	public void setPiltoe(String pilote) {
		this.pilote = pilote;
	}

	public Boolean isSousTraiter() {
		return sousTraiter;
	}

	public void setSousTraiter(Boolean sousTraiter) {
		this.sousTraiter = sousTraiter;
	}


	public String getNumMarche() {
		return numMarche;
	}

	public void setNumMarche(String numMarche) {
		this.numMarche = numMarche;
	}

	public Boolean getSousTraiter() {
		return sousTraiter;
	}

	public void setPilote(String pilote) {
		this.pilote = pilote;
	}

	public String getMotCle() {
		return motCle;
	}

	public void setMotCle(String motCle) {
		this.motCle = motCle;
	}

	public String getNumContrat() {
		return numContrat;
	}

	public void setNumContrat(String numContrat) {
		this.numContrat = numContrat;
	}

	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public Date getDateFinContrat() {
		return dateFinContrat;
	}

	public void setDateFinContrat(Date dateFinContrat) {
		this.dateFinContrat = dateFinContrat;
	}
	
	
	
	 

}
