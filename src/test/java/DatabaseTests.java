import org.Application.Administrator.Admin;
import org.Application.Clients.Parent;
import org.Application.Clients.Student;
import org.Application.Organization.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.List;


public class DatabaseTests {

    private Admin director;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }


    @BeforeEach
    public void setUp() {
        director = new Admin();
    }

    @Test
    public void testSearchStudentInDatabase() throws SQLException {
        String firstName = "Elena";
        String lastName = "Ilie";
        int age = 4;
        int groupId = Group.BUMBLEBEES.getId();
        int parentId1 = 31;
        int parentId2 = 32;


        Student foundStudent = director.searchStudentInDatabase(63);


        if (foundStudent != null){
        Assertions.assertEquals(63, foundStudent.getId());
        Assertions.assertEquals(firstName, foundStudent.getFirstName());
        Assertions.assertEquals(lastName, foundStudent.getLastName());
        Assertions.assertEquals(age, foundStudent.getAge());
        Assertions.assertEquals(Group.BUMBLEBEES, foundStudent.getGroup());
        Assertions.assertEquals(31, parentId1);
        Assertions.assertEquals(32, parentId2);}

    }

    private int insertTestStudentIntoDatabase(String firstName, String lastName, int age, int groupId, int parentId1, int parentId2) throws SQLException {
        int generatedStudentID = -1;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db",
                "root", "root")) {
            String insertSql = "INSERT INTO students (first_name, last_name, age, group_id, parent_id_1, parent_id_2) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, groupId);
            preparedStatement.setInt(5, parentId1);
            preparedStatement.setInt(6, parentId2);

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedStudentID = generatedKeys.getInt(1);
            }
        }
        return generatedStudentID;
    }

    private void removeStudentFromDatabase(int studentID) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db",
                "root", "root")) {
            String deleteSql = "DELETE FROM students WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setInt(1, studentID);

            preparedStatement.executeUpdate();
        }
    }


@Test
    public void testGetAllExistingParents() {
        Admin admin = new Admin();
        List<Parent> parents;

        parents = admin.getAllExistingParents();

        Assertions.assertEquals((7), parents.size());
        Parent firstParent = parents.get(0);
        Assertions.assertEquals("Andra", firstParent.getFirstName());
        Assertions.assertEquals("Ilie", firstParent.getLastName());
        Assertions.assertEquals("2890514559340", firstParent.getPersonalIdentityCode());
        Assertions.assertEquals("0744678213", firstParent.getPhoneNumber());

        Parent secondParent = parents.get(1);
        Assertions.assertEquals("Victor", secondParent.getFirstName());
        Assertions.assertEquals("Ilie", secondParent.getLastName());
        Assertions.assertEquals("1860219440056", secondParent.getPersonalIdentityCode());
        Assertions.assertEquals("0755643433", secondParent.getPhoneNumber());
    }
}
