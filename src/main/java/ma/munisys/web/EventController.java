package ma.munisys.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ma.munisys.entities.Event;
import ma.munisys.service.EventService;

@RestController
@CrossOrigin(origins="*")
public class EventController {
	
	private static final Logger LOGGER = LogManager.getLogger(EventController.class);
	
	@Autowired
	private EventService eventService;

	@RequestMapping(value="/events",method=RequestMethod.GET)
	public Collection<Event> getEvents(@RequestParam(name="username") String username,@RequestParam(name="service" ,required = false) String service,@RequestParam(name="lastConnectionDate" ,required = false) String lastConnectionDate) {
		
		SimpleDateFormat sp =new SimpleDateFormat("DD/MM/YYYY");
		Date lastConnectionDate2 = null;
		try {
			lastConnectionDate2 = sp.parse(lastConnectionDate);
			
		} catch (ParseException e) {
			try {
				lastConnectionDate2 = sp.parse("01/01/2019");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return eventService.getEvents(username, lastConnectionDate2,service);
	}
	
	@RequestMapping(value="/events",method=RequestMethod.POST)
	public Event saveEvent(Event e) {
		return eventService.saveEvent(e);
	}
	
	@RequestMapping(value = "/events/{idEvent}", method = RequestMethod.PUT)
	public Event updateStatutEvent(@PathVariable(name="idEvent") Long idEvent,@RequestBody Event event) {
		return eventService.updateStatutEvent(event.getId());
	}

	
	
	
	

}
