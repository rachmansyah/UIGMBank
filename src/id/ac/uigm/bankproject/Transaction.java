package id.ac.uigm.bankproject;

import java.util.Date;

public class Transaction {

	private double amount; // nilai transaksi
	private Date timestamp; // tanggal transaksi
	private String note; // catatan
	private Account inAccount;

	// bikin transaksi baru
	public Transaction(double amount, Account inAccount) {

		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.note = "";

	}

	// overloading
	public Transaction(double amount, String memo, Account inAccount) {
		this(amount, inAccount);
		this.note = memo;
	}

	// ambil nilai transaksi
	public double getAmount() {
		return this.amount;
	}

	// ringkasan transaksi
	public String getSummaryLine() {

		if (this.amount >= 0) {
			return String.format("%s, %.02f : %s", this.timestamp.toString(), this.amount, this.note);
		} else {
			return String.format("%s, (%.02f) : %s", this.timestamp.toString(), -this.amount, this.note);
		}
	}
}
