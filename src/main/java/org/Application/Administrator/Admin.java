package org.Application.Administrator;
import org.Application.Clients.Parent;
import org.Application.Clients.Student;
import org.Application.Communication.Communicator;
import org.Application.Communication.Message;
import org.Application.Communication.MessageSender;
import org.Application.Organization.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.Application.Communication.MessageStorage.MESSAGES;
import static org.Application.Organization.RoleType.*;

public class Admin extends LocalManager implements ReportGenerator, Communicator {
    private final RoleType role = ADMIN;
    private final ReportGenerator reportGenerator;
    private int staffId;
    private Group correspondingGroup;
    public Admin() {
        reportGenerator = new Report();
    }
    public Group getCorrespondingGroup() {
        return correspondingGroup;
    }
    public void setCorrespondingGroup(Group correspondingGroup) {
        this.correspondingGroup = correspondingGroup;
    }
    public RoleType getRole() {
        return role;
    }

//    TODO: adding data to Database

    @Override
    public String getName() {
        return "Admin";
    }

    public void addStudentToDatabase(Student student) {
        List<Student> existingStudents = getAllExistingStudents();

        for (Student existingStudent : existingStudents) {
            if (existingStudent.getFirstName().equals(student.getFirstName()) &&
                    existingStudent.getLastName().equals(student.getLastName()) &&
                    existingStudent.getAge() == student.getAge()) {
                return;
            }
        }

        String sqlUpdated;
        if (student.getParent2() != null) {
            sqlUpdated = "INSERT INTO students (first_name, last_name, age, group_id, parent_id_1, parent_id_2) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sqlUpdated = "INSERT INTO students (first_name, last_name, age, group_id, parent_id_1) VALUES (?, ?, ?, ?, ?)";
        }


        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {

            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdated, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setInt(4, student.getGroup().getId());
            preparedStatement.setInt(5, student.getParent1().getId());

            if (student.getParent2() != null) {
                preparedStatement.setInt(6, student.getParent2().getId());
            }

            preparedStatement.executeUpdate();
            System.out.println("Student successfully added to the database.");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int studentID = generatedKeys.getInt(1);
                student.setId(studentID);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add student to the database.");
        }
    }

    public void addStaffToDatabase(Staff staff) {
        List<Staff> existingStaffList = loadStaffFromDatabase();

        for (Staff existingStaff : existingStaffList) {
            if (existingStaff.getFirstName().equals(staff.getFirstName()) &&
                    existingStaff.getLastName().equals(staff.getLastName()) &&
                    existingStaff.getRole() == staff.getRole()) {
                return;
            }
        }

        String sql = "INSERT INTO staff (first_name, last_name, role, corresponding_group, description, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, staff.getFirstName());
            preparedStatement.setString(2, staff.getLastName());
            preparedStatement.setString(3, staff.getRole().name());

            if (staff.getGroupId() != null) {
                preparedStatement.setInt(4, staff.getGroupId());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            preparedStatement.setString(5, staff.getDescription());
            preparedStatement.setString(6, staff.getPhoneNumber());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int staffId = generatedKeys.getInt(1);
                staff.setId(staffId);
            }

            System.out.println("Staff member successfully added to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addParentToDatabase(List<Parent> parents) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "INSERT INTO parents (first_name, last_name, personal_identity_code, phone_number) VALUES (?, ?, ?, ?)";
            String checkSql = "SELECT * FROM parents WHERE first_name = ? AND last_name = ? AND personal_identity_code = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);

            for (Parent parent : parents) {
                checkStatement.setString(1, parent.getFirstName());
                checkStatement.setString(2, parent.getLastName());
                checkStatement.setString(3, parent.getPersonalIdentityCode());

                ResultSet resultSet = checkStatement.executeQuery();
                if (!resultSet.next()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, parent.getFirstName());
                    preparedStatement.setString(2, parent.getLastName());
                    preparedStatement.setString(3, parent.getPersonalIdentityCode());
                    preparedStatement.setString(4, parent.getPhoneNumber());
                    preparedStatement.executeUpdate();
                    myParents.add(parent);
                } else {
                    System.out.println("Parent " + parent.getFirstName() + " " + parent.getLastName() + " already exists in the database");
                }

            }


            System.out.println("Successfully added parents to the database.");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addParentToDatabase(Parent parent) {
        List<Parent> existingParents = getAllExistingParents();

        for (Parent existingParent : existingParents) {
            if (existingParent.getPersonalIdentityCode().equals(parent.getPersonalIdentityCode())) {
                return;
            }
        }

        String sql = "INSERT INTO parents (first_name, last_name, personal_identity_code, phone_number) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, parent.getFirstName());
            preparedStatement.setString(2, parent.getLastName());
            preparedStatement.setString(3, parent.getPersonalIdentityCode());
            preparedStatement.setString(4, parent.getPhoneNumber());

            preparedStatement.executeUpdate();
            System.out.println("Parent successfully added to the database.");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int parentId = generatedKeys.getInt(1);
                parent.setId(parentId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add parent to the database.");
        }
    }

    public void addGroupToDatabase(Group group) {
        String groupName = group.getName();
        String checkSql = "SELECT * FROM `groups` WHERE name = ?";
        String insertSql = "INSERT INTO `groups` (name) VALUES (?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {

            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, groupName);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                int groupId = resultSet.getInt("id");
                group.setId(groupId);
                System.out.println("Group " + groupName + " already exists in the database.");
            } else {
                PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                insertStatement.setString(1, groupName);
                insertStatement.executeUpdate();

                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int groupId = generatedKeys.getInt(1);
                    group.setId(groupId);
                    System.out.println("Group " + groupName + " successfully added to the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    TODO: Remove data from database

    public void removeStudentFromDatabase(int studentID) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student with ID " + studentID + " removed from the database.");
            } else {
                System.out.println("No student found with ID " + studentID + ". No rows were affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStaffFromDatabase(int staffId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "DELETE FROM staff WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, staffId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Staff member with ID " + staffId + " removed from the database.");
            } else {
                System.out.println("No staff member found with ID " + staffId + ". No rows were affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeParentFromDatabase(int parentId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "DELETE FROM parents WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, parentId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Parent with ID " + parentId + " removed from the database.");
            } else {
                System.out.println("No parent found with ID " + parentId + ". No rows were affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to remove parent from the database.");
        }
    }

    public void removeGroupFromDatabase(String groupName) {
        String deleteSql = "DELETE FROM `groups` WHERE name = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setString(1, groupName);

            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Group " + groupName + " successfully removed from the database.");
            } else {
                System.out.println("No group found with the name: " + groupName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// TODO: Load data from Database


    public List<Teacher> loadTeacherFromDatabase() {
        myTeachers.clear();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "SELECT * FROM staff where role = 'teacher'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int staffId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String role = resultSet.getString("role");

                int groupId = resultSet.getInt("corresponding_group");
                Group correspondingGroup = (groupId != 0) ? Group.convertToGroupEnum(groupId) : null;


                String description = resultSet.getString("description");
                String phoneNumber = resultSet.getString("phone_number");

                Teacher teacher = new Teacher(firstName, lastName, RoleType.TEACHER, correspondingGroup, description, phoneNumber);
                teacher.setId(staffId);
                myTeachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myTeachers;
    }

    public List<Staff> loadStaffFromDatabase() {
        myStaff.clear();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "SELECT * FROM staff";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int staffId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String role = resultSet.getString("role");

                int groupId = resultSet.getInt("corresponding_group");
                Group correspondingGroup = (groupId != 0) ? Group.convertToGroupEnum(groupId) : null;


                String description = resultSet.getString("description");
                String phoneNumber = resultSet.getString("phone_number");

                Staff staff = new Staff(firstName, lastName, RoleType.valueOf(role), correspondingGroup, description, phoneNumber);
                staff.setId(staffId);
                myStaff.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myStaff;
    }

    public List<Student> loadStudentsFromDatabase() {
        myStudents.clear();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "SELECT * FROM students";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                int groupId = resultSet.getInt("group_id");
                Group correspondingGroup = (groupId != 0) ? Group.convertToGroupEnum(groupId) : null;
                int parent1Id = resultSet.getInt("parent_id_1");
                int parent2Id = resultSet.getInt("parent_id_2");
                Parent parent1 = getParentById(parent1Id);
                Parent parent2 = (parent2Id != 0) ? getParentById(parent2Id) : null;

                Student student = new Student(firstName, lastName, age, correspondingGroup, parent1, parent2);
                student.setId(studentId);
                myStudents.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myStudents;
    }

    public List<Parent> loadParentsFromDatabase() {
        myParents.clear();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "SELECT * FROM parents";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int parentId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String personalIdentityCode = resultSet.getString("personal_identity_code");
                String phoneNumber = resultSet.getString("phone_number");

                Parent parent = new Parent(firstName, lastName, personalIdentityCode, phoneNumber);
                parent.setId(parentId);
                myParents.add(parent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myParents;
    }


//    TODO: Retrieve data from database

    public List<Student> getAllExistingStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "SELECT * FROM students";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt(1));
                student.setFirstName(resultSet.getString(2));
                student.setLastName(resultSet.getString(3));
                student.setAge(resultSet.getInt(4));
                student.setGroup(Group.convertToGroupEnum(resultSet.getInt(5)));
                student.setParent1Id(resultSet.getInt("parent_id_1"));
                student.setParent2Id(resultSet.getInt("parent_id_2"));

                students.add(student);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Parent> getAllExistingParents() {
        List<Parent> parents = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "SELECT * FROM parents";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String personalIdentityCode = resultSet.getString("personal_identity_code");
                String phoneNumber = resultSet.getString("phone_number");
                Parent parent = new Parent(firstName, lastName, personalIdentityCode, phoneNumber);
                parent.setId(resultSet.getInt("id"));
                parents.add(parent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parents;
    }

    public List<Staff> getAllExistingStaff() {
        List<Staff> staffList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String sql = "SELECT * FROM staff";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String role = resultSet.getString("role");

                int groupId = resultSet.getInt("corresponding_group");
                Group correspondingGroup = (groupId != 0) ? Group.convertToGroupEnum(groupId) : null;

                String specialization = resultSet.getString("description");
                String phoneNumber = resultSet.getString("phone_number");

                Staff staff = new Staff(firstName, lastName, RoleType.valueOf(role), correspondingGroup, specialization, phoneNumber);
                staff.setId(resultSet.getInt("id"));
                staffList.add(staff);
                System.out.println(staffList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

//    TODO: Search parents/students/staff by ID in database

    public Student searchStudentInDatabase(int studentID) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db",
                "root", "root")) {
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, studentID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setAge(resultSet.getInt("age"));
                student.setGroup(Group.convertToGroupEnum(resultSet.getInt("group_id")));

                System.out.println("Student found: " + student.getId() + ", " + student.getFirstName() + ", "
                        + student.getLastName() + ", " +
                        student.getAge() + ", " + student.getGroup());
            } else {
                System.out.println("Student with the given ID not found in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Staff searchStaffInDatabase(int staffId) {
        String sql = "SELECT * FROM staff WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, staffId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String role = resultSet.getString("role");
                Integer correspondingGroupId = resultSet.getInt("corresponding_group");
                String description = resultSet.getString("description");
                String phoneNumber = resultSet.getString("phone_number");

                System.out.println("Staff member found:");
                System.out.println("ID: " + id);
                System.out.println("Name: " + firstName + " " + lastName);
                System.out.println("Phone Number: " + phoneNumber);
            } else {
                System.out.println("No staff member found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void searchParentInDatabase(int parentId) {
        String sql = "SELECT * FROM parents WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, parentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");

                System.out.println("Parent found:");
                System.out.println("ID: " + parentId);
                System.out.println("Name: " + firstName + " " + lastName);
                System.out.println("Phone Number: " + phoneNumber);
            } else {
                System.out.println("No parent found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    TODO: Update/edit in database

    public void updateStudentInDatabase(String uniqueId, String newFirstName, String newLastName, int newAge, Group newGroup) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String updateQuery = "UPDATE students SET first_name=?, last_name=?, grade=?, group_name=? WHERE unique_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newFirstName);
                preparedStatement.setString(2, newLastName);
                preparedStatement.setInt(3, newAge);
                preparedStatement.setString(4, newGroup.toString());

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Student information updated successfully!");
                } else {
                    System.out.println("Student with ID " + uniqueId + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateParentInDatabase(String uniqueId, String newFirstName, String newLastName, String newPhoneNumber) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String updateQuery = "UPDATE parents SET first_name=?, last_name=?, phone_number=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newFirstName);
                preparedStatement.setString(2, newLastName);
                preparedStatement.setString(3, newPhoneNumber);
                preparedStatement.setString(4, uniqueId);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Parent information updated successfully!");
                } else {
                    System.out.println("Parent with ID " + uniqueId + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStaffInDatabase(String staffId, String newFirstName, String newLastName, RoleType newRole, Group newCorrespondingGroup, String newPhoneNumber) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root")) {
            String updateQuery = "UPDATE staff SET first_name=?, last_name=?, role = ?, corresponding_group = ?, phone_number = ? WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newFirstName);
                preparedStatement.setString(2, newLastName);
                preparedStatement.setString(3, newRole.toString());
                preparedStatement.setString(4, newCorrespondingGroup.toString());
                preparedStatement.setString(5, newPhoneNumber);
                preparedStatement.setString(6, staffId);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Staff information updated successfully!");
                } else {
                    System.out.println("Staff with ID " + staffId + " not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    TODO: OTHER

    public Parent getParentById(int parentId) {
        String sqlQuery = "SELECT * FROM parents WHERE id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kindergarten_db", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, parentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String cnp = resultSet.getString("personal_identity_code");
                String phoneNumber = resultSet.getString("phone_number");

                return new Parent(firstName, lastName, cnp, phoneNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch parent from the database.");
        }

        return null;
    }

    @Override
    public void generateFeedbackReport(String filePath) {
        Report reportGenerator = new Report();
        reportGenerator.generateFeedbackReport(filePath);
    }

    @Override
    public void generateStudentReport(String filePath) {
        Report reportGenerator = new Report();
        reportGenerator.generateStudentReport(filePath);
    }

    @Override
    public void generateParentReport(String filePath) {
        Report reportGenerator = new Report();
        reportGenerator.generateParentReport(filePath);
    }

    @Override
    public void getMessages() {
        List<Message> recipientMessages = MESSAGES.stream().filter(message -> message.getRecipient().getName().equals("Admin")).toList();
        System.out.println(recipientMessages);
    }

    @Override
    public void sendMessage(Communicator recipient, String messageContent) {
        if (recipient.getRole() == OTHER || recipient.getRole() == TEACHER || recipient.getRole() == PARENT) {
            MessageSender sender1 = new MessageSender(this);
            sender1.sendText(recipient, messageContent, this.getRole());
        } else {
            System.out.println("Message could not be send, please try again.");
        }
    }
}