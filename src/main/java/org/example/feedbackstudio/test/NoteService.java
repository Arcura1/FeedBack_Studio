package org.example.feedbackstudio.test;


import java.util.List;

public interface NoteService {
    public String view();
    public List<String> viewAll();
    public String deleteAll();
    public String add(NoteDto note);
}
