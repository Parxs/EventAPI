package at.ac.uibk.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event extends ResourceSupport {

	private int eventId;
	private String title;
	private String description;
	private String startTime;
	private int venueId;
	private String country;
	private String city;
	private String address;
	private int artistId;
	private String artistName;

	@JsonCreator
	public Event() {
		this.eventId = -1;
		this.title = "";
		this.description = "";
		this.startTime = "";
		this.venueId = -1;
		this.country = "";
		this.city = "";
		this.address = "";
		this.artistId = -1;
		this.artistName = "";
	}

	@JsonCreator
	public Event(@JsonProperty("eventId") int eventId, @JsonProperty("title") String title,
			@JsonProperty("description") String description, @JsonProperty("startTime") String startTime,
			@JsonProperty("venueId") int venueId, @JsonProperty("country") String country,
			@JsonProperty("city") String city, @JsonProperty("address") String address,
			@JsonProperty("artistId") int artistId, @JsonProperty("artistName") String artistName) {
		this.eventId = eventId;
		this.title = title;
		this.description = description;
		this.startTime = startTime;
		this.venueId = venueId;
		this.country = country;
		this.city = city;
		this.address = address;
		this.artistId = artistId;
		this.artistName = artistName;
	}

	public Event(int eventId, String title, String description, String startTime, int venueId, int artistId) {
		this();
		this.eventId = eventId;
		this.title = title;
		this.description = description;
		this.startTime = startTime;
		this.venueId = venueId;
		this.artistId = artistId;
	}

	public void fillWith(Venue venue) {
		this.country = venue.getCountry();
		this.city = venue.getCity();
		this.address = venue.getAddress();
	}

	public void fillWith(Artist artist) {
		this.artistName = artist.getName();
	}

}
