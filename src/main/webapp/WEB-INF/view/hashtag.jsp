<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
String hashtag = (String) request.getAttribute("hashtag");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= hashtag %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">

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

    <h1><%= hashtag%>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>
    <%
      for (Message message : messages) {
        String author = UserStore.getInstance()
          .getUser(message.getAuthorId()).getName();
        String ID = UserStore.getInstance()
                .getUser(message.getAuthorId()).getId().toString();
        String URL = "/profile/" + author;
    %>
    	
      <li><strong><a href= <%= URL %> ><%= author %></a>:</strong> <%= message.getContent() %></li>
    <%
      }
    %>
      </ul>
    </div>

    <hr/>
  </div>

</body>
</html>
