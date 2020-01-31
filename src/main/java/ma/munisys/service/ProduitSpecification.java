package ma.munisys.service;

import org.springframework.data.jpa.domain.Specification;

import ma.munisys.entities.Produit;

public class ProduitSpecification {
	
	
	
	public static Specification<Produit> byNature(String nature) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("nature"), nature);
	     };
	  }
	
	public static Specification<Produit> byQteGreaterThanZero() {
	     return (root, query, cb) -> {
	         return cb.greaterThan(root.get("qte"), 0);
	     };
	  }
	
	public static Specification<Produit> byQteEqualsZero() {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("qte"), 0);
	     };
	  }
	
	
	public static Specification<Produit> byClient(String client) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("client"), client);
	     };
	  }
	
	public static Specification<Produit> bySousNature(String sousNature) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("sousNature"), sousNature);
	     };
	  }
	
	public static Specification<Produit> byDomaine(String domaine) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("domaine"), domaine);
	     };
	  }
	
	public static Specification<Produit> bySousDomaine(String sousDomaine) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("sousDomaine"), sousDomaine);
	     };
	  }
	
	public static Specification<Produit> byNumLot(String numLot) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("numLot"), numLot);
	     };
	  }
	
	public static Specification<Produit> byArticle(String reference) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("itemCode"), reference);
	     };
	  }
	
	public static Specification<Produit> byNomMagasin(String magasin) {
		
	     return (root, query, cb) -> {
	    	 return cb.equal(root.get("nomMagasin"), magasin);
	     };
	  }
	
	

}
