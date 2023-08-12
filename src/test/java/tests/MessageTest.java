package tests;
import org.Application.Communication.Message;
import org.Application.Organization.Group;
import org.Application.Organization.RoleType;
import org.Application.Organization.Staff;
import org.junit.Test;
import static org.Application.Communication.MessageStorage.MESSAGES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessageTest {
    @Test
    public void testSendText() {

        Staff sender = new Staff("Selena", "Dinu", RoleType.OTHER, Group.SUPERHEROES, "Security", "0766547782");

        Staff recipient = new Staff("Alina", "Radu", RoleType.OTHER, "IT", "0789657778");

        sender.sendMessage(recipient, "Hello, this is a test message.");

        Message lastMessage = MESSAGES.get(MESSAGES.size() - 1);

        assertNotNull(lastMessage);
        assertEquals("Hello, this is a test message.", lastMessage.getMessageContent());
        assertEquals(sender, lastMessage.getSender());
        assertEquals(recipient, lastMessage.getRecipient());
    }
}
