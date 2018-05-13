package at.ac.uibk.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event extends ResourceSupport{

	private final String eventId;
	private final String title;
	private final String description;
	private final String startTime;
	private final String country;
	private final String city;
	private final String address;

	@JsonCreator
	public Event() {
    	this.eventId = "";
    	this.title = "";
    	this.description = "";
    	this.startTime = "";
    	this.country = "";
    	this.city = "";
    	this.address = "";
	}
	
    @JsonCreator
    public Event(@JsonProperty("eventId") String eventId, @JsonProperty("title") String title, 
    		@JsonProperty("description") String description, @JsonProperty("startTime") String startTime, @JsonProperty("country") String country,
    		@JsonProperty("city") String city, @JsonProperty("address") String address) {
    	this.eventId = eventId;
    	this.title = title;
    	this.description = description;
    	this.startTime = startTime;
    	this.country = country;
    	this.city = city;
    	this.address = address;
    }
    
}
