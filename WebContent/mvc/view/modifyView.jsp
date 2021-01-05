<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import ="es.uco.pw.data.dao.interest.DAOInterest" %>
<%@page import = "java.util.ArrayList"%>
<jsp:useBean  id="ContactBean" scope="session" class="es.uco.pw.business.display.javabean.ContactBean"></jsp:useBean>

<!DOCTYPE html>
<html>

<body>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
		
	<title>Modify</title>
	<link rel="stylesheet" href="mvc/css/main.css">
	<link rel="stylesheet" href="mvc/css/login-register.css">
	</head>
<%
/* Posibles flujos:
	1) customerBean está logado (!= null && != "") -> Se redirige al index.jsp (no debería estar aquí pero hay que comprobarlo)
	2) customerBean no está logado:
		a) Hay parámetros en el request  -> procede del controlador /con mensaje 
		b) No hay parámetros en el request -> procede del controlador /sin mensaje
	*/
	
	String sql_prop = request.getServletContext().getInitParameter("sqlprop");
    java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
	java.util.Properties prop = new java.util.Properties();
	prop.load(myIO);

	DAOInterest daointerest = new DAOInterest(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
	ArrayList <String> interests_ = daointerest.ListInterests();
	
String nextPage = "Modify";
String messageNextPage = request.getParameter("message");
if (messageNextPage == null) messageNextPage = "";

if (ContactBean == null || ContactBean.getEmail().equals("")) {
	nextPage = "index.jsp";
} else {
%>


<div class="main-content">
        <form action="Modify" class="form-register" method="post" >

            <div class="form-register-with-email">

                <div class="form-white-background">

                    <div class="form-title-row">
                        <h1>Fill with new information</h1>
                    </div>
				   
					<p style="color:green">				   		
					<%
					if(request.getAttribute("successMsg")!=null)
					{
						out.println(request.getAttribute("successMsg")); //register success message
					}
					%>
					</p>
				   
				
				   
                    <div class="form-row">
                        <label>
                            <span>Firstname</span>
                            <input type="text" name="name" id="fname" placeholder="enter firstname">
                        </label>
                    </div>
					<div class="form-row">
                        <label>
                            <span>Surname</span>
                            <input type="text" name="surname" id="lname" placeholder="enter lastname">
                        </label>
                    </div>

                    <div class="form-row">
                        <label>
                            <span>Birthday</span>
                            <input type="text" name="birthday" id="birthday" placeholder="(format: dd-MM-yyyy)">
                        </label>
                    </div>
                    

                    <div class="form-row">
                        <label>
                            <span>Interests</span>
                            <input type="text" name="interests" id="interests" placeholder="enter interests split by commas">
                        </label>
                    </div>
                    <h5>Possible interests: <%=interests_%></h5>
                    <%= messageNextPage %><br/><br/>
                    

					<input type="submit" name="btn_register" value="Submit">
					<input type="button" onclick="window.location.href='index.jsp';" value="Back" />
					
                </div>
				

            </div>

        </form>

    </div>

  
<%
}
%>

</body>
</html>


