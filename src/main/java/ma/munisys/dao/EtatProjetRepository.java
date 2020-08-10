<<<<<<< HEAD
package ma.munisys.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ma.munisys.entities.EtatProjet;





public interface EtatProjetRepository extends JpaRepository<EtatProjet, Long> {
	@Query(value="select e from EtatProjet e where e.id = 1")
	public EtatProjet findEtatProjet();
}
=======
package ma.munisys.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ma.munisys.entities.EtatProjet;





public interface EtatProjetRepository extends JpaRepository<EtatProjet, Long> {
	@Query(value="select e from EtatProjet e where e.id = 1")
	public EtatProjet findEtatProjet();
}
>>>>>>> munisysRepo/main
