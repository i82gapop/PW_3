package es.uco.pw.data.dao.interest;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.pw.business.contact.Contact;



/**
 * A class to represent the DAO interest and its functions
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */


public class DAOInterest{

	private String url;
	private String user;
	private String pwd;
	private Properties sqlprop;
	
	/**
	 * Empty (default) constructor
	 * */
	
	public DAOInterest() {}
	
	/**
	 * Parameterized constructor
	 * @param url URL of the database
	 * @param user User name of the database
	 * @param pwd Password of the database
	 * @param sqlprop Properties file
	 * */
	
	public DAOInterest(String url, String user, String pwd, Properties sqlprop) {
		this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.sqlprop = sqlprop;
	}

	/**
	 *  Function that returns the connection to the database
	 * @return Connection to the database
	 * @throws IOException no file found
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
 * Function that saves a given contact to the database CI
 *
 * @param contact Contact to add
 * @return integer value, it represents the status of the action
 *
 **/




    public int Save(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("InsertInterest");
            
            for (String string : contact.getInterests()) {

                PreparedStatement ps=con.prepareStatement(statement);
            
                ps.setString(1,contact.getEmail());
                ps.setString(2, string);
                
                status = ps.executeUpdate();
            }
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}






/**
 * Function that shows all the contacts in the database CI
 *
 * @return ArrayList value, a list with all the contacts with their interests
 *
 **/

    public ArrayList <String> ListInterests(){

		Statement stmt = null; 
		ArrayList <String> resul = new ArrayList <String>();

		try {
			
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("ListInterests");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement);
		    
		    while (rs.next()) {
		    	
				resul.add(rs.getString("Interest"));
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
 * Function that queries interests in the database from a given contact
 *
 * @param contact Contact to query
 * @return ArrayList , a list of interests the given contact has
 *
 **/

    public ArrayList <String> QueryInterestsByContact(Contact contact){

		Statement stmt = null; 
		ArrayList <String> resul = new ArrayList <String>();

		try {
			
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("QueryInterestByEmail");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement+ "'" + contact.getEmail() + "'");
		    
		    while (rs.next()) {
		    	
				resul.add(rs.getString("Interest"));
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
 * Function that deletes a given contact from the database
 *
 * @param contact Contact to delete
 * @return integer value, it represents the status of the action
 *
 **/
    public int Delete(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("DeleteInterests");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
    }
	
	


/**
 * Function that queries contacts in the database from a given list of interests to search for
 *
 * @param interests list of interests to search for
 * @return ArrayList , a list of contacts with the given list
 *
 **/


    public ArrayList<Contact> QueryByInterests(ArrayList <String> interests){

        Statement stmt = null; 
		ArrayList <Contact> contacts = new ArrayList <Contact>();
        Contact resul = null;

		try{

            Connection con = getConnection();
            
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
            String statement = sqlprop.getProperty("QueryByInterest");
            
            for (String string : interests) {

                stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(statement + "'" + string + "'");
                
                while (rs.next()) {

					boolean existence = false;//nuevo
		    	
                    resul = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));
                    
                    if(contacts.isEmpty()){

                        contacts.add(resul);
                    }

                    else{

						if(contacts.isEmpty()){//nuevo

							contacts.add(resul);
						}

						else{//nuevo

							for (Contact contact : contacts) {
                        
								if(contact.getEmail().equals(resul.getEmail())/* == false */){
			
									//contacts.add(resul);
									
									existence = true;//nuevo
								}
							}
							
							if(!existence){//nuevo
	
								contacts.add(resul);
							}
						}
                    }
                }
               
                if (stmt != null) {
                    
                    stmt.close(); 
                }
            }

		}catch(Exception e){System.out.println(e);}

        return contacts;
	}
}
