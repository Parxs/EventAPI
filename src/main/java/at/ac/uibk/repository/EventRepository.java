package at.ac.uibk.repository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.uibk.model.Event;

@Service("eventRepository")
public class EventRepository {
	ArrayList<Event> events = new ArrayList<>();

	private int currentId = 100;

	public EventRepository() {
		this.createEvent("Justin Bieber Concert", "A very good concert with Justin Bieber", "16.05.2018", 1, 1);
		this.createEvent("Lady Gaga Concert", "A very meh concert with Lady Gaga", "17.05.2018", 2, 2);
		this.createEvent("Metallica Concert", "A awesome concert with Metallica", "18.05.2018", 3, 3);

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
	}

	public boolean deleteEvent(int id) {
		Event toRemove = this.getEvent(id);
		if (toRemove == null)
			return false;
		this.events.remove(toRemove);
		return true;
	}

	public List<Event> getEvents() {
		return events;
	}

	public int createEvent(String title, String description, String startTime, int venueId, int artistId) {

		Event event = new Event(currentId, title, description, startTime, venueId, artistId);
		currentId++;
		events.add(event);

		return currentId - 1;
	}

	public boolean updateEvent(int id, String title, String description, String startTime, int venueId, int artistId) {
		this.events.remove(this.getEvent(id));
		Event event = new Event(id, title, description, startTime, venueId, artistId);
		events.add(event);
		return true;
	}

	public List<Event> searchForEvent(String name, int artistId, int venueId) {
		ArrayList<Event> result = new ArrayList<>();
		int fitting;
		for (Event event : events) {
			fitting = 0;
			if (name == "") {
				fitting++;
			} else if (event.getTitle().contains(name)) {
				fitting++;
			} else {
				fitting = event.getDescription().contains(name) ? fitting + 1 : fitting;
			}

			if (artistId == -1) {
				fitting++;
			} else {
				fitting = event.getArtistId() == artistId ? fitting + 1 : fitting;
			}

			if (venueId == -1) {
				fitting++;
			} else {
				fitting = event.getVenueId() == venueId ? fitting + 1 : fitting;
			}
			if (fitting == 3) {
				result.add(event);
			}
		}
		return result;
	}

	public List<Event> getEventsOfArtist(int id) {
		ArrayList<Event> list = new ArrayList<>();
		for (Event event : events) {
			if (event.getArtistId() == id) {
				list.add(event);
			}
		}
		return list;
	}

	public List<Integer> getArtistsInRevenue(int venueId) {
		ArrayList<Integer> list = new ArrayList<>();
		LinkedHashSet<Integer> hashSet = new LinkedHashSet<>();
		for (Event event : events) {
			if (event.getVenueId() == venueId) {
				int artistId = event.getArtistId();
				if (hashSet.add(artistId)) {
					list.add(artistId);
				}
			}
		}
		return list;
	}
}
