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
		
	<title>Create a Post</title>
	<link rel="stylesheet" href="mvc/css/main.css">
	<link rel="stylesheet" href="mvc/css/login-register.css">
	 <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet">
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
    
String nextPage = "CreatePost";
String messageNextPage = request.getParameter("message");
if (messageNextPage == null) messageNextPage = "";

if (ContactBean == null || ContactBean.getEmail().equals("")) {
	//No debería estar aquí -> flujo salta a index.jsp
	nextPage = "index.jsp";
} else {
%>



    <div class="main-content">

        <form action="CreatePost" class="form-register" method="post" id="form" >

            <div class="form-register-with-email">

                <div class="form-white-background">

                    <div class="form-title-row">
                        <h1>Create a Post</h1>

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
                            <input type = "button" value="General" onclick="general();">
                            <input type = "button" value="Thematic" onclick="thematic();">
                            <input type = "button" value="Individualized" onclick="individualized();">
                            <input type = "button" value="Flash" onclick="flash();">

                                <script type = "text/javascript" src="javascript/createPost.js">

                                </script>
                        </label>
                    </div>
				
					<div id="title" class="form-row" style="display:none">
                        <label>
                            <span>Title</span>
                            <input type="text" name="_title" id="_title" placeholder="enter Title">
                        </label>
                    </div>

                    <div id="body" class="form-row" style="display:none">
                        <label>
                            <span>Body</span>
                            <input type="text" name="_body" id="_body" placeholder="enter Body">
                        </label>
                    </div>
                    
                    <div id="interests" class="form-row" style="display:none">
                        <label>
                            <span>Interests</span>
                            <input type="text" name="_interests" id="_interests" placeholder="enter interests">
                        </label>
                        <h5>Possible interests: <%=interests_%></h5>
                        <%= messageNextPage %><br/><br/>
                    </div>

                    <div id = "recipients" class="form-row" style="display:none">
                        <label>
                            <span>Recipients</span>
                            <input type="text" name="_recipients" id="_recipients" placeholder="enter recipients">
                        </label>
                    </div>


                    <div id = "date_start" class="form-row" style="display:none" >
                        <label>
                            <span>Starting Date</span>
                            <input type="text" name="_date_start" id="_date_start" placeholder="(format: HH:mm/dd-MM-yyyy)">
                        </label>


                    </div>

                    <div id = "date_end" class="form-row" style="display:none">
                        <label>
                            <span>Ending Date</span>
                            <input type="text" name="_date_end" id="_date_end" placeholder="(format: HH:mm/dd-MM-yyyy)">
                        </label>

                    </div>


					<span style = "color: red;">${error} </span>
					<span style = "color: green;">${success} </span>	<br/>
					
					<input type="submit" name="btn_register" value="Submit">
					<input type="button" onclick="window.location.href='index.jsp';" value="Back" />
					<p class="warnings" id="warnings"></p>
					
					         <script src="javascript/createPostVali.js"></script>
					
                </div>
				

            </div>

        </form>
        


    </div>

  
<%
}
%>

</body>
</html>