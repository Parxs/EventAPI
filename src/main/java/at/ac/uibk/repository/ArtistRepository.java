package at.ac.uibk.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import at.ac.uibk.model.Artist;

@Service("ArtistRepository")
public class ArtistRepository {

	ArrayList<Artist> artists = new ArrayList<>();

	public ArtistRepository() {
		createArtist(1, "Justin Bieber", 12, "Pop");
		createArtist(2, "Lady Gaga", 30, "Weird Pop");
		createArtist(3, "Metallica", 50, "METAL");
		createArtist(4, "Joondalup event", 0, "various");
	}

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
		this.removeArtist(id);		
		Artist artist = new Artist(id, name, age, genre);
		artists.add(artist);
		return true;
	}

	public boolean deleteArtist(int id) {
		Artist toRemove = this.getArtist(id);
		if (toRemove == null)
			return false;
		this.artists.remove(toRemove);
		return true;
	}
	
	public void removeArtist(int id) {
		for (int i = 0; i < artists.size(); i++) {
			if(artists.get(i).getArtistId() == id) {
				artists.remove(i);
				return;
			}
		}
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
