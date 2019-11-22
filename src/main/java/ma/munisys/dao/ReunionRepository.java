package ma.munisys.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ma.munisys.entities.Reunion;

public interface ReunionRepository extends JpaRepository<Reunion, Long> {
	
	@Query("SELECT r FROM Reunion r  order by r.datePlannification asc")
	public Collection<Reunion> getAllReunions();
	
	

}
