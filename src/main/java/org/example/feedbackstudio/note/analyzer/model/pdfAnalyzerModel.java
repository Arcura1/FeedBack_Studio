package org.example.feedbackstudio.note.analyzer.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class pdfAnalyzerModel {

    private Long userId;
    private Long pdfInfoEntityId;
    private String encoded;
    private String title;
    private List<String> examples;
    private String conf;
}
