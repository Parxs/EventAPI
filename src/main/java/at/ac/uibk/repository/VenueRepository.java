package at.ac.uibk.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.uibk.model.Venue;

// TODO use real data and db
@Service("venueRepository")
public class VenueRepository {

	public VenueRepository() {
		createVenue(1, "olympia stadion", "Austria", "Innsbruck", "olympia straﬂe 1", "TAUSEND");
		createVenue(2, "kleines ding", "Austria", "Innsbruck", "klein ding straﬂe 1", "net tausend");
		createVenue(3, "mittleres halle", "Austria", "Innsbruck", "haupt straﬂe 1", "fast tausend");
		
	}
	
	ArrayList<Venue> venues = new ArrayList<>();

	// TODO threadsafe solution
	private int currentId = 100;

	public Venue getVenue(int id) {
		for (Venue revenue : venues) {
			if (revenue.getVenueId() == id) {
				return revenue;
			}
		}

		if (id < 100) {
			Venue artist = new Venue(id, "name" + id, "country" + id, "city" + id, "address" + id,
					"" + id * 200 + " Personen");
			venues.add(artist);
			return artist;
		}
		return null;
	}

	public List<Venue> getVenues() {
		return venues;
	}

	public void createVenue(int id, String name, String country, String city, String address, String size) {
		Venue venue = new Venue(id, name, country, city, address, size);
		venues.add(venue);
	}
	
	public int createVenue(String name, String country, String city, String address, String size) {
		Venue venue = new Venue(currentId, name, country, city, address, size);
		venues.add(venue);
		currentId++;
		return currentId - 1;
	}
}
