package at.ac.uibk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.uibk.model.Artist;
import at.ac.uibk.model.Event;
import at.ac.uibk.model.GenericList;
import at.ac.uibk.repository.ArtistRepository;
import at.ac.uibk.repository.EventRepository;

@Service("artistService")
public class ArtistService {
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private EventRepository eventRepository;

	public Artist getArtist(int id) {

		return artistRepository.getArtist(id);
	}

	public List<Artist> getArtists() {
		return artistRepository.getArtists();
	}

	public int createArtist(String name, int age, String genre) {
		return artistRepository.createArtist(name, age, genre);
	}
	
	public boolean updateArtist(int id, String name, int age, String genre) {
		return artistRepository.updateArtist(id,name, age, genre);
	}
	
	public boolean deleteArtist(int id) {
		return artistRepository.deleteArtist(id);
	}

	public GenericList<Artist> searchForArtist(String name, int age, String genre) {
		List<Artist> searchForArtist = artistRepository.searchForArtist(name, age, genre);
		GenericList<Artist> list = new GenericList<>(searchForArtist);
		return list;
	}

	public GenericList<Event> getEventsOfArtist(int id) {
		List<Event> eventsOfArtist = eventRepository.getEventsOfArtist(id);
		GenericList<Event> list = new GenericList<>(eventsOfArtist);
		return list;
	}

}