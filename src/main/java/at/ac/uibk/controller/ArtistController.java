package at.ac.uibk.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.uibk.model.Artist;
import at.ac.uibk.model.GenericList;
import at.ac.uibk.model.Navigation;
import at.ac.uibk.service.ArtistService;

//TODO real rest via crud

@RestController
public class ArtistController {

	@Autowired
	private ArtistService artistService;

	private void addStandardNavigation(Navigation navi) {
		navi.add(linkTo(methodOn(ArtistController.class).createArtist("name", 18, "genre")).withSelfRel());
		navi.add(linkTo(methodOn(ArtistController.class).searchForArtist("name", 18, "genre")).withSelfRel());
	}

	@RequestMapping(value = "/artists") // , method = RequestMethod.GET)
	public HttpEntity<Navigation> getArtists() {

		Navigation navi = new Navigation("Operations for Artists");

		addStandardNavigation(navi);

		List<Artist> artists = artistService.getArtists();
		if (artists != null) {
			for (Artist artist : artists) {
				navi.add(linkTo(methodOn(ArtistController.class).getArtist(artist.getArtistId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(navi, HttpStatus.OK);
	}

	@RequestMapping(value = "/artists/{id}") // , method = RequestMethod.GET)
	public HttpEntity<Artist> getArtist(@PathVariable("id") int id) {

		Artist artist = artistService.getArtist(id);
		artist.add(linkTo(methodOn(ArtistController.class).getArtists()).withSelfRel());
		artist.add(linkTo(methodOn(ArtistController.class).createArtist("name", 18, "genre")).withSelfRel());

		return new ResponseEntity<>(artist, HttpStatus.OK);
	}

	@RequestMapping(value = "/artists/new") // , method = RequestMethod.POST)
	public HttpEntity<Navigation> createArtist(
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "age", required = true, defaultValue = "") int age,
			@RequestParam(value = "genre", required = true, defaultValue = "") String genre) {

		Navigation navi = new Navigation();
		int id = artistService.createArtist(name, age, genre);
		if (-1 < id) {
			navi.setContent("Artist created");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(ArtistController.class).getArtist(id)).withSelfRel());
			navi.add(linkTo(methodOn(ArtistController.class).getArtists()).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.OK);
		} else {
			navi.setContent("Failed to create Artist");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(ArtistController.class).getArtists()).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/artists/search")
	public HttpEntity<GenericList<Artist>> searchForArtist(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "age", required = false, defaultValue = "-1") int age,
			@RequestParam(value = "genre", required = false, defaultValue = "") String genre) {

		GenericList<Artist> searchForArtist = artistService.searchForArtist(name, age, genre);
		searchForArtist.add(linkTo(methodOn(ArtistController.class).getArtists()).withSelfRel());
		searchForArtist.add(linkTo(methodOn(ArtistController.class).createArtist("name", 18, "genre")).withSelfRel());
		if (searchForArtist != null) {
			for (Artist artist : searchForArtist.getList()) {
				searchForArtist
						.add(linkTo(methodOn(ArtistController.class).getArtist(artist.getArtistId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(searchForArtist, HttpStatus.OK);
	}

}
