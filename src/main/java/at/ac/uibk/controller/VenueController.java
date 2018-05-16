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

import at.ac.uibk.model.Event;
import at.ac.uibk.model.GenericList;
import at.ac.uibk.model.Navigation;
import at.ac.uibk.model.Venue;
import at.ac.uibk.service.VenueService;

@RestController
public class VenueController {

	@Autowired
	private VenueService venueService;

	private void addStandardNavigation(Navigation navi) {
		navi.add(linkTo(methodOn(VenueController.class).createVenue("name", "country", "city", "address", "size"))
				.withSelfRel());
	}

	@RequestMapping("/venues")
	public HttpEntity<Navigation> getVenues() {

		Navigation navi = new Navigation("Operations for Venues");

		addStandardNavigation(navi);

		List<Venue> venues = venueService.getRevenues();
		if (venues != null) {
			for (Venue venue : venues) {
				navi.add(linkTo(methodOn(VenueController.class).getVenue(venue.getVenueId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(navi, HttpStatus.OK);
	}

	@RequestMapping("/venues/{id}")
	public HttpEntity<Venue> getVenue(@PathVariable("id") int id) {

		Venue venue = venueService.getVenue(id);
		venue.add(linkTo(methodOn(VenueController.class).getVenues()).withSelfRel());
		venue.add(
				linkTo(methodOn(VenueController.class).createVenue("name", "country", "city", "address", "size"))
						.withSelfRel());

		return new ResponseEntity<>(venue, HttpStatus.OK);
	}

	@RequestMapping("/venues/new")
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
			navi.add(linkTo(methodOn(VenueController.class).getVenues()).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.OK);
		} else {
			navi.setContent("Failed to create Venue");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(VenueController.class).getVenues()).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@RequestMapping(value = "/venues/search")
	public HttpEntity<GenericList<Venue>> searchForVenue(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "country", required = false, defaultValue = "") String country,
			@RequestParam(value = "city", required = false, defaultValue = "") String city) {

		GenericList<Venue> searchForVenue = venueService.searchVenue(name, country, city);
		searchForVenue.add(linkTo(methodOn(VenueController.class).getVenues()).withSelfRel());
		searchForVenue.add(linkTo(methodOn(VenueController.class).searchForVenue(name, country, city)).withSelfRel());
		if (searchForVenue != null) {
			for (Venue venue: searchForVenue.getList()) {
				searchForVenue
						.add(linkTo(methodOn(VenueController.class).getVenue(venue.getVenueId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(searchForVenue, HttpStatus.OK);
	}

}
