<html>
<head>
 <title>Profile</title>
 <link rel="stylesheet" href="/css/main.css">
 </head>
<body>
	<nav>
   <a id="navTitle" href="/">CodeU Chat App</a>
   <a href="/conversations">Conversations</a>
   <% if(request.getSession().getAttribute("user") != null){ %>
     <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
   <% } else{ %>
     <a href="/login">Login</a>
     <a href="/register">Register</a>
   <% } %>
   <a href="/about.jsp">About</a>
 </nav>

 <div id="container">
   <h1>Profile</h1>

   <form action="/profile" method="POST">
     <label for="username">Username: </label>
     <input type="text" name="username" id="username">
     <br/>
     <label for="aboutme">AboutMe: </label>
     <input type="text" name="aboutme" id="aboutme">
     <br/><br/>
     <button type="submit">Edit</button>
   </form>

</div>
</body>
</html>