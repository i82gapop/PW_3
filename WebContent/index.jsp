<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="ContactBean" scope="session" class="es.uco.pw.business.display.javabean.ContactBean"></jsp:useBean>

<%@page import = "java.util.ArrayList, es.uco.pw.data.dao.post.DAOPost, es.uco.pw.business.post.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Contact Management System with MVC!</title>

	<link rel="stylesheet" href="mvc/css/main.css">
	<link rel="stylesheet" href="mvc/css/login-register.css">
	<link rel="stylesheet" href="mvc/css/index.css">

</head>

<style>

.postfilter {
  overflow: auto;
  float: left;
  width: 50%;
  height: 50vh;
  padding-top: 30px;
  padding-left: 15px;
  padding-right: 15px;
  padding-bottom: 30px;
  display: none;
}

.show {
  display: block;
}
</style>

<body>
	


<% 
if (ContactBean == null || ContactBean.getEmail()=="") {
	// Usuario no logado -> Se invoca al controlador de la funcionalidad
%>
	<center>
    
    <div class="main-content">


            <div class="form-register-with-email">

                <div class="form-white-background">
                    <div class="form-title-row">

                        <h1>Contact Management System</h1>
                    </div>
					
					<p style="color:red">	
	<input type="button" onclick="window.location.href='Login';" value="Log in" />
	<input type="button" onclick="window.location.href='Register';" value="Register" />	
	

		</div>
		<a class="form-log-in-with-existing">By <b>Ruben Borrego Canovaca and Pedro Pablo García Pozo </b></a>
	</div>
</div>
</center>


<% } else { %>

	<%
	String sql_prop = request.getServletContext().getInitParameter("sqlprop");
	java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
	java.util.Properties prop = new java.util.Properties();
	prop.load(myIO);
	DAOPost daopost = new DAOPost(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
	ArrayList <Post> posts = new ArrayList <Post>();
	posts = daopost.ListPosts();

	ArrayList <String> authors = new ArrayList <String>();
	authors = daopost.DistinctsAuthors();
	ArrayList <String> publications = new ArrayList <String>();
	publications = daopost.DistinctsDates();
	%>

	<div class="header">
		
		<div class="searchrow">

		     <form name = "searchform" action = "Search">
		        
		        <select class="contentselect" name = "contentselect" id="contentselect" onchange="selectOrder();">
		             <option value="" disabled selected>Select...</option>
		            <option value="Author">Author</option>
		            <option value="Date">Date</option>
		            <option value="Interests">Interests</option>
		            <option value="Recipients">Recipients</option>
		        </select>
		
		        <input type="text" id = "searcheng"name = "searcheng" placeholder = "Search here" class="src" autocomplete="on">
		           <button type="submit"class="form__btn">Search</button>
		
		    </form>
	
	    </div>
			
		<div class="buttons">			
			<a href="Logout">Log Out</a>
			<a href="Modify">Modify your profile</a>
			<a href="RecoverPost">Recover a Post</a>
			<a href="ArchivePost">Archive a Post</a>
			<a href="PostPost">Post a Post</a>
			<a href="EditPost">Edit a Post</a>
			<a href="CreatePost">Create Post</a>
		</div>
	</div>

	<div class="row">
		<div class="column">
		  <h2>Post Board</h2>
			<div>
				<form action="" id="filter1">
					<fieldset>
					<label><input type="radio" value="Type" name="Option1" onclick="Type();">Type</label>
					<label><input type="radio" value="Author" name="Option1" onclick="Author();">Author</label>
					<label><input type="radio" value="Publication" name="Option1" onclick="Publication();">Publication</label>
					</fieldset>
				</form>

				<script type = "text/javascript" src="javascript/filterPost.js"></script>
			</div>

			<div id="types" style="display:none">
				
				<select>
					
					<option onclick="filterSelection('GENERAL')">General</option>
					<option onclick="filterSelection('INDIVIDUALIZED')">Individualized</option>
					<option onclick="filterSelection('THEMATIC')">Thematic</option>
					<option onclick="filterSelection('FLASH')">Flash</option>
					<option onclick="filterSelection('all')">All</option>

				</select>
								
			</div>

			<div id="authors" style="display:none">
				
				<select>
					
					<%
					
					for(int i = 0; i < authors.size(); i++){

						out.println("<option onclick=\"filterSelection('" + authors.get(i) + "')\">" + authors.get(i) + "</option>");
						System.out.println("<option onclick=\"filterSelection('" + authors.get(i) + "')\">" + authors.get(i) + "</option>");
					}
					
					%>

				</select>
						
			</div>
			
			<div id="dates" style="display:none">
				
				<select>
					
					<%
					
					for(int i = 0; i < publications.size(); i++){

						out.println("<option onclick=\"filterSelection('" + publications.get(i) + "')\">" + publications.get(i) + "</option>");
						System.out.println("<option onclick=\"filterSelection('" + publications.get(i) + "')\">" + publications.get(i) + "</option>");
					}
					
					%>

				</select>
						
			</div>

			<p>
				<div class="row">
					
					<%
					
					for(int i = 0; i < posts.size(); i++){

						if(posts.get(i).getOwner().getEmail().equals(ContactBean.getEmail())){
							
							if(posts.get(i).getPublication() != null){
								
								out.println("<div" +  " class=\"postfilter " + posts.get(i).getType().name() + " "  + posts.get(i).getPublication().toString().replaceAll(" ", "") + " " + posts.get(i).getOwner().getEmail() + "\">" + posts.get(i) + "</div>");
								System.out.println("<div" +  " class=\"postfilter " + posts.get(i).getType().name() + " "  + posts.get(i).getPublication().toString().replaceAll(" ", "") + " " +  posts.get(i).getOwner().getEmail() + "\">" + posts.get(i) + "</div>");
							}
							
							else{
								
								out.println("<div" +  " class=\"postfilter " + posts.get(i).getType().name() + " " + posts.get(i).getOwner().getEmail() + "\">" + posts.get(i) + "</div>");
								System.out.println("<div" +  " class=\"postfilter " + posts.get(i).getType().name() + " " + posts.get(i).getOwner().getEmail() + "\">" + posts.get(i) + "</div>");
							}
							
						}
											
					}
					
					%>
					
				</div>			
		 	</p>
		</div>

		<div class="column">
		  <h2>All your posts</h2>
		  <p>
			<div class="row">
				<%
				
				for(int i = 0; i < posts.size(); i++){

					if(posts.get(i).getOwner().getEmail().equals(ContactBean.getEmail())){
						
						out.println("<div class=\"post\">" + posts.get(i) + "</div>");
					}
										
				} %>
			</div>
		  </p>
		</div>
	</div>

	<div class="footer">
		
		Welcome, <jsp:getProperty property="email" name="ContactBean"/>!!
	</div>

<% } %>
</body>
</html>