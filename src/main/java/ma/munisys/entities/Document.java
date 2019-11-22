package ma.munisys.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "documents")
public class Document implements Serializable {
	
	@Id
	private String codePiece;
	
	private String numPiece;
	
	private String typeDocument;
	
	@Transient
	private String numPieceByTypeDocument;
	
	private Date datePiece;
	
	private String codeClient;
	
	private String client;
	
	private String refClient;
	
	private String codeProjet;
	
	private String projet;
	
	
	
	private String codeCommercial;
	
	
	private String commercial;
	
	
	private String codeChefProjet;
	
	private String chefProjet;
	
	private Double montantPiece;
	
	private Double montantOuvert;

	private String chargerRecouvrement;
	
	private String codeChargeRecouvrement;
	
	private int anneePiece;
	
	@Column(name="agePiece", columnDefinition="Decimal(10,2) default '00.00'")
	private int agePiece;
	

	private String age;
	
	
	private Double montantPayer;
	
	
	private String conditionDePaiement;
	
	private boolean caution;
	
	private String numCaution;
	
	private String typeCaution;
	
	private Double montantCaution;
	
	private Date dateLiberationCaution;
	
	private boolean cloture;
	
	// changeable
	
	private String statut;
	
	@Column(columnDefinition = "TEXT")
	private String motif;
	
	

	@ManyToOne
	private EtatRecouvrement etatRecouvrement;
		
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "document", cascade = CascadeType.ALL,orphanRemoval=true)
	@OrderBy("date DESC")
	private Set<Commentaire> commentaires = new HashSet<Commentaire>();
	
	private double montantGarantie;
	
	private double montantProvision;
	
	private Date dateFinGarantie;
	
	private Date datePrevuEncaissement;
	
	private String dureeGarantie;
	
	@Column(columnDefinition = "TEXT")
	private String action;
	
	
	private String responsable;
	

	private Date dateDepot;
	
	private String motifChangementDate;
	
	private Date datePvProvisoire;
	
	private Date datePrevuReceptionDefinitive;

	/*@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Commentaire secondCommentaire;*/

	// private Commentaire commentMerged;
	
	
	private Date creation;

	private Date lastUpdate;
	
	private Date dateEcheance;
	
	@Column(columnDefinition = "TEXT")
    private String infoClient;
    @Column(columnDefinition = "TEXT")
    private String infoChefProjetOrCommercial;
    @Column(columnDefinition = "TEXT")
    private String infoProjet;
	
	
	@OneToOne
	private AppUser updatedBy;
	
	
	
	
	public void addDetail(Detail detail) {
		detail.setDocument(this);
		try {
			switch(detail.getHeader().getLabel()) {
			case "N° Pièce":
				this.numPiece  = detail.getValue();
				break;
			case "Type Document":
				this.typeDocument  = detail.getValue();
				break;
			case "Date Pièce":
				this.datePiece  = convertDate(detail.getValue())  ;
				break;
			case "Code Client":
				this.codeClient  =  detail.getValue();
				break;
			case "Client":
				this.client  =  detail.getValue() ;
				break;
			case "Réf. Client":
				this.refClient  = detail.getValue()  ;
				break;
			case "Code Pojet":
				this.codeProjet  = detail.getValue()  ;
				break;
			case "Nom Projet":
				this.projet  = detail.getValue()  ;
				break;
			case "Code Commercial":
				this.codeCommercial  = detail.getValue()  ;
				break;
			case "Commercial":
				this.commercial  = detail.getValue()  ;
				break;
			case "Chef Projet":
				this.chefProjet  = detail.getValue()  ;
				break;
			case "Responsable":
				this.responsable  = detail.getValue()  ;
				break;			
			case "Montant Pièce":
				if(detail.getValue()!=null && !detail.getValue().isEmpty())
				this.montantPiece  = Double.parseDouble(convertToMontant(detail.getValue()))  ;
				break;
			case "Montant Ouvert":
				if(detail.getValue()!=null && !detail.getValue().isEmpty())
				this.montantOuvert  = Double.parseDouble(convertToMontant(detail.getValue()))  ;
				break;
			case "Année Pièce":
				if(detail.getValue()!=null && !detail.getValue().isEmpty())
				this.anneePiece  =  Integer.parseInt(detail.getValue()) ;
				break;
			case "Age Pièce":
				if(detail.getValue()!=null && !detail.getValue().isEmpty())
				this.agePiece  = Integer.parseInt(detail.getValue());
				break;
			case "Age":
				if(detail.getValue()!=null && !detail.getValue().isEmpty())
				this.age  = detail.getValue()  ;
				break;
			case "Montant Payé":
				if(detail.getValue()!=null && !detail.getValue().isEmpty())
				this.montantPayer  =  Double.parseDouble(convertToMontant(detail.getValue()))   ;
				break;
			case "Condition payement":
				this.conditionDePaiement  = detail.getValue()  ;
				break;
			case "Caution Oui / Non":
				boolean value = false;
				if(detail.getValue()!=null && !detail.getValue().isEmpty()) {
					if(detail.getValue().equalsIgnoreCase("oui")) {
						value = true;
					}
				}
					
				this.caution  =  value;
				break;
			case "N° Caution":
				this.numCaution  = detail.getValue()  ;
				break;
			case "Chargé Recouvrement":
				this.chargerRecouvrement  = detail.getValue()  ;
				break;
			case "Type Caution":
				this.typeCaution  = detail.getValue()  ;
				break;
			case "Montant Caution":
				if(detail.getValue()!=null && !detail.getValue().isEmpty())
				this.montantCaution  =   Double.parseDouble(convertToMontant(detail.getValue())) ;
				break;
			case "Date Libération Caution":
				this.dateLiberationCaution  = convertDate(detail.getValue())  ;
				break;
				
			}
		}catch(Exception e ) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
	}
	
	private Date convertDate(String date) {
		Date dateRes=null;
		if(date!= null && !date.isEmpty()) {
			try {
				dateRes = new SimpleDateFormat("dd/MM/yy").parse(date);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}  
		}
		return dateRes;
	}
	
	public String convertToMontant(String mt) {
		
		 
		return mt.replaceAll("\\u00A0","").replace(",","").replace("- 0","0").replace("-", "0");
		
	}
	
	public Document() {
		// TODO Auto-generated constructor stub
	}
	
	public String getNumPiece() {
		return numPiece;
	}



	public void setNumPiece(String numPiece) {
		this.numPiece = numPiece;
	}



	public String getTypeDocument() {
		return typeDocument;
	}



	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}



	public Date getDatePiece() {
		return datePiece;
	}



	public void setDatePiece(Date datePiece) {
		this.datePiece = datePiece;
	}



	public String getCodeClient() {
		return codeClient;
	}



	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}


	


	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getRefClient() {
		return refClient;
	}



	public void setRefClient(String refClient) {
		this.refClient = refClient;
	}



	public String getCodeProjet() {
		return codeProjet;
	}



	public void setCodeProjet(String codeProjet) {
		this.codeProjet = codeProjet;
	}



	public String getProjet() {
		return projet;
	}



	public void setProjet(String projet) {
		this.projet = projet;
	}






	



	public Double getMontantPiece() {
		return montantPiece;
	}



	public void setMontantPiece(Double montantPiece) {
		this.montantPiece = montantPiece;
	}



	public Double getMontantOuvert() {
		return montantOuvert;
	}

	public int getAnneePiece() {
		return anneePiece;
	}



	public void setAnneePiece(int anneePiece) {
		this.anneePiece = anneePiece;
	}



	public int getAgePiece() {
		return agePiece;
	}



	public void setAgePiece(int agePiece) {
		this.agePiece = agePiece;
	}



	



	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Double getMontantPayer() {
		return montantPayer;
	}



	public void setMontantPayer(Double montantPayer) {
		this.montantPayer = montantPayer;
	}



	public String getConditionDePaiement() {
		return conditionDePaiement;
	}



	public void setConditionDePaiement(String conditionDePaiement) {
		this.conditionDePaiement = conditionDePaiement;
	}



	public boolean isCaution() {
		return caution;
	}



	public void setCaution(boolean caution) {
		this.caution = caution;
	}



	public String getNumCaution() {
		return numCaution;
	}



	public void setNumCaution(String numCaution) {
		this.numCaution = numCaution;
	}



	public String getTypeCaution() {
		return typeCaution;
	}



	public void setTypeCaution(String typeCaution) {
		this.typeCaution = typeCaution;
	}



	public Double getMontantCaution() {
		return montantCaution;
	}



	public void setMontantCaution(Double montantCaution) {
		this.montantCaution = montantCaution;
	}



	public Date getDateLiberationCaution() {
		return dateLiberationCaution;
	}



	public void setDateLiberationCaution(Date dateLiberationCaution) {
		this.dateLiberationCaution = dateLiberationCaution;
	}



	public boolean isCloture() {
		return cloture;
	}



	public void setCloture(boolean cloture) {
		this.cloture = cloture;
	}



	public String getStatut() {
		return statut;
	}



	public void setStatut(String statut) {
		this.statut = statut;
	}



	public String getMotif() {
		return motif;
	}



	public void setMotif(String motif) {
		this.motif = motif;
	}



	public EtatRecouvrement getEtatRecouvrement() {
		return etatRecouvrement;
	}



	public void setEtatRecouvrement(EtatRecouvrement etatRecouvrement) {
		this.etatRecouvrement = etatRecouvrement;
	}



	public Set<Commentaire> getCommentaires() {
		return commentaires;
	}



	public void setCommentaires(Set<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}



	public double getMontantGarantie() {
		return montantGarantie;
	}



	public void setMontantGarantie(double montantGarantie) {
		this.montantGarantie = montantGarantie;
	}



	public double getMontantProvision() {
		return montantProvision;
	}



	public void setMontantProvision(double montantProvision) {
		this.montantProvision = montantProvision;
	}



	public Date getDateFinGarantie() {
		return dateFinGarantie;
	}



	public void setDateFinGarantie(Date dateFinGarantie) {
		this.dateFinGarantie = dateFinGarantie;
	}



	public Date getDatePrevuEncaissement() {
		return datePrevuEncaissement;
	}



	public void setDatePrevuEncaissement(Date datePrevuEncaissement) {
		this.datePrevuEncaissement = datePrevuEncaissement;
	}



	public String getDureeGarantie() {
		return dureeGarantie;
	}



	public void setDureeGarantie(String dureeGarantie) {
		this.dureeGarantie = dureeGarantie;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}

	public Date getDateDepot() {
		return dateDepot;
	}



	public void setDateDepot(Date dateDepot) {
		this.dateDepot = dateDepot;
	}



	public String getMotifChangementDate() {
		return motifChangementDate;
	}



	public void setMotifChangementDate(String motifChangementDate) {
		this.motifChangementDate = motifChangementDate;
	}



	public Date getDatePvProvisoire() {
		return datePvProvisoire;
	}



	public void setDatePvProvisoire(Date datePvProvisoire) {
		this.datePvProvisoire = datePvProvisoire;
	}



	public Date getDatePrevuReceptionDefinitive() {
		return datePrevuReceptionDefinitive;
	}



	public void setDatePrevuReceptionDefinitive(Date datePrevuReceptionDefinitive) {
		this.datePrevuReceptionDefinitive = datePrevuReceptionDefinitive;
	}



	public Date getCreation() {
		return creation;
	}



	public void setCreation(Date creation) {
		this.creation = creation;
	}



	public Date getLastUpdate() {
		return lastUpdate;
	}



	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}



	public AppUser getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(AppUser updatedBy) {
		this.updatedBy = updatedBy;
	}

	

	public String getCodeCommercial() {
		return codeCommercial;
	}

	public void setCodeCommercial(String codeCommercial) {
		this.codeCommercial = codeCommercial;
	}

	public String getChefProjet() {
		return chefProjet;
	}

	public void setChefProjet(String chefProjet) {
		this.chefProjet = chefProjet;
	}

	public String getChargerRecouvrement() {
		return chargerRecouvrement;
	}

	public void setChargerRecouvrement(String chargerRecouvrement) {
		this.chargerRecouvrement = chargerRecouvrement;
	}

	public void setMontantOuvert(Double montantOuvert) {
		this.montantOuvert = montantOuvert;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getResponsable() {
		return responsable;
	}

	public String getCommercial() {
		return commercial;
	}

	public void setCommercial(String commercial) {
		this.commercial = commercial;
	}

	
	public String getCodePiece() {
		return codePiece;
	}

	public void setCodePiece(String codePiece) {
		this.codePiece = codePiece;
	}

	public String getCodeChargeRecouvrement() {
		return codeChargeRecouvrement;
	}

	public void setCodeChargeRecouvrement(String codeChargeRecouvrement) {
		this.codeChargeRecouvrement = codeChargeRecouvrement;
	}

	public String getCodeChefProjet() {
		return codeChefProjet;
	}

	public void setCodeChefProjet(String codeChefProjet) {
		this.codeChefProjet = codeChefProjet;
	}

	public String getNumPieceByTypeDocument() {
		return numPieceByTypeDocument;
	}

	public void setNumPieceByTypeDocument(String numPieceByTypeDocument) {
		this.numPieceByTypeDocument =  numPieceByTypeDocument;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public String getInfoClient() {
		return infoClient;
	}

	public void setInfoClient(String infoClient) {
		this.infoClient = infoClient;
	}

	public String getInfoChefProjetOrCommercial() {
		return infoChefProjetOrCommercial;
	}

	public void setInfoChefProjetOrCommercial(String infoChefProjetOrCommercial) {
		this.infoChefProjetOrCommercial = infoChefProjetOrCommercial;
	}

	public String getInfoProjet() {
		return infoProjet;
	}

	public void setInfoProjet(String infoProjet) {
		this.infoProjet = infoProjet;
	}

	@Override
	public String toString() {
		return "Document [codePiece=" + codePiece + ", numPiece=" + numPiece + ", typeDocument=" + typeDocument + "]";
	}
	
	
	
	
	
	
	

}
