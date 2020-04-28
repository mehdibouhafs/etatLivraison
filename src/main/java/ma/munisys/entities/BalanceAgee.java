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

import org.jfree.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "balanceAgee")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class BalanceAgee implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_balance;
		
	private String client;

	@Column(name="toisMois", nullable= true)
	private Double tois_mois;
	
	@Column(name="sixMois", nullable= true)	
	private Double six_mois;

	@Column(name="douzeMois", nullable= true)
	private Double douze_mois;
	
	@Column(name="supDouzeMois", nullable= true)
	private Double sup_douze_mois;
	
	@Column(name="total", nullable= true)
	private Double total;
	
	

	public BalanceAgee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BalanceAgee(String client, Double tois_mois, Double six_mois, Double douze_mois, Double sup_douze_mois,
			double total) {
		super();
		this.client = client;
		this.tois_mois = tois_mois;
		this.six_mois = six_mois;
		this.douze_mois = douze_mois;
		this.sup_douze_mois = sup_douze_mois;
		this.total = total;
	}

	public int getId_balance() {
		return id_balance;
	}

	public void setId_balance(int id_balance) {
		this.id_balance = id_balance;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Double getTois_mois() {
		return tois_mois;
	}

	public void setTois_mois(Double tois_mois) {
		this.tois_mois = tois_mois;
	}

	public Double getSix_mois() {
		return six_mois;
	}

	public void setSix_mois(Double six_mois) {
		this.six_mois = six_mois;
	}

	public Double getDouze_mois() {
		return douze_mois;
	}

	public void setDouze_mois(double douze_mois) {
		this.douze_mois = douze_mois;
	}

	public Double getSup_douze_mois() {
		return sup_douze_mois;
	}

	public void setSup_douze_mois(double sup_douze_mois) {
		this.sup_douze_mois = sup_douze_mois;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
	

	
	
	
	

}
