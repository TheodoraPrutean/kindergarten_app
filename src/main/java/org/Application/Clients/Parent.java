package org.Application.Clients;

import org.Application.Communication.*;
import org.Application.Organization.RoleType;

import java.util.ArrayList;
import java.util.List;

import static org.Application.Communication.MessageStorage.MESSAGES;

public class Parent extends FeedbackSubmitter implements Communicator {
    private static final List<Parent> allParents = new ArrayList<>();
    private final RoleType role = RoleType.PARENT;
    private int id;
    private String uniqueId;
    private String firstName;
    private String lastName;
    private String personalIdentityCode;
    private final FeedbackSubmitter feedbackSubmitter = new FeedbackSubmitter();
    private String phoneNumber;

    public Parent() {

    }
    public Parent(String firstName, String lastName, String personalIdentityCode, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        setPersonalIdentityCode(personalIdentityCode);
        if (phoneNumber != null && phoneNumber.length() >= 10) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Invalid phone number format. Phone number should be at least 10 characters long.");
        }
        allParents.add(this);
    }

    public Parent(int idFromDatabase) {
        this.id = idFromDatabase;
        this.uniqueId = "P" + idFromDatabase;
    }


    public static List<Parent> getAllParents() {
        return allParents;
    }
    public String getUniqueId() {
        this.uniqueId = "P" + this.id;
        return uniqueId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPersonalIdentityCode() {
        return personalIdentityCode;
    }
    public void setPersonalIdentityCode(String personalIdentityCode) {
        if (personalIdentityCode == null || personalIdentityCode.length() != 13) {
            throw new IllegalArgumentException("Personal identity code must be 13 characters long.");
        }
        this.personalIdentityCode = personalIdentityCode;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() >= 10) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Invalid phone number format. Phone number should be at least 10 characters long.");
        }
    }
    public RoleType getRole() {
        return role;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Parent: id = " + id +
                ", Name = " + firstName + " " + lastName +
                ", personalIdentityCode = " + personalIdentityCode +
                ", phoneNumber = " + phoneNumber + "\n";
    }

    @Override
    public void getMessages() {
        List<Message> recipientMessages = MESSAGES.stream().filter(message -> message.getRecipient().getName().equals(getName())).toList();
        System.out.println(recipientMessages);
    }

    @Override
    public void sendMessage(Communicator recipient, String messageContent) {
        if (recipient.getRole() == RoleType.TEACHER || recipient.getRole() == RoleType.ADMIN) {
            MessageSender sender1 = new MessageSender(this);
            sender1.sendText(recipient, messageContent, this.getRole());
        } else {
            System.out.println("Unauthorized access. Only ADMIN, STAFF and TEACHER roles are allowed to send messages to staff.");
        }

    }
}

