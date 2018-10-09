/**
 * ResponseObj.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.co.mtn.bright;

import java.io.InputStream;

public class ATResponseObj implements java.io.Serializable {
    private java.lang.String docName;

    private java.lang.String docUrl;

    private java.lang.String id;
    private InputStream is;

   	public ATResponseObj() {
    }
     
    public ATResponseObj(
           String docName,
           String docUrl,
           String id) {
           this.docName = docName;
           this.docUrl = docUrl;
           this.id = id;
    }
    
    public ATResponseObj(
            String docName,
            String docUrl,
            String id,
            InputStream is) {
    	
            this.docName = docName;
            this.docUrl = docUrl;
            this.id = id;
            this.is = is;
     }
   
    /**
     * Gets the docName value for this ResponseObj.
     * 
     * @return docName
     */
    public String getDocName() {
        return docName;
    }


    /**
     * Sets the docName value for this ResponseObj.
     * 
     * @param docName
     */
    public void setDocName(String docName) {
        this.docName = docName;
    }


    /**
     * Gets the docUrl value for this ResponseObj.
     * 
     * @return docUrl
     */
    public String getDocUrl() {
        return docUrl;
    }


    /**
     * Sets the docUrl value for this ResponseObj.
     * 
     * @param docUrl
     */
    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }


    /**
     * Gets the id value for this ResponseObj.
     * 
     * @return id
     */
    public String getId() {
        return id;
    }


    /**
     * Sets the id value for this ResponseObj.
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

}
