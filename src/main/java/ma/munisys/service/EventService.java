package ma.munisys.service;
import java.util.Collection;
import java.util.Date;


import ma.munisys.entities.Event;


public interface EventService {
	
	
	
	public Collection<Event> getEvents(String username,Date lastConnectionDate);
	

	
	public Event saveEvent (Event e);
	
	public Event updateStatutEvent (Long id);
	
	
	
	

}
