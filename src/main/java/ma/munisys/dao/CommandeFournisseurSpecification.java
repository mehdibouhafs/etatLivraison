package ma.munisys.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import ma.munisys.dto.CommandeFournisseurSearch;
import ma.munisys.dto.ContratSearch;
import ma.munisys.entities.CommandeFournisseur;
import ma.munisys.entities.Contrat;

public class CommandeFournisseurSpecification implements Specification<CommandeFournisseur> {
	
	private CommandeFournisseurSearch commandeFournisseurSearch;
	
	

	public CommandeFournisseurSpecification(CommandeFournisseurSearch commandeFournisseurSearch) {
		super();
		this.commandeFournisseurSearch = commandeFournisseurSearch;
	}
	
	public static Specification<CommandeFournisseur> byId(Long id) {
		return (root, query, cb) -> {
	    	query.distinct(true);
	         return cb.equal(root.get("id"),
	        		id );
	     };
	}
	


	@Override
	public Predicate toPredicate(Root<CommandeFournisseur> root, CriteriaQuery<?> query, 
			CriteriaBuilder cb) {
		
		final List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(cb.equal(root.join("contrats").get("numContrat"),commandeFournisseurSearch.getNumContrat()));
		
		if(commandeFournisseurSearch.getMotCle()!=null) {
			Predicate fournisseur = cb.like(root.get("fournisseur"), "%"+commandeFournisseurSearch.getMotCle()+"%");
			
			Predicate remarque = cb.like(root.get("remarque"), "%"+commandeFournisseurSearch.getMotCle()+"%");
			
			Predicate numeroDocument = cb.like(root.get("numeroDocument").as(String.class), "%"+commandeFournisseurSearch.getMotCle()+"%");
		
			
			Predicate descriptionArticle = cb.like(root.get("descriptionArticle"), "%"+commandeFournisseurSearch.getMotCle()+"%");
			
			
			predicates.add(cb.or(fournisseur,remarque,numeroDocument,descriptionArticle));
		}
		
        
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
	
	

}
