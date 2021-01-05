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
ArrayList<Post> results = new ArrayList <Post>();
Post aux_post = new Post();
Contact aux_contact = new Contact();

String type = (String)request.getAttribute("typeselection");
String searchst = (String)request.getAttribute("searchstr");



switch(type){



	case "Interests":
		
		StringTokenizer interests = new StringTokenizer(searchst.replace(" ", ""), ",");
        ArrayList <String> token_interests = new ArrayList <String>();

        while(interests.hasMoreTokens()){

            token_interests.add(interests.nextToken().toUpperCase());
        }
        
		for(int i = 0; i < token_interests.size(); i++){


			 aux_contact.addInterest(token_interests.get(i));
            }
			aux_post.setInterests(aux_contact.getInterests());
        	
 
			results = daopost.QueryByInterests(aux_post);
		
		
		
		break;
	
	case "Author":
		
		aux_contact.setEmail(searchst);
		aux_post.setOwner(aux_contact);
		
		results = daopost.QueryByOwner(aux_post);
		
		
		break;
		
	case "Date":
		
		try {//its a flash
			java.sql.Timestamp date_pub;
			java.util.Date date = new java.util.Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			date = format.parse(searchst);
			date_pub = new java.sql.Timestamp(date.getTime());
			

    		aux_post.setPublication(date_pub);
    		
    		results = daopost.QueryByDate(aux_post);
    		

			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		
		
		
		break;
		
	case "Recipients":
		DAOContact daocontact = new DAOContact(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
		
		StringTokenizer recipients = new StringTokenizer(searchst.replace(" ", ""), ",");
        ArrayList <String> token_recipients = new ArrayList <String>();
        ArrayList <String> buff_recipients = new ArrayList<String>();

        while(recipients.hasMoreTokens()){

            token_recipients.add(recipients.nextToken());
        }

        for(int i = 0; i < token_recipients.size(); i++){

            aux_contact.setEmail(token_recipients.get(i));

            if(daocontact.QueryByEmail(aux_contact)!=null){

                buff_recipients.add(token_recipients.get(i));
            }
        }


        aux_post.setRecipients(buff_recipients);

		results = daopost.QueryByRecipient(aux_post);
		
		break;
	
}





String nextPage = "Search";
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
		<h1>Results of the search</h1>
		<hr/>
		
		
	
		<table class = "table table-striped table-bordered" id="datatable">
			<thead>
				<tr class = "thead-dark">
					<th>ID POST</th>
					<th>Title</th>
					<th>Body</th>
				</tr>
			</thead>
			<tbody>
			
			<%	for(Post post : results){%>
			
					<tr>
						<td><%=post.getIdentifier()%></td>
						<td><%=post.getTitle()%></td>
						<td><%=post.getBody()%></td>
					</tr>
						<%}%>
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