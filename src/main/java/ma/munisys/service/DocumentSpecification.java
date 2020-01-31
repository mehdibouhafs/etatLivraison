package ma.munisys.service;

import org.springframework.data.jpa.domain.Specification;

import ma.munisys.entities.Document;

public class DocumentSpecification {
	
	public static Specification<Document> isCloture(boolean cloturer) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("cloture"), cloturer);
	     };
	  }
	
	public static Specification<Document> isNotCloture() {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("cloture"), false);
	     };
	  }
	
	
	public static Specification<Document> byClient(String client) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("client"), client);
	     };
	  }
	
	public static Specification<Document> byCommercial(String commercial) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("commercial"), commercial);
	     };
	  }
	
	public static Specification<Document> byChefProjet(String chefDocument) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("chefProjet"), chefDocument);
	     };
	  }
	
	public static Specification<Document> byChargeRecouvrement(String chargeRecouvrement) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("chargerRecouvrement"), chargeRecouvrement);
	     };
	  }
	
	public static Specification<Document> byStatut(String statut) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("statut"), statut);
	     };
	  }
	
	public static Specification<Document> byAnnee(String anneePiece) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("anneePiece"), anneePiece);
	     };
	  }
	
	
	
	

}
