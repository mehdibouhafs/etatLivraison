package ma.munisys.service;

import java.util.Collection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ma.munisys.dao.EventRepository;
import ma.munisys.entities.Event;


@Service
public class EventServiceImpl implements EventService {
	
	private static final Logger LOGGER = LogManager.getLogger(EventServiceImpl.class);
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Collection<Event> getEvents(String username, Date lastConnectionDate,String service) {
		// TODO Auto-generated method stub
		
		if(service!=null && !service.isEmpty()) {
			return eventRepository.findAllEventsbyService(username, service);
		}else {
			return eventRepository.findAllEvents(username);
		}
		
		
	}

	@Override
	public Event saveEvent(Event e) {
		// TODO Auto-generated method stub
		LOGGER.info("saving event " + e.getId());
		return eventRepository.save(e);
	}

	@Override
	public Event updateStatutEvent(Long id) {
		// TODO Auto-generated method stub
		LOGGER.info("updateStatutEvent " + id);
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
