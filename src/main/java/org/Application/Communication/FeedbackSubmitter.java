package org.Application.Communication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FeedbackSubmitter {
    public void submitFeedback(String feedbackContent){
        UUID feedbackId = UUID.randomUUID();

        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = timestamp.format(formatter);

        String sql = "INSERT INTO feedback (id, content, submission_time) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, feedbackId.toString());
            preparedStatement.setString(2, feedbackContent);
            preparedStatement.setString(3, formattedTimestamp);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Feedback submitted successfully.");
            } else {
                System.out.println("Failed to submit feedback.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to submit feedback.");
        }
    }
}
