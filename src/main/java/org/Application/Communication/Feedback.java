package org.Application.Communication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Feedback {

    private UUID id;
    private String content;
    private LocalDateTime submissionTime;

    public Feedback(String content) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.submissionTime = LocalDateTime.now();
    }

    public String getFormattedSubmissionTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy");
        return submissionTime.format(formatter);
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }

    @Override
    public String toString() {
        return "Feedback: " + content +
                ", submission time=" + submissionTime + "\n";
    }
}
