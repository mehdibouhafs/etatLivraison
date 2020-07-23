package ma.munisys.dao;


import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ma.munisys.entities.Employer;



public interface EmployerRepository extends JpaRepository<Employer, Long> {
	
	
	@Query("SELECT e FROM Employer e  order by e.name asc")
	public Collection<Employer> getAllEmployees();
	
	
	@Query("SELECT e FROM Employer e where e.service.servName = :x  order by e.name asc")
	public Collection<Employer> getAllEmployeesByService(@Param("x") String serviceName);
	
	
	@Query("select e from Employer e where e.name like :x ")
	public Collection<Employer> getEmployeesByName(@Param("x") String name);
	
	
	@Query("select e from Employer e where e.code = :x")
	public Employer getEmployee(@Param("x") String codeEmployer);

}
