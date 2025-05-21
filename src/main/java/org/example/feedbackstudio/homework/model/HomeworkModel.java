package org.example.feedbackstudio.homework.model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.user.entity.User;


@Getter
@Setter
public class HomeworkModel {

    private Long id;
    private String title;
    private String description;
    private User teacher;

}
