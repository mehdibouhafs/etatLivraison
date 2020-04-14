package ma.munisys.service;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import ma.munisys.entities.StockProjet;

public class StockProjetSpecification {

	private StockProjet stockProjet;
	
	

	public StockProjetSpecification(StockProjet stockProjet) {
		super();
		this.stockProjet = stockProjet;
	}
	
	
	public static Specification<StockProjet> byClient(String client) {
		System.out.println("NIVEAU OK 2" + client);

	     return (root, query, cb) -> {
	         return cb.equal(root.get("client"), client);
	     };
	  }


	public static Specification<StockProjet> byNumLot(String numLot) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("numLot"), numLot);
	     };
	  }
	
	public static Specification<StockProjet> byAnnee(String annee) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("annee"), annee);
	     };
	  }
	
	public static Specification<StockProjet> byCommercial(String com) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("commercial"), com);
	     };
	  }
	
	
	public static Specification<StockProjet> byMagasin(String magasin) {
		System.out.println("MAGASIN BEFORE QUERY "+magasin);

	     return (root, query, cb) -> {
	         return cb.like(root.get("magasin"), magasin);
	     };
	  }
	
	

	public static Specification<StockProjet> byNomMagasin(String magasin) {
		
	     return (root, query, cb) -> {
	    	 return cb.equal(root.get("nomMagasin"), magasin);
	     };
	  }
	
	
}
