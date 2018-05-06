<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<%
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<html>
<head>
 <% if(request.getSession().getAttribute("profileName") != null) { %>
   <title><%= (request.getSession().getAttribute("profileName")) %>'s Profile</title>
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
   <% if(request.getSession().getAttribute("user") != null){ %>
     <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
     <a href="/profile/"<%= request.getSession().getAttribute("userID") %>>Profile</a>
   <% } else{ %>
     <a href="/login">Login</a>
     <a href="/register">Register</a>
   <% } %>
   <a href="/about.jsp">About</a>
   
 </nav>

 <div id="container">
  
  <% if(request.getSession().getAttribute("profileName") != null){ %>
    <h1><%= request.getSession().getAttribute("profileName") %>'s Profile</h1>
  <% } else { %>
    <h1>Invalid</h1>
  <% } %>
    <hr/>

  <% if(request.getSession().getAttribute("profileName") != null){ %>
    <h2>About <%= request.getSession().getAttribute("profileName") %></h2>
    <% if(request.getSession().getAttribute("profileAboutMe") != null){ %>
      <body><%= request.getSession().getAttribute("profileAboutMe") %></body>
      <% } %>
  <% } else { %>
    <h2>Invalid</h2>
  <% } %>
    <hr/>

   <form action="/profile/"<%= request.getSession().getAttribute("userID") %> method="POST">
     <label for="aboutme">About Me: </label>
     <input type="text" name="aboutme" id="aboutme">
     <br/><br/>
     <button type="submit">Edit</button>
   </form>

</div>
</body>
</html>