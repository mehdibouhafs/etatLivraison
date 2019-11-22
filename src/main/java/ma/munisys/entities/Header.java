package ma.munisys.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "headers")
public class Header implements Serializable {
	
	@Id
	private String label;
	
	@OneToMany(mappedBy = "projet",fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<Detail> details  = new HashSet<Detail>();
	
	
	@ManyToOne
	private EtatProjet etatProjet;
	
	@ManyToOne
	private EtatRecouvrement etatRecouvrement;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public Header() {
		
	}
	
	

	public Header(String label) {
		super();
		this.label = label;
	}

	@JsonIgnore
	public EtatProjet getEtatProjet() {
		return etatProjet;
	}

	public void setEtatProjet(EtatProjet etatProjet) {
		this.etatProjet = etatProjet;
	}
	
	@Override
    public boolean equals(Object header) {
        
        if (!(header instanceof Header)) return false;
        Header newheader = (Header) header;
        return newheader.getLabel().equals(this.getLabel());
    }
	
	
	@JsonIgnore
	public Set<Detail> getDetails() {
		return details;
	}

	public void setDetails(Set<Detail> details) {
		this.details = details;
	}
	

	public EtatRecouvrement getEtatRecouvrement() {
		return etatRecouvrement;
	}

	public void setEtatRecouvrement(EtatRecouvrement etatRecouvrement) {
		this.etatRecouvrement = etatRecouvrement;
	}

	@Override
	public String toString() {
		return "Header [label=" + label + "]";
	}
	
	
	
	
	

}
