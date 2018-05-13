package at.ac.uibk.service;

import at.ac.uibk.model.Event;
import at.ac.uibk.repository.EventRepository;

public class EventService {
	private EventRepository eventRepository;
	
	public EventService(){
		this.eventRepository = new EventRepository();
	}

	public Event getEvent(String id) {
		
		return eventRepository.getEvent(id);
	}
	
	public boolean createEvent(String eventId,String title, String description, String startTime, String country, String city,String address) {
		return eventRepository.createEvent(eventId, title, description, startTime, country, city, address);
	}
}