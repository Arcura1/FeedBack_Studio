package org.example.feedbackstudio;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class testDto {
    private Long id;
    private String name;
}
