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
		
	<title>Register</title>
	<link rel="stylesheet" href="mvc/css/main.css">
	<link rel="stylesheet" href="mvc/css/login-register.css">
	<link rel="stylesheet" href="mvc/css/validation.css">
	<link rel="stylesheet" href="https://necolas.github.io/normalize.css/8.0.1/normalize.css">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet"> 
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
    
String nextPage = "Register";
String messageNextPage = request.getParameter("message");
if (messageNextPage == null) messageNextPage = "";

if (ContactBean != null && !ContactBean.getEmail().equals("")) {
	//No debería estar aquí -> flujo salta a index.jsp
	nextPage = "index.jsp";
} else {
%>



   <main>
   


            <div class="form-register-with-email">


                    <div class="form-title-row">
                        <h1>Register</h1>
                    </div>
				   
					<p style="color:green">				   		
					<%
					if(request.getAttribute("successMsg")!=null)
					{
						out.println(request.getAttribute("successMsg")); //register success message
					}
					%>
					</p>
					
				

            </div>


			

		<form action="Register" class="form" id="form" method="post">

					
			<!-- Group: Firstname -->
			<div class="form__group" id="group__firstname">
				<label for="firstname" class="form__label">Firstname</label>
				<div class="form__group-input">
					<input type="text" class="form__input" name="firstname" id="fname" placeholder="enter firstname">
					<i class="form__validation-status fas fa-times-circle"></i>
				</div>
				<p class="form__input-error">The first name must have between 1 and 16 digits and can only contain letters, numbers and underscores.</p>
			</div>

			<!-- Group: Surname -->
			<div class="form__group" id="group__surname">
				<label for="surname" class="form__label">Surname</label>
				<div class="form__group-input">
					<input type="text" class="form__input" name="surname" id="lname" placeholder="enter last name">
					<i class="form__validation-status fas fa-times-circle"></i>
				</div>
				<p class="form__input-error">The surname must have between 1 and 16 digits and can only contain letters, numbers and underscores.</p>
			</div>
			
			
			<!-- Group: Email -->
			<div class="form__group" id="group__email">
				<label for="email" class="form__label">Email</label>
				<div class="form__group-input">
					<input type="email" class="form__input" name="email" id="email" placeholder="enter email (example@example.com)">
					<i class="form__validation-status fas fa-times-circle"></i>
				</div>
				<p class="form__input-error">The email must have an '@' and can only contain letters, numbers, points, dashes and underscores.</p>
			</div>
			
			
						<!-- group: Teléfono -->
			<div class="form__group" id="group__birthday">
				<label for="birthday" class="form__label">Birthday</label>
				<div class="form__group-input">
					<input type="date" class="form__input" name="birthday" id="birthday" placeholder="enter birthday">
					<i class="form__validation-status fas fa-times-circle"></i>
				</div>
				<p class="form__input-error">The birthday must have this format: (dd/MM/yyyy).</p>
			</div>
			

			<!-- group: Contraseña -->
			<div class="form__group" id="group__password">
				<label for="password" class="form__label">Password</label>
				<div class="form__group-input">
					<input type="password" class="form__input" name="password" id="password" placeholder="enter password">
					<i class="form__validation-status fas fa-times-circle"></i>
				</div>
				<p class="form__input-error">The password must be between 6 and 16 digits.</p>
			</div>

			<!-- group: Contraseña 2 -->
			<div class="form__group" id="group__password2">
				<label for="password2" class="form__label">Repeat Password</label>
				<div class="form__group-input">
					<input type="password" class="form__input" name="password2" id="password2" placeholder="repeat password">
					<i class="form__validation-status fas fa-times-circle"></i>
				</div>
				<p class="form__input-error">Both passwords must be the same one.</p>
			</div>
			
						<div class="form__group" id="group__interests">
				<label for="interests" class="form__label">Interests</label>
				<div class="form__group-input">
					<input type="text" class="form__input" name="interests" id="interests" placeholder="enter interests">
					<i class="form__validation-status fas fa-times-circle"></i>
				</div>
				<p class="form__input-error">Possible interests: <%=interests_%></p>
			</div> <br/>






			<!-- group: Terms and conditions -->
			<div class="form__group" id="group__terms">
				<label class="form__label">
					<input class="form__checkbox" type="checkbox" name="terms" id="terms">
					Accept terms and conditions
				</label>
			</div>

			<div class="form__message" id="form__message">
				<p><i class="fas fa-exclamation-triangle"></i> <b>Error:</b> Please fill the form correctly. </p>
			</div>
			
			
			<div class="form__group form__group-btn-send">
				<button type="submit" class="form__btn">Submit</button>
				<p class="form__message-success" id="form__message-success">Contact successfully registered</p>
				
			</div>
							
			
						
			<div class="form__group form__group-btn-send">
				<button type="reset" onclick="window.location.href='index.jsp';" class="form__btn">Back</button>
				
			</div>
			


	
		</form>
		
				<center>
            <div class="form-register-with-email">
			

					 <br/>
					 <br/><a href="Login" class="form-log-in-with-existing">Already have an account? <b> Login here </b></a>
					
					</div>
			
			</center>
		
		

         

   </main>
   
    
	<script src="javascript/validationForms.js"></script>
	<script src="https://kit.fontawesome.com/2c36e9b7b1.js"></script>
  
<%
}
%>

</body>
</html>