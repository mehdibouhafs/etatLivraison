package ma.munisys.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ma.munisys.entities.EtatRecouvrement;




public interface EtatRecouvrementRepository extends JpaRepository<EtatRecouvrement, Long> {
	
	
	@Query(value="select e from EtatRecouvrement e where e.id = 1")
	public EtatRecouvrement findEtatRecouvrement();
	
	
}
