<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="ContactBean" scope="session" class="es.uco.pw.business.display.javabean.ContactBean"></jsp:useBean>

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

</head>
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
	    

<center>
    <h1>Welcome, <jsp:getProperty property="email" name="ContactBean"/>!!</h1>
				
	<a href="Logout">Log Out</a>
	<a href="Modify">Modify your profile</a>
	
	
	
	</center>
	
<% } %>
</body>
</html>