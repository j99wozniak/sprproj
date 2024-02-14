package jwoz.sprproj.record;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Setter
@Getter
public class Person {
    @Id
    private Long id;
    private String name;
    private int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public Person(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
