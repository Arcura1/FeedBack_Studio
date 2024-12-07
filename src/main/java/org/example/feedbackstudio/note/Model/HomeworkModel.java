package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;


@Getter
@Setter
public class HomeworkModel {

    private String id;
    private String title;
    private String description;
    private User teacher;

}
