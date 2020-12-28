package es.uco.pw.data.dao.contact;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import es.uco.pw.business.contact.Contact;



/**
 * A class to represent the DAO contact and its functions
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */

public class DAOContact{
	
	private String url;
	private String user;
	private String pwd;
	private Properties sqlprop;
	
	/**
	 * Empty (default) constructor
	 * */
	
	public DAOContact() {}
	
	/**
	 * Parameterized constructor
	 * @param url URL of the database
	 * @param user User name of the database
	 * @param pwd Password of the database
	 * @param sqlprop Properties file
	 * */
	
	public DAOContact(String url, String user, String pwd, Properties sqlprop) {
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
 * Function that saves a given contact to the database Contacts
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
			String statement = sqlprop.getProperty("Insert");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getEmail());
			ps.setString(2,contact.getName());
			ps.setString(3,contact.getSurname());
			ps.setDate(4,contact.getBirthday());
			ps.setString(5, contact.getPassword());
			
			status = ps.executeUpdate();
	
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	

/**
 * Function that updates a given contact in the database
 *
 * @param contact Contact to update
 * @return integer value, it represents the status of the action
 *
 **/
	public int Update(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("Update");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getName());
			ps.setString(2,contact.getSurname());
			ps.setDate(3,contact.getBirthday());
			ps.setString(4,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}



/**
 * Function that updates the password of a given contact in the database
 *
 * @param contact Contact which password is going to be updated
 * @return integer value, it represents the status of the action
 *
 **/

	public int UpdatePassword(Contact contact){
		
		int status=0;
		
		try{
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("UpdatePassword");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getPassword());
			ps.setString(2,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
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
			String statement = sqlprop.getProperty("Delete");
			
			PreparedStatement ps=con.prepareStatement(statement);
			ps.setString(1,contact.getEmail());
			status=ps.executeUpdate();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}


/**
 * Function that shows all the contact in the database
 *
 * @return ArrayList value, a list with all the contacts
 *
 **/
	
	public ArrayList <Contact> ListContacts(){

		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement =sqlprop.getProperty("ListContacts");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement);
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));

				resul.add(aux);
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
 * Function that queries a contact in the database from a given contact with the email to search for
 *
 * @param contact Contact to query
 * @return Contact, the contact with the full information
 *
 **/

	public Contact QueryByEmail (Contact contact){
		
		Statement stmt = null; 
		Contact resul = null;
		
		try {
			
			Connection con=getConnection();
			
			//FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			//sql_properties.load(sql_properties_file);
			String statement = sqlprop.getProperty("QueryByEmail");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getEmail() + "'");
		    
		    while (rs.next()) {
		    	
		    	resul = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));
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
 * Function that queries contacts in the database from a given contact with the name to search for
 *
 * @param contact Contact to query
 * @return ArrayList, a list of contacts with the given name
 *
 **/

	public ArrayList <Contact> QueryByName (Contact contact){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement =sqlprop.getProperty("QueryByName");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getName() + "'");
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));
		    	
				resul.add(aux);
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
 * Function that queries contacts in the database from a given contact with the surname to search for
 *
 * @param contact Contact to query
 * @return ArrayList, a list of contacts with the given surname
 *
 **/

	public ArrayList <Contact> QueryBySurname (Contact contact){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
		/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("QueryBySurname");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement + "'" + contact.getSurname() + "'");
		    
		    while (rs.next()) {
		    	
		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));

				resul.add(aux);
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
 * Function that queries contacts in the database from a given contact with the fullname to search for
 *
 * @param contact Contact to query
 * @return ArrayList , a list of contacts with the given fullname
 *
 **/

	public ArrayList <Contact> QueryByFullname (Contact contact){
		
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null; 

		try {
			
			Connection con=getConnection();

			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("QueryByFullname");
			
			PreparedStatement stmt = con.prepareStatement(statement);
			stmt.setString(1, contact.getName());
			stmt.setString(2, contact.getSurname());
			ResultSet rs = stmt.executeQuery();
			
		    while (rs.next()) {

		    	aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));

				resul.add(aux);
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
 * Function that queries contacts in the database from a given contact with the age to search for
 *
 * @param Age Age of the contact to query
 * @return ArrayList , a list of contacts with the given age
 *
 **/
	public ArrayList <Contact> QueryByAge (int Age){
		
		Statement stmt = null; 
		ArrayList <Contact> resul = new ArrayList <Contact>();
		Contact aux = null;

		try {
			
			Connection con=getConnection();
			
			/*FileInputStream sql_properties_file = new FileInputStream("sql.properties");
			sql_properties.load(sql_properties_file);*/
			String statement = sqlprop.getProperty("ListContacts");
			
			stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery(statement);
		    
		    while (rs.next()) {
		    	
				aux = new Contact(rs.getString("Name"), rs.getString("Surname"), rs.getDate("Birthday"), rs.getString("Email"), rs.getString("Password"));
				
				if(aux.getAge() == Age){

					resul.add(aux);
				}
		    }
		   
		    if (stmt != null) {
		    	
		    	stmt.close(); 
		    }
		    	
		} catch (Exception e) {
			System.out.println(e);
		} 
		return resul;
	}
}
