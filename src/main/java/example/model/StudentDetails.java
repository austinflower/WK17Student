package example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StudentDetails {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("programme")
    private String programme;
    @JsonProperty("courses")
    private List<String> courses;

}

