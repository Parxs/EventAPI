package at.ac.uibk.model;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event extends ResourceSupport{

	private final String content;

    @JsonCreator
    public Event(@JsonProperty("content") String content) {
        this.content = content;
    }
    
    
}
