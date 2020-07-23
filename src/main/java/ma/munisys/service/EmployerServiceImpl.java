package ma.munisys.service;


import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.munisys.dao.EmployerRepository;
import ma.munisys.entities.Employer;


@Service
public class EmployerServiceImpl implements EmployeService {
	
	@Autowired
	private EmployerRepository emplRepository;

	@Override
	public Collection<Employer> getALlEmploye() {
		// TODO Auto-generated method stub
		return emplRepository.getAllEmployees();
	}

	@Override
	public Collection<Employer> getAllEmployeesByService(String serviceName) {
		// TODO Auto-generated method stub
		return emplRepository.getAllEmployeesByService(serviceName);
	}

	@Override
	public Collection<Employer> getEmployeesByName(String name) {
		// TODO Auto-generated method stub
		return emplRepository.getEmployeesByName("%"+name+"%");
	}

	@Override
	public Employer getEmployer(String codeEmployer) {
		// TODO Auto-generated method stub
		return emplRepository.getEmployee(codeEmployer);
	}

	@Override
	public Employer saveEmployer(Employer e) {
		// TODO Auto-generated method stub
		return emplRepository.save(e);
	}

	

}
