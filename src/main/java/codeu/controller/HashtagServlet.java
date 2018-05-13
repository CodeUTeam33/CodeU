package codeu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Message;
import codeu.model.store.basic.MessageStore;

/** This will service individual hashtags showing all of the messages they have been used in*/
public class HashtagServlet extends HttpServlet{
     
     /** Store class that gives access to Messages. */
     private MessageStore messageStore;
    
    @Override
    public void init() throws ServletException {
        super.init();
        setMessageStore(MessageStore.getInstance());
    }
    
    
    /** Sets the messageStore for this Servlet*/
    void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }
    
 /**
     * Get the profile page
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        String username = (String) request.getSession().getAttribute("user");

        String requestUrl = request.getRequestURI();
        String tag = requestUrl.substring("/hashtag/".length());


        List<Message> messages = messageStore.getHashTagMessages(tag);
        
        request.setAttribute("hashtag", tag);
        request.setAttribute("messages", messages);


        request.getRequestDispatcher("/WEB-INF/view/hashtag.jsp").forward(request, response);
    }
    /**
     * Change about me
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        request.getRequestDispatcher("/WEB-INF/view/hashtag.jsp").forward(request, response);
        
    }
}
