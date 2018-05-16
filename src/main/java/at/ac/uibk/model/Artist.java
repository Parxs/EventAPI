package at.ac.uibk.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Artist extends ResourceSupport {

	private final int artistId;
	private final String name;
	private final int age;
	private final String genre;

	@JsonCreator
	public Artist() {
		this.artistId = 0;
		this.name = "";
		this.age = 0;
		this.genre = "";
	}

	@JsonCreator
	public Artist(@JsonProperty("artistId") int artistId, @JsonProperty("name") String name,
			@JsonProperty("age") int age, @JsonProperty("genre") String genre) {
		this.artistId = artistId;
		this.name = name;
		this.age = age;
		this.genre = genre;
	}

}
