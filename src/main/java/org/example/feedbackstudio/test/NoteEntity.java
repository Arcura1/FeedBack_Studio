package org.example.feedbackstudio.test;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Builder
@Document(collection = "users")
public class NoteEntity {
    @Id
    private String id;
    private int Xcoordinate;
    private int Ycoordinate;

}
