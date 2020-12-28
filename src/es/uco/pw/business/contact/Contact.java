package es.uco.pw.business.contact;

import java.util.ArrayList;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A class to represent the DTO contact
 * @author Pedro Pablo Garcia Pozo
 * @author Ruben Borrego Canovaca
 * @since 4-11-2020
 * @version 2.0
 *
 * */

public class Contact {


	/* Attributes */

	private String name;
	private String surname;
	private Date birthday;
	private String email;
	private String password;
	ArrayList <String> interests = new ArrayList <String>();

	/**
	 * Empty (default) constructor
	 * */
	public Contact() {};


	/**
	 * Parameterized constructor
	 * @param name The name of the contact
	 * @param surname The surname of the contact
	 * @param birthday The birthday of the contact
	 * @param email The email of the contact
	 * @param password The password of the contact
	 * */


	public Contact(String name, String surname, Date birthday, String email, String password) {

		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.email = email;
		this.password = password;
	}



	/**
	 * Parameterized constructor
	 * @param name The name of the contact
	 * @param surname The surname of the contact
	 * @param birthday The birthday of the contact
	 * @param email The email of the contact
	 * @param password The password of the contact
	 * @param interests A list with the interests of a contact
	 * */

	public Contact(String name, String surname, Date birthday, String email, String password, ArrayList <String> interests) {

		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.email = email;
		this.password = password;
		this.interests = interests;
	}

	/**
	 * Returns the name of a contact
	 *
	 * @return Name of the contact
	 * */

	public String getName() {return name;}

	/**
	 * Returns the surname of a contact
	 *
	 * @return Surname of the contact
	 * */

	public String getSurname() {return surname;}

	/**
	 * Returns the Fullname of a contact
	 *
	 * @return Fullname of the contact
	 * */

	public String getFullname() {return name + " " + surname;}


	/**
	 * Returns the birthday of a contact
	 *
	 * @return Birthday of the contact
	 * */


	public Date getBirthday() {return birthday;}

	/**
	 * Returns the birthday of a contact in string format
	 *
	 * @return String of the birthday of the contact
	 * */

	public String getBirthdayString(){

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.format(birthday);
	}


	/**
	 * Returns the email of a contact
	 *
	 * @return Email of the contact
	 * */

	public String getEmail() {return email;}



	/**
	 * Returns the password of a contact
	 *
	 * @return Password of the contact
	 * */

	public String getPassword(){return password;}


	/**
	 * Returns the list of interests of a contact
	 *
	 * @return Interests of the contact
	 * */
	public ArrayList <String> getInterests(){return interests;}


	/**
	 * Calculates the age of a contact
	 *
	 * @return Age of the contact
	 * */

	public int getAge(){


		Calendar birth = Calendar.getInstance();
		Calendar actualDate = Calendar.getInstance();
		birth.setTime(birthday);

		int year = actualDate.get(Calendar.YEAR)- birth.get(Calendar.YEAR);
		int month = actualDate.get(Calendar.MONTH)- birth.get(Calendar.MONTH);
		int day = actualDate.get(Calendar.DATE)- birth.get(Calendar.DATE);

		if(month<0 || (month==0 && day<0)){
			year--;
		}

		return year;
	}

	/**
	 * Sets the name of a contact
	 *
	 * @param name Name of the contact
	 * */

	public void setName(String name) {this.name = name;}

	/**
	 * Sets the surname of a contact
	 *
	 * @param surname Surname of the contact
	 * */

	public void setSurname(String surname) {this.surname = surname;}

	/**
	 * Sets the birthday of a contact
	 *
	 * @param birthday Birthday of the contact
	 * */


	public void setBirthday(Date birthday) {this.birthday = birthday;}

	/**
	 * Sets the email of a contact
	 *
	 * @param email Email of the contact
	 * */


	public void setEmail(String email) {this.email = email;}


	/**
	 * Sets the password of a contact
	 *
	 * @param password Password of the contact
	 * */


	public void setPassword(String password){this.password = password;}

	/**
	 * Sets the list of interests of a contact
	 *
	 * @param interests List of interests of a contact
	 * */


	public void setInterest(ArrayList<String> interests){this.interests = interests;}



	/**
	 * Add an interest to a contact
	 *
	 * @param interest Interest to add to the contact
	 * @return boolean value, true if added, false if not
	 * */

	public boolean addInterest(String interest){

		if(interests.contains(interest)){

			return false;
		}

		interests.add(interest);
		return true;
	}

	/**
	 * Remove an interest to a contact
	 *
	 * @param interest Interest to remove to the contact
	 * @return boolean value, true if removed, false if not
	 * */

	public boolean removeInterest(String interest){

		if(interests.contains(interest)){

			interests.remove(interest);
			return true;
		}

		return false;
	}

	//@Override

	/**
	 * Auxiliar functions to see the info of a contact
	 *
	 * */

	public String toString(){

		return "Contact {Name: " + name + "; Surname: " + surname + "; Birthdate: " + birthday + "; Email: " + email + "; Interests: " + interests + "}";
	}
}
