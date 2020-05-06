package ma.munisys.service;

import org.springframework.data.jpa.domain.Specification;

import ma.munisys.entities.BalanceAgee;

public class BalanceAgeeSpecification {

	
	
	public static Specification<BalanceAgee> byClient(String client) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("client"), client);
	     };
	  }
	
	public static Specification<BalanceAgee> byCR(String cr) {
	     return (root, query, cb) -> {
	         return cb.equal(root.get("chargee_recouv"), cr);
	     };
	  }
	
	
}
