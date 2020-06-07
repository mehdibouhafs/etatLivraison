package ma.munisys.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
		
		final List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(cb.equal(root.get("cloture"),false));
		
		if(contratSearch.getNumContrat()!=null) {
			predicates.add(cb.equal(root.get("numContrat"),contratSearch.getNumContrat()));
		}
		
		if(contratSearch.getMotCle()!=null) {
			Predicate nomPartenaire = cb.like(root.get("nomPartenaire"), "%"+contratSearch.getMotCle()+"%");
			
			Predicate pilote = cb.like(root.get("pilote"), "%"+contratSearch.getMotCle()+"%");
			
			Predicate numContrat = cb.like(root.get("numContrat").as(String.class), "%"+contratSearch.getMotCle()+"%");
		
			
			Predicate statut = cb.like(root.get("statut"), "%"+contratSearch.getMotCle()+"%");
			
			Predicate occurenceFacturation = cb.like(root.get("occurenceFacturationLabel"), "%"+contratSearch.getMotCle()+"%");
			
			Predicate codeProjet = cb.like(root.get("codeProjet"), "%"+contratSearch.getMotCle()+"%");
			
			Predicate nomSousTraitant = cb.like(root.get("nomSousTraitant"), "%"+contratSearch.getMotCle()+"%");
			
			Predicate numMarche = cb.like(root.get("numMarche"), "%"+contratSearch.getMotCle()+"%");
			
			Predicate description = cb.like(root.get("description"), "%"+contratSearch.getMotCle()+"%");
			
			predicates.add(cb.or(nomPartenaire,pilote,numContrat,statut,occurenceFacturation,codeProjet,nomSousTraitant,numMarche,description));
		}
		
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
