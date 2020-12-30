package es.uco.pw.business.display.javabean;

import java.util.ArrayList;
import es.uco.pw.business.contact.Contact;
import es.uco.pw.business.post.*;



    /**
     * The DTO Post of the database
     * @author Ruben Borrego Canovaca
     * @author Pedro Pablo Garcia Pozo
     * @since 4-11-2020
     * @version 2.0
     * */
    
    public class PostBean implements java.io.Serializable{
        private static final long serialVersionUID = 1L;

    
    //Attributes
    private int identifier;
    private String title="";
    private String body="";
    private Contact owner;
    private java.sql.Timestamp publication;
    private Status status;
    private Type type;
    private ArrayList <String> recipients;
    private ArrayList <String> interests;
    java.sql.Timestamp date_start;
    java.sql.Timestamp date_end;




    /**
     * Returns the id of a post
     *
     * @return ID of the post
     * */

    public int getIdentifier() {
        return identifier;
    }


    /**
  	 * Sets the id of a post
  	 *
  	 * @param identifier id of the post
  	 * */



    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
    
    /**
     * Returns the title of a post
     *
     * @return Title of the post
     * */

    public String getTitle() {
        return title;
    }



    /**
     * Sets the title of a post
     *
     * @param title Title of the post
     * */


    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Returns the body of a post
     *
     * @return Body of the post
     * */


    public String getBody() {
        return body;
    }


    /**
     * Sets the body of a post
     *
     * @param body body of the post
     * */


    public void setBody(String body) {
        this.body = body;
    }


    /**
     * Returns the owner of a post
     *
     * @return Owner of the post
     * */

    public Contact getOwner() {
        return owner;
    }

    /**
    * Sets the owner of a post
    *
    * @param owner owner of the post
    * */


    public void setOwner(Contact owner) {
        this.owner = owner;
    }


    /**
     * Returns the date of publication of a post
     *
     * @return Date of publication of the post
     * */

    public java.sql.Timestamp getPublication() {
        return publication;
    }


    /**
    * Sets the date of publication of a post
    *
    * @param publication Date of publication of the post
    * */



    public void setPublication(java.sql.Timestamp publication) {
        this.publication = publication;
    }


    /**
     * Returns the status of a post
     *
     * @return Status of the post
     * */



    public Status getStatus() {
        return status;
    }


    /**
     * Sets the status of a post
     *
     * @param status status of the post
     * */


    public void setStatus(Status status) {
        this.status = status;
    }

    /**
  	 * Returns the type of a post
  	 *
  	 * @return Type of the post
  	 * */

    public Type getType() {
        return type;
    }

    /**
     * Sets the type of a post
     *
     * @param type Type of the post
     * */


    public void setType(Type type) {
        this.type = type;
    }

    /**
  	 * Returns the recipients of an Individualized post
  	 *
  	 * @return Recipients of the Individualized post
  	 * */



    public ArrayList<String> getRecipients() {
        return recipients;
    }


    /**
     * Sets the recipients of an Individualized post
     *
     * @param recipients recipients of the Individualized post
     * */



    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }

    /**
  	 * Returns the interests of a Thematic post
  	 *
  	 * @return interests of the Thematic post
  	 * */


    public ArrayList<String> getInterests() {
        return interests;
    }


    /**
     * Sets the interests of an Thematic post
     *
     * @param interests interests of the Thematic post
     * */

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }


    /**
  	 * Returns the starting date of an flash post
  	 *
  	 * @return starting date of the flash post
  	 * */


    public java.sql.Timestamp getDate_start() {
        return date_start;
    }

    /**
     * Sets the starting date of a flash post
     *
     * @param date_start starting date of the flash post
     * */



    public void setDate_start(java.sql.Timestamp date_start) {
        this.date_start = date_start;
    }



    /**
     * Returns the ending date of an flash post
     *
     * @return ending date of the flash post
     * */



    public java.sql.Timestamp getDate_end() {
        return date_end;
    }


    /**
     * Sets the ending date of a flash post
     *
     * @param date_end ending date of the flash post
     * */



    public void setDate_end(java.sql.Timestamp date_end) {
        this.date_end = date_end;
    }
    
   }

    /**
       * Auxiliar functions to see the info of a post
       *
       * */

