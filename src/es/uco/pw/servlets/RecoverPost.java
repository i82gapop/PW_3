package es.uco.pw.servlets;

import es.uco.pw.business.display.javabean.ContactBean;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import es.uco.pw.data.dao.interest.DAOInterest;
import es.uco.pw.business.contact.Contact;
import es.uco.pw.data.dao.contact.DAOContact;
import es.uco.pw.business.post.Post;
import es.uco.pw.business.post.Type;
import es.uco.pw.data.dao.post.DAOPost;
/**
 * Servlet implementation class PostaPost
 */
public class RecoverPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecoverPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql_prop = request.getServletContext().getInitParameter("sqlprop");
		String nextPage = "index.jsp";
		ContactBean contactBean = (ContactBean)request.getSession().getAttribute("ContactBean");
		ServletContext application = getServletContext();
		
		if(contactBean == null || contactBean.getEmail().equals("")){
			
			nextPage = "index.jsp";
		}


	//Caso 2: its logged in
		else{
			
			nextPage = "/mvc/view/recoverPostView.jsp";
	        java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
			java.util.Properties prop = new java.util.Properties();
			prop.load(myIO);

			DAOPost daopost = new DAOPost(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);

			String action = request.getParameter("action");
			
			if(action == null) {
			} 
			else {
				recoverPost(request, response);
				
			}
			
	        
		}
		RequestDispatcher disp = request.getRequestDispatcher(nextPage);
		disp.forward(request, response);
		
	}
	
	
	private void recoverPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sql_prop = request.getServletContext().getInitParameter("sqlprop");
		ServletContext application = getServletContext();
        java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		DAOPost daopost = new DAOPost(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);

		
		String id = request.getParameter("id");
		int aux_id = Integer.parseInt(id);
		Post aux_post = new Post();
		aux_post.setIdentifier(aux_id);
		daopost.Recover(aux_post);
		

		}
		
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
