package ma.munisys.service;

import java.util.Collection;

import ma.munisys.entities.Reunion;

public interface ReunionService {
	
	public Collection<Reunion> getAllReunions();
	
	public Reunion addReunion(Reunion reunion);
	
	public void deleteReunion(Long idReunion);
	
	public Reunion modifierReunion(Long idReunion,Reunion reunion);

}
