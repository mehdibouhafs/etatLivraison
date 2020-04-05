package ma.munisys.entities;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FactureEcheance implements Serializable, Comparable<FactureEcheance>,Comparator<FactureEcheance>,Cloneable {
	
	@Id
	private String id;
	
	@ManyToOne
	private Contrat contrat;
	
	@ManyToOne
	private Facture facture;
	
	@ManyToOne
	private Echeance echeance;
	
	private Double montant;
	
	private boolean cloture;
	

	
	public FactureEcheance() {
		
	}

	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	@JsonIgnore
	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	
	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	
	public Echeance getEcheance() {
		return echeance;
	}

	public void setEcheance(Echeance echeance) {
		this.echeance = echeance;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	@Override
	public int compareTo(FactureEcheance o) {
		return getEcheance().compareTo(o.getEcheance());
	}

	@Override
	public int compare(FactureEcheance o1, FactureEcheance o2) {
		    return o1.getEcheance().compareTo(o2.getEcheance());
	}
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FactureEcheance other = (FactureEcheance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (FactureEcheance)super.clone();
	}



	public boolean isCloture() {
		return cloture;
	}



	public void setCloture(boolean cloture) {
		this.cloture = cloture;
	}

	
	
	
	
	
	

}
