package id.ac.uigm.bankproject;

import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
	private String name;
	private String userID;
	private byte pinHash[];
	private ArrayList<Account> accounts;

	// Contructor data nasabah

	public User(String name, String pin, Bank theBank) {

		this.name = name;

		// Simpan PIN dalam MD5 hash untuk alasan keamanan
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (Exception e) {
			System.err.println("error, exeption : " + e.getMessage());
			System.exit(1);
		}

		// buat userID baru
		this.userID = theBank.getNewUserID();

		// buat list akun kosongan
		this.accounts = new ArrayList<Account>();

		System.out.printf("Nasabah baru %s, dengan ID %s sudah dibuat.\n", name, this.userID);

	}

	public String getUserID() {
		return this.userID;
	}

	// Tambah akun nasabah
	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}

	// ambil jumlah akun nasabah
	public int numAccounts() {
		return this.accounts.size();
	}

	// ambil saldo sebuah akun
	public double getAcctBalance(int acctIdx) {
		return this.accounts.get(acctIdx).getBalance();
	}

	// ambil userID dari akun
	public String getAcctUserID(int acctIdx) {
		return this.accounts.get(acctIdx).getAccUserID();
	}

	//cetak histori transaksi
	public void printAcctTransHistory(int acctIdx) {
		this.accounts.get(acctIdx).printTransHistory();
	}

	// tambah transaksi
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}

	// validasi PIN
	public boolean validatePin(String aPin) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (Exception e) {
			System.err.println("error, exeption : " + e.getMessage());
			System.exit(1);
		}

		return false;
	}

	// tampilkan ringkasan akun nasabah
	public void printAccountsSummary() {

		System.out.printf("\n\nRingkasan akun nasabah %s\n", this.name);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("%d) %s\n", a + 1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();

	}

}
