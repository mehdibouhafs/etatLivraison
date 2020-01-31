package ma.munisys.service;

import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ma.munisys.dao.EventRepository;
import ma.munisys.entities.Event;


@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Collection<Event> getEvents(String username, Date lastConnectionDate) {
		// TODO Auto-generated method stub
		return eventRepository.findAllEvents(username);
	}

	@Override
	public Event saveEvent(Event e) {
		// TODO Auto-generated method stub
		return eventRepository.save(e);
	}

	@Override
	public Event updateStatutEvent(Long id) {
		// TODO Auto-generated method stub
		Event e = eventRepository.getOne(id);
		e.setStatut(true);
		return eventRepository.save(e);
	}

	public EventRepository getEventRepository() {
		return eventRepository;
	}

	public void setEventRepository(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	

	

	

}
