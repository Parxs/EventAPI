package at.ac.uibk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.uibk.model.Artist;
import at.ac.uibk.model.Event;
import at.ac.uibk.model.GenericList;
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
	private VenueRepository venueRepository;

	public EventService() {
	}

	public Event getEvent(int id) {
		Event event = eventRepository.getEvent(id);
		return event;
	}
	
	public boolean deleteEvent(int id) {
		return eventRepository.deleteEvent(id);
	}

	public List<Event> getEvents() {
		return eventRepository.getEvents();
	}

	public int createEvent(String title, String description, String startTime, int revenueId, int artistId) {
		return eventRepository.createEvent(title, description, startTime, revenueId, artistId);
	}
	
	public boolean updateEvent(int id, String title, String description, String startTime, int revenueId, int artistId) {
		return eventRepository.updateEvent(id, title, description, startTime, revenueId, artistId);
	}
	
	public Artist getArtistsOfEvent(int id){
		Event e = eventRepository.getEvent(id);
		return artistRepository.getArtist(e.getArtistId());
	}
	
	public Venue getVenueOfEvent(int id) {
		Event e = eventRepository.getEvent(id);
		return venueRepository.getVenue(e.getVenueId());
	}
	
	public GenericList<Event> searchEvent(String name, String artistName, String venueName){
		List<Artist> artistList = artistRepository.searchForArtist(name, -1, "");
		int artistId = -1;
		if(artistList.size() > 0) {
			artistId = artistList.get(0).getArtistId();
		}
		
		// TODO use venue search here to get id
		int venueId = -1;
		List<Event> searchForEvent = eventRepository.searchForEvent(name, artistId, venueId);
		
		GenericList<Event> list = new GenericList<>(searchForEvent);
		return list;
	}

}