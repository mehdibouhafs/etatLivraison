package ma.munisys.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.munisys.entities.Employer;
import ma.munisys.service.EmployeService;

@RestController
@CrossOrigin(origins="*")
public class EmployeController {
	
	@Autowired
	private EmployeService employeService;

	@RequestMapping(value="/getAllEmployees",method=RequestMethod.GET)
	public Collection<Employer> getAllEmployees() {
		return employeService.getALlEmploye();
	}
	
	@RequestMapping(value="/getAllEmployeesByService",method=RequestMethod.GET)
	public Collection<Employer> getAllEmployeesByService(@RequestParam(name="serviceName")String serviceName){
		return employeService.getAllEmployeesByService(serviceName);
	}
	
	
	@RequestMapping(value="/getEmployeesByName",method=RequestMethod.GET)
	public Collection<Employer> getEmployeesByName(@RequestParam(name="nameEmploye")String nameEmploye){
		return employeService.getEmployeesByName(nameEmploye);
	}
	
	
	

}
