<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="es.uco.pw.business.contact.Contact,es.uco.pw.data.dao.contact.DAOContact" %>
<jsp:useBean  id="ContactBean" scope="session" class="es.uco.pw.business.display.javabean.ContactBean"></jsp:useBean>
<%
/* Posibles flujos:
	1) customerBean está logado (!= null && != "") -> Se redirige al index.jsp
	2) customerBean no está logado:
		a) Hay parámetros en el request  -> procede de la vista 
		b) No hay parámetros en el request -> procede de otra funcionalidad o index.jsp
	*/
//Caso 1: Esta logeado, vuelve al index
	

	String sql_prop = request.getServletContext().getInitParameter("sqlprop");

		
		
	String nextPage = "index.jsp";
	String mensajeNextPage = "";
	


//Caso 2: No esta logeado
if (ContactBean == null || ContactBean.getEmail().equals("")) {
	String emailUser = request.getParameter("email");
	String passwordUser = request.getParameter("password");

	//Caso 2.a: Hay parámetros -> procede de la VISTA
	if (emailUser != null) {
		//Se accede a bases de datos para obtener el usuario
		Contact user = new Contact();
		
		java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		DAOContact daocontact = new DAOContact(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
		
		user.setEmail(emailUser);
		Contact aux_user = daocontact.QueryByEmail(user);

		//Se realizan todas las comprobaciones necesarias del dominio
		//Aquí sólo comprobamos que exista el usuario
		if (aux_user != null && aux_user.getPassword().equals(passwordUser)) {
			// Usuario válido		
%>
<jsp:setProperty property="email" value="<%=emailUser%>" name="ContactBean"/>
<jsp:setProperty property="password" value="<%=passwordUser%>" name="ContactBean"/>
<%
		}

		else {
			// Usuario no válido
			int attempt = ContactBean.getAttempt();
			attempt--;
			if(attempt + 1== 0){
				nextPage = "/mvc/controller/redirectController.jsp";
				
			}
			else{
				
				
			nextPage = "/mvc/view/loginView.jsp";
			mensajeNextPage = "Login credentials' incorrect. Remaining attempts: " + ContactBean.getAttempt();
			ContactBean.setAttempt(attempt);
			}
		}
	//Caso 2.b -> se debe ir a la vista por primera vez
	} else {
		ContactBean.setAttempt(2);
		nextPage = "/mvc/view/loginView.jsp";
	}
}
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>