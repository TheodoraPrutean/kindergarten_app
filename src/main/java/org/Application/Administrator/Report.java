package org.Application.Administrator;
import org.Application.Clients.Student;
import org.Application.Organization.Group;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class Report implements ReportGenerator {
    @Override
    public void generateFeedbackReport(String filePath) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root");
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            String sql = "SELECT * FROM feedback";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String feedbackId = resultSet.getString("id");
                String submissionTime = resultSet.getString("submission_time");
                String content = resultSet.getString("content");

                writer.write("Feedback ID: " + feedbackId);
                writer.newLine();
                writer.write("Submission Time: " + submissionTime);
                writer.newLine();
                writer.write("Content:");
                writer.newLine();
                writer.write(content);
                writer.newLine();
                writer.write("-----------------------------------------");
                writer.newLine();
            }

            System.out.println("Feedback report generated successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("Failed to generate feedback report.");
        }
    }

    public void generateStudentReport(String filePath) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root");
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            String sql = "SELECT * FROM students";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                int groupId = resultSet.getInt("group_id");
                int parent1Id = resultSet.getInt("parent_id_1");
                int parent2Id = resultSet.getInt("parent_id_2");

                String studentInfo = "Student ID: " + studentId +
                        "\nFirst Name: " + firstName +
                        "\nLast Name: " + lastName +
                        "\nAge: " + age +
                        "\nGroup ID: " + groupId +
                        "\nParent 1 ID: " + parent1Id +
                        "\nParent 2 ID: " + parent2Id;

                writer.write(studentInfo);

                writer.newLine();
                writer.write("-----------------------------------------");
                writer.newLine();
            }

            System.out.println("Student report generated successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("Failed to generate student report.");
        }
    }
    public void generateParentReport(String filePath) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root");
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            String sql = "SELECT * FROM parents";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int parentId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String personalIdentityCode = resultSet.getString("personal_identity_code");
                String phoneNumber = resultSet.getString("phone_number");

                String parentInfo = "Parent ID: " + parentId +
                        "\nFirst Name: " + firstName +
                        "\nLast Name: " + lastName +
                        "\nPersonal Identity Code: " + personalIdentityCode +
                        "\nPhone Number: " + phoneNumber;

                writer.write(parentInfo);
                writer.newLine();
                writer.write("-----------------------------------------");
                writer.newLine();
            }

            System.out.println("Parent report generated successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.out.println("Failed to generate parent report.");
        }
    }

}