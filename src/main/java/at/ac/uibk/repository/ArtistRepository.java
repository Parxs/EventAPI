package at.ac.uibk.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.uibk.model.Artist;

// TODO use real data and db
@Service("ArtistRepository")
public class ArtistRepository {

	ArrayList<Artist> artists = new ArrayList<>();

	public ArtistRepository() {
		createArtist(1, "justin Bieber", 12, "pop");
		createArtist(2, "lady gaga", 30, "weird pop");
		createArtist(3, "metallica", 50, "METAL");
	}

	// TODO threadsafe solution
	private int currentId = 100;

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

	public void createArtist(int id, String name, int age, String genre) {
		Artist artist = new Artist(id, name, age, genre);
		artists.add(artist);
	}
	
	public boolean updateArtist(int id, String name, int age, String genre) {
		this.artists.remove(this.getArtist(id));
		Artist artist = new Artist(id, name, age, genre);
		artists.add(artist);
		return true;
	}

	public boolean deleteArtist(int id) {
		Artist toRemove = this.getArtist(id);
		if(toRemove == null)
			return false;
		this.artists.remove(toRemove);
		return true;
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

	public List<Artist> getArtists(List<Integer> artistsInRevenue) {
		ArrayList<Artist> list = new ArrayList<>();

		for (int id : artistsInRevenue) {
			list.add(getArtist(id));
		}

		return list;
	}
}
