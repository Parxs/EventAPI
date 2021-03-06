package at.ac.uibk.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.uibk.model.Artist;
import at.ac.uibk.model.Event;
import at.ac.uibk.model.GenericList;
import at.ac.uibk.model.Navigation;
import at.ac.uibk.model.SchemaResponse;
import at.ac.uibk.service.ArtistService;

@RestController
public class ArtistController {

	@Autowired
	private ArtistService artistService;

	private void addStandardNavigation(ResourceSupport rs) {
		rs.add(linkTo(methodOn(ArtistController.class).getArtists()).withRel("artists"));
		rs.add(linkTo(methodOn(ArtistController.class).createArtist("name", 18, "genre")).withRel("create"));
	}

	@RequestMapping(value = "/artists", method = RequestMethod.GET)
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

	@RequestMapping(value = "/artists/{id}/events", method = RequestMethod.GET)
	public HttpEntity<GenericList<Event>> getAllEvents(@PathVariable("id") int id) {

		GenericList<Event> eventsOfArtist = artistService.getEventsOfArtist(id);
		addStandardNavigation(eventsOfArtist);
		for (Event event : eventsOfArtist.getList()) {
			eventsOfArtist.add(linkTo(methodOn(EventController.class).getEvents(event.getEventId())).withRel("events"));
		}
		eventsOfArtist.add(linkTo(methodOn(ArtistController.class).getAllEvents(id)).withSelfRel());
		eventsOfArtist.add(linkTo(methodOn(ArtistController.class).getArtist(id)).withRel("artist"));

		return new ResponseEntity<>(eventsOfArtist, HttpStatus.OK);
	}

	@RequestMapping(value = "/artists/{id}", method = RequestMethod.GET)
	public HttpEntity<ResourceSupport> getArtist(@PathVariable("id") int id) {

		Artist artist = artistService.getArtist(id);
		if (artist == null) {
			Navigation eventError = new Navigation("Artist not found");
			return new ResponseEntity<>(eventError, HttpStatus.NOT_FOUND);
		}
		SchemaResponse res = new SchemaResponse("ViewAction");
		res.SetResult("Artist", artist);
		addStandardNavigation(res);
		res.add(linkTo(methodOn(ArtistController.class).getArtists()).withSelfRel());
		res.add(linkTo(methodOn(ArtistController.class).getAllEvents(id)).withRel("events.artist"));

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/artists", method = RequestMethod.POST)
	public HttpEntity<ResourceSupport> createArtist(
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "age", required = true, defaultValue = "") int age,
			@RequestParam(value = "genre", required = true, defaultValue = "") String genre) {

		int id = artistService.createArtist(name, age, genre);
		if (-1 < id) {
			SchemaResponse res = new SchemaResponse("CreateAction");
			res.SetResult("Artist", artistService.getArtist(id));
			addStandardNavigation(res);
			res.add(linkTo(methodOn(ArtistController.class).getArtist(id)).withSelfRel());

			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			Navigation navi = new Navigation();

			navi.setContent("Failed to create Artist");
			addStandardNavigation(navi);

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/artists/{id}", method = RequestMethod.PUT)
	public HttpEntity<ResourceSupport> updateArtist(@PathVariable("id") int id,
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "age", required = true, defaultValue = "") int age,
			@RequestParam(value = "genre", required = true, defaultValue = "") String genre) {

		boolean ok = artistService.updateArtist(id, name, age, genre);
		if (ok) {
			SchemaResponse res = new SchemaResponse("UpdateAction");
			res.SetResult("Artist", artistService.getArtist(id));
			addStandardNavigation(res);
			res.add(linkTo(methodOn(ArtistController.class).getArtist(id)).withSelfRel());

			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			Navigation navi = new Navigation();

			navi.setContent("Failed to update Artist");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(ArtistController.class).getArtist(id)).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/artists/{id}", method = RequestMethod.DELETE)
	public HttpEntity<ResourceSupport> deleteArtist(@PathVariable("id") int id) {
		Artist a = artistService.getArtist(id);
		boolean ok = artistService.deleteArtist(id);
		if (!ok) {
			Navigation navi = new Navigation("Artist not found");
			return new ResponseEntity<>(navi, HttpStatus.NOT_FOUND);
		}
		SchemaResponse res = new SchemaResponse("DeleteAction");
		res.SetResult("Artist", a);
		addStandardNavigation(res);
		res.add(linkTo(methodOn(ArtistController.class).getArtists()).withSelfRel());

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/artists/search", method = RequestMethod.GET)
	public HttpEntity<GenericList<Artist>> searchForArtist(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "age", required = false, defaultValue = "-1") int age,
			@RequestParam(value = "genre", required = false, defaultValue = "") String genre) {

		GenericList<Artist> searchForArtist = artistService.searchForArtist(name, age, genre);
		addStandardNavigation(searchForArtist);
		searchForArtist.add(linkTo(methodOn(ArtistController.class).getArtists()).withSelfRel());
		if (searchForArtist != null) {
			for (Artist artist : searchForArtist.getList()) {
				searchForArtist
						.add(linkTo(methodOn(ArtistController.class).getArtist(artist.getArtistId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(searchForArtist, HttpStatus.OK);
	}

}
