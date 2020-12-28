package es.uco.pw.servlets;

import es.uco.pw.business.display.javabean.PostBean;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.sql.Date;
import es.uco.pw.data.dao.interest.DAOInterest;
import es.uco.pw.business.contact.Contact;
import es.uco.pw.data.dao.contact.DAOContact;


/**
 * Servlet implementation class Servlet
 */

public class CreatePost extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CreatePost() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String sql_prop = request.getServletContext().getInitParameter("sqlprop");
	String nextPage = "index.jsp";
	String mensajeNextPage = "";
	PostBean postBean = (PostBean)request.getSession().getAttribute("postBean");
	ServletContext application = getServletContext();
	
if(contactBean != null && !contactBean.getEmail().equals("")){
		
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
			
		contactBean contactobean = request.getSession().getAttribute("contactBean");
				
			nextPage = "index.jsp";
			mensajeNextPage = "";
			}
		}			
	RequestDispatcher disp = request.getRequestDispatcher(nextPage);
	disp.forward(request, response);



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
