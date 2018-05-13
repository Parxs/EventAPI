package at.ac.uibk.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.ac.uibk.model.Event;

public class EventRepository {

	public EventRepository(){
		
	}

	public Event getEvent(String id) {
		// TODO use real data and db
		return new Event(id , "farmers market", "a market with farmers", "2018-05-05 12:00:00", "austria", "innsbruck", "hauptstrasse 7");
	}
	
	public boolean createEvent(String eventId,String title, String description, String startTime, String country, String city,String address) {
		Event event = new Event(eventId, title, description, startTime, country, city, address);
		// save event somewhere
		return true;
	}
}
