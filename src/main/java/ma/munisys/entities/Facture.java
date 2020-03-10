package ma.munisys.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Facture implements Serializable,Cloneable {
	
	@Id
	private Long numFacture;
	
	@ManyToOne
	private Contrat contrat;
	
	@Temporal(TemporalType.DATE)
	private Date dateEnregistrement;
	
	@Temporal(TemporalType.DATE)
	private Date debutPeriode;

	@Temporal(TemporalType.DATE)
	private Date finPeriode;
	
	private double montantRestant;
	
	private double montantHT;
	
	private double montantTTC;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "facture", cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<FactureEcheance> factureEcheances;
	
	
	
	public Facture() {
		this.factureEcheances = new HashSet<FactureEcheance>();
	}

	public Long getNumFacture() {
		return numFacture;
	}

	public void setNumFacture(Long numFacture) {
		this.numFacture = numFacture;
	}

	@JsonIgnore
	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public Date getDebutPeriode() {
		return debutPeriode;
	}

	public void setDebutPeriode(Date debutPeriode) {
		this.debutPeriode = debutPeriode;
	}

	public Date getFinPeriode() {
		return finPeriode;
	}

	public void setFinPeriode(Date finPeriode) {
		this.finPeriode = finPeriode;
	}

	

	public double getMontantRestant() {
		return montantRestant;
	}

	public void setMontantRestant(double montantRestant) {
		this.montantRestant = montantRestant;
	}

	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	@JsonIgnore
	public Set<FactureEcheance> getFactureEcheances() {
		return factureEcheances;
	}

	public void setFactureEcheances(Set<FactureEcheance> factureEcheances) {
		this.factureEcheances = factureEcheances;
	}

	@Override
	public String toString() {
		return "Facture [numFacture=" + numFacture + ", contrat=" + contrat + "]";
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public double getMontantHT() {
		return montantHT;
	}

	public void setMontantHT(double montantHT) {
		this.montantHT = montantHT;
	}

	public double getMontantTTC() {
		return montantTTC;
	}

	public void setMontantTTC(double montantTTC) {
		this.montantTTC = montantTTC;
	}

	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
