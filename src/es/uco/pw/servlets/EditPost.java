package es.uco.pw.servlets;

import es.uco.pw.business.display.javabean.ContactBean;
import java.io.IOException;


import javax.servlet.*;
import javax.servlet.http.*;
import es.uco.pw.business.post.Post;
import es.uco.pw.business.post.Status;
import es.uco.pw.data.dao.post.DAOPost;
/**
 * Servlet implementation class PostaPost
 */
public class EditPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPost() {
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
			
			nextPage = "/mvc/view/editPostView.jsp";
	        java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
			java.util.Properties prop = new java.util.Properties();
			prop.load(myIO);
			

			
			Post aux_post = new Post();
			
			
			String aux_id = request.getParameter("_id");
			if(aux_id != null && aux_id != "") {
				int idPost = Integer.parseInt(aux_id.trim());
                aux_post.setIdentifier(idPost);
                DAOPost daopost = new DAOPost(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);

                if(daopost.QueryByID(aux_post) != null) {

                    if(daopost.QueryByID(aux_post).getOwner().getEmail().equals(contactBean.getEmail())){
                    	
                    	if(daopost.QueryByID(aux_post).getStatus() == Status.EDITED) {
                    		request.setAttribute("id", idPost);
                    		nextPage = "EditPostBis";
                    
                    	}
                    	
                    	else {
                        	request.setAttribute("error", "The post is not in an EDITED Status");
                        	RequestDispatcher disp = request.getRequestDispatcher(nextPage);
                        	disp.include(request, response); 
                    	}
                    }

                    else{
                    	request.setAttribute("error", "You are not the owner of that post");
                    	RequestDispatcher disp = request.getRequestDispatcher(nextPage);
                    	disp.include(request, response); 

                    }
                }

                else {
                	request.setAttribute("error", "That ID doesn't exist");
                	RequestDispatcher disp = request.getRequestDispatcher(nextPage);
                	disp.include(request, response); 
                }
				
			}
			
			

			
			

			
			
			
		}
		RequestDispatcher disp = request.getRequestDispatcher(nextPage);
		disp.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
