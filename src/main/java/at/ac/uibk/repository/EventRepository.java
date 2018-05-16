package at.ac.uibk.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.uibk.model.Event;

@Service("eventRepository")
public class EventRepository {
	ArrayList<Event> events = new ArrayList<>();

	// TODO threadsafe solution
	private int currentId = 100;

	public EventRepository() {

	}

	public Event getEvent(int id) {
		for (Event event : events) {
			if (event.getEventId() == id) {
				return event;
			}
		}

		if (id < 100) {
			Event event = new Event(id, "title", "description", "startTime", 0, 0);
			events.add(event);
			return event;
		}
		return null;
		// TODO use real data and db
		// return new Event(id, "farmers market", "a market with farmers", "2018-05-05
		// 12:00:00", "austria", "innsbruck",
		// "hauptstrasse 7");
	}

	public List<Event> getEvents() {
		return events;
	}

	public int createEvent(String title, String description, String startTime, int revenueId, int artistId) {

		Event event = new Event(currentId, title, description, startTime, revenueId, artistId);
		currentId++;
		events.add(event);

		return currentId - 1;
	}
}
