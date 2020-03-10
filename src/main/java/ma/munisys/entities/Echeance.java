package ma.munisys.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Echeance implements Serializable, Comparable<Echeance>,Comparator<Echeance> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date du;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date au;
	
	private Double montantPrevision;
	
	private PeriodeFacturation PeriodeFacturation;
	
	private OccurenceFacturation OccurenceFacturation;
	
	@ManyToOne
	private Contrat contrat;
	
	private Double montantFacture;
	
	private Double montantRestFacture;
	
	private String factures;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "echeance", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<FactureEcheance> factureEcheances = new HashSet<FactureEcheance>();
	
	
	@ManyToOne
	private ContratModel contratModel;
	
	@Column(columnDefinition = "TEXT")
	private String commentaire;
	
	public Echeance() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	@JsonIgnore
	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public Double getMontantPrevision() {
		return montantPrevision;
	}

	public void setMontantPrevision(Double montantPrevision) {
		this.montantPrevision = montantPrevision;
	}

	public PeriodeFacturation getPeriodeFacturation() {
		return PeriodeFacturation;
	}

	public void setPeriodeFacturation(PeriodeFacturation periodeFacturation) {
		PeriodeFacturation = periodeFacturation;
	}

	public OccurenceFacturation getOccurenceFacturation() {
		return OccurenceFacturation;
	}

	public void setOccurenceFacturation(OccurenceFacturation occurenceFacturation) {
		OccurenceFacturation = occurenceFacturation;
	}

	@JsonIgnore
	public Set<FactureEcheance> getFactureEcheances() {
		
		
		
		return factureEcheances;
	}

	public void calculMontantFacture() {
		if(factureEcheances!=null) {
			Set<String> facts = new HashSet<>();
			double c =0;
			for(FactureEcheance f : factureEcheances) {
				
				facts.add(f.getFacture().getNumFacture().toString());
				c = c + f.getFacture().getMontantHT();
			}
			this.montantFacture = c;
			this.factures = "["+ String.join(",", facts)+"]";
			if(this.montantFacture==null) {
				this.montantFacture = 0.0;
				this.montantRestFacture = this.montantPrevision;
			}
			
			if(this.montantFacture!=null && this.montantPrevision!=null) {
				if(this.montantPrevision - this.montantFacture>0) {
					this.montantRestFacture = this.montantPrevision - this.montantFacture;
				}else {
					this.montantRestFacture=0.0;
				}
			}
		}
	}
	
	public void setFactureEcheances(Set<FactureEcheance> factureEcheances) {
		
		this.factureEcheances = factureEcheances;
	}

	public Double getMontantFacture() {
		return montantFacture;
	}

	public void setMontantFacture(Double montantFacture) {
		this.montantFacture = montantFacture;
	}

	public Double getMontantRestFacture() {
		return montantRestFacture;
	}

	public void setMontantRestFacture(Double montantRestFacture) {
		this.montantRestFacture = montantRestFacture;
	}

	public String getFactures() {
		return factures;
	}

	public void setFactures(String factures) {
		this.factures = factures;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Override
	public int compareTo(Echeance o) {
		if (getDu() == null || o.getDu() == null)
		      return 0;
		int i = getDu().compareTo(o.getDu());
		if(i!=0) {
			return i;
		}
		else {
			return getAu().compareTo(o.getAu());
		}
		 
	}

	@Override
	public int compare(Echeance o1, Echeance o2) {
		if (o1.getDu() == null || o2.getDu() == null)
		      return 0;
		int i = o1.getDu().compareTo(o2.getDu());
		if(i!=0) {
			return i;
		}
		else {
			return o1.getAu().compareTo(o2.getAu());
		}
		   
	}

	@JsonIgnore
	public ContratModel getContratModel() {
		return contratModel;
	}

	public void setContratModel(ContratModel contratModel) {
		this.contratModel = contratModel;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
}
