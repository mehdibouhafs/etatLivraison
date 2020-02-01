package ma.munisys.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.NaturalId;

@Entity
public class Contrat implements Serializable {
	
	@Id
	private Long numContrat;
	
	private String codePartenaire;
	
	private String nomPartenaire;
	
	private String statut;
	
	private Date du;
	
	private Date au;
	
	private String description;
	
	private String nomSousTraitant;
	
	private boolean contratSigne;

	private String codeProjet;
	
	private String numMarche;
	
	private String pilote;
	
	private Double montantContrat;
	
	private PeriodeFacturation periodeFacturation = PeriodeFacturation.UNKNOWN;
	
	private OccurenceFacturation occurenceFacturation = OccurenceFacturation.UNKNOWNPERIODE;
	
	private Double montantValueSi;
	
	private Double montantValueRs;
	
	private Double montantValueSw;
	
	private Double montantVolume;
	
	private Double montantCablage;
	
	private Double montantAssitanceAn;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contrat", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OrderBy("date DESC")
    private Set<CommentaireContrat> commentaires;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contrat", cascade = { CascadeType.ALL }, orphanRemoval = true)
	@OrderBy("du ASC")
	private Set<Echeance> echeances;
	
	
	
	
	public Contrat() {
		
	}



	public Long getNumContrat() {
		return numContrat;
	}



	public void setNumContrat(Long numContrat) {
		this.numContrat = numContrat;
	}



	public String getCodePartenaire() {
		return codePartenaire;
	}



	public void setCodePartenaire(String codePartenaire) {
		this.codePartenaire = codePartenaire;
	}



	public String getNomPartenaire() {
		return nomPartenaire;
	}



	public void setNomPartenaire(String nomPartenaire) {
		this.nomPartenaire = nomPartenaire;
	}



	public String getStatut() {
		return statut;
	}



	public void setStatut(String statut) {
		this.statut = statut;
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



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getNomSousTraitant() {
		return nomSousTraitant;
	}



	public void setNomSousTraitant(String nomSousTraitant) {
		this.nomSousTraitant = nomSousTraitant;
	}



	public boolean isContratSigne() {
		return contratSigne;
	}



	public void setContratSigne(boolean contratSigne) {
		this.contratSigne = contratSigne;
	}



	public String getCodeProjet() {
		return codeProjet;
	}



	public void setCodeProjet(String codeProjet) {
		this.codeProjet = codeProjet;
	}



	public String getNumMarche() {
		return numMarche;
	}



	public void setNumMarche(String numMarche) {
		this.numMarche = numMarche;
	}



	public String getPilote() {
		return pilote;
	}



	public void setPilote(String pilote) {
		this.pilote = pilote;
	}



	



	public Double getMontantContrat() {
		return montantContrat;
	}



	public void setMontantContrat(Double montantContrat) {
		this.montantContrat = montantContrat;
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



	public void setOccurenceFacturation(OccurenceFacturation occurenceFacturation) {
		this.occurenceFacturation = occurenceFacturation;
	}



	public Double getMontantValueSi() {
		return montantValueSi;
	}



	public void setMontantValueSi(Double montantValueSi) {
		this.montantValueSi = montantValueSi;
	}



	public Double getMontantValueRs() {
		return montantValueRs;
	}



	public void setMontantValueRs(Double montantValueRs) {
		this.montantValueRs = montantValueRs;
	}



	public Double getMontantValueSw() {
		return montantValueSw;
	}



	public void setMontantValueSw(Double montantValueSw) {
		this.montantValueSw = montantValueSw;
	}



	public Double getMontantVolume() {
		return montantVolume;
	}



	public void setMontantVolume(Double montantVolume) {
		this.montantVolume = montantVolume;
	}



	public Double getMontantCablage() {
		return montantCablage;
	}



	public void setMontantCablage(Double montantCablage) {
		this.montantCablage = montantCablage;
	}



	public Double getMontantAssitanceAn() {
		return montantAssitanceAn;
	}



	public void setMontantAssitanceAn(Double montantAssitanceAn) {
		this.montantAssitanceAn = montantAssitanceAn;
	}


	

	public Set<CommentaireContrat> getCommentaires() {
		return commentaires;
	}



	public void setCommentaires(Set<CommentaireContrat> commentaires) {
		this.commentaires = commentaires;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((au == null) ? 0 : au.hashCode());
		result = prime * result + ((codePartenaire == null) ? 0 : codePartenaire.hashCode());
		result = prime * result + ((codeProjet == null) ? 0 : codeProjet.hashCode());
		result = prime * result + (contratSigne ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((du == null) ? 0 : du.hashCode());
		result = prime * result + ((montantAssitanceAn == null) ? 0 : montantAssitanceAn.hashCode());
		result = prime * result + ((montantCablage == null) ? 0 : montantCablage.hashCode());
		result = prime * result + ((montantContrat == null) ? 0 : montantContrat.hashCode());
		result = prime * result + ((montantValueRs == null) ? 0 : montantValueRs.hashCode());
		result = prime * result + ((montantValueSi == null) ? 0 : montantValueSi.hashCode());
		result = prime * result + ((montantValueSw == null) ? 0 : montantValueSw.hashCode());
		result = prime * result + ((montantVolume == null) ? 0 : montantVolume.hashCode());
		result = prime * result + ((nomPartenaire == null) ? 0 : nomPartenaire.hashCode());
		result = prime * result + ((nomSousTraitant == null) ? 0 : nomSousTraitant.hashCode());
		result = prime * result + ((numContrat == null) ? 0 : numContrat.hashCode());
		result = prime * result + ((numMarche == null) ? 0 : numMarche.hashCode());
		result = prime * result + ((occurenceFacturation == null) ? 0 : occurenceFacturation.hashCode());
		result = prime * result + ((periodeFacturation == null) ? 0 : periodeFacturation.hashCode());
		result = prime * result + ((pilote == null) ? 0 : pilote.hashCode());
		result = prime * result + ((statut == null) ? 0 : statut.hashCode());
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
		Contrat other = (Contrat) obj;
		if (au == null) {
			if (other.au != null)
				return false;
		} else if (!au.equals(other.au))
			return false;
		if (codePartenaire == null) {
			if (other.codePartenaire != null)
				return false;
		} else if (!codePartenaire.equals(other.codePartenaire))
			return false;
		if (codeProjet == null) {
			if (other.codeProjet != null)
				return false;
		} else if (!codeProjet.equals(other.codeProjet))
			return false;
		if (contratSigne != other.contratSigne)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (du == null) {
			if (other.du != null)
				return false;
		} else if (!du.equals(other.du))
			return false;
		if (montantAssitanceAn == null) {
			if (other.montantAssitanceAn != null)
				return false;
		} else if (!montantAssitanceAn.equals(other.montantAssitanceAn))
			return false;
		if (montantCablage == null) {
			if (other.montantCablage != null)
				return false;
		} else if (!montantCablage.equals(other.montantCablage))
			return false;
		if (montantContrat == null) {
			if (other.montantContrat != null)
				return false;
		} else if (!montantContrat.equals(other.montantContrat))
			return false;
		if (montantValueRs == null) {
			if (other.montantValueRs != null)
				return false;
		} else if (!montantValueRs.equals(other.montantValueRs))
			return false;
		if (montantValueSi == null) {
			if (other.montantValueSi != null)
				return false;
		} else if (!montantValueSi.equals(other.montantValueSi))
			return false;
		if (montantValueSw == null) {
			if (other.montantValueSw != null)
				return false;
		} else if (!montantValueSw.equals(other.montantValueSw))
			return false;
		if (montantVolume == null) {
			if (other.montantVolume != null)
				return false;
		} else if (!montantVolume.equals(other.montantVolume))
			return false;
		if (nomPartenaire == null) {
			if (other.nomPartenaire != null)
				return false;
		} else if (!nomPartenaire.equals(other.nomPartenaire))
			return false;
		if (nomSousTraitant == null) {
			if (other.nomSousTraitant != null)
				return false;
		} else if (!nomSousTraitant.equals(other.nomSousTraitant))
			return false;
		if (numContrat == null) {
			if (other.numContrat != null)
				return false;
		} else if (!numContrat.equals(other.numContrat))
			return false;
		if (numMarche == null) {
			if (other.numMarche != null)
				return false;
		} else if (!numMarche.equals(other.numMarche))
			return false;
		if (occurenceFacturation != other.occurenceFacturation)
			return false;
		if (periodeFacturation != other.periodeFacturation)
			return false;
		if (pilote == null) {
			if (other.pilote != null)
				return false;
		} else if (!pilote.equals(other.pilote))
			return false;
		if (statut == null) {
			if (other.statut != null)
				return false;
		} else if (!statut.equals(other.statut))
			return false;
		return true;
	}

	
	public  Integer getMonthContrat() {
	 Integer nbMonth = 0;
		switch(this.periodeFacturation) {
		case ANUELLE :  nbMonth =12; break;
		case SEMESTRIELLE : nbMonth=6;break;
		case TRIMESTRIELLE: nbMonth=3; break;
		case MENSUELLE :nbMonth=1;break;
		default: nbMonth= null;
		
		}
		return nbMonth;
	}



	public Set<Echeance> getEcheances() {
		return echeances;
	}



	public void setEcheances(Set<Echeance> echeances) {
		this.echeances = echeances;
	}
	
	



	


	

	




	

	
	
	
	
	

}
