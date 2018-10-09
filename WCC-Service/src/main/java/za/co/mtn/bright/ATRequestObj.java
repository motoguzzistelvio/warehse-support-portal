package za.co.mtn.bright;

import java.io.InputStream;

public class ATRequestObj  implements java.io.Serializable {
 
	private String docAccount;

    private String docName;

    private String docTitle;

    private String docType;

    private String securityGroup;
    
    private String filename;
    
    private String id;

 	private byte[] fileContents;
    
    private InputStream is;
    
	public ATRequestObj() {
    }

    public ATRequestObj(
           String docAccount,
           String docName,
           String docTitle,
           String docType,
           String securityGroup,
           String filename,
           byte[] fileContents) {
           this.docAccount = docAccount;
           this.docName = docName;
           this.docTitle = docTitle;
           this.docType = docType;
           this.securityGroup = securityGroup;
           this.filename = filename;
           this.fileContents = fileContents;
    }
    public ATRequestObj(
            String docAccount,
            String docName,
            String docTitle,
            String docType,
            String securityGroup) {
            this.docAccount = docAccount;
            this.docName = docName;
            this.docTitle = docTitle;
            this.docType = docType;
            this.securityGroup = securityGroup;
    }
    public ATRequestObj(
            String docAccount,
            String docName,
            String docTitle,
            String docType,
            String securityGroup,
            String filename) {
            this.docAccount = docAccount;
            this.docName = docName;
            this.docTitle = docTitle;
            this.docType = docType;
            this.securityGroup = securityGroup;
            this.filename = filename;
    }
    public String getDocAccount() {
 		return docAccount;
 	}

 	public void setDocAccount(String docAccount) {
 		this.docAccount = docAccount;
 	}

 	public String getDocName() {
 		return docName;
 	}

 	public void setDocName(String docName) {
 		this.docName = docName;
 	}

 	public String getDocTitle() {
 		return docTitle;
 	}

 	public void setDocTitle(String docTitle) {
 		this.docTitle = docTitle;
 	}

 	public String getDocType() {
 		return docType;
 	}

 	public void setDocType(String docType) {
 		this.docType = docType;
 	}

 	public String getSecurityGroup() {
 		return securityGroup;
 	}

 	public void setSecurityGroup(String securityGroup) {
 		this.securityGroup = securityGroup;
 	}

 	public byte[] getFileContents() {
 		return fileContents;
 	}

 	public void setFileContents(byte[] fileContents) {
 		this.fileContents = fileContents;
 	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

    public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
