package ma.munisys.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ma.munisys.dto.ContratSearch;
import ma.munisys.entities.Contrat;

public class ContratSpecification implements Specification<Contrat> {
	
	private ContratSearch contratSearch;
	
	

	public ContratSpecification(ContratSearch contratSearch) {
		super();
		this.contratSearch = contratSearch;
	}
	
	public static Specification<Contrat> byNumContrat(Long numContrat) {
		return (root, query, cb) -> {
	    	query.distinct(true);
	         return cb.equal(root.get("numContrat"),
	        		numContrat );
	     };
	}
	


	@Override
	public Predicate toPredicate(Root<Contrat> root, CriteriaQuery<?> query, 
			CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		
		final List<Predicate> predicates = new ArrayList<Predicate>();
        if(contratSearch.getNomPartenaire() !=null) {
            predicates.add(cb.equal(root.get("nomPartenaire"),contratSearch.getNomPartenaire()));
        }
        if(contratSearch.getNumMarche()!=null) {
            predicates.add(cb.equal(root.get("numMarche"),contratSearch.getNumMarche()));
        }
        
        if(contratSearch.getPilote()!=null) {
            predicates.add(cb.equal(root.get("pilote"),contratSearch.getPilote()));
        }
        if(contratSearch.getSousTraiter()!=null) {
            predicates.add(cb.equal(root.get("sousTraiter"),contratSearch.getSousTraiter()));
        }
		
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	}
	
	

}
