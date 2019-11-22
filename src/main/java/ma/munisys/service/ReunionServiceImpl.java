package ma.munisys.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.munisys.dao.ReunionRepository;
import ma.munisys.entities.Reunion;

@Service
public class ReunionServiceImpl implements ReunionService {
	
	@Autowired
	private ReunionRepository reunionRepository;

	@Override
	public Reunion addReunion(Reunion reunion) {
		// TODO Auto-generated method stub
		return reunionRepository.save(reunion);
	}

	@Override
	public void deleteReunion(Long idReunion) {
		
		Reunion r = reunionRepository.getOne(idReunion);
		
		reunionRepository.delete(r);
		
	}

	@Override
	public Reunion modifierReunion(Long idReunion, Reunion reunion) {
		// TODO Auto-generated method stub
		
		Reunion r = reunionRepository.getOne(idReunion);
		
		if(r != null) {
			reunion.setId(idReunion);
			
			reunionRepository.saveAndFlush(reunion);
		}else {
			throw new RuntimeException("don't find reunion");
		}
		return reunion;
	}

	@Override
	public Collection<Reunion> getAllReunions() {
		// TODO Auto-generated method stub
		return reunionRepository.getAllReunions();
	}

}
