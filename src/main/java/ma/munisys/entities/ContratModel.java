package ma.munisys.entities;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="contratsModel")
public class ContratModel implements Serializable,Cloneable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name="cm_du")
	private Date du;

	@Temporal(TemporalType.DATE)
	@Column(name="cm_au")
	private Date au;
	
	@ManyToOne
	private Contrat contrat;
	
	private String codeProjet;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contratModel", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private Set<Echeance> echeances;
	
	private Double montant;
	
	private Double montantPrevisionel;
	
	private String periodeFacturationLabel;
	
	private PeriodeFacturation periodeFacturation = PeriodeFacturation.UNKNOWN;
	
	private OccurenceFacturation occurenceFacturation = OccurenceFacturation.UNKNOWNPERIODE;
	
	private String occurenceFacturationLabel;
	
	private Boolean cloture;
	
	private Boolean deleteByUser;
		
	public ContratModel() {
		this.echeances = new HashSet<Echeance>();
		
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


	public String getCodeProjet() {
		return codeProjet;
	}
	
	public  Integer getMonthContrat() {
		 Integer nbMonth = null;
			switch(this.periodeFacturation) {
			case ANNUELLE :  nbMonth =12; break;
			case SEMESTRIELLE : nbMonth=6;break;
			case TRIMESTRIELLE: nbMonth=3; break;
			case MENSUELLE :nbMonth=1;break;
			default: nbMonth= null;
			
			}
			return nbMonth;
		}


	public void setCodeProjet(String codeProjet) {
		this.codeProjet = codeProjet;
	}


	public Double getMontant() {
		return montant;
	}


	public void setMontant(Double montant) {
		this.montant = montant;
	}


	public String getPeriodeFacturationLabel() {
		return periodeFacturationLabel;
	}


	public void setPeriodeFacturationLabel(String periodeFacturationLabel) {
		this.periodeFacturationLabel = periodeFacturationLabel;
	}


	public PeriodeFacturation getPeriodeFacturation() {
		return periodeFacturation;
	}


	public void setPeriodeFacturation(PeriodeFacturation periodeFacturation) {
		this.periodeFacturation = periodeFacturation;
	}


	public OccurenceFacturation getOccurenceFacturation() {
		return occurenceFacturation;
	}

	@JsonIgnore
	public Contrat getContrat() {
		return contrat;
	}


	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}


	public String getOccurenceFacturationLabel() {
		return occurenceFacturationLabel;
	}


	public void setOccurenceFacturationLabel(String occurenceFacturationLabel) {
		this.occurenceFacturationLabel = occurenceFacturationLabel;
	}


	public Boolean getCloture() {
		return cloture;
	}


	public void setCloture(Boolean cloture) {
		this.cloture = cloture;
	}


	public void setOccurenceFacturation(OccurenceFacturation occurenceFacturation) {
		this.occurenceFacturation = occurenceFacturation;
	}

	@JsonIgnore
	public Set<Echeance> getEcheances() {
		return echeances;
	}


	public void setEcheances(Set<Echeance> echeances) {
		this.echeances = echeances;
	}


	public Double getMontantPrevisionel() {
		return montantPrevisionel;
	}


	public void setMontantPrevisionel(Double montantPrevisionel) {
		this.montantPrevisionel = montantPrevisionel;
	}
	
	
	public List<Echeance> generateEcheanceModele() {
		
		List<Echeance> echeances = new ArrayList<Echeance>();

		Integer nbMonthPeriod = this.getMonthContrat();

		if (this.getDu() != null && this.getAu() != null) {
			DateTime start = new DateTime(this.getDu());
			
			DateTime end = new DateTime(this.getAu());
			
				while (start.compareTo(end) < 0) {
					Echeance c = new Echeance();
					c.setContrat(this.getContrat());
					c.setDu(start.toDate());
					c.setContratModel(this);
					c.setCloture(false);
					c.setNomModele(this.name);
					DateTime dateBetween = start.plusMonths(nbMonthPeriod);
					start = dateBetween;
					c.setAu(start.plusDays(-1).toDate());
					c.setMontant(this.getMontant());
					c.setMontantRestFacture(c.getMontant());
					c.setMontantFacture(0.0);
					c.setMontantPrevision(this.getMontantPrevisionel());
					c.setPeriodeFacturation(this.getPeriodeFacturation());
					c.setOccurenceFacturation(this.getOccurenceFacturation());

					if (start.compareTo(end) >= 0) {
						c.setAu(end.toDate());
					}
				
					echeances.add(c);

				}
			}
		

		return echeances;

	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((au == null) ? 0 : au.hashCode());
		result = prime * result + ((cloture == null) ? 0 : cloture.hashCode());
		result = prime * result + ((codeProjet == null) ? 0 : codeProjet.hashCode());
		result = prime * result + ((contrat == null) ? 0 : contrat.hashCode());
		result = prime * result + ((du == null) ? 0 : du.hashCode());
		result = prime * result + ((montant == null) ? 0 : montant.hashCode());
		result = prime * result + ((montantPrevisionel == null) ? 0 : montantPrevisionel.hashCode());
		result = prime * result + ((periodeFacturation == null) ? 0 : periodeFacturation.hashCode());
		result = prime * result + ((periodeFacturationLabel == null) ? 0 : periodeFacturationLabel.hashCode());
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
		ContratModel other = (ContratModel) obj;
		if (au == null) {
			if (other.au != null)
				return false;
		} else if (!au.equals(other.au))
			return false;
		if (cloture == null) {
			if (other.cloture != null)
				return false;
		} else if (!cloture.equals(other.cloture))
			return false;
		if (codeProjet == null) {
			if (other.codeProjet != null)
				return false;
		} else if (!codeProjet.equals(other.codeProjet))
			return false;
		if (contrat == null) {
			if (other.contrat != null)
				return false;
		} else if (!contrat.equals(other.contrat))
			return false;
		if (du == null) {
			if (other.du != null)
				return false;
		} else if (!du.equals(other.du))
			return false;
		if (montant == null) {
			if (other.montant != null)
				return false;
		} else if (!montant.equals(other.montant))
			return false;
		if (montantPrevisionel == null) {
			if (other.montantPrevisionel != null)
				return false;
		} else if (!montantPrevisionel.equals(other.montantPrevisionel))
			return false;
		if (periodeFacturation != other.periodeFacturation)
			return false;
		if (periodeFacturationLabel == null) {
			if (other.periodeFacturationLabel != null)
				return false;
		} else if (!periodeFacturationLabel.equals(other.periodeFacturationLabel))
			return false;
		return true;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Boolean getDeleteByUser() {
		return deleteByUser;
	}


	public void setDeleteByUser(Boolean deleteByUser) {
		this.deleteByUser = deleteByUser;
	}

	
	
	
	
	
	
	



	


	

	




	

	
	
	
	
	

}
