package ma.munisys.dto;

public class DocumentAnalyse {
	
	
	private String statut;
	
	private int nombre;
	
	private Double montant;
	
	public DocumentAnalyse() {
		// TODO Auto-generated constructor stub
	}

	public DocumentAnalyse(String statut, long count, Double montant) {
		super();
		this.statut = statut;
		this.nombre = (int) count;
		this.montant = montant;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}
	
	
	

}
