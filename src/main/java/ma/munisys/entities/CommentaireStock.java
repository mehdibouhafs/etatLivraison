package ma.munisys.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "commentaire_stock")
public class CommentaireStock implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private Date date;
	

	private String employer;
	
	
	@ManyToOne
	private AppUser user;

	@ManyToOne
	private Projet projet;
	
	@ManyToOne
	@JsonBackReference
	private StockProjet stock;
	

	public CommentaireStock(Long id, String content, Date date, String employer, AppUser user_username,
			Projet projet_code_projet,StockProjet stock) {
		super();
		this.id = id;
		this.content = content;
		this.date = date;
		this.employer = employer;
		this.user = user_username;
		this.projet = projet_code_projet;
		this.stock = stock;
	}

	public CommentaireStock() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public StockProjet getStock() {
		return stock;
	}

	public void setStock(StockProjet stock) {
		this.stock = stock;
	}


	public AppUser getUser_username() {
		return user;
	}

	public void setUser_username(AppUser user_username) {
		this.user = user_username;
	}
	
	
	public Projet getProjet_code_projet() {
		return projet;
	}

	public void setProjet_code_projet(Projet projet_code_projet) {
		this.projet = projet_code_projet;
	}
	
	

	

	
	
	
	
	
	
	
}



