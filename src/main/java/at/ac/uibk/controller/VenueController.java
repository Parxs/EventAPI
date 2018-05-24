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
import at.ac.uibk.model.GenericList;
import at.ac.uibk.model.Navigation;
import at.ac.uibk.model.Venue;
import at.ac.uibk.service.VenueService;

@RestController
public class VenueController {

	@Autowired
	private VenueService venueService;

	private void addStandardNavigation(ResourceSupport rs) {
		rs.add(linkTo(methodOn(VenueController.class).getVenues()).withRel("venues"));
		rs.add(linkTo(methodOn(VenueController.class).createVenue("name", "country", "city", "address", "size"))
				.withRel("create"));
	}

	@RequestMapping(value = "/venues", method = RequestMethod.GET)
	public HttpEntity<Navigation> getVenues() {

		Navigation navi = new Navigation("Operations for Venues");

		navi.add(linkTo(methodOn(VenueController.class).getVenues()).withSelfRel());
		navi.add(linkTo(methodOn(VenueController.class).createVenue("name", "country", "city", "address", "size"))
				.withRel("create"));

		List<Venue> venues = venueService.getRevenues();
		if (venues != null) {
			for (Venue venue : venues) {
				navi.add(linkTo(methodOn(VenueController.class).getVenue(venue.getVenueId())).withRel("venue"));
			}
		}
		return new ResponseEntity<>(navi, HttpStatus.OK);
	}

	@RequestMapping(value = "/venues/{id}", method = RequestMethod.GET)
	public HttpEntity<ResourceSupport> getVenue(@PathVariable("id") int id) {

		Venue venue = venueService.getVenue(id);
		if (venue == null) {
			Navigation eventError = new Navigation("Venue not found");
			return new ResponseEntity<>(eventError, HttpStatus.NOT_FOUND);
		}
		addStandardNavigation(venue);
		venue.add(linkTo(methodOn(VenueController.class).getVenue(id)).withSelfRel());
		venue.add(linkTo(methodOn(VenueController.class).getArtistsInVenue(id)).withRel("venues.artists"));

		return new ResponseEntity<>(venue, HttpStatus.OK);
	}

	@RequestMapping(value = "/venues/{id}", method = RequestMethod.DELETE)
	public HttpEntity<ResourceSupport> deleteVenue(@PathVariable("id") int id) {

		boolean ok = venueService.deleteVenue(id);
		if (!ok) {
			Navigation navi = new Navigation("Venue " + id + " not found");
			addStandardNavigation(navi);
			return new ResponseEntity<>(navi, HttpStatus.NOT_FOUND);

		} else {
			Navigation navi = new Navigation("Venue Deleted");
			addStandardNavigation(navi);

			return new ResponseEntity<>(navi, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/venues/{id}/artists", method = RequestMethod.GET)
	public HttpEntity<GenericList<Artist>> getArtistsInVenue(@PathVariable("id") int id) {

		GenericList<Artist> list = venueService.getArtistsInVenue(id);
		addStandardNavigation(list);

		for (Artist artist : list.getList()) {
			list.add(linkTo(methodOn(ArtistController.class).getArtist(artist.getArtistId())).withRel("artist"));
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/venues", method = RequestMethod.POST)
	public HttpEntity<Navigation> createVenue(
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "country", required = true, defaultValue = "") String country,
			@RequestParam(value = "city", required = true, defaultValue = "") String city,
			@RequestParam(value = "address", required = true, defaultValue = "") String address,
			@RequestParam(value = "size", required = true, defaultValue = "") String size) {

		Navigation navi = new Navigation();
		int id = venueService.createVenue(name, country, city, address, size);
		if (-1 < id) {
			navi.setContent("Venue created");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(VenueController.class).getVenue(id)).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.OK);
		} else {
			navi.setContent("Failed to create Venue");
			addStandardNavigation(navi);

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/venues/{id}", method = RequestMethod.PUT)
	public HttpEntity<Navigation> updateVenue(@PathVariable("id") int id,
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "country", required = true, defaultValue = "") String country,
			@RequestParam(value = "city", required = true, defaultValue = "") String city,
			@RequestParam(value = "address", required = true, defaultValue = "") String address,
			@RequestParam(value = "size", required = true, defaultValue = "") String size) {

		Navigation navi = new Navigation();
		boolean ok = venueService.updateVenue(id, name, country, city, address, size);
		if (ok) {
			navi.setContent("Venue updated");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(VenueController.class).getVenue(id)).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.OK);
		} else {
			navi.setContent("Failed to update Venue");
			addStandardNavigation(navi);

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/venues/search", method = RequestMethod.GET)
	public HttpEntity<GenericList<Venue>> searchForVenue(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "country", required = false, defaultValue = "") String country,
			@RequestParam(value = "city", required = false, defaultValue = "") String city) {

		GenericList<Venue> searchForVenue = venueService.searchVenue(name, country, city);
		addStandardNavigation(searchForVenue);
		searchForVenue.add(linkTo(methodOn(VenueController.class).searchForVenue(name, country, city)).withSelfRel());
		if (searchForVenue != null) {
			for (Venue venue : searchForVenue.getList()) {
				searchForVenue.add(linkTo(methodOn(VenueController.class).getVenue(venue.getVenueId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(searchForVenue, HttpStatus.OK);
	}

}
