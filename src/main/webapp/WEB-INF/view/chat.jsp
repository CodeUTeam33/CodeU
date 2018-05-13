<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= conversation.getTitle() %></title>
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

  <script type="text/javascript">
    function toggle_visibility(id) {
       var e = document.getElementById(id);
       if(e.style.display == 'block')
          e.style.display = 'none';
       else
          e.style.display = 'block';
    }
</script>

</head>
<!--
<div id="popup-box" class="popup-position">
          <div id="popup-wrapper"> 
            <div id= "popup-container"> 
              <h3> Likes </h3>
              <p> testing this text </p>
              <p> <%="count"%> </p>
              <p> line 3 </p>
              <p> <a href= "javascript:void(0)" onclick="toggle_visibility('popup-box');"> Close</a></p>
            </div>
          </div>
        </div> -->

<body onload="scrollChat()">

<nav>
   <a id="navTitle" href="/">CodeU Chat App</a>
   <a href="/conversations">Conversations</a>
   <% if(request.getSession().getAttribute("user") != null){ %>
     <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
     <a href=<%= "/profile/" + request.getSession().getAttribute("userID") %>>Profile</a>
   <% } else{ %>
     <a href="/login">Login</a>
     <a href="/register">Register</a>
   <% } %>
   <a href="/about.jsp">About</a>
 </nav>
  <div id="container">

    <h1><%= conversation.getTitle() %>
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
        String URL = "/profile/" + ID;
        String instance = message.getCreationTime().toString();
    %>

      <form action="/chat/<%= conversation.getTitle() %>" method="POST">
      	<input type="hidden" name="author" value= <%= ID %>>
      	<input type="hidden" name="instance" value= <%= instance %>>
      	<input type="hidden" name="like" value="true">
  
     	<table border="0">
      <tr>
      <td align="center"><div>
      <button type="submit">Like</button> <br />
      <%= message.getLikeCount()%>
    </div></td>

      <td valign="center"><strong><a href= <%= URL %> ><%= author %></a>:</strong> <%= message.getContent() %> </td>
      	
      </tr>
      <!-- <tr>
        <td align="center" valign="top"><%= message.getLikeCount()%></td>
        <div id="wrapper">
        <a href="javascript:void(0)" onclick="toggle_visibility('popup-box');" > <%= message.getLikeCount()%> </a>
      </div> 
      </tr> -->
      <tr> <br /></tr>
    </table>
      
    	</form>

    <%
      }
    %>

      </ul>
    </div>

    <hr/>

    <% if (request.getSession().getAttribute("user") != null) { %>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input type="text" name="message">
        <br/>
        <button type="submit">Send</button>
    </form>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>

  </div>

</body>
</html>
