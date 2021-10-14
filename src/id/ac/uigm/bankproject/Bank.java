package id.ac.uigm.bankproject;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
	private String name; // nama bank
	private ArrayList<User> users;
	private ArrayList<Account> accounts;

	public Bank(String name) {
		this.name = name;
		// init daftar nasabah dan daftar akun
		users = new ArrayList<User>();
		accounts = new ArrayList<Account>();
	}
	
	// generate ID nasabah
	public String getNewUserID() {
		
		// inits
		String userID;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		// looping sampe dapat userID unik
		do {
			userID = "";
			for (int c = 0; c < len; c++) {
				userID += ((Integer)rng.nextInt(10)).toString();
			}
			
			// cek unik atau tidak
			nonUnique = false;
			for (User u : this.users) {
				if (userID.compareTo(u.getUserID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique);
		
		return userID;
	}
	
	// Generate ID Akun
	public String getAccUserID() {
		
		// inits
		String userID;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique = false;
		
		// looping sampe dapat userID unik
		do {
			userID = "";
			for (int c = 0; c < len; c++) {
				userID += ((Integer)rng.nextInt(10)).toString();
			}
			
			// cek unik atau tidak
			for (Account a : this.accounts) {
				if (userID.compareTo(a.getAccUserID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique);
		
		return userID;
				
	}

	// bikin nasabah baru
	public User addUser(String name, String pin) {
		
		User newUser = new User(name, pin, this);
		this.users.add(newUser);
		
		Account newAccount = new Account("Tabungan", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
		
	}
	
	// tambahkan akun seorang nasabah
	public void addAccount(Account newAccount) {
		this.accounts.add(newAccount);
	}
	
	// login nasabah
	public User userLogin(String userID, String pin) {
		
		for (User u : this.users) {
			
			// if we find the user, and the pin is correct, return User object
			if (u.getUserID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		
		// if we haven't found the user or have an incorrect pin, return null
		return null;
		
	}
	
	//get nama bank
	public String getName() {
		return this.name;
	}
}
