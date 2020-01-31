package ma.munisys.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fournisseurs")
public class Fournisseur implements Serializable {
	
	@Id
	private String name;
	
	public Fournisseur() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Fournisseur [name=" + name + "]";
	}
	
	

	
	
	

}
