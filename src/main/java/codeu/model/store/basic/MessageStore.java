// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.basic;

import java.time.Instant;
import codeu.model.data.Message;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class MessageStore {
    
    /** Singleton instance of MessageStore. */
    private static MessageStore instance;
    
    
    
    
    /**
     * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
     *
     * @param persistentStorageAgent a mock used for testing
     */
    public static MessageStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
        return new MessageStore(persistentStorageAgent);
    }
    
    /**
     * The PersistentStorageAgent responsible for loading Messages from and saving Messages to
     * Datastore.
     */
    private PersistentStorageAgent persistentStorageAgent;
    
    /** The in-memory list of Messages. */
    private List<Message> messages;
    
    private HashMap<String,List<Message>> hashTags;
    
    /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
    private MessageStore(PersistentStorageAgent persistentStorageAgent) {
        this.persistentStorageAgent = persistentStorageAgent;
        messages = new ArrayList<>();
        hashTags = new HashMap<>();
    }
    
    /**
     * Load a set of randomly-generated Message objects.
     *
     * @return false if an error occurs.
     */
    public boolean loadTestData() {
        boolean loaded = false;
        try {
            messages.addAll(DefaultDataStore.getInstance().getAllMessages());
            loaded = true;
        } catch (Exception e) {
            loaded = false;
            System.out.println("ERROR: Unable to establish initial store (messages).");
        }
        return loaded;
    }
    
    /**
     * Returns the singleton instance of MessageStore that should be shared between all servlet
     * classes. Do not call this function from a test; use getTestInstance() instead.
     */
    public static MessageStore getInstance() {
        if (instance == null) {
            instance = new MessageStore(PersistentStorageAgent.getInstance());
        }
        return instance;
    }
    
    
    public Message getMessage(UUID conversationId, UUID author, Instant creation) {
        List<Message> messages = getMessagesInConversation(conversationId);
        
        if (author == null || creation == null) {
            return null;
        }
        
        for (Message message : messages) {
            if (message.getAuthorId().equals(author) && message.getCreationTime().equals(creation)) {
                return message;
            }
        }
        return null;
    }
    
    /** Add a new message to the current set of messages known to the application. */
    public void addMessage(Message message) {
        messages.add(message);
        parseMessage(message);
        persistentStorageAgent.writeThrough(message);
    }
    
    /** Access the current set of Messages within the given Conversation. */
    public List<Message> getMessagesInConversation(UUID conversationId) {
        
        List<Message> messagesInConversation = new ArrayList<>();
        
        for (Message message : messages) {
            if (message.getConversationId().equals(conversationId)) {
                messagesInConversation.add(message);
            }
        }
        
        return messagesInConversation;
    }
    
    /** Sets the List of Messages stored by this MessageStore. */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        checkMessagesForTags();
    }
    
    private void checkMessagesForTags(){
        for(int i = 0; i < messages.size(); i++){
            parseMessage(messages.get(i));
        }
    }
    
    public List<Message> getRecentMessages(UUID authorID, int recent){
        ArrayList<Message> userMessages = new ArrayList<Message>();
        for(int i = 0; i < messages.size(); i++){
            Message crntMessage = messages.get(i);
            if(crntMessage.getAuthorId().equals(authorID)){
                userMessages.add(crntMessage);
            }
        }
        userMessages.sort( new Comparator<Message>(){
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getCreationTime().compareTo(o2.getCreationTime());
            }   
        });
        
        if(recent < userMessages.size()){
            return userMessages.subList(0, recent);   
        }
        else{
            return userMessages;
        }
    }
    
    /** Inserts the message into the appropriate hashtag list if there is a hash tag*/
    private void parseMessage(Message message){
        String content = message.getContent();
        String[] splitString = content.split(" ");
        
        for(int i = 0; i < splitString.length; i++){
            String crnt = splitString[i];
            if(crnt.startsWith("#")){
                List<Message> hashTagMessages = hashTags.get(crnt.substring(1));
                if(hashTagMessages == null){
                    //hashTagMessages.add(message);
                    List<Message> needToAdd = new ArrayList<Message>();
                    needToAdd.add(message);
                    hashTags.put(crnt.substring(1), needToAdd);
                }
                else{
                    hashTagMessages.add(message);
                }
            }
        }
    }
    
    public List<Message> getHashTagMessages(String tag){
        return hashTags.get(tag);
    }
    
    public List<String> getTags(){
        List<String> tags = new ArrayList<>();
        tags.addAll(hashTags.keySet());
        return tags;
        
    }
    
}

