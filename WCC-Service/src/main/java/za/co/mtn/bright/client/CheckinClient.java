package za.co.mtn.bright.client;

import za.co.mtn.bright.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CheckinClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		za.co.mtn.bright.ATRequestObj req = new ATRequestObj("GUZZ",//account
										null,//doc name
										"guzzTitle",//title
										"Bursary",//doctype
										"Bursary",//security group
										"moto_guzzi.xlsx");//file name
		
		File file = new File("C:/Users/LyntonHegele/Documents/bright/moto_guzzi.xlsx");//file to checkin
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			req.setIs(is);
			WccCheckin checkin = new WccCheckin("nlwowcrvq01.mtn.co.za",//machine to connect to
													"weblogic_wcc",//user
													"weblogic1",//password
													"qa");//environment
			
			ATResponseObj respObj = checkin.checkinWCC(req);
			CreateWCCFile createFile = new CreateWCCFile();
			createFile.writeFile(respObj.getIs(), req.getFilename()); 
			//file written to file system to test the input stream retrieved from WCC. 
			
			System.out.println("doc url in client " + respObj.getDocUrl());
			System.out.println("doc name in client " + respObj.getDocName());
			System.out.println("doc id in client " + respObj.getId());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
