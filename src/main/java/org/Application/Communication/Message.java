package org.Application.Communication;

import java.time.LocalDateTime;

public class Message {
    private final Communicator sender;
    private final Communicator recipient;
    LocalDateTime time;
    String messageContent;

    public Communicator getSender() {
        return sender;
    }

    public Communicator getRecipient() {
        return recipient;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public Message(Communicator sender, Communicator recipient, LocalDateTime time, String messageContent) {
        this.sender = sender;
        this.recipient = recipient;
        this.time = time;
        this.messageContent = messageContent;
    }

    @Override
    public String toString() {
        return "Message: " + sender.getName() +
                ", " + time +
                ", " + messageContent + '\'';
    }
}
