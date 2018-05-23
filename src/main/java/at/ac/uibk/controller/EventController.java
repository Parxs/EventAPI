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
import at.ac.uibk.model.Venue;
import at.ac.uibk.service.EventService;

@RestController
public class EventController {
	@Autowired
	private EventService eventService;

	private void addStandardNavigation(ResourceSupport navi) {
		navi.add(linkTo(methodOn(EventController.class).getEvents()).withRel("events"));
		navi.add(linkTo(methodOn(EventController.class).createEvent("title", "description", "startTime", 0, 0))
				.withRel("create"));
	}

	@RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
	public HttpEntity<ResourceSupport> getEvents(@PathVariable("id") int id) {

		Event event = eventService.getEvent(id);
		if (event == null) {
			Navigation eventError = new Navigation("Event not found");
			addStandardNavigation(eventError);
			return new ResponseEntity<>(eventError, HttpStatus.NOT_FOUND);

		}

		addStandardNavigation(event);
		event.add(linkTo(methodOn(EventController.class).getEvents(id)).withSelfRel());
		event.add(linkTo(methodOn(VenueController.class).getVenue(event.getVenueId())).withRel("venues"));
		event.add(linkTo(methodOn(ArtistController.class).getArtist(event.getArtistId())).withRel("artists"));
		return new ResponseEntity<>(event, HttpStatus.OK);
	}

	@RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE)
	public HttpEntity<Navigation> deleteEvents(@PathVariable("id") int id) {
		boolean ok = eventService.deleteEvent(id);
		if (!ok) {
			Navigation navi = new Navigation("Event " + id + " not found");
			addStandardNavigation(navi);
			return new ResponseEntity<>(navi, HttpStatus.NOT_FOUND);

		} else {
			Navigation navi = new Navigation("Event " + id + " deleted");
			addStandardNavigation(navi);

			return new ResponseEntity<>(navi, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/events", method = RequestMethod.POST)
	public HttpEntity<Navigation> createEvent(
			@RequestParam(value = "title", required = true, defaultValue = "") String title,
			@RequestParam(value = "description", required = true, defaultValue = "") String description,
			@RequestParam(value = "startTime", required = true, defaultValue = "") String startTime,
			@RequestParam(value = "venueId", required = true, defaultValue = "") int venueId,
			@RequestParam(value = "artistId", required = true, defaultValue = "") int artistId) {

		Navigation navi = new Navigation();
		int eventId = eventService.createEvent(title, description, startTime, venueId, artistId);
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

	@RequestMapping(value = "/events/{id}", method = RequestMethod.PUT)
	public HttpEntity<Navigation> updateEvent(@PathVariable("id") int id,
			@RequestParam(value = "title", required = true, defaultValue = "") String title,
			@RequestParam(value = "description", required = true, defaultValue = "") String description,
			@RequestParam(value = "startTime", required = true, defaultValue = "") String startTime,
			@RequestParam(value = "venueId", required = true, defaultValue = "") int venueId,
			@RequestParam(value = "artistId", required = true, defaultValue = "") int artistId) {

		Navigation navi = new Navigation();
		boolean ok = eventService.updateEvent(id, title, description, startTime, venueId, artistId);
		if (ok) {
			navi.setContent("Event updated");
			addStandardNavigation(navi);
			navi.add(linkTo(methodOn(EventController.class).getEvents(id)).withSelfRel());

			return new ResponseEntity<>(navi, HttpStatus.OK);
		} else {
			navi.setContent("Failed to updated Event");
			addStandardNavigation(navi);

			return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/events/{id}/artists", method = RequestMethod.GET)
	public HttpEntity<Artist> getArtistForEvents(@PathVariable("id") int id) {

		Artist a = eventService.getArtistsOfEvent(id);
		addStandardNavigation(a);
		a.add(linkTo(methodOn(EventController.class).getEvents(id)).withRel("events"));
		a.add(linkTo(methodOn(EventController.class).getArtistForEvents(id)).withSelfRel());
		a.add(linkTo(methodOn(ArtistController.class).getArtist(a.getArtistId())).withRel("artists"));

		return new ResponseEntity<>(a, HttpStatus.OK);
	}

	@RequestMapping(value = "/events/{id}/venues", method = RequestMethod.GET)
	public HttpEntity<Venue> getVenueForEvents(@PathVariable("id") int id) {

		Venue r = eventService.getVenueOfEvent(id);
		r.add(linkTo(methodOn(EventController.class).getEvents(id)).withRel("events"));
		addStandardNavigation(r);
		r.add(linkTo(methodOn(EventController.class).getVenueForEvents(id)).withSelfRel());
		r.add(linkTo(methodOn(VenueController.class).getVenue(r.getVenueId())).withRel("venues"));

		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public HttpEntity<Navigation> getEvents() {

		Navigation navi = new Navigation("Operations for Events");
		List<Event> events = eventService.getEvents();
		navi.add(linkTo(methodOn(EventController.class).getEvents()).withSelfRel());
		navi.add(linkTo(methodOn(EventController.class).createEvent("title", "description", "startTime", 0, 0))
				.withRel("create"));
		if (events != null) {
			for (Event event : events) {
				navi.add(linkTo(methodOn(EventController.class).getEvents(event.getEventId())).withRel("event"));
			}
		}

		return new ResponseEntity<>(navi, HttpStatus.OK);
	}

	@RequestMapping(value = "/events/search", method = RequestMethod.GET)
	public HttpEntity<GenericList<Event>> searchForEvents(
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "artistName", required = false, defaultValue = "") String artistName,
			@RequestParam(value = "venuName", required = false, defaultValue = "") String venueName) {

		GenericList<Event> searchForEvents = eventService.searchEvent(name, artistName, venueName);
		addStandardNavigation(searchForEvents);
		searchForEvents.add(
				linkTo(methodOn(EventController.class).searchForEvents(name, artistName, venueName)).withSelfRel());

		if (searchForEvents != null) {
			for (Event event : searchForEvents.getList()) {
				searchForEvents
						.add(linkTo(methodOn(EventController.class).getEvents(event.getEventId())).withSelfRel());
			}
		}
		return new ResponseEntity<>(searchForEvents, HttpStatus.OK);
	}
}
