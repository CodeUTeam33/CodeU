<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>


<!DOCTYPE html>
<html>
<head>
  <title>Hashtags</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
   <a id="navTitle" href="/">CodeU Chat App</a>
   <a href="/conversations">Conversations</a>
   <% if(request.getSession().getAttribute("user") != null){ %>
     <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
     <a href=<%= "/profile/" + request.getSession().getAttribute("userID") %>>Profile</a>
     <a href="/hashtags">Hashtags</a>
   <% } else{ %>
     <a href="/login">Login</a>
     <a href="/register">Register</a>
   <% } %>
   <a href="/about.jsp">About</a>
 </nav>

  <div id="container">

    <h1>Hashtags</h1>

    <%
    List<String> hashtags = (List<String>) request.getAttribute("hashtags");
    if(hashtags == null || hashtags.isEmpty()){
    %>
      <p>Use the '#' in a conversation to get hashtags started.</p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for(String hashtag : hashtags){
    %>
      <li><a href="/hashtag/<%= hashtag %>">
        <%= hashtag %></a></li>
    <%
      }
    %>
      </ul>
    <%
    }
    %>
    <hr/>
  </div>
</body>
</html>
