package es.uco.pw.business.display.javabean;


/**
 * A class to represent the contact bean implemented in the JSP
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */

public class ContactBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/* Attributes */
	
	private String email = "";
	private String password = "";
	private int attempt;

	
	
	
	
	/**
	 * Returns the email of a contact
	 *
	 * @return Email of the contact
	 * */

	
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email of a contact
	 *
	 * @param email Email of the contact
	 * */
	

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * Returns the password of a contact
	 *
	 * @return Password of the contact
	 * */
	
	
	public String getPassword() {
		return password;
		
	}
	
	/**
	 * Sets the password of a contact
	 *
	 * @param password Password of the contact
	 * */
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Returns the attempts of a contact
	 *
	 * @return Attempts of the contact
	 * */
	
	
	public int getAttempt() {
		return attempt;
	}
	
	/**
	 * Sets the attempts of a contact
	 *
	 * @param attempt Numer of attempts of the contact
	 * */
	
	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}
	
	
	
	
}
