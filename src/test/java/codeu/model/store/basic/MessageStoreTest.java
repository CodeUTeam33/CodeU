package codeu.model.store.basic;

import codeu.model.data.Message;
import codeu.model.store.persistence.PersistentStorageAgent;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class MessageStoreTest {

  private MessageStore messageStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final UUID CONVERSATION_ID_ONE = UUID.randomUUID();
  private final UUID USER_ONE_ID = UUID.randomUUID();
  private final Message MESSAGE_ONE =
      new Message(
          UUID.randomUUID(),
          CONVERSATION_ID_ONE,
          USER_ONE_ID,
          "message one #testTag",
          Instant.ofEpochMilli(1000));
  private final Message MESSAGE_TWO =
      new Message(
          UUID.randomUUID(),
          CONVERSATION_ID_ONE,
          USER_ONE_ID,
          "message two",
          Instant.ofEpochMilli(2000));
  private final Message MESSAGE_THREE =
      new Message(
          UUID.randomUUID(),
          UUID.randomUUID(),
          UUID.randomUUID(),
          "message three #testTag",
          Instant.ofEpochMilli(3000));
  private final Message MESSAGE_FOUR =
	  new Message(
	      UUID.randomUUID(),
	      CONVERSATION_ID_ONE,
	      UUID.randomUUID(),
	      "message four",
	      Instant.ofEpochMilli(2000));
  private final Message MESSAGE_FIVE =	
	  new Message(
	      UUID.randomUUID(),
	      UUID.randomUUID(),
	      USER_ONE_ID,
	      "message five",
	      Instant.ofEpochMilli(3000));


  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    messageStore = MessageStore.getTestInstance(mockPersistentStorageAgent);

    final List<Message> messageList = new ArrayList<>();
    messageList.add(MESSAGE_FOUR);
    messageList.add(MESSAGE_FIVE);
    messageList.add(MESSAGE_ONE);
    messageList.add(MESSAGE_TWO);
    messageList.add(MESSAGE_THREE);

    messageStore.setMessages(messageList);
  }

  @Test
  public void testGetMessagesInConversation() {
    List<Message> resultMessages = messageStore.getMessagesInConversation(CONVERSATION_ID_ONE);

    Assert.assertEquals(3, resultMessages.size());
    assertEquals(MESSAGE_FOUR, resultMessages.get(0));
    assertEquals(MESSAGE_ONE, resultMessages.get(1));
    assertEquals(MESSAGE_TWO, resultMessages.get(2));
  }

  @Test
  public void testAddMessage() {
    UUID inputConversationId = UUID.randomUUID();
    Message inputMessage =
        new Message(
            UUID.randomUUID(),
            inputConversationId,
            UUID.randomUUID(),
            "test message",
            Instant.now());

    messageStore.addMessage(inputMessage);
    Message resultMessage = messageStore.getMessagesInConversation(inputConversationId).get(0);

    assertEquals(inputMessage, resultMessage);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputMessage);
  }

  private void assertEquals(Message expectedMessage, Message actualMessage) {
    Assert.assertEquals(expectedMessage.getId(), actualMessage.getId());
    Assert.assertEquals(expectedMessage.getConversationId(), actualMessage.getConversationId());
    Assert.assertEquals(expectedMessage.getAuthorId(), actualMessage.getAuthorId());
    Assert.assertEquals(expectedMessage.getContent(), actualMessage.getContent());
    Assert.assertEquals(expectedMessage.getCreationTime(), actualMessage.getCreationTime());
  }
  
  @Test
  public void testRecentMessages(){
	  List<Message> recentMessages = messageStore.getRecentMessages(USER_ONE_ID, 3);
	  List<Message> expected = (List<Message>) Arrays.asList(MESSAGE_ONE, MESSAGE_TWO, MESSAGE_FIVE);
	  
	  for(int i = 0; i < recentMessages.size(); i++){
		  Message testMessage = recentMessages.get(i);
		  Message expectedMessage = expected.get(i);
		  Assert.assertEquals(testMessage, expectedMessage);
	  }
	  
  }
  
  @Test
  public void testHashTag(){
	  List<Message> expected = messageStore.getHashTagMessages("testTag");
	  Assert.assertEquals(expected.get(0), MESSAGE_ONE);
	  Assert.assertEquals(expected.get(1), MESSAGE_THREE);
  }
}
