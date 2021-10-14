package id.ac.uigm.bankproject;

import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Bank theBank = new Bank("Bank UIGM Palembang");

		User aUser = theBank.addUser("Rachmansyah", "1234");

		// Tambahkan akun giro
		Account newAccount = new Account("Giro", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;

		// continue looping forever
		while (true) {

			// login
			curUser = ATM.mainMenuPrompt(theBank, sc);

			// menu utama ATM
			ATM.printUserMenu(curUser, sc);
		}
	} // end main

	// Tampilkan prompt login.
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {

		// inits
		String userID;
		String pin;
		User authUser;

		do {
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Ketikkan user ID: ");
			userID = sc.nextLine();
			System.out.print("Ketikkan pin: ");
			pin = sc.nextLine();

			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("kombinasi user ID/pin salah. " + "Silahkan ulangi lagi");
			}

		} while (authUser == null); // continue looping sampe berhasil login
		return authUser;
	} // end mainMenuPrompt

	// Tampilkan menu utama ATM
	public static void printUserMenu(User theUser, Scanner sc) {
		theUser.printAccountsSummary();
		int choice;
		do {
			System.out.println("Silahkan pilih jenis transaksi");
			System.out.println("  1) Melihat histori transaksi");
			System.out.println("  2) Penarikan tunai");
			System.out.println("  3) Setor tunai");
			System.out.println("  4) Transfer");
			System.out.println("  5) Selesai");
			System.out.println();
			System.out.print("Pilihan anda: ");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("Salah pilih. Harap pilih 1-5.");
			}

		} while (choice < 1 || choice > 5);

		switch (choice) {
		case 1:
			ATM.showTransHistory(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;
		case 3:
			ATM.depositFunds(theUser, sc);
			break;
		case 4:
			ATM.transferFunds(theUser, sc);
			break;
		case 5:
			sc.nextLine();
			break;
		}

		if (choice != 5) {
			ATM.printUserMenu(theUser, sc); // rekursif
		}

	} // end menu

	// tampilkan histori transaksi
	public static void showTransHistory(User theUser, Scanner sc) {

		int theAcct;
		do {
			System.out.printf("Ketikkan nomer nomer akun(1-%d) yang ingin dilihat: ",
					theUser.numAccounts());
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Akun tidak valid. Silahkan coba lagi.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());

		theUser.printAcctTransHistory(theAcct);
	} // end showTransHistory

	// tarik tunai
	public static void withdrawFunds(User theUser, Scanner sc) {
		
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		do {
			System.out.printf("Ketikkan nomer akun(1-%d) yang ingin " + 
					"ditarik tunai: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Akun tidak valid. Silahkan coba lagi.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		do {
			System.out.printf("Ketikkan jumlah yang ingin ditarik tunai (max Rp.%.02f): Rp.", 
					acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Jumlah harus lebih besar dari nol.");
			} else if (amount > acctBal) {
				System.out.printf("Jumlah harus lebih kecil dari saldo " +
						"of Rp.%.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		sc.nextLine();
		System.out.print("Ketikkan catatan: ");
		memo = sc.nextLine();
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
	} // end withdrawFunds
	
	// setor tunai
	public static void depositFunds(User theUser, Scanner sc) {
		
		int toAcct;
		double amount;
		String memo;
		
		do {
			System.out.printf("Ketikkan nomer akun(1-%d) tujuan setor " + 
					"tunai: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Akun tidak valid. Silahkan coba lagi.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		// get amount to transfer
		do {
			System.out.printf("Ketikkan jumlah setor: Rp.");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Jumlah harus lebih besar dari nol.");
			} 
		} while (amount < 0);
		sc.nextLine();
		System.out.print("Ketikkan catatan: ");
		memo = sc.nextLine();
		theUser.addAcctTransaction(toAcct, amount, memo);
	} // end depositFunds
	
	
	// transfer
public static void transferFunds(User theUser, Scanner sc) {
		
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		do {
			System.out.printf("Ketikkan nomer akun(1-%d) sumber   " + 
					"dana transfer: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Akun tidak valid. Silahkan coba lagi.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		// get account to transfer to
		do {
			System.out.printf("Ketikkan nomer akun(1-%d) tujuan " + 
					"transfer : ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Akun tidak valid. Silahkan coba lagi.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		// get amount to transfer
		do {
			System.out.printf("Jumlah dana yang ingin ditransfer (max Rp.%.02f): Rp.", 
					acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Jumlah harus lebih besar dari nol.");
			} else if (amount > acctBal) {
				System.out.printf("Jumlah harus lebih kecil dari saldo " +
						"of Rp.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer ke akun %s", theUser.getAcctUserID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer dari akun %s", theUser.getAcctUserID(fromAcct)));
		
	}
}
