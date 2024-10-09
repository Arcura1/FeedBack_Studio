package org.example.feedbackstudio.test;



public interface NoteService {
    public String view();
    public String viewAll();
    public String deleteAll();
    public String add(NoteDto note);
}
