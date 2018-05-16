package at.ac.uibk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.uibk.model.Venue;
import at.ac.uibk.repository.VenueRepository;

@Service("venueService")
public class VenueService {
	@Autowired
	private VenueRepository venueRepository;

	public Venue getVenue(int id) {

		return venueRepository.getVenue(id);
	}

	public List<Venue> getRevenues() {
		return venueRepository.getVenues();
	}

	public int createVenue(String name, String country, String city, String address, String size) {
		return venueRepository.createVenue(name, country, city, address, size);
	}
}