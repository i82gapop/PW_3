<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean  id="ContactBean" scope="session" class="es.uco.pw.business.display.javabean.ContactBean"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log in</title>
</head>
<body>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">


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
String nextPage = "Login";
		
String messageNextPage = request.getParameter("message");
int aux = ContactBean.getAttempt();
if (messageNextPage == null) messageNextPage = "";

if (ContactBean != null && !ContactBean.getEmail().equals("")) {
	//No debería estar aquí -> flujo salta a index.jsp
	nextPage = "index.jsp";
} else {
%>



    <div class="main-content">

        <form class="form-register" method="post" name="myform" action="Login">

            <div class="form-register-with-email">

                <div class="form-white-background">

                    <div class="form-title-row">
                        <h1>Login</h1>
                    </div>
					
					<p style="color:red">				   		
					<%
					if(request.getAttribute("errorMsg")!=null)
					{
						out.println(request.getAttribute("errorMsg")); //error message for email or password 
					}
					%>
					</p>
				   
	

                    <div class="form-row">
                        <label>
                            <span>Email</span>
                            <input type="text" name="email" placeholder="enter email">
                        </label>
                    </div>

                    <div class="form-row">
                        <label>
                            <span>Password</span>
                            <input type="password" name="password" placeholder="enter password">
                        </label>
                    </div>
                        <%= messageNextPage %><br/><br/>

					<input type="submit" name="btn_login" value="Submit">
					<input type="button" onclick="window.location.href='index.jsp';" value="Back" />
                    
                </div>

				<a href="Register" class="form-log-in-with-existing">You Don't have an account? <b> Register here </b></a>

            </div>

        </form>

    </div>


<%
}
%>
</body>


</html>

