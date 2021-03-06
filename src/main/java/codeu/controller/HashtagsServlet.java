package codeu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codeu.model.data.Message;
import codeu.model.store.basic.MessageStore;

/** This servlet will service the home page for all of the different hashtags*/
public class HashtagsServlet  extends HttpServlet{

    
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
    * Get the hashtags that are in use
    */
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
       throws IOException, ServletException {

       List<String> tags = messageStore.getTags();
       
       request.setAttribute("hashtags", tags);
       request.getRequestDispatcher("/WEB-INF/view/hashtags.jsp").forward(request, response);
   }
   /**
    */
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
       throws IOException, ServletException {
       
	   request.getRequestDispatcher("/WEB-INF/view/hashtags.jsp").forward(request, response);
       
   }
}
