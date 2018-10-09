package za.co.mtn.bright;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CreateWCCFile {
	
	public File writeFile(InputStream is, String filename) {
		
		OutputStream os = null;
		File file = null;
		
		if (filename == null || is == null) {
			try {
				throw new Exception("A file name needs to be passed in to checkin or input stream is null");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		try {
			file = new File(filename);
			os = new FileOutputStream(file);
		
			byte[] buf = new byte[1024 * 256];
			long i = 0;
			int len;

			while (true) {
				i++;
				len = is.read(buf);
				if (len == -1) {
					break;
				}
				os.write(buf, 0, len);
			}
			os.flush();
			
			} catch (IOException ioe) {
	        	 System.out.println("IO Exception occurred. Unable to retrieve file. Message: " + ioe.getMessage()); 
	    	} catch (Exception ex) {
	        	 System.out.println("Exception message: " + ex.getMessage() );
	    	}finally {
	    		try {
					is.close();
					os.close();
					return file;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}	
		return null;
	    }

}
