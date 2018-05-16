package at.ac.uibk.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericList<T> extends ResourceSupport {

	List<T> list;

	@JsonCreator
	public GenericList() {
		list = new ArrayList<>();
	}

	@JsonCreator
	public GenericList(@JsonProperty("list") List<T> list) {
		this.list = list;
	}

}
