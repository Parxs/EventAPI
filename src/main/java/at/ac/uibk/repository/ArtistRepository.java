package at.ac.uibk.repository;

import java.util.ArrayList;
import java.util.List;

import at.ac.uibk.model.Artist;

// TODO use real data and db
public class ArtistRepository {

	ArrayList<Artist> artists = new ArrayList<>();

	// TODO threadsafe solution
	private int currentId = 100;

	public ArtistRepository() {

	}

	public Artist getArtist(int id) {
		for (Artist artist : artists) {
			if (artist.getArtistId() == id) {
				return artist;
			}
		}

		if (id < 100) {
			Artist artist = new Artist(id, "name" + id, id, "genre" + id);
			artists.add(artist);
			return artist;
		}
		return null;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public int createArtist(String name, int age, String genre) {

		Artist artist = new Artist(currentId, name, age, genre);
		artists.add(artist);
		currentId++;
		return currentId - 1;
	}

	public List<Artist> searchForArtist(String name, int age, String genre) {
		ArrayList<Artist> result = new ArrayList<>();
		int fitting;
		for (Artist artist : artists) {
			fitting = 0;
			if (name == "") {
				fitting++;
			} else {
				fitting = artist.getName().contains(name) ? fitting + 1 : fitting;
			}

			if (age == -1) {
				fitting++;
			} else {
				fitting = artist.getAge() == age ? fitting + 1 : fitting;
			}

			if (genre == "") {
				fitting++;
			} else {
				fitting = artist.getGenre().contains(genre) ? fitting + 1 : fitting;
			}
			if (fitting == 3) {
				result.add(artist);
			}
		}
		return result;
	}
}
