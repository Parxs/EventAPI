package at.ac.uibk.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.uibk.model.Venue;

@Service("venueRepository")
public class VenueRepository {

	public VenueRepository() {
		createVenue(1, "Olympia Stadion", "Austria", "Innsbruck", "Olympia Strasse 1", "TAUSEND");
		createVenue(2, "Kleines Ding", "Austria", "Innsbruck", "Klein Ding strasse 1", "net tausend");
		createVenue(3, "Mittleres Halle", "Austria", "Innsbruck", "Haupt Strasse 1", "fast tausend");

	}

	ArrayList<Venue> venues = new ArrayList<>();

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

	public boolean deleteVenue(int id) {
		Venue venue = getVenue(id);
		if (venue == null)
			return false;
		this.venues.remove(venue);
		return true;
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

	public boolean updateVenue(int id, String name, String country, String city, String address, String size) {
		this.venues.remove(this.getVenue(id));
		Venue venue = new Venue(id, name, country, city, address, size);
		venues.add(venue);
		return true;
	}

	public List<Venue> searchVenue(String name, String country, String city) {
		ArrayList<Venue> result = new ArrayList<>();
		int fitting;
		for (Venue venue : venues) {
			fitting = 0;
			if (name == "") {
				fitting++;
			} else {
				fitting = venue.getName().contains(name) ? fitting + 1 : fitting;
			}

			if (country == "") {
				fitting++;
			} else {
				fitting = venue.getCountry().contains(country) ? fitting + 1 : fitting;
			}

			if (city == "") {
				fitting++;
			} else {
				fitting = venue.getCity().contains(city) ? fitting + 1 : fitting;
			}
			if (fitting == 3) {
				result.add(venue);
			}
		}
		return result;
	}
}
