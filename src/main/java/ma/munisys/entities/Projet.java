package ma.munisys.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Projet implements Serializable
{
    @Id
    private String codeProjet;
    private String projet;
    private String refCom;
    private Double age;
    private String codeClient;
    private String client;
    private Double facturation;
    private Double ralJrsPrestCalc;
    private Double prestationCommande;
    private String factEncours;
    private String codeCommercial;
    private String commercial;
    private String chefProjet;
    @Column(columnDefinition = "TEXT")
    private String conditionFacturation;
    private Double montantResteAReceptionnner;
    @ManyToOne
    private EtatProjet etatProjet;
    private String bu;
    private String statut;
    private Date dateCmd;
    private Double montantCmd;
    private Double restAlivrer;
    private Double livrer;
    private Double livrerNonFacture;
    private Double livreFacturePayer;
    private Double montantPayer;
    private Double montantStock;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "projet", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OrderBy("date DESC")
    private Set<Commentaire> commentaires;
    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    private Commentaire commentaireDirection;
    private Date dateFinProjet;
    private String condPaiement;
    private String preRequis;
    private String livraison;
    private String designProjet;
    private String action;
    private String garantie;
    private String maintenance;
    private boolean cloture;
    private Date creation;
    private Date lastUpdate;
    @OneToOne
    private AppUser updatedBy;
    @Column(columnDefinition = "TEXT")
    private String risque;
    @Column(columnDefinition = "TEXT")
    private String syntheseProjet;
    private String avantVente;
    @Column(columnDefinition = "TEXT")
    private String perimetreProjet;
    private String intervenantPrincipal;
    private Boolean suivre;
    @Column(columnDefinition = "TEXT")
    private String infoClient;
    @Column(columnDefinition = "TEXT")
    private String infoFournisseur;
    @Column(columnDefinition = "TEXT")
    private String infoProjet;
    
    private String priorite;
    
    private boolean decloturedByUser;
    
    @Column(nullable = false)
    private boolean cloturedByUser;
    
    private Date datePvReceptionProvisoire;
    
    private Date datePvReceptionDefinitive;
    
    private Integer tauxAvancement;
    
    private String statutProjet;
    
    private String type;
    
    private String flag;
    
    public Projet() {
        this.commentaires = new HashSet<Commentaire>();
    }
    
    public String getCodeProjet() {
        return this.codeProjet;
    }
    
    public void setCodeProjet(final String codeProjet) {
        this.codeProjet = codeProjet;
    }
    
    public Date getDateFinProjet() {
        return this.dateFinProjet;
    }
    
    public void setDateFinProjet(final Date dateFinProjet) {
        this.dateFinProjet = dateFinProjet;
    }
    
    public String getCondPaiement() {
        return this.condPaiement;
    }
    
    public void setCondPaiement(final String condPaiement) {
        this.condPaiement = condPaiement;
    }
    
    public String getPreRequis() {
        return this.preRequis;
    }
    
    public void setPreRequis(final String preRequis) {
        this.preRequis = preRequis;
    }
    
    public String getLivraison() {
        return this.livraison;
    }
    
    public void setLivraison(final String livraison) {
        this.livraison = livraison;
    }
    
    public String getDesignProjet() {
        return this.designProjet;
    }
    
    public void setDesignProjet(final String designProjet) {
        this.designProjet = designProjet;
    }
    
    public String getAction() {
        return this.action;
    }
    
    public void setAction(final String action) {
        this.action = action;
    }
    
    public String getGarantie() {
        return this.garantie;
    }
    
    public void setGarantie(final String garantie) {
        this.garantie = garantie;
    }
    
    public String getMaintenance() {
        return this.maintenance;
    }
    
    public void setMaintenance(final String maintenance) {
        this.maintenance = maintenance;
    }
    
    public boolean isCloture() {
        return this.cloture;
    }
    
    public void setCloture(final boolean cloture) {
        this.cloture = cloture;
    }
    
    public Date getCreation() {
        return this.creation;
    }
    
    public void setCreation(final Date creation) {
        this.creation = creation;
    }
    
    public Date getLastUpdate() {
        return this.lastUpdate;
    }
    
    public void setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public EtatProjet getEtatProjet() {
        return this.etatProjet;
    }
    
    public void setEtatProjet(final EtatProjet etatProjet) {
        this.etatProjet = etatProjet;
    }
    
    public void addDetail(final Detail detail) {
        detail.setProjet(this);
        try {
            final String label = detail.getHeader().getLabel();
            switch (label) {
                case "codeProjet": {
                    this.codeProjet = detail.getValue();
                    break;
                }
                case "Code projet": {
                    this.codeProjet = detail.getValue();
                    break;
                }
                case "Code Projet": {
                    this.codeProjet = detail.getValue();
                    break;
                }
                case "Client": {
                    this.client = detail.getValue();
                    break;
                }
                case "Code Commercial": {
                    this.commercial = detail.getValue();
                    break;
                }
                case "Commercial": {
                    this.commercial = detail.getValue();
                    break;
                }
                case "Chef Projet": {
                    this.chefProjet = detail.getValue();
                    break;
                }
                case "Date CMD": {
                    this.dateCmd = this.convertDate(detail.getValue());
                    break;
                }
                case "RAL": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.restAlivrer = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "Statut": {
                    this.statut = detail.getValue();
                    break;
                }
                case "LIV": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.livrer = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "LNF": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.livrerNonFacture = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "LFP": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.livreFacturePayer = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "Mnt Pay\u00e9": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.montantPayer = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "Montant stock": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.montantStock = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "Montant Stock": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.montantStock = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "Mnt CMD": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        //System.out.println("mnt cmd " + this.convertToMontant(detail.getValue()));
                    }
                    this.montantCmd = Double.parseDouble(this.convertToMontant(detail.getValue()));
                    break;
                }
                case "BU": {
                    this.bu = detail.getValue();
                    break;
                }
                case "Projet": {
                    this.projet = detail.getValue();
                    break;
                }
                case "Ref. COM": {
                    this.refCom = detail.getValue();
                    break;
                }
                case "Ref Client": {
                    this.refCom = detail.getValue();
                    break;
                }
                case "Age (mois)": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                       // System.out.println("age " + detail.getValue());
                        this.age = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "Code Client": {
                    this.codeClient = detail.getValue();
                    break;
                }
               
                case "% Facturation": {
                	 if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                         this.facturation = Double.parseDouble(this.convertToMontant(detail.getValue().trim()));
                	 }
                    break;
                }
                case "RAL JRS PREST CALC": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.ralJrsPrestCalc = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "Prestation Command\u00e9": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.prestationCommande = Double.parseDouble(this.convertToMontant(detail.getValue()));
                        break;
                    }
                    break;
                }
                case "Risque": {
                    this.risque = detail.getValue();
                    break;
                }
                case "Fact EC": {
                    if (detail.getValue() != null && !detail.getValue().isEmpty()) {
                        this.factEncours = detail.getValue();
                        break;
                    }
                    break;
                }
                default: {
                   // System.out.println("column not managed " + detail.getHeader().getLabel());
                    break;
                }
            }
        }
        catch (Exception e) {
           // System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Date convertDate(final String date) {
        Date dateRes = null;
        if (date != null && !date.isEmpty()) {
            try {
                dateRes = new SimpleDateFormat("dd/MM/yy").parse(date);
            }
            catch (ParseException e) {
               // System.out.println(e.getMessage());
            }
        }
        return dateRes;
    }
    
    public void addCommentaire(final Commentaire commentaire, final boolean commentaireDirection) {
        commentaire.setProjet(this);
        if (commentaireDirection) {
            this.setCommentaireDirection(commentaire);
        }
        else {
            this.getCommentaires().add(commentaire);
        }
    }
    
    public String convertToMontant(final String mt) {
        return mt.replaceAll("\\u00A0", "").replace(",", "").replace("- 0", "0").replace("-", "0").replace("%", "");
    }
    
    public String getClient() {
        return this.client;
    }
    
    public void setClient(final String client) {
        this.client = client;
    }
    
    public Set<Commentaire> getCommentaires() {
        return this.commentaires;
    }
    
    public void setCommentaires(final Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }
    
    public Commentaire getCommentaireDirection() {
        return this.commentaireDirection;
    }
    
    public void setCommentaireDirection(final Commentaire commentaireDirection) {
        this.commentaireDirection = commentaireDirection;
    }
    
    public Date getDateCmd() {
        return this.dateCmd;
    }
    
    public void setDateCmd(final Date dateCmd) {
        this.dateCmd = dateCmd;
    }
    
    public Double getMontantCmd() {
        return this.montantCmd;
    }
    
    public void setMontantCmd(final Double montantCmd) {
        this.montantCmd = montantCmd;
    }
    
    public Double getRestAlivrer() {
        return this.restAlivrer;
    }
    
    public void setRestAlivrer(final Double restAlivrer) {
        this.restAlivrer = restAlivrer;
    }
    
    public Double getLivrer() {
        return this.livrer;
    }
    
    public void setLivrer(final Double livrer) {
        this.livrer = livrer;
    }
    
    public Double getLivrerNonFacture() {
        return this.livrerNonFacture;
    }
    
    public void setLivrerNonFacture(final Double livrerNonFacture) {
        this.livrerNonFacture = livrerNonFacture;
    }
    
    public Double getLivreFacturePayer() {
        return this.livreFacturePayer;
    }
    
    public void setLivreFacturePayer(final Double livreFacturePayer) {
        this.livreFacturePayer = livreFacturePayer;
    }
    
    public Double getMontantPayer() {
        return this.montantPayer;
    }
    
    public void setMontantPayer(final Double montantPayer) {
        this.montantPayer = montantPayer;
    }
    
    public String getProjet() {
        return this.projet;
    }
    
    public void setProjet(final String projet) {
        this.projet = projet;
    }
    
    public String getRefCom() {
        return this.refCom;
    }
    
    public void setRefCom(final String refCom) {
        this.refCom = refCom;
    }
    
    public String getCodeClient() {
        return this.codeClient;
    }
    
    public void setCodeClient(final String codeClient) {
        this.codeClient = codeClient;
    }
    
    
    
    public Double getFacturation() {
		return facturation;
	}

	public void setFacturation(Double facturation) {
		this.facturation = facturation;
	}

	public String getFactEncours() {
        return this.factEncours;
    }
    
    public void setFactEncours(final String factEncours) {
        this.factEncours = factEncours;
    }
    
    public String getRisque() {
        return this.risque;
    }
    
    public void setRisque(final String risque) {
        this.risque = risque;
    }
    
    public String getBu() {
        return this.bu;
    }
    
    public void setBu(final String bu) {
        this.bu = bu;
    }
    
    public String getStatut() {
        return this.statut;
    }
    
    public void setStatut(final String statut) {
        this.statut = statut;
    }
    
    public String getSyntheseProjet() {
        return this.syntheseProjet;
    }
    
    public void setSyntheseProjet(final String syntheseProjet) {
        this.syntheseProjet = syntheseProjet;
    }
    
    public String getPerimetreProjet() {
        return this.perimetreProjet;
    }
    
    public void setPerimetreProjet(final String perimetreProjet) {
        this.perimetreProjet = perimetreProjet;
    }
    
    public Boolean getSuivre() {
        return this.suivre;
    }
    
    public void setSuivre(final Boolean suivre) {
        this.suivre = suivre;
    }
    
    public AppUser getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(final AppUser updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public Double getMontantStock() {
        return this.montantStock;
    }
    
    public void setMontantStock(final Double montantStock) {
        this.montantStock = montantStock;
    }
    
    @Override
    public String toString() {
        return "Projet [codeProjet=" + this.codeProjet + ", dateCmd=" + this.dateCmd + ", montantCmd=" + this.montantCmd + ", restAlivrer=" + this.restAlivrer + ", livrer=" + this.livrer + ", montantPayer=" + this.montantPayer + ", livraison=" + this.livraison + "]";
    }
    
    public String getInfoClient() {
        return this.infoClient;
    }
    
    public void setInfoClient(final String infoClient) {
        this.infoClient = infoClient;
    }
    
    public String getInfoFournisseur() {
        return this.infoFournisseur;
    }
    
    public void setInfoFournisseur(final String infoFournisseur) {
        this.infoFournisseur = infoFournisseur;
    }
    
    public String getInfoProjet() {
        return this.infoProjet;
    }
    
    public void setInfoProjet(final String infoProjet) {
        this.infoProjet = infoProjet;
    }
    
    public String getCodeCommercial() {
        return this.codeCommercial;
    }
    
    public void setCodeCommercial(final String codeCommercial) {
        this.codeCommercial = codeCommercial;
    }
    
    public String getAvantVente() {
        return this.avantVente;
    }
    
    public void setAvantVente(final String avantVente) {
        this.avantVente = avantVente;
    }
    
    public String getIntervenantPrincipal() {
        return this.intervenantPrincipal;
    }
    
    public void setIntervenantPrincipal(final String intervenantPrincipal) {
        this.intervenantPrincipal = intervenantPrincipal;
    }
    
    public String getChefProjet() {
        return this.chefProjet;
    }
    
    public void setChefProjet(final String chefProjet) {
        this.chefProjet = chefProjet;
    }
    
    public String getCommercial() {
        return this.commercial;
    }
    
    public void setCommercial(final String commercial) {
        this.commercial = commercial;
    }
    
    public Double getAge() {
        return this.age;
    }
    
    public void setAge(final Double age) {
        this.age = age;
    }
    
    public Double getRalJrsPrestCalc() {
        return this.ralJrsPrestCalc;
    }
    
    public void setRalJrsPrestCalc(final Double ralJrsPrestCalc) {
        this.ralJrsPrestCalc = ralJrsPrestCalc;
    }
    
    public Double getPrestationCommande() {
        return this.prestationCommande;
    }
    
    public void setPrestationCommande(final Double prestationCommande) {
        this.prestationCommande = prestationCommande;
    }

	public String getPriorite() {
		return priorite;
	}

	public void setPriorite(String priorite) {
		this.priorite = priorite;
	}

	public boolean isDecloturedByUser() {
		return decloturedByUser;
	}

	public void setDecloturedByUser(boolean decloturedByUser) {
		this.decloturedByUser = decloturedByUser;
	}

	public Date getDatePvReceptionProvisoire() {
		return datePvReceptionProvisoire;
	}

	public void setDatePvReceptionProvisoire(Date datePvReceptionProvisoire) {
		this.datePvReceptionProvisoire = datePvReceptionProvisoire;
	}

	public Date getDatePvReceptionDefinitive() {
		return datePvReceptionDefinitive;
	}

	public void setDatePvReceptionDefinitive(Date datePvReceptionDefinitive) {
		this.datePvReceptionDefinitive = datePvReceptionDefinitive;
	}

	public String getConditionFacturation() {
		return conditionFacturation;
	}

	public void setConditionFacturation(String conditionFacturation) {
		this.conditionFacturation = conditionFacturation;
	}

	public Double getMontantResteAReceptionnner() {
		return montantResteAReceptionnner;
	}

	public void setMontantResteAReceptionnner(Double montantResteAReceptionnner) {
		this.montantResteAReceptionnner = montantResteAReceptionnner;
	}

	public boolean isCloturedByUser() {
		return cloturedByUser;
	}

	public void setCloturedByUser(boolean cloturedByUser) {
		this.cloturedByUser = cloturedByUser;
	}

	public Integer getTauxAvancement() {
		return tauxAvancement;
	}

	public void setTauxAvancement(Integer tauxAvancement) {
		this.tauxAvancement = tauxAvancement;
	}

	public String getStatutProjet() {
		return statutProjet;
	}

	public void setStatutProjet(String statutProjet) {
		this.statutProjet = statutProjet;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
	

	
	
	
	
	
	
	
	
    

}