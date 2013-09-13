package data.persistentEntities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHash implements Serializable{
	private static final long serialVersionUID = 3993003202048390306L;
	
	public byte[] hash;
	
	public FileHash(File theFile){
		update(theFile);
	}
	
	public void update(File file) {
		if(file == null) 
			return;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			updateDigestWithFileBytes(file, md);
			hash = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateDigestWithFileBytes(File theFile, MessageDigest digest) throws IOException {
		FileInputStream in = new FileInputStream(theFile);
		byte[] buffer = new byte[1024];
		int read;
		
		while((read = in.read(buffer)) != -1)
			digest.update(buffer, 0, read);
		in.close();
	}
	
	public boolean equals(Object ot){
		if(!(ot instanceof FileHash))
			return false;
		FileHash otHash = (FileHash) ot;
		
		if(hash == null || otHash.hash == null)
			return hash == otHash.hash;
		if(hash.length != otHash.hash.length)
			return false;
		
		for(int i = 0; i < hash.length; ++i){
			if(hash[i] != otHash.hash[i])
				return false;
		}
		return true;
	}
	
	public boolean isSuccesfullyCalculated(){
		return hash != null;
	}
}
