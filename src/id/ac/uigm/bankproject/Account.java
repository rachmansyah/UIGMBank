package id.ac.uigm.bankproject;

import java.util.ArrayList;

public class Account {
	private String accName;
	private String userID;
	private User holder;
	private ArrayList<Transaction> transactions;

	// Class Account
	// accName : Nama akun (tabungan/giro)
	// holder : nama nasabah
	public Account(String accName, User holder, Bank theBank) {

		// nama akun dan nama nasabah
		this.accName = accName;
		this.holder = holder;

		// ambil ID akun
		this.userID = theBank.getAccUserID();

		// bikin transaksi kosong
		this.transactions = new ArrayList<Transaction>();

	}

	// ambil ID nasabah
	public String getAccUserID() {
		return this.userID;
	}

	// buat transaksi baru di akun ini
	public void addTransaction(double amount) {

		// buat transaksi baru dan masukkan ke list
		Transaction newTrans = new Transaction(amount, this);
		this.transactions.add(newTrans);

	}

	// buat transaksi baru di akun ini overloading note
	public void addTransaction(double amount, String note) {

		// // buat transaksi baru dan masukkan ke list
		Transaction newTrans = new Transaction(amount, note, this);
		this.transactions.add(newTrans);

	}

	// ambik saldo
	public double getBalance() {

		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;

	}

	// ambil ringkasan
	public String getSummaryLine() {

		// ambil saldo
		double balance = this.getBalance();

		// format ringkasan apakah saldonya negatif atau tidak
		if (balance >= 0) {
			return String.format("%s : %.02f : %s", this.userID, balance, this.accName);
		} else {
			return String.format("%s : (%.02f) : %s", this.userID, balance, this.accName);
		}

	}

	// cetak riwayat transaksi akun ini
	public void printTransHistory() {

		System.out.printf("\nHistori transaksi untuk akun %s\n", this.userID);
		for (int t = this.transactions.size() - 1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();

	}

}
