package za.co.mtn.bright;

import oracle.stellent.ridc.IdcClientManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;

public class WccCheckin {

	private String machineName;
	private String port;
	private String env;
	private String lbUrl;
	
	private String usr;
	private String password;
	private String dID;
	private IdcContext idcContext;
	
	@SuppressWarnings("rawtypes")
	private IdcClient idcClient;
	
	@SuppressWarnings("rawtypes")
	public IdcClient getIdcClient() {
		return idcClient;
	}
	@SuppressWarnings("rawtypes")
	public void setIdcClient(IdcClient idcClient) {
		this.idcClient = idcClient;
	}
	public IdcContext getIdcContext() {
		return idcContext;
	}
	public void setIdcContext(IdcContext idcContext) {
		this.idcContext = idcContext;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public WccCheckin(String machineName,
							String usr,
							String password,
							Integer dID,
							String env) {
		this.usr = usr;
		this.machineName = machineName;
		this.password = password;
		this.dID = dID.toString();
		this.env = env;
		
		setEnvString();
				
	}
	public WccCheckin(String machineName,
			String usr,
			String password,
			String env) {
		this.usr = usr;
		this.machineName = machineName;
		this.password = password;
		this.env = env;
		
		setEnvString();
		
	}
	
	public WccCheckin() {
		
	}
	
	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getdID() {
		return dID;
	}

	public void setdID(String dID) {
		this.dID = dID;
	}
	public String getPort() {
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getLbUrl() {
		return lbUrl;
	}
	public void setLbUrl(String lbUrl) {
		this.lbUrl = lbUrl;
	}

	@SuppressWarnings("deprecation")
	public InputStream getFile(ATRequestObj req) {
		
		if (getIdcClient()==null) {
			setIdcClient(getUCMConnection("idc://".concat(getMachineName())
												.concat(":4444")));
		}
		
		DataBinder dataBinder = getIdcClient().createBinder ();
	    dataBinder.putLocal ("IdcService", "GET_FILE");
	    dataBinder.putLocal ("dID", req.getId());
	    
	    if(getIdcContext() == null) {
	    	setIdcContext(new IdcContext(getUsr(), getPassword()));
	    }
	    ServiceResponse response = null;
		try {
			response = getIdcClient().sendRequest (getIdcContext(), dataBinder);
			//String contentType = response.getHeader("Content-Type");
			
		} catch (IdcClientException e) {
			e.printStackTrace();
		}
	   
	    return response.getResponseStream();
	}
	
	@SuppressWarnings("deprecation")
	public ATResponseObj checkinWCC (ATRequestObj req) {
		
		ATResponseObj respObj = null;
		
		if (getIdcClient()==null) {
			setIdcClient(getUCMConnection("idc://".concat(getMachineName())
												.concat(":4444")));
		}
				
		// build a client that will communicate using the intradoc protocol
		
		if (getIdcContext()==null) {
			setIdcContext(new IdcContext(getUsr(), getPassword()));
		}
						
		// get the binder
		 DataBinder binder = getIdcClient().createBinder();
		
		// populate the binder with the parameters
		 binder.putLocal ("IdcService", "CHECKIN_UNIVERSAL");

		// get the binder
		
		binder.putLocal ("dDocTitle", req.getDocTitle());
		binder.putLocal ("dDocName", req.getDocName());
		binder.putLocal ("dDocType", req.getDocType());
		binder.putLocal ("dSecurityGroup", req.getSecurityGroup());
		binder.putLocal ("dDocAccount", req.getDocAccount());
									
     	TransferFile  tf = null;
			
		try {
			if (req.getFilename() == null) {
				throw new IOException("A filename must be passed into WCC Checkin");
			}
			
			File doc = new CreateWCCFile().writeFile(req.getIs(), req.getFilename());
			tf = new TransferFile(doc, req.getFilename());
			binder.addFile ("primaryFile", tf);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// execute the request
		DataObject dataObject = null;	
		ServiceResponse response = null;
		
		try {
			response = idcClient.sendRequest (getIdcContext(), binder);
			binder = response.getResponseAsBinder();
			dataObject = binder.getLocalData();
			
			req.setDocName(dataObject.get("dDocName"));
			req.setId(dataObject.get("dID"));
			respObj = new ATResponseObj(req.getDocName(),
					                        null,
											req.getId());
			
			//wait for the doc to load
			if (docReleased(req)) {
				System.out.println("file loaded .....");
				
				respObj = getUrl(req);
				if(getLbUrl() != null ) {
					respObj.setDocUrl(getLbUrl().concat(respObj.getDocUrl()));
				}else {
					respObj.setDocUrl("http://".concat(getMachineName())
							.concat(":")
							.concat(getPort())
							.concat(respObj.getDocUrl()));
				}
				respObj.setIs(getFile(req));
			}else {
				throw new IdcClientException("The document is not loading....!");
			}
				
		} catch (IdcClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try { 
				getIdcClient().logout(getIdcContext());
			} catch (IdcClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		return respObj;
	}
	
	@SuppressWarnings("rawtypes")
	private IdcClient getUCMConnection(String connectString) {
		
		IdcClientManager manager = new IdcClientManager();
		try {
			return manager.createClient(connectString);
				
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public ATResponseObj getUrl(ATRequestObj req){
		
		if (getIdcClient()==null) {
			setIdcClient(getUCMConnection("idc://".concat(getMachineName())
												.concat(":4444")));
		}
		if (getIdcContext()==null) {
			setIdcContext(new IdcContext(getUsr(), getPassword()));
		}
		
		String qText = "dID".concat("<matches>")
					.concat("`")
					.concat(req.getId().trim())
					.concat("`");
			
		System.out.println("Query text is "+ qText);
			
		// get the binder
		DataBinder binder = getIdcClient().createBinder();
			
		binder.putLocal ("IdcService", "GET_SEARCH_RESULTS");
		binder.putLocal("QueryText",qText);
		binder.putLocal ("ResultCount", "20");

		// execute the request
		ServiceResponse response;
		try {
			response = idcClient.sendRequest (getIdcContext(), binder);
			// get the binder
			binder = response.getResponseAsBinder();
		} catch (IdcClientException e) {
			
			e.printStackTrace();
		}
	
		DataResultSet resultSet = binder.getResultSet ("SearchResults");
		
		// loop over the results
		String url = null;
		String docName = null;
		String id = null;
		
		for (DataObject dataObject : resultSet.getRows()) {
		    url = dataObject.get ("URL");
		    docName = dataObject.get ("dDocName");
		    id = dataObject.get ("dID");

		}
		ATResponseObj resp = new  ATResponseObj(docName, url, id);
		
		return resp;
					
	}
	@SuppressWarnings("deprecation")
	private boolean docReleased(ATRequestObj req) {
		
		if (getIdcClient()==null) {
				setIdcClient(getUCMConnection("idc://".concat(getMachineName())
													.concat(":4444")));
		}
		if (getIdcContext()==null) {
				setIdcContext(new IdcContext(getUsr(), getPassword()));
		}
		
		DataBinder dataBinder = getIdcClient().createBinder();
		dataBinder.putLocal ("IdcService", "DOC_INFO");
		dataBinder.putLocal("dID", req.getId());
		ServiceResponse response = null;
		String status = null;
		int count = 0;
		
		while (true) {
			try {
				response = getIdcClient().sendRequest(getIdcContext(), dataBinder);
				dataBinder = response.getResponseAsBinder();
				DataResultSet resultSet = dataBinder.getResultSet("DOC_INFO");
				
				for (DataObject sdataObject : resultSet.getRows()) {
				  
					System.out.println ("Status is : " + sdataObject.get ("dStatus"));
					status = sdataObject.get ("dStatus");
    
				}
				if (status.equalsIgnoreCase("RELEASED")){
					count++;
					break;
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					count = 0;
					break;
				}
				
			} catch (IdcClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			if (count==20) {
				break;
			}
		}
		
		if (count > 19 && !status.equalsIgnoreCase("RELEASED")) {
			System.out.println("Doc upload timed out");
			return false;
		}else{
			if(count==0) {
				System.out.println("The thread threw an interrupted exception");
				return false;
			}
		}
		
		return true;
	}
	private void setEnvString() {
		
		setEnv(getEnv().toUpperCase());
		if (getEnv().startsWith("PROD")) {
			setPort(EnvPort.PROD.port());
			setLbUrl(LBUrl.PROD.lbUrl());
		}
		if (getEnv().startsWith("STAGE")) {
			setPort(EnvPort.STAGE.port());
			setLbUrl(LBUrl.STAGE.lbUrl());
		}
		if (getEnv().startsWith("QA")) {
			setPort(EnvPort.QA.port());
			setLbUrl(LBUrl.QA.lbUrl());
		}
		if (getEnv().startsWith("DEV")) {
			setPort(EnvPort.DEV.port());
			setLbUrl(LBUrl.DEV.lbUrl());
		}
	}
}
