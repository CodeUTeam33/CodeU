<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>

<%
User profile = (User) request.getAttribute("profile");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<html>
<head>

 <% if(request.getAttribute("profileName") != null) { %>
   <title><%= (request.getAttribute("profileName")) %>'s Profile</title>

 <% } else { %>
   <title>Profile</title>
 <% } %>
 <link rel="stylesheet" href="/css/main.css">

 <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollChat()">

	<nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") == null){ %>
      <a href="/login">Login</a>
      <a href="/register">Register</a>
    <% } else { %>
    	<a href="/hashtags">Hashtags</a>
    <% } %>
    <a href="/about.jsp">About</a>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <a> | </a>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href=<%= "/profile/" + request.getSession().getAttribute("userID") %>>Profile</a>
      <a href="/logout">Logout</a>
    <% } %>

  </nav>

 <div id="container">
  

  <% if(request.getAttribute("profileName") != null){ %>
    <h1><%= request.getAttribute("profileName") %>'s Profile</h1>

  <% } else { %>
    <h1>Invalid</h1>
  <% } %>
    <hr/>


  <% if(request.getAttribute("profileName") != null){ %>
    <h2>About <%= request.getAttribute("profileName") %></h2>
    <% if(profile.getAboutMe() != null){ %>
      <body><%= profile.getAboutMe() %></body>

      <% } %>
  <% } else { %>
    <h2>Invalid</h2>
  <% } %>

<% if(request.getSession().getAttribute("user") != null) { %>
  <%if(((String)request.getSession().getAttribute("userID")).equals(request.getAttribute("profileID"))){  %>

   <form action="/profile/"<%= request.getAttribute("userID") %> method="POST">
     <label for="aboutme">About Me: </label>
     <input type="text" name="aboutme" id="aboutme">
     <input type="hidden" name="profileID" value=<%= request.getAttribute("profileID")%>>
     <br/><br/>
     <button type="submit">Edit</button>
   </form>
   <%} 
   else{}%>
   <% } else { %>
        <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>
	
	    <div id="chat">
      <ul>
    <%
      for (Message message : messages) {
        String author = UserStore.getInstance()
          .getUser(message.getAuthorId()).getName();
        String URL = "/profile/" + author;
    %>
    	
      <li><strong><a href= <%= URL %> ><%= author %></a>:</strong> <%= message.getContent() %></li>
    <%
      }
    %>
      </ul>
    </div>

</div>
</body>
</html>
