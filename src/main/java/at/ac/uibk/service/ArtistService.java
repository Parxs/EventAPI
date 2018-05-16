package at.ac.uibk.service;

import java.util.List;

import at.ac.uibk.model.Artist;
import at.ac.uibk.model.GenericList;
import at.ac.uibk.repository.ArtistRepository;

public class ArtistService {
	private ArtistRepository artistRepository;

	public ArtistService() {
		this.artistRepository = new ArtistRepository();
	}

	public Artist getArtist(int id) {

		return artistRepository.getArtist(id);
	}

	public List<Artist> getArtists() {
		return artistRepository.getArtists();
	}

	public int createArtist(String name, int age, String genre) {
		return artistRepository.createArtist(name, age, genre);
	}

	public GenericList<Artist> searchForArtist(String name, int age, String genre) {
		List<Artist> searchForArtist = artistRepository.searchForArtist(name, age, genre);
		GenericList<Artist> list = new GenericList<>(searchForArtist);
		return list;
	}

}