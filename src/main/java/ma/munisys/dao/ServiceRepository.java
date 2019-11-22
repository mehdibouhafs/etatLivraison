package ma.munisys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ma.munisys.entities.Service;

@RepositoryRestResource(collectionResourceRel = "service", path = "service")
public interface ServiceRepository extends JpaRepository<Service,Long> {
	
	@Query("select e from Service e where e.servName = :x ")
	public Service getService(@Param("x") String nomService);

}
