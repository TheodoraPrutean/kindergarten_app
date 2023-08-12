package org.Application.Communication;

import org.Application.Organization.RoleType;

public interface Communicator {
    void getMessages();
    void sendMessage(Communicator recipient, String messageContent);
    RoleType getRole();
    String getName();
}
