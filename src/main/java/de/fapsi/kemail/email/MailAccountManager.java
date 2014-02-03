/**
 * 
 */
package de.fapsi.kemail.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.fapsi.kemail.KindletEMail;

/**
 * @author fapsi
 *
 */
public class MailAccountManager{
	
	public ArrayList<Account> accounts;
	
	public MailAccountManager() throws IOException, ClassNotFoundException{
		if(!checkAccountsFileAvailable()){
			if (!createAccountsFolder())
				throw new IllegalStateException("Could not create input folder.");
			accounts =  new ArrayList<Account>();
			
			storeAccounts();
		} else {
			readAccounts();
		}
	}

	private boolean checkAccountsFileAvailable() {
		File accounts_file = new File(KindletEMail.input_accounts_file_path);
		return accounts_file.exists();
	}
	
	private boolean createAccountsFolder(){
		File folder_accounts = new File(KindletEMail.input_accounts_file_path);		
		return folder_accounts.getParentFile().mkdirs();
	}

	public void storeAccounts() throws IOException{
		File file_accounts = new File(KindletEMail.input_accounts_file_path);
		FileOutputStream fos = new FileOutputStream(file_accounts, false);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		try{
			oos.writeObject(accounts);
		} finally {
			oos.flush();
			oos.close();
		}
	}
	
	public void readAccounts() throws IOException, ClassNotFoundException{
		File file_accounts = new File(KindletEMail.input_accounts_file_path);
		FileInputStream fis = new FileInputStream(file_accounts);
		ObjectInputStream ois = new ObjectInputStream(fis);
		try{
		Object o = ois.readObject();
			if (o instanceof ArrayList<?>){
				accounts = (ArrayList<Account>) o;
			}
		}finally {
			ois.close();
		}
	}
	
	public boolean addAccount(Account a) throws IOException{
		boolean added = accounts.add(a);
		if (added)
			storeAccounts();
		return added;
	}
	
	public boolean removeAccount(Account a){
		//TODO
		return false;
	}
	
	public List<Account> getAccounts(){
		return accounts;
	}	
}
