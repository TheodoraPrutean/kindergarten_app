package org.Application.Communication;

import java.util.ArrayList;
import java.util.List;

public class MessageStorage {
    public static final List<Message> MESSAGES =  new ArrayList<>();
    public void addMessage(Message message) {
        MESSAGES.add(message);
    }
}
