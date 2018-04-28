package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServlet extends HttpServlet {
    
     private UserStore userStore;
    
    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
    }
    /*
     * Get the profile page
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        String requestUrl = request.getRequestURI();
        String userPage = requestUrl.substring("/user/".length());
        User user = userStore.getUser(userPage.substring(6));
        
        request.setAttribute("profile", user);
        request.getRequestDispatcher("/WEB-INF/view/profilepage.jsp").forward(request, response);
    }
    /*
     * Change about me
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        String username = request.getParameter("username");
        User user = userStore.getUser(username);
        User profile = userStore.getUser(request.getAttribute("profile"));
        
        if (!user.equals(profile)) {
            response.sendRedirect("/chat");
            return;
        }
        
        String aboutMe = request.getParameter("aboutMe");
        user.setAboutMe(aboutMe);
        
    }
}