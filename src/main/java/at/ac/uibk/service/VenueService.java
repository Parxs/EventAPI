package at.ac.uibk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.uibk.model.Artist;
import at.ac.uibk.model.GenericList;
import at.ac.uibk.model.Venue;
import at.ac.uibk.repository.ArtistRepository;
import at.ac.uibk.repository.EventRepository;
import at.ac.uibk.repository.VenueRepository;

@Service("venueService")
public class VenueService {
	@Autowired
	private VenueRepository venueRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ArtistRepository artistRepository;

	public Venue getVenue(int id) {

		return venueRepository.getVenue(id);
	}
	
	public boolean deleteVenue(int id) {
		return venueRepository.deleteVenue(id);
	}

	public List<Venue> getRevenues() {
		return venueRepository.getVenues();
	}

	public int createVenue(String name, String country, String city, String address, String size) {
		return venueRepository.createVenue(name, country, city, address, size);
	}
	
	public boolean updateVenue(int id, String name, String country, String city, String address, String size) {
		return venueRepository.updateVenue(id, name, country, city, address, size);
	}

	public GenericList<Venue> searchVenue(String name, String country, String city) {
		List<Venue> searchForVenue = venueRepository.searchVenue(name, country, city);
		GenericList<Venue> list = new GenericList<>(searchForVenue);
		return list;
	}

	public GenericList<Artist> getArtistsInVenue(int id) {
		List<Integer> artistsInRevenue = eventRepository.getArtistsInRevenue(id);
		List<Artist> artists = artistRepository.getArtists(artistsInRevenue);
		GenericList<Artist> list = new GenericList<>(artists);
		return list;
	}
}