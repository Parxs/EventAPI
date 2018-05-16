package at.ac.uibk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.uibk.model.Event;
import at.ac.uibk.repository.EventRepository;

@Service("eventService")
public class EventService {
	private EventRepository eventRepository;

	public EventService() {
		eventRepository = new EventRepository();
	}

	public Event getEvent(int id) {
		Event event = eventRepository.getEvent(id);
		event.getRevenueId();
		return event;
	}

	public List<Event> getEvents() {
		return eventRepository.getEvents();
	}

	public int createEvent(String title, String description, String startTime, int revenueId, int artistId) {
		return eventRepository.createEvent(title, description, startTime, revenueId, artistId);
	}
}