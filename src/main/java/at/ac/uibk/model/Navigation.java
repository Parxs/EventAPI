package at.ac.uibk.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Navigation extends ResourceSupport{

	private String content;
	
	@JsonCreator
	public Navigation() {
		this.content = "";
	}
	@JsonCreator
	public Navigation(String content) {
		this.content = content;
	}
}
