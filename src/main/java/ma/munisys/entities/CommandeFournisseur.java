package ma.munisys.entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class CommandeFournisseur implements Serializable,Comparable<CommandeFournisseur>,Comparator<CommandeFournisseur> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long numeroDocument; 
	
	@ManyToMany
	@JoinColumn(referencedColumnName = "codeProjet")
	private Set<Contrat> contrats;
	
	
	
	@Temporal(TemporalType.DATE)
	private Date dateEnregistrement; 
	
	
	private String fournisseur;
	
	
	private String numReference;
	
	private String remarque;
	
	
	private String numArticle;
	
	private String descriptionArticle;
	
	private int qte; 
	
	private Double prix; 
	private Double totalCmd;
	private int qteLiv;
	private Double totalLiv;
	private int qteEnCours;
	
	private double totalRal;
	
	private int qteFacture;
	private Double montantFacture;
	
	private int qteRnf;
	private Double montantRnf;
	private String technologie;
	
	@Temporal(TemporalType.DATE)
	@Column(name="cmd_du")
	private Date du; 
	@Temporal(TemporalType.DATE)
	@Column(name="cmd_au")
	private Date au; 
	
	
	public CommandeFournisseur() {
		this.contrats = new HashSet<Contrat>();
	}

	
	

	public Long getNumeroDocument() {
		return numeroDocument;
	}


	public void setNumeroDocument(Long numeroDocument) {
		this.numeroDocument = numeroDocument;
	}


	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}


	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}


	public String getFournisseur() {
		return fournisseur;
	}


	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}


	public String getNumReference() {
		return numReference;
	}


	public void setNumReference(String numReference) {
		this.numReference = numReference;
	}


	public String getRemarque() {
		return remarque;
	}


	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}


	public String getNumArticle() {
		return numArticle;
	}


	public void setNumArticle(String numArticle) {
		this.numArticle = numArticle;
	}


	public String getDescriptionArticle() {
		return descriptionArticle;
	}


	public void setDescriptionArticle(String descriptionArticle) {
		this.descriptionArticle = descriptionArticle;
	}


	public int getQte() {
		return qte;
	}


	public void setQte(int qte) {
		this.qte = qte;
	}


	public Double getPrix() {
		return prix;
	}


	public void setPrix(Double prix) {
		this.prix = prix;
	}


	public Double getTotalCmd() {
		return totalCmd;
	}


	public void setTotalCmd(Double totalCmd) {
		this.totalCmd = totalCmd;
	}


	public int getQteLiv() {
		return qteLiv;
	}


	public void setQteLiv(int qteLiv) {
		this.qteLiv = qteLiv;
	}


	public Double getTotalLiv() {
		return totalLiv;
	}


	public void setTotalLiv(Double totalLiv) {
		this.totalLiv = totalLiv;
	}


	public int getQteEnCours() {
		return qteEnCours;
	}


	public void setQteEnCours(int qteEnCours) {
		this.qteEnCours = qteEnCours;
	}


	public double getTotalRal() {
		return totalRal;
	}


	public void setTotalRal(double totalRal) {
		this.totalRal = totalRal;
	}


	public int getQteFacture() {
		return qteFacture;
	}


	public void setQteFacture(int qteFacture) {
		this.qteFacture = qteFacture;
	}


	public Double getMontantFacture() {
		return montantFacture;
	}


	public void setMontantFacture(Double montantFacture) {
		this.montantFacture = montantFacture;
	}


	public int getQteRnf() {
		return qteRnf;
	}


	public void setQteRnf(int qteRnf) {
		this.qteRnf = qteRnf;
	}


	public Double getMontantRnf() {
		return montantRnf;
	}


	public void setMontantRnf(Double montantRnf) {
		this.montantRnf = montantRnf;
	}

	
	

	@JsonIgnore
	public Set<Contrat> getContrats() {
		return contrats;
	}

	public void setContrats(Set<Contrat> contrats) {
		this.contrats = contrats;
	}
	
	


	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	@Override
	public int compareTo(CommandeFournisseur o) {
		
				return -getId().compareTo(o.getId());
			
	}




	@Override
	public int compare(CommandeFournisseur o1, CommandeFournisseur o2) {
		
				return -o1.getId().compareTo(o2.getId());
			
	}




	public String getTechnologie() {
		return technologie;
	}




	public void setTechnologie(String technologie) {
		this.technologie = technologie;
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
	
	
	
	

	




	
	
	



	
	
	
	
	

}
