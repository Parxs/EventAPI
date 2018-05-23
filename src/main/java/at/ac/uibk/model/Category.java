package at.ac.uibk.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category extends ResourceSupport{
	
	private int categoryId;
	private String name;
	private String identifier;
	private String description;
	
	@JsonCreator
	public Category() {
		this.categoryId = -1;
		this.name = "";
		this.identifier = "";
		this.description = "";
	}
	
	@JsonCreator
	public Category(@JsonProperty("categoryId") int categoryId, @JsonProperty("name") String name, @JsonProperty("identifier") String identifier, @JsonProperty("description") String description) {
		this.categoryId = categoryId;
		this.name = name;
		this.identifier = identifier;
		this.description = description;
	}

}
