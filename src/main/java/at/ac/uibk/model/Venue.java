package at.ac.uibk.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Venue extends ResourceSupport {

	private final int venueId;
	private final String name;
	private final String country;
	private final String city;
	private final String address;
	private final String size;

	@JsonCreator
	public Venue() {
		this.venueId = 0;
		this.name = "";
		this.country = "";
		this.city = "";
		this.address = "";
		this.size = "";
	}

	@JsonCreator
	public Venue(@JsonProperty("revenueId") int venueId, @JsonProperty("name") String name,
			@JsonProperty("country") String country, @JsonProperty("city") String city,
			@JsonProperty("address") String address, @JsonProperty("size") String size) {
		this.venueId = venueId;
		this.name = name;
		this.country = country;
		this.city = city;
		this.address = address;
		this.size = size;
	}

}
