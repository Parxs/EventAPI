package at.ac.uibk.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchemaResult {

	private String type;
	private String name;
	
	@JsonCreator
	public SchemaResult() {
		
	}
}
