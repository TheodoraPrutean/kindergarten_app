package org.Application.Organization;
import org.Application.Administrator.Report;
import org.Application.Clients.Student;
import org.Application.Communication.Communicator;
import org.Application.Communication.FeedbackSubmitter;
import org.Application.Communication.Message;
import org.Application.Communication.MessageSender;
import java.util.ArrayList;
import java.util.List;

import static org.Application.Communication.MessageStorage.MESSAGES;

public class Staff extends FeedbackSubmitter implements Communicator {
    private RoleType role;
    private int id;
    private String uniqueId;
    private String firstName;
    private Integer groupId;
    private String lastName;
    private String phoneNumber;
    private String description;
    private Group correspondingGroup;
    private final List<Student> myStudents = new ArrayList<>();

    public String getUniqueId() {
        this.uniqueId = "S" + this.id;
        return uniqueId;
    }

    public Staff(int idFromDatabase) {
        this.id = idFromDatabase;
        this.uniqueId = "S" + idFromDatabase;
    }

    public Staff() {
    }

    public Staff(String firstName, String lastName, RoleType role, Group correspondingGroup, String description, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.correspondingGroup = correspondingGroup;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    public Staff(String firstName, String lastName, RoleType role, String description, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.groupId = null;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public void generateStudentReport(String filePath) {
        Report reportGenerator = new Report();
        reportGenerator.generateStudentReport(filePath);
    }

    public void generateParentReport(String filePath) {
        Report reportGenerator = new Report();
        reportGenerator.generateParentReport(filePath);
    }

    public Group getCorrespondingGroup() {
        return correspondingGroup;
    }

    public void setCorrespondingGroup(Group correspondingGroup) {
        this.correspondingGroup = correspondingGroup;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public String getStaffPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.phoneNumber = PhoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public Student getStudentById(int studentID) {
        for (Student student : myStudents) {
            if (student.getId() == studentID) {
                return student;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Staff: id = " + id +
                ", Name = " + firstName + " " + lastName +
                ", role = " + role +
                ", group = " + correspondingGroup +
                ", description = " + description +
                ", phoneNumber = " + phoneNumber + "\n";
    }

    @Override
    public void getMessages() {
        List<Message> recipientMessages = MESSAGES.stream().filter(message -> message.getRecipient().getName().equals(getName())).toList();
        System.out.println(recipientMessages);
    }

    @Override
    public void sendMessage(Communicator recipient, String messageContent) {
        if (recipient.getRole() == RoleType.OTHER || recipient.getRole() == RoleType.ADMIN) {
            MessageSender sender1 = new MessageSender(this);
            sender1.sendText(recipient, messageContent, this.getRole());
        } else {
            System.out.println("Unauthorized access. STAFF role cannot send message to teacher or parent.");
        }
    }
}
