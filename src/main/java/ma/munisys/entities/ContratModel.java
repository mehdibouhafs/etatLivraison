package ma.munisys.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.SortNatural;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="contratsModel")
public class ContratModel implements Serializable,Cloneable {
	
	@Id
	private String id;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="du")
	private Date du;

	@Temporal(TemporalType.DATE)
	@Column(name="au")
	private Date au;
	
	@ManyToOne
	private Contrat contrat;
	
	private String codeProjet;
	
	
	private Double montant;
	
	private String periodeFacturationLabel;
	
	private PeriodeFacturation periodeFacturation = PeriodeFacturation.UNKNOWN;
	
	private OccurenceFacturation occurenceFacturation = OccurenceFacturation.UNKNOWNPERIODE;
	
	private String occurenceFacturationLabel;
	
	private Boolean cloture;
	
	
	public ContratModel() {
		
		
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
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


	

	
	
	
	
	
	
	



	


	

	




	

	
	
	
	
	

}
