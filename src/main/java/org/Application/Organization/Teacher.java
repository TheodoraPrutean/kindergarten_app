package org.Application.Organization;

import org.Application.Administrator.Report;
import org.Application.Communication.Communicator;
import org.Application.Communication.Message;
import org.Application.Communication.MessageSender;

import java.util.List;

import static org.Application.Communication.MessageStorage.MESSAGES;

public class Teacher extends Staff implements Communicator {
    public Teacher(String firstName, String lastName, RoleType role, Group correspondingGroup, String description, String phoneNumber) {
        super(firstName, lastName, role, correspondingGroup, description, phoneNumber);
        this.teacherName = firstName + " " + lastName;
    }

    private String uniqueId;
    private String teacherName;
    private Group assignedGroup;
    private final RoleType role = RoleType.TEACHER;
    public Teacher() {
    }
    public Teacher(String teacherName, Group assignedGroup) {
        this.teacherName = teacherName;
        this.assignedGroup = assignedGroup;
    }

    @Override
    public String getUniqueId() {
        this.uniqueId = "T" + this.getId();
        return uniqueId;
    }
    @Override
    public void getMessages() {
        List<Message> recipientMessages = MESSAGES.stream().filter(message -> message.getRecipient().getName().equals(getName())).toList();
        System.out.println(recipientMessages);
    }

    @Override
    public void sendMessage(Communicator recipient, String messageContent) {
        if (recipient.getRole() == RoleType.OTHER || recipient.getRole() == RoleType.ADMIN || recipient.getRole() == RoleType.PARENT) {
            MessageSender sender1 = new MessageSender(this);
            sender1.sendText(recipient, messageContent, this.getRole());
        } else {
            System.out.println("Message not sent, please try again.");
        }
    }

    public RoleType getRole() {
        return role;
    }

    @Override
    public String getName() {
        return teacherName;
    }
    public Group getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(Group group) {
        this.assignedGroup = group;
    }


    @Override
    public void generateStudentReport(String filePath) {
        Report reportGenerator = new Report();
        reportGenerator.generateParentReport(filePath);
    }

    @Override
    public void generateParentReport(String filePath) {
        Report reportGenerator = new Report();
        reportGenerator.generateParentReport(filePath);
    }
}
