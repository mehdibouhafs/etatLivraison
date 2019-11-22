package ma.munisys.service;
import java.util.Collection;

import org.springframework.data.repository.query.Param;

import ma.munisys.entities.Employer;


public interface EmployeService {
	
	
	
	public Collection<Employer> getALlEmploye();
	
	public Collection<Employer> getAllEmployeesByService(String serviceName);
	
	public Collection<Employer> getEmployeesByName(String name);
	
	public Employer getEmployer (String codeEmployer);
	
	public Employer saveEmployer (Employer e);
	
	

}
