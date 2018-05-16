package at.ac.uibk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.uibk.model.Artist;
import at.ac.uibk.model.Event;
import at.ac.uibk.model.Venue;
import at.ac.uibk.repository.ArtistRepository;
import at.ac.uibk.repository.EventRepository;
import at.ac.uibk.repository.VenueRepository;

@Service("eventService")
public class EventService {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private VenueRepository revenueRepository;

	public EventService() {
	}

	public Event getEvent(int id) {
		Event event = eventRepository.getEvent(id);
		return event;
	}

	public List<Event> getEvents() {
		return eventRepository.getEvents();
	}

	public int createEvent(String title, String description, String startTime, int revenueId, int artistId) {
		return eventRepository.createEvent(title, description, startTime, revenueId, artistId);
	}
	
	public Artist getArtistsOfEvent(int id){
		Event e = eventRepository.getEvent(id);
		return artistRepository.getArtist(e.getArtistId());
	}
	
	public Venue getVenueOfEvent(int id) {
		Event e = eventRepository.getEvent(id);
		return revenueRepository.getVenue(e.getVenueId());
	}
}