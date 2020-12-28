<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="es.uco.pw.business.contact.Contact,es.uco.pw.data.dao.contact.DAOContact, es.uco.pw.data.dao.interest.DAOInterest" %>
<jsp:useBean  id="ContactBean" scope="session" class="es.uco.pw.business.display.javabean.ContactBean"></jsp:useBean>

<%@page import = "java.util.ArrayList"%>
<%@page import = "java.text.SimpleDateFormat"%>
<%@page import = "java.util.StringTokenizer" %>
<%@page import = "java.sql.Date"%>



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
	
if(ContactBean != null && !ContactBean.getEmail().equals("")){
		
		nextPage = "index.jsp";
	 	mensajeNextPage = "You are already logged in.";
	}


//Caso 2: No esta logeado
else{
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	nextPage = "/mvc/view/registerView.jsp";
	String emailUser = request.getParameter("email");
	String passwordUser = request.getParameter("password");

	
	if (emailUser != null) {
        java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		DAOInterest daointerest = new DAOInterest(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
		ArrayList <String> interests_list = daointerest.ListInterests();
		
		
		String nameUser = request.getParameter("name");
		String surnameUser = request.getParameter("surname");
		String birthdayUser = request.getParameter("birthday");
		emailUser = request.getParameter("email");
		passwordUser = request.getParameter("password");
		String aux_interests = request.getParameter("interests");
		
		StringTokenizer interests = new StringTokenizer(aux_interests.replace(" ", ""), ",");
        ArrayList <String> token_interests = new ArrayList <String>();

        while(interests.hasMoreTokens()){

            token_interests.add(interests.nextToken().toUpperCase());
        }

        
        java.util.Date date = new java.util.Date();
        date = format.parse(birthdayUser); 
        java.sql.Date sql_date = new java.sql.Date(date.getTime());
        
        
		

		
		DAOContact daocontact = new DAOContact(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
		

		Contact aux_contact = new Contact(nameUser, surnameUser, sql_date, emailUser, passwordUser);
        
		for(int i = 0; i < token_interests.size(); i++){

            if(interests_list.contains(token_interests.get(i))){

                aux_contact.addInterest(token_interests.get(i));
            }
        }

		
		daointerest.Save(aux_contact);
		
		daocontact.Save(aux_contact);
		
		%>
		<jsp:setProperty property="email" value="<%=emailUser%>" name="ContactBean"/>
		
		<jsp:setProperty property="password" value="<%=passwordUser%>" name="ContactBean"/>

		<%
		
		nextPage = "index.jsp";
		mensajeNextPage = "";
		
		}


}
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>