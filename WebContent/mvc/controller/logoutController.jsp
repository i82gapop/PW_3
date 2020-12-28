<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="ContactBean" scope="session" class="es.uco.pw.business.display.javabean.ContactBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<body>


<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>Log out user</title>
		
	<link rel="stylesheet" href="mvc/css/main.css">
	<link rel="stylesheet" href="mvc/css/login-register.css">
	</head>
	
<%
//Se comprueba primero que el usuario no está logado
if (ContactBean.getEmail()=="" || ContactBean == null) { %>
The user was already logged in. <br/>
<% } else {

	// Para desconectar al usuario, se accede al método removeAttribute() de la sesión)
	request.getSession().removeAttribute("ContactBean");
} %>
<center>
<div class="main-content">

            <div class="form-register-with-email">

                <div class="form-white-background">
                    <div class="form-title-row">
                        <h1> logged out.</h1>
                    </div>
					
					<p style="color:red">	
	<input type="button" onclick="window.location.href='index.jsp';" value="Back" />	
	
		</div>
	</div>
</div>
</center>


</body>
</html>