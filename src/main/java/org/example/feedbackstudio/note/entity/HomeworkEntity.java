package org.example.feedbackstudio.note.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "HomeWork")
public class HomeworkEntity {
    @Id
    private String id;
    private String title;
    private String description;

}
