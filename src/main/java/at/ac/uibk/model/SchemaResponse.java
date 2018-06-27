package at.ac.uibk.model;

import java.util.Collections;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchemaResponse extends ResourceSupport{

	@JsonProperty("@content")
	private String content;
	@JsonProperty("@type")
	private String type;
	@JsonIgnore
	private ResourceSupport object;
	@JsonIgnore
	private String resultType;
	private String actionStatus;
	@JsonCreator
	public SchemaResponse(String type) {
		this.type = type;
		content = "http://schema.org";
		actionStatus = "CompletedActionStatus";
	}
	
	public void SetResult(String type, ResourceSupport object) {
		this.resultType = type;
		this.object = object;
	}
	
	@JsonAnyGetter
	public Map<String,Object> any(){
        return Collections.singletonMap(resultType, object);
	}
}
