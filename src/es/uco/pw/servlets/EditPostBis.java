package es.uco.pw.servlets;

import es.uco.pw.business.display.javabean.ContactBean;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
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
 * Servlet implementation class EditPostBis
 */
public class EditPostBis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPostBis() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sql_prop = request.getServletContext().getInitParameter("sqlprop");
		String nextPage = "index.jsp";
		ContactBean contactBean = (ContactBean)request.getSession().getAttribute("ContactBean");
		ServletContext application = getServletContext();
		
		if(contactBean == null || contactBean.getEmail().equals("")){
			
			nextPage = "index.jsp";
		}
		else{
			SimpleDateFormat format = new SimpleDateFormat("HH:mm/dd-MM-yyyy");
			
			nextPage = "/mvc/view/editPostBisView.jsp";
	        java.io.InputStream myIO = application.getResourceAsStream(sql_prop);
			java.util.Properties prop = new java.util.Properties();
			prop.load(myIO);
			
			DAOInterest daointerest = new DAOInterest(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
			DAOContact daocontact = new DAOContact(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
			ArrayList <String> interests_list = daointerest.ListInterests();
			DAOPost daopost = new DAOPost(request.getServletContext().getInitParameter("Url"), request.getServletContext().getInitParameter("User"), request.getServletContext().getInitParameter("Pwd"), prop);
			Post aux_post = new Post();
			Contact aux_contact = new Contact();
			if(request.getAttribute("id")!=null) {
				
				int aux_id = (int) request.getAttribute("id");
				System.out.println(aux_id);
			
		
		aux_post.setIdentifier(aux_id);
        
        aux_post = daopost.QueryByID(aux_post);
        request.setAttribute("id_post", aux_id);
        request.setAttribute("title_post", aux_post.getTitle());
        request.setAttribute("body_post", aux_post.getBody());
        request.setAttribute("type_post", aux_post.getType().toString());
			}
        

			else {
        String id_post = request.getParameter("id_post_");
		String titlePost = request.getParameter("_title");
		String bodyPost = request.getParameter("_body");
		String aux_interests = request.getParameter("_interests");
		String aux_recipients = request.getParameter("_recipients");
		String date_start_post = request.getParameter("_date_start");
		String date_end_post = request.getParameter("_date_end");
		int id_post_ = Integer.parseInt(id_post);
        
		if((aux_interests != null) && (aux_interests != "")) {
			StringTokenizer interests = new StringTokenizer(aux_interests.replace(" ", ""), ",");
	        ArrayList <String> token_interests = new ArrayList <String>();

	        while(interests.hasMoreTokens()){

	            token_interests.add(interests.nextToken().toUpperCase());
	        }
	    		for(int i = 0; i < token_interests.size(); i++){

	                if(interests_list.contains(token_interests.get(i))){

	                    aux_contact.addInterest(token_interests.get(i));
	                }
	            }
	    		aux_contact.setEmail(contactBean.getEmail());
	    		aux_post = new Post(id_post_, titlePost, bodyPost, aux_contact);
	    		aux_post.setInterests(aux_contact.getInterests());
	    		aux_post.setType(Type.THEMATIC);
	    		daopost.Update(aux_post);
	    		nextPage = "index.jsp";
	    		
	    		
	        
			
		}//end thematic
		
		else if((aux_recipients != null)&& (aux_recipients != "")) { //its an individualized post
			
			StringTokenizer recipients = new StringTokenizer(aux_recipients.replace(" ", ""), ",");
            ArrayList <String> token_recipients = new ArrayList <String>();
            ArrayList <String> buff_recipients = new ArrayList<String>();

            while(recipients.hasMoreTokens()){

                token_recipients.add(recipients.nextToken());
            }

            for(int i = 0; i < token_recipients.size(); i++){

                aux_contact.setEmail(token_recipients.get(i));

                if(daocontact.QueryByEmail(aux_contact)!=null){

                    buff_recipients.add(token_recipients.get(i));
                }
            }

    		aux_contact.setEmail(contactBean.getEmail());
    		aux_post = new Post(id_post_, titlePost, bodyPost, aux_contact);
            aux_post.setRecipients(buff_recipients);
            aux_post.setType(Type.INDIVIDUALIZED);

            daopost.Update(aux_post);
            nextPage = "index.jsp";

		
			
		}//end individualized
		
		else if((date_end_post != "") && (date_start_post != "") && (date_start_post != null) && (date_end_post != null)) {
			try {//its a flash
			java.sql.Timestamp date_start, date_end;
			java.util.Date date = new java.util.Date();
			
			date = format.parse(date_start_post);
			date_start = new java.sql.Timestamp(date.getTime());
			
				date = format.parse(date_end_post);

			date_end = new java.sql.Timestamp(date.getTime());

    		aux_contact.setEmail(contactBean.getEmail());
    		aux_post = new Post(id_post_, titlePost, bodyPost, aux_contact);
    		aux_post.setDate_start(date_start);
    		aux_post.setDate_end(date_end);
    		aux_post.setType(Type.FLASH);
    		
    		daopost.Update(aux_post);
    		nextPage = "index.jsp";
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			
		}//end flash
		
		else if((titlePost != null) && (bodyPost != null)){
    		aux_contact.setEmail(contactBean.getEmail());
    		aux_post = new Post(id_post_, titlePost, bodyPost, aux_contact);
    		aux_post.setType(Type.GENERAL);
    		daopost.Update(aux_post);
    		nextPage = "index.jsp";
			
			
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
