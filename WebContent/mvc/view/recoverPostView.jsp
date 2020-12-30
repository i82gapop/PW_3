<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="es.uco.pw.data.dao.interest.DAOInterest" %>
<%@page import = "java.util.ArrayList"%>
<%@page import = "es.uco.pw.business.display.javabean.ContactBean"%>
<%@page import ="java.io.IOException"%>
<%@page import = "javax.servlet.*"%>
<%@page import ="javax.servlet.http.*"%>
<%@page import ="java.util.ArrayList"%>
<%@page import ="java.text.ParseException"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.StringTokenizer"%>
<%@page import ="es.uco.pw.data.dao.interest.DAOInterest"%>
<%@page import ="es.uco.pw.business.contact.Contact"%>
<%@page import ="es.uco.pw.data.dao.contact.DAOContact"%>
<%@page import ="es.uco.pw.business.post.Post"%>
<%@page import ="es.uco.pw.business.post.Type"%>
<%@page import ="es.uco.pw.business.post.Status"%>
<%@page import ="es.uco.pw.data.dao.post.DAOPost"%>
    
<jsp:useBean  id="ContactBean" scope="session" class="es.uco.pw.business.display.javabean.ContactBean"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recover Post</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/dt-1.10.20/datatables.min.css"/>
</head>
<body>

<%
String sql_prop = request.getServletContext().getInitParameter("sqlprop");
java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
java.util.Properties prop = new java.util.Properties();
prop.load(myIO);
DAOPost daopost = new DAOPost(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);


ArrayList<Post> res = daopost.ListPosts();


String nextPage = "RecoverPost";
String messageNextPage = request.getParameter("message");
if (messageNextPage == null) messageNextPage = "";   
        //redirect user to login page if not logged in
if (ContactBean == null || ContactBean.getEmail().equals("")) {
	//No debería estar aquí -> flujo salta a index.jsp
	response.sendRedirect("index.jsp");
}
%>
	<div class = "container">
		<div class="float-right">
			<a href="index.jsp">Go to menu</a>
		</div>
		<h1>Archived Posts List</h1>
		<hr/>
		
		
		<p>
			<button class = "btn btn-primary" onclick="window.location.href = 'ArchivePost'">Archive a Post</button>
		</p>
	
		<table class = "table table-striped table-bordered" id="datatable">
			<thead>
				<tr class = "thead-dark">
					<th>ID POST</th>
					<th>Title</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
			
			<%	for(Post post : res){
					if(post.getStatus().equals(Status.ARCHIVED)){%>
					<tr>
						<td><%=post.getIdentifier()%></td>
						<td><%=post.getTitle()%></td>
						<td> 
							| 
							<a href = "RecoverPost?action=RECOVER&id=<%=post.getIdentifier()%>">Recover</a> 
						</td>
					</tr>
						<%}
					}%>
			</tbody>
		</table>
	</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/dt-1.10.20/datatables.min.js"></script>
<script>
	$(document).ready(function(){
		$("#datatable").DataTable();
	})
</script>










</body>
</html>