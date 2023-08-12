package org.Application.Communication;
import org.Application.Organization.RoleType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.Application.Communication.MessageStorage.MESSAGES;

public class MessageSender {

    private final Communicator sender;

    public MessageSender(Communicator sender) {
        this.sender = sender;
    }
    public void sendText(Communicator recipient, String messageContent, RoleType userRole) {

        if (recipient != null) {
                LocalDateTime timestamp = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd-MM-yyyy");
                String formattedTimestamp = timestamp.format(formatter);
                Message message = new Message(sender, recipient, timestamp, messageContent);

                MessageStorage messageStorage = new MessageStorage();
                messageStorage.addMessage(message);

                System.out.println("Message sent at " + formattedTimestamp + ": "+ messageContent);

            } else {
                System.out.println("Message not sent, please try again.");
            }
        }
    }
