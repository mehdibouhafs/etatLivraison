package ma.munisys.dto;

public class Contrat {
	
	private Long numContrat;
	
	private String nom;
	
	private String pilote;
	
	private String numMarche;
	
	private String client;
	
	
	public Contrat() {
		
	}

	public Long getNumContrat() {
		return numContrat;
	}

	public void setNumContrat(Long numContrat) {
		this.numContrat = numContrat;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPilote() {
		return pilote;
	}

	public void setPilote(String pilote) {
		this.pilote = pilote;
	}

	public String getNumMarche() {
		return numMarche;
	}

	public void setNumMarche(String numMarche) {
		this.numMarche = numMarche;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	
	

}
