package at.ac.uibk.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.uibk.model.Event;

@RestController
public class EventController {
	
	private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping("/events")
    public HttpEntity<Event> getEvents(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Event event = new Event(String.format(TEMPLATE, name));
        event.add(linkTo(methodOn(EventController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping("/greeting")
    public Event greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Event event = new Event(String.format(TEMPLATE, name));
        event.add(linkTo(methodOn(EventController.class).greeting(name)).withSelfRel());

        return event;
    }
}
