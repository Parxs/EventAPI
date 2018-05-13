package at.ac.uibk.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.uibk.model.Event;
import at.ac.uibk.model.Navigation;
import at.ac.uibk.service.EventService;

@RestController
public class EventController {
	
	private static final String TEMPLATE = "Hello, %s!";
	private EventService eventService = new EventService();
	
    @RequestMapping("/events/{id}")
    public HttpEntity<Event> getEvents(@PathVariable("id") String id) {

		Event event = eventService.getEvent(id);
        event.add(linkTo(methodOn(EventController.class).getEvents(id)).withSelfRel());
        event.add(linkTo(methodOn(EventController.class).createEvent(null, null, null, null, null, null, null)).withSelfRel());

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping("/events")
    public HttpEntity<Navigation> getEvents() {

		Navigation navi= new Navigation("Operations for Events");
		navi.add(linkTo(methodOn(EventController.class).getEvents("1")).withSelfRel());
    	navi.add(linkTo(methodOn(EventController.class).createEvent("eventId", "title", "description", "startTime", "country", "city", "address")).withSelfRel());

        return new ResponseEntity<>(navi, HttpStatus.OK);
    }
    
    @RequestMapping("/events/new")
    public HttpEntity<Navigation> createEvent(
            @RequestParam(value = "id", required = true, defaultValue = "") String eventId,
            @RequestParam(value = "title", required = true, defaultValue = "") String title,
            @RequestParam(value = "description", required = true, defaultValue = "") String description,
            @RequestParam(value = "startTime", required = true, defaultValue = "") String startTime,
            @RequestParam(value = "country", required = true, defaultValue = "") String country,
            @RequestParam(value = "city", required = true, defaultValue = "") String city,
            @RequestParam(value = "address", required = true, defaultValue = "") String address) {
    	
		Navigation navi = new Navigation();
    	if(eventService.createEvent(eventId, title, description, startTime, country, city, address)) {
    		navi.setContent("Event created");
            navi.add(linkTo(methodOn(EventController.class).getEvents()).withSelfRel());
            navi.add(linkTo(methodOn(EventController.class).getEvents("1")).withSelfRel());
        	navi.add(linkTo(methodOn(EventController.class).createEvent("eventId", "title", "description", "startTime", "country", "city", "address")).withSelfRel());

            return new ResponseEntity<>(navi, HttpStatus.OK);
    	}else {
    		navi.setContent("Failed to create Event");
            navi.add(linkTo(methodOn(EventController.class).getEvents()).withSelfRel());
            navi.add(linkTo(methodOn(EventController.class).getEvents("1")).withSelfRel());
        	navi.add(linkTo(methodOn(EventController.class).createEvent("eventId", "title", "description", "startTime", "country", "city", "address")).withSelfRel());

            return new ResponseEntity<>(navi, HttpStatus.EXPECTATION_FAILED);
    	}
    }

}
