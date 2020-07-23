package ma.munisys.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "commentairesProduits")
public class CommentaireProduit  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private Date date;
	

	private String employer;
	
	@ManyToOne
	private AppUser user;
	
	@ManyToOne
	private Produit produit;
	

	
	public CommentaireProduit() {
		
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

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	@JsonIgnore
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
	@Override
	public boolean equals(Object obj) {

		CommentaireProduit c = (CommentaireProduit) obj;

		if (c!=null&& c.getContent().equals(this.getContent()) && c.getDate().equals(this.getDate())
				&& c.getEmployer().equals(this.getEmployer())
				&& c.getUser().getUsername().equals(this.getUser().getUsername())) {
			return true;
		} else {
			return false;
		}

	}

	

	
	

	
	
	
	
	
}
