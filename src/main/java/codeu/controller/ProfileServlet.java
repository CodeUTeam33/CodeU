package codeu.controller;

import java.util.UUID;
import java.util.List;
import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServlet extends HttpServlet {
    
    /** Store class that gives access to Users. */ 
    private UserStore userStore;
     
     /** Store class that gives access to Messages. */
     private MessageStore messageStore;
    
    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
        setMessageStore(MessageStore.getInstance());
    }
    
    /** Sets the userStore for this Servlet*/
    private void setUserStore(UserStore instance) {
     this.userStore = instance;
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
        User user = userStore.getUser(username);
        String requestUrl = request.getRequestURI();
        String ID = requestUrl.substring("/profile/".length());
        User profileUser = userStore.getUser(UUID.fromString(ID));
        
        String profileName =  profileUser.getName();
        String profileAboutMe = profileUser.getAboutMe();
        String profileID = profileUser.getId().toString();
        List<Message> messages = messageStore.getRecentMessages(profileUser.getId(), 50);
        
        request.setAttribute("profile", profileUser);
        request.setAttribute("profileName", profileName);
        request.setAttribute("profileAboutMe", profileAboutMe);
        request.setAttribute("profileID", profileID);
        request.setAttribute("messages", messages);


        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
    }
    /**
     * Change about me
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        String username = (String) request.getSession().getAttribute("user");
        User user = userStore.getUser(username);
        User profile = (User) request.getAttribute("profile");
        
        if (!user.getId().equals(profile.getId())) {
            response.sendRedirect("/profile");
            return;
        }
        
        String aboutMe = request.getParameter("aboutme");
        user.setAboutMe(aboutMe);
        
    }
}
