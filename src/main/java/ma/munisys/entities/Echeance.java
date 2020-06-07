package ma.munisys.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	
	private Double montant;
	
	private Double montantPrevision;
	
	private PeriodeFacturation PeriodeFacturation;
	
	private OccurenceFacturation OccurenceFacturation;
	
	@ManyToOne
	private Contrat contrat;
	
	private Double montantFacture;
	
	private Double montantRestFacture;
	
	private String factures;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "echeance", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<FactureEcheance> factureEcheances = new HashSet<FactureEcheance>();
	
	@ManyToOne
	private ContratModel contratModel;
	
	@ManyToOne
	private CommentaireEcheance commentaire;
	
	private boolean cloture;
	
	private boolean addedByUser;
	
	private boolean deletedByUser;
	
	private String messageDelete;
	
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
				
				if(!f.isCloture()) {
					facts.add(f.getFacture().getNumFacture().toString());
					c = c + f.getFacture().getMontantHT();
				}
				
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

	public CommentaireEcheance getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(CommentaireEcheance commentaire) {
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

	public boolean getCloture() {
		return cloture;
	}

	public void setCloture(boolean cloture) {
		this.cloture = cloture;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((au == null) ? 0 : au.hashCode());
		result = prime * result + ((contratModel == null) ? 0 : contratModel.hashCode());
		result = prime * result + ((du == null) ? 0 : du.hashCode());
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
		Echeance other = (Echeance) obj;
		if (au == null) {
			if (other.au != null)
				return false;
		} else if (!au.equals(other.au))
			return false;
		if (contratModel == null) {
			if (other.contratModel != null)
				return false;
		} else if (!contratModel.getId().equals(other.contratModel.getId()))
			return false;
		if (du == null) {
			if (other.du != null)
				return false;
		} else if (!du.equals(other.du))
			return false;
		return true;
	}

	public boolean isAddedByUser() {
		return addedByUser;
	}

	public void setAddedByUser(boolean addedByUser) {
		this.addedByUser = addedByUser;
	}

	public boolean isDeletedByUser() {
		return deletedByUser;
	}

	public void setDeletedByUser(boolean deletedByUser) {
		this.deletedByUser = deletedByUser;
	}

	public String getMessageDelete() {
		return messageDelete;
	}

	public void setMessageDelete(String messageDelete) {
		this.messageDelete = messageDelete;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	@Override
	public String toString() {
		String pattern = "dd/MM/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		
		return "Echeance [du=" + df.format(du) + ", au=" + df.format(au) + ", montant=" + montant + ", contratModel=" + contratModel + "]";
	}
	
	
	
}
