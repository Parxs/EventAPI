package at.ac.uibk.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Revenue extends ResourceSupport {

	private final int revenueId;
	private final String name;
	private final String country;
	private final String city;
	private final String address;
	private final String size;

	@JsonCreator
	public Revenue() {
		this.revenueId = 0;
		this.name = "";
		this.country = "";
		this.city = "";
		this.address = "";
		this.size = "";
	}

	@JsonCreator
	public Revenue(@JsonProperty("revenueId") int revenueId, @JsonProperty("name") String name,
			@JsonProperty("country") String country, @JsonProperty("city") String city,
			@JsonProperty("address") String address, @JsonProperty("size") String size) {
		this.revenueId = revenueId;
		this.name = name;
		this.country = country;
		this.city = city;
		this.address = address;
		this.size = size;
	}

}
