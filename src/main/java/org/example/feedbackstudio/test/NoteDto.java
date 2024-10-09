package org.example.feedbackstudio.test;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@Getter
@Setter
@Builder
public class NoteDto {
    @Id
    private String id;
    private int Xcoordinate;
    private int Ycoordinate;

}
