package codeu.controller;

import java.util.List;
import java.util.UUID;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet class responsible for popup displaying the users who have liked a message.
 */
public class LikesServlet extends HttpServlet {
    
    /** Store class that gives access to Conversations. */
    private ConversationStore conversationStore;
    
    /** Store class that gives access to Messages. */
    private MessageStore messageStore;
    
    /** Store class that gives access to Users. */
    private UserStore userStore;
  
    @Override
    public void init() throws ServletException {
        super.init();
        setConversationStore(ConversationStore.getInstance());
        setMessageStore(MessageStore.getInstance());
        setUserStore(UserStore.getInstance());
    }
    
    /**
     * Sets the ConversationStore used by this servlet. This function provides a common setup method
     * for use by the test framework or the servlet's init() function.
     */
    void setConversationStore(ConversationStore conversationStore) {
        this.conversationStore = conversationStore;
    }
    
    /**
     * Sets the MessageStore used by this servlet. This function provides a common setup method for
     * use by the test framework or the servlet's init() function.
     */
    void setMessageStore(MessageStore messageStore) {
        this.messageStore = messageStore;
    }
    
    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }
    /** 
     * This function fires when the users clicks on the number of likes to the left of a message.
     * It obtains the conversation and message IDs from the URL and it redirects to likes.jsp to display the popup.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        String requestUrl = request.getRequestURI();
        String url = requestUrl.substring("/likes/".length());
        String conversationID = url.substring(0, url.indexOf("/"));
        String messageID = url.substring(url.indexOf("/") + 1);
        
        Conversation conversation = conversationStore.getConversationWithID(conversationID);
        if (conversation == null) {
            // couldn't find conversation, redirect to conversation list
            System.out.println("Conversation was null: " + conversationID);
            response.sendRedirect("/conversations");
            return;
        }
        
        UUID conversationId = conversation.getId();
        List<Message> messages = messageStore.getMessagesInConversation(conversationId);
        Message message = null;
        
        for (Message mess : messages) {
            if (mess.getId().toString().equals(messageID)) {
                message = mess;
                break;
            }
        }
        
        request.setAttribute("conversation", conversation);
        request.setAttribute("messages", messages);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/view/likes.jsp").forward(request, response);
    }
    
    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/view/likes.jsp").forward(request, response);
    }
}
