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

		Navigation navi = new Navigation("Operations for Revenues");

		addStandardNavigation(navi);

		List<Venue> revenues = venueService.getRevenues();
		if (revenues != null) {
			for (Venue revenue : revenues) {
				navi.add(linkTo(methodOn(VenueController.class).getVenue(revenue.getVenueId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(navi, HttpStatus.OK);
	}

	@RequestMapping("/venue/{id}")
	public HttpEntity<Venue> getVenue(@PathVariable("id") int id) {

		Venue revenue = venueService.getVenue(id);
		revenue.add(linkTo(methodOn(VenueController.class).getVenues()).withSelfRel());
		revenue.add(
				linkTo(methodOn(VenueController.class).createVenue("name", "country", "city", "address", "size"))
						.withSelfRel());

		return new ResponseEntity<>(revenue, HttpStatus.OK);
	}

	@RequestMapping("/venue/new")
	public HttpEntity<Navigation> createVenue(
			@RequestParam(value = "name", required = true, defaultValue = "") String name,
			@RequestParam(value = "country", required = true, defaultValue = "") String country,
			@RequestParam(value = "city", required = true, defaultValue = "") String city,
			@RequestParam(value = "address", required = true, defaultValue = "") String address,
			@RequestParam(value = "size", required = true, defaultValue = "") String size) {

		Navigation navi = new Navigation();
		int id = venueService.createVenue(name, country, city, address, size);
		if (-1 < id) {
			navi.setContent("Revenue created");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(VenueController.class).getVenue(id)).withSelfRel());
			navi.add(linkTo(methodOn(VenueController.class).getVenues()).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.OK);
		} else {
			navi.setContent("Failed to create Revenue");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(VenueController.class).getVenues()).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
