package es.uco.pw.data.dao.post;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.pw.business.contact.Contact;
import es.uco.pw.business.post.*;
import es.uco.pw.data.dao.contact.DAOContact;





/**
 * A class to represent the DAO post and its functions
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */





public class DAOPost{

	private String url;
	private String user;
	private String pwd;
	private Properties sqlprop;
	private DAOContact daoContact = new DAOContact();
	
	/**
	 * Empty (default) constructor
	 * */
	
    public DAOPost(){}

    
    /**
	 * Parameterized constructor
	 * @param url URL of the database
	 * @param user User name of the database
	 * @param pwd Password of the database
	 * @param sqlprop Properties file
	 * */
	
	public DAOPost(String url, String user, String pwd, Properties sqlprop) {
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.sqlprop = sqlprop;
	}
	
	/**
	 *  Function that returns the connection to the database
	 * @return Connection to the database
	 * @throws IOException if the file is not found
	 * */

    public Connection getConnection() throws IOException{

		//properties_file = new FileInputStream("properties.properties");

		//properties.load(properties_file);
    	Connection con=null;
		//final String url = properties.getProperty("Url");
		//final String user = properties.getProperty("User");
		//final String pwd = properties.getProperty("Pwd");
    	
		try {

			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pwd);
		} catch (final Exception e) {
			
			System.out.println(e);
		}
		
		return con;
	}
    
    
    /**
    * Function that saves a given post to the database Posts
    *
    * @param post Post to add
    * @return integer value, it represents the status of the action
    *
    **/

	public int Save(Post post){
		
        int status=0;
        
		try{
            
			Connection con=getConnection();
			
            
            if(post.getType().equals(Type.GENERAL)){

                String statement = sqlprop.getProperty("InsertGeneralPost");

                PreparedStatement ps=con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,"GENERAL");
                ps.setString(2,"EDITED");
                ps.setString(3,post.getTitle());
                ps.setString(4,post.getBody());
                ps.setString(5, post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("The post created will have assigned the ID: " + "<" + id + ">");                     
                    }

                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else if(post.getType().equals(Type.INDIVIDUALIZED)){
    
                String statement = sqlprop.getProperty("InsertIndividualizedPost");

                PreparedStatement ps=con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,"INDIVIDUALIZED");
                ps.setString(2,"EDITED");
                ps.setString(3,post.getTitle());
                ps.setString(4,post.getBody());
                ps.setString(5, post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("The post created will have assigned the ID: " + "<" + id + ">");
                        post.setIdentifier(id);
                        SaveBis(post);
                    }

                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else if(post.getType().equals(Type.THEMATIC)){
    
                String statement = sqlprop.getProperty("InsertThematicPost");

                PreparedStatement ps=con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,"THEMATIC");
                ps.setString(2,"EDITED");
                ps.setString(3,post.getTitle());
                ps.setString(4,post.getBody());
                ps.setString(5, post.getOwner().getEmail());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("The post created will have assigned the ID: " + "<" + id + ">");
                        post.setIdentifier(id);
                        SaveBis(post);
                    }

                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
    
            else{
                
                String statement = sqlprop.getProperty("InsertFlashPost");

                PreparedStatement ps=con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,"FLASH");
                ps.setString(2,"EDITED");
                ps.setString(3,post.getTitle());
                ps.setString(4,post.getBody());
                ps.setString(5, post.getOwner().getEmail());
                ps.setTimestamp(6, post.getDate_start());
                ps.setTimestamp(7, post.getDate_end());

                status = ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        int id;
                        id = generatedKeys.getInt(1);

                        System.out.println("The post created will have assigned the ID: " + "<" + id + ">");
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }  

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }    



    /**
     * Function that saves a given post to the database IP and CP
     *
     * @param post Post to add
     * @return integer value, it represents the status of the action
     *
     **/

    public int SaveBis(Post post){
		
        int status=0;
        
		try{
            
			Connection con=getConnection();
    
            if(post.getType().equals(Type.INDIVIDUALIZED)){
    
                String statement = sqlprop.getProperty("InsertPostRecipients");

                PreparedStatement ps=con.prepareStatement(statement);

                for (String string : post.getRecipients()) {
                    
                    ps.setString(1, string);
                    ps.setInt(2, post.getIdentifier());
    
                    status = ps.executeUpdate();
                }
            }
    
            else{
    
                String statement = sqlprop.getProperty("InsertPostInterests");

                PreparedStatement ps=con.prepareStatement(statement);
                
                for (String string : post.getInterests()) {
                    
                    ps.setString(1, string);
                    ps.setInt(2, post.getIdentifier());
    
                    status = ps.executeUpdate();
                }
            }

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }


    /**
     * Function that updates the status of a given post in the database
     *
     * @param post Post to update
     * @return integer value, it represents the status of the action
     *
     **/

    public int UpdateStatus(Post post){
		
        int status=0;
		
		try{
			Connection con=getConnection();
			
			String statement = sqlprop.getProperty("UpdateStatus");

            Status post_status = QueryByID(post).getStatus();

            if(!post_status.equals(Status.ARCHIVED) && !post_status.equals(post.getStatus())){//no esta archivado por lo que si se puede cambiar el estado

                if(post.getType().equals(Type.FLASH)){//if its flash

                    if(post.getStatus().equals(Status.POSTED)){//if you want to post it
    
                        if(post.getDate_start().after(new Timestamp(System.currentTimeMillis()))){//si todavia no ha llegado la fecha
    
                            PreparedStatement ps=con.prepareStatement(statement);
                            ps.setString(1,Status.WAITING.name());
                            ps.setInt(2,post.getIdentifier());
    
                            status=ps.executeUpdate();
                        }
    
                        else{//if start date has reached out
    
                            statement = sqlprop.getProperty("UpdateStatusPublication");
    
                            PreparedStatement ps=con.prepareStatement(statement);
                            ps.setString(1,post.getStatus().name());
                            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                            ps.setInt(3,post.getIdentifier());
    
                            status=ps.executeUpdate();
                        }
    
                    }
    
                    else{//if you don't want to post it
    
                        PreparedStatement ps=con.prepareStatement(statement);
                        ps.setString(1,post.getStatus().name());
                        ps.setInt(2,post.getIdentifier());
    
                        status=ps.executeUpdate();
                    }
                }
    
                else{//if it's not flash
    
                    if(post.getStatus().equals(Status.POSTED)){//if you want to post it
    
                        statement = sqlprop.getProperty("UpdateStatusPublication");
    
                        PreparedStatement ps=con.prepareStatement(statement);
                        ps.setString(1,post.getStatus().name());
                        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                        ps.setInt(3,post.getIdentifier());
    
                        status=ps.executeUpdate();
                    }
    
                    else{//if you don't want to post it
    
                        PreparedStatement ps=con.prepareStatement(statement);
                        ps.setString(1,post.getStatus().name());
                        ps.setInt(2,post.getIdentifier());
    
                        status=ps.executeUpdate();
                    }   
                }
            }

            else{
                
                System.out.println("You can't change the status of the post because it's already archived or has the same status");
            }            

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }
    

    /**
     * Function that queries a post in the database from a given post with the ID to search for
     *
     * @param post Post to query
     * @return Post, the post with the full information
     *
     **/

    public Post QueryByID(Post post){
        
        Statement stmt = null;
        Post resul = null;
        Contact capsule = new Contact();

        try {
            
            Connection con=getConnection();
            
            String statement = sqlprop.getProperty("QueryByID");
            
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement + post.getIdentifier());
            
            while (rs.next()) {

                capsule.setEmail(rs.getString("Owner"));
                
                resul = new Post(rs.getInt("ID"), rs.getString("Title"), rs.getString("Body"), daoContact.QueryByEmail(capsule));

                resul.setType(Type.valueOf(rs.getString("Type")));
                resul.setStatus(Status.valueOf(rs.getString("Status")));
                resul.setPublication(rs.getTimestamp("Publication"));
                resul.setDate_start(rs.getTimestamp("Start"));
                resul.setDate_end(rs.getTimestamp("End"));
            }
           
            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return resul;
    }
    

    /**
     * Function that queries a list of posts in the database from a given date of publication to search for
     *
     * @param post Post to query
     * @return results The ArrayList with the full information
     *
     **/

    public ArrayList <Post> QueryByDate(Post post){
        
        Statement stmt = null;
        ArrayList <Post> results = new ArrayList<Post>();
        Post resul = null;
        Contact capsule = new Contact();

        try {
            
            Connection con=getConnection();
            
            String statement = sqlprop.getProperty("QueryByDate");
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(post.getPublication());


            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement + "'" + date + "'");
            
            while (rs.next()) {

                capsule.setEmail(rs.getString("Owner"));
                
                resul = new Post(rs.getInt("ID"), rs.getString("Title"), rs.getString("Body"), daoContact.QueryByEmail(capsule)); 

                resul.setType(Type.valueOf(rs.getString("Type")));
                resul.setStatus(Status.valueOf(rs.getString("Status")));
                resul.setPublication(rs.getTimestamp("Publication"));
                resul.setDate_start(rs.getTimestamp("Start"));
                resul.setDate_end(rs.getTimestamp("End"));

                if(resul.getType().equals(Type.INDIVIDUALIZED)){

                    ArrayList <String> recipients = new ArrayList <String>();

                    recipients = SelectRecipients(resul);
                    resul.setRecipients(recipients);
                }

                else if(resul.getType().equals(Type.THEMATIC)){

                    ArrayList <String> interests = new ArrayList <String>();

                    interests = SelectInterests(resul);
                    resul.setInterests(interests);                    
                }

                results.add(resul);
            }
           
            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return results;
    }
    
    /**
     * Function that queries a list of posts in the database from a given owner to search for
     *
     * @param post Post to query
     * @return results The ArrayList with the full information
     *
     **/

    public ArrayList <Post> QueryByOwner(Post post){
        
        Statement stmt = null;
        ArrayList <Post> results = new ArrayList<Post>();
        Post resul = null;
        Contact capsule = new Contact();

        try {
            
            Connection con=getConnection();
            
            String statement = sqlprop.getProperty("QueryByOwner");
            
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement + "'" + post.getOwner().getEmail() + "'");
            
            while (rs.next()) {

                capsule.setEmail(rs.getString("Owner"));
                
                resul = new Post(rs.getInt("ID"), rs.getString("Title"), rs.getString("Body"), daoContact.QueryByEmail(capsule)); 

                resul.setType(Type.valueOf(rs.getString("Type")));
                resul.setStatus(Status.valueOf(rs.getString("Status")));
                resul.setPublication(rs.getTimestamp("Publication"));
                resul.setDate_start(rs.getTimestamp("Start"));
                resul.setDate_end(rs.getTimestamp("End"));

                if(resul.getType().equals(Type.INDIVIDUALIZED)){

                    ArrayList <String> recipients = new ArrayList <String>();

                    recipients = SelectRecipients(resul);
                    resul.setRecipients(recipients);
                }

                else if(resul.getType().equals(Type.THEMATIC)){

                    ArrayList <String> interests = new ArrayList <String>();

                    interests = SelectInterests(resul);
                    resul.setInterests(interests);
                }

                results.add(resul);
            }
           
            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return results;
    }
    
    /**
     * Function that queries a list of posts in the database from given interests to search for
     *
     * @param post Post to query
     * @return results The ArrayList with the full information
     *
     **/

    public ArrayList <Post> QueryByInterests(Post post){
        
        Statement stmt = null;
        ArrayList <Post> results = new ArrayList<Post>();
        Post resul = null;
        Contact capsule = new Contact();

        try {
            
            Connection con=getConnection();

            String statement = sqlprop.getProperty("QueryByInterests");
            
            stmt = con.createStatement();
            
        
            for (String string : post.getInterests()) {

                ResultSet rs = stmt.executeQuery(statement + "'" + string + "'");
                
                while (rs.next()) {

                    boolean existence = false;

                    capsule.setEmail(rs.getString("Owner"));

                    resul = new Post(rs.getInt("ID"), rs.getString("Title"), rs.getString("Body"), daoContact.QueryByEmail(capsule)); 

                    resul.setType(Type.valueOf(rs.getString("Type")));
                    resul.setStatus(Status.valueOf(rs.getString("Status")));
                    resul.setPublication(rs.getTimestamp("Publication"));
                    resul.setDate_start(rs.getTimestamp("Start"));
                    resul.setDate_end(rs.getTimestamp("End"));

                    ArrayList <String> interests = new ArrayList <String>();

                    interests = SelectInterests(resul);
                    resul.setInterests(interests);

                    for (Post aux_post : results) {
                        
                        if(aux_post.getIdentifier() == resul.getIdentifier()){

                            existence = true;
                        }
                    }

                    if(!existence){

                        results.add(resul);
                    }
                }
            }

            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return results;
    }

    /**
     * Function that queries a list of posts in the database from a given recipient to search for
     *
     * @param post Post to query
     * @return results The ArrayList with the full information
     *
     **/
    
    public ArrayList <Post> QueryByRecipient(Post post){//comprobar
        
        Statement stmt = null;
        ArrayList <Post> results = new ArrayList<Post>();
        Post resul = null;
        Contact capsule = new Contact();

        try {
            
            Connection con=getConnection();
            
            String statement = sqlprop.getProperty("QueryByRecipients");
            
            stmt = con.createStatement();
            
        
            for (String string : post.getRecipients()) {

                ResultSet rs = stmt.executeQuery(statement + "'" + string + "'");
                
                while (rs.next()) {

                    capsule.setEmail(rs.getString("Owner"));

                    resul = new Post(rs.getInt("ID"), rs.getString("Title"), rs.getString("Body"), daoContact.QueryByEmail(capsule)); 

                    resul.setType(Type.valueOf(rs.getString("Type")));
                    resul.setStatus(Status.valueOf(rs.getString("Status")));
                    resul.setPublication(rs.getTimestamp("Publication"));
                    resul.setDate_start(rs.getTimestamp("Start"));
                    resul.setDate_end(rs.getTimestamp("End"));
                    resul.setRecipients(SelectRecipients(resul));

                    results.add(resul);
                }
            }

            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return results;
    }
    
    /**
     * Function that queries a list of recipients in the database from a given post id to search for
     *
     * @param post Post to query
     * @return recipients The ArrayList with the recipients of the post
     *
     **/

    public ArrayList <String> SelectRecipients(Post post){
        
        Statement stmt = null;
        ArrayList <String> recipients = new ArrayList<String>();

        try {
            
            Connection con=getConnection();

            String statement = sqlprop.getProperty("SelectRecipients");
            
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement + post.getIdentifier());
            
            while (rs.next()) {

                recipients.add(rs.getString("Email"));
            }
           
            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return recipients;
    }
    
    /**
     * Function that queries a list of interests in the database from a given post id to search for
     *
     * @param post Post to query
     * @return interests The ArrayList with the interests of the post
     *
     **/
    
    public ArrayList <String> SelectInterests(Post post){
        
        Statement stmt = null;
        ArrayList <String> interests = new ArrayList<String>();

        try {
            
            Connection con=getConnection();

            String statement = sqlprop.getProperty("SelectInterests");
            
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(statement + post.getIdentifier());
            
            while (rs.next()) {

                interests.add(rs.getString("Interest"));
            }
           
            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return interests;
    }




    /**
     * Function that deletes a given post from the database IP
     *
     * @param post Post to delete
     * @return integer value, it represents the status of the action
     *
     **/

    public int DeleteInterests(Post post){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			String statement = sqlprop.getProperty("DeletePostInterests");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setInt(1,post.getIdentifier());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
    }


    /**
     * Function that deletes a given post from the database CP
     *
     * @param post Post to delete
     * @return integer value, it represents the status of the action
     *
     **/

    public int DeleteRecipients(Post post){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			String statement = sqlprop.getProperty("DeletePostRecipients");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setInt(1,post.getIdentifier());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
    }



    /**
     * Function that updates a given post in the database
     *
     * @param post Post to update
     * @return integer value, it represents the status of the action
     *
     **/

    public int Update(Post post){
		
        int status=0;
        
		try{
            
			Connection con=getConnection();        

            if(post.getType().equals(Type.GENERAL)){

                String statement = sqlprop.getProperty("UpdateGeneralPost");

                PreparedStatement ps=con.prepareStatement(statement);

                ps.setString(1,post.getTitle());
                ps.setString(2,post.getBody());
                ps.setInt(3, post.getIdentifier());

                status = ps.executeUpdate();
            }
    
            else if(post.getType().equals(Type.INDIVIDUALIZED)){
    
                String statement = sqlprop.getProperty("UpdateIndividualizedPost");

                PreparedStatement ps=con.prepareStatement(statement);
                ps.setString(1,post.getTitle());
                ps.setString(2,post.getBody());
                ps.setInt(3, post.getIdentifier());

                status = ps.executeUpdate();
                DeleteRecipients(post);
                SaveBis(post);

            }
    
            else if(post.getType().equals(Type.THEMATIC)){
    
                String statement = sqlprop.getProperty("UpdateThematicPost");

                PreparedStatement ps=con.prepareStatement(statement);
                ps.setString(1,post.getTitle());
                ps.setString(2,post.getBody());
                ps.setInt(3, post.getIdentifier());

                status = ps.executeUpdate();
                DeleteInterests(post);
                SaveBis(post);

            }
    
            else{
                
                String statement = sqlprop.getProperty("UpdateFlashPost");

                PreparedStatement ps=con.prepareStatement(statement);

                ps.setString(1,post.getTitle());
                ps.setString(2,post.getBody());
                ps.setTimestamp(3, post.getDate_start());
                ps.setTimestamp(4, post.getDate_end());
                ps.setInt(5,post.getIdentifier());

                status = ps.executeUpdate();

            }  

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }
    
    /**
     * Function that updates all waiting flash posts in the database that need to update to posted state
     *
     * @param post Post with actual date and time
     * @return integer value, it represents the status of the action
     *
     **/
    
    
    public int UpdateFlashStart(Post post){
		
        int status=0;
        
		try{
            
			Connection con=getConnection();

            String statement = sqlprop.getProperty("WaitingToPost");

            PreparedStatement ps=con.prepareStatement(statement);
            
            ps.setTimestamp(1,post.getPublication());
            ps.setTimestamp(2,post.getPublication());

            status = ps.executeUpdate();

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }
    
    /**
     * Function that updates all posted flash posts in the database that need to update to archived state
     *
     * @param post Post with actual date and time
     * @return integer value, it represents the status of the action
     *
     **/

    public int UpdateFlashEnd(Post post){
		
        int status=0;
        
		try{
            
			Connection con=getConnection();

            String statement = sqlprop.getProperty("PostToArchived");

            PreparedStatement ps=con.prepareStatement(statement);
            
            ps.setTimestamp(1,post.getPublication());
            ps.setTimestamp(2,post.getPublication());

            status = ps.executeUpdate();

		}catch(Exception e){System.out.println(e);}
		
		return status;
    }
    
    /**
     * Function that list all posts sorting them by owner email asc
     *
     * @return results The ArrayList with the posts
     *
     **/

    public ArrayList <Post> OrderByOwner(){
        
        Statement stmt = null;
        ArrayList <Post> results = new ArrayList<Post>();
        Post resul = null;
        Contact capsule = new Contact();

        try {
            
            Connection con=getConnection();

            String statement = sqlprop.getProperty("OrderByOwner");
            
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(statement);
            
            while (rs.next()) {

                boolean existence = false;

                capsule.setEmail(rs.getString("Owner"));

                resul = new Post(rs.getInt("ID"), rs.getString("Title"), rs.getString("Body"), daoContact.QueryByEmail(capsule)); 

                resul.setType(Type.valueOf(rs.getString("Type")));
                resul.setStatus(Status.valueOf(rs.getString("Status")));
                resul.setPublication(rs.getTimestamp("Publication"));
                resul.setDate_start(rs.getTimestamp("Start"));
                resul.setDate_end(rs.getTimestamp("End"));

                ArrayList <String> array = new ArrayList <String>();

                if(resul.getType().equals(Type.INDIVIDUALIZED)){

                    array = SelectRecipients(resul);
                    resul.setRecipients(array);
                }

                else if(resul.getType().equals(Type.THEMATIC)){

                    array = SelectInterests(resul);
                    resul.setInterests(array);
                }


                if(!existence){

                    results.add(resul);
                }
            }
            

            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return results;
    }
    
    /**
     * Function that list all posts sorting them by publication date desc
     *
     * @return results The ArrayList with the posts
     *
     **/

    public ArrayList <Post> OrderByDate(){
        
        Statement stmt = null;
        ArrayList <Post> results = new ArrayList<Post>();
        Post resul = null;
        Contact capsule = new Contact();

        try {
            
            Connection con=getConnection();

            String statement = sqlprop.getProperty("OrderByDate");
            
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(statement);
            
            while (rs.next()) {

                boolean existence = false;

                capsule.setEmail(rs.getString("Owner"));

                resul = new Post(rs.getInt("ID"), rs.getString("Title"), rs.getString("Body"), daoContact.QueryByEmail(capsule)); 

                resul.setType(Type.valueOf(rs.getString("Type")));
                resul.setStatus(Status.valueOf(rs.getString("Status")));
                resul.setPublication(rs.getTimestamp("Publication"));
                resul.setDate_start(rs.getTimestamp("Start"));
                resul.setDate_end(rs.getTimestamp("End"));

                ArrayList <String> array = new ArrayList <String>();

                if(resul.getType().equals(Type.INDIVIDUALIZED)){

                    array = SelectRecipients(resul);
                    resul.setRecipients(array);
                }

                else if(resul.getType().equals(Type.THEMATIC)){

                    array = SelectInterests(resul);
                    resul.setInterests(array);
                }


                if(!existence){

                    results.add(resul);
                }
            }
            

            if (stmt != null) {
                
                stmt.close();
            }
                
        } catch (Exception e) {
            System.out.println(e);
        } 

        return results;
    }
}

