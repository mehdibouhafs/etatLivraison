package ma.munisys.service;

import org.springframework.data.jpa.domain.Specification;

import ma.munisys.entities.Projet;

public class ProjetSpecification {
	
	public static Specification<Projet> isCloture(boolean cloturer) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("cloture"), cloturer);
	     };
	  }
	
	public static Specification<Projet> isNotCloture() {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("cloture"), false);
	     };
	  }
	
	
	public static Specification<Projet> byClient(String client) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("client"), client);
	     };
	  }
	
	public static Specification<Projet> byCommercial(String commercial) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("commercial"), commercial);
	     };
	  }
	
	public static Specification<Projet> byChefProjet(String chefProjet) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("chefProjet"), chefProjet);
	     };
	  }
	
	public static Specification<Projet> byStatut(String statut) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("statut"), statut);
	     };
	  }
	
	public static Specification<Projet> byBu(String bu) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("bu"), bu);
	     };
	  }
	
	public static Specification<Projet> byType(String[] type) {
	     return (root, query, cb) -> {
	         return root.get("type").in(type);
	     };
	  }
	
	public static Specification<Projet> isNotAffecte() {
		
	     return (root, query, cb) -> {
	         return cb.isNull(root.get("chefProjet"));
	     };
	  }
	
	public static Specification<Projet> istAffecte() {
		
	     return (root, query, cb) -> {
	         return cb.isNotNull(root.get("chefProjet"));
	     };
	  }
	

}
