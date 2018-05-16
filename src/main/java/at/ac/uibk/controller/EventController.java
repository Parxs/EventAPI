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
import at.ac.uibk.model.Event;
import at.ac.uibk.model.Navigation;
import at.ac.uibk.model.Venue;
import at.ac.uibk.service.ArtistService;
import at.ac.uibk.service.EventService;
import at.ac.uibk.service.VenueService;

@RestController
public class EventController {
	@Autowired
	private EventService eventService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private VenueService revenueService;

	@RequestMapping("/init")
	public HttpEntity<Boolean> init() {
		// artistService.createArtist(0, "Informatixs", 30, "House, HipHop");
		// artistService.createArtist(1, "Acapella", 67, "Rock");
		// revenueService.createRevenue(0, "StadtSaal", "Austria", "Innsbruck", "AbcdWeg
		// 12", "40 Personen");
		// revenueService.createRevenue(1, "Baumhaus", "Deutschland", "Hamburg",
		// "StrassenWeg 89", "4000 Personen");
		// eventService.createEvent(0, "FarmersMarket", "Fresh beets?!", "18:00", 0, 0);
		// eventService.createEvent(0, "Klangkonzert", "Viva la Voice", "18:00", 1, 1);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	private void addStandardNavigation(Navigation navi) {
		navi.add(linkTo(methodOn(EventController.class).getEvents()).withSelfRel());
		navi.add(linkTo(methodOn(EventController.class).createEvent("title", "description", "startTime", 0, 0))
				.withSelfRel());
	}

	@RequestMapping("/events/{id}")
	public HttpEntity<Event> getEvents(@PathVariable("id") int id) {

		Event event = eventService.getEvent(id);
		event.add(linkTo(methodOn(EventController.class).getEvents(id)).withSelfRel());
		event.add(linkTo(methodOn(EventController.class).createEvent("title", "description", "startTime", 0, 0))
				.withSelfRel());

		return new ResponseEntity<>(event, HttpStatus.OK);
	}

	@RequestMapping("/events/{id}/artist")
	public HttpEntity<Artist> getArtistForEvents(@PathVariable("id") int id) {

		Artist a = eventService.getArtistsOfEvent(id);
		a.add(linkTo(methodOn(EventController.class).getEvents(id)).withSelfRel());
		a.add(linkTo(methodOn(EventController.class).createEvent("title", "description", "startTime", 0, 0))
				.withSelfRel());
		a.add(linkTo(methodOn(EventController.class).getArtistForEvents(id)).withSelfRel());

		return new ResponseEntity<>(a, HttpStatus.OK);
	}
	
	@RequestMapping("/events/{id}/venue")
	public HttpEntity<Venue> getVenueForEvents(@PathVariable("id") int id) {

		Venue r = eventService.getVenueOfEvent(id);
		r.add(linkTo(methodOn(EventController.class).getEvents(id)).withSelfRel());
		r.add(linkTo(methodOn(EventController.class).createEvent("title", "description", "startTime", 0, 0))
				.withSelfRel());
		r.add(linkTo(methodOn(EventController.class).getVenueForEvents(id)).withSelfRel());

		return new ResponseEntity<>(r, HttpStatus.OK);
	}
	
	@RequestMapping("/events")
	public HttpEntity<Navigation> getEvents() {

		Navigation navi = new Navigation("Operations for Events");
		List<Event> events = eventService.getEvents();
		addStandardNavigation(navi);
		if (events != null) {
			for (Event event : events) {
				navi.add(linkTo(methodOn(EventController.class).getEvents(event.getEventId())).withSelfRel());
			}
		}

		return new ResponseEntity<>(navi, HttpStatus.OK);
	}

	@RequestMapping("/events/new")
	public HttpEntity<Navigation> createEvent(
			@RequestParam(value = "title", required = true, defaultValue = "") String title,
			@RequestParam(value = "description", required = true, defaultValue = "") String description,
			@RequestParam(value = "startTime", required = true, defaultValue = "") String startTime,
			@RequestParam(value = "revenueId", required = true, defaultValue = "") int revenueId,
			@RequestParam(value = "artistId", required = true, defaultValue = "") int artistId) {

		Navigation navi = new Navigation();
		int eventId = eventService.createEvent(title, description, startTime, revenueId, artistId);
		if (eventId > -1) {
			navi.setContent("Event created");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(EventController.class).getEvents(eventId)).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.OK);
		} else {
			navi.setContent("Failed to create Event");
			addStandardNavigation(navi);

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
