package com.apsc.newspaperwala.starter;

import java.io.FileWriter;
import java.io.IOException;

import com.apsc.newspaperwala.mail.Mail;
import com.apsc.newspaperwala.newspapers.FinancialExpress;
import com.apsc.newspaperwala.newspapers.NBTNews;
import com.apsc.newspaperwala.newspapers.NBTNews2;
import com.apsc.newspaperwala.newspapers.Tribune;
import com.apsc.newspaperwala.util.NewsFiles;
import com.apsc.newspaperwala.util.ReduceSize;

public class NewsCaller {

	public static void main(String[] args) {

		NBTNews2.downloadNBT();
		Tribune.downloadTribune();
		FinancialExpress.downloadFinancialExpress();
		
		Mail m = new Mail();

		// check if file is eligible to be sent via mail
		if (NewsFiles.checkFileEligibleForMail("Navbharat_EPaper.pdf") == true) {
			m.sendMail("Navbharat_EPaper");
		} else {
			try {
				System.out.println("Size limit exceeded for NBT");
				FileWriter fw = new FileWriter("Navbharat_EPaper.txt");
				fw.append("Size limit exceeded".toString());
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			boolean asposeResult = false;
			
				System.out.println("compressing pdf...");
			try {
				asposeResult = ReduceSize.manipulatePdfUsingAspose("Navbharat_EPaper");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (asposeResult == true) {
				m.sendMail("Navbharat_EPaper");
			}
		}

		if (NewsFiles.checkFileEligibleForMail("Tribune_Epaper.pdf") == true) {
			m.sendMail("Tribune_Epaper");
		} else {
			try {
				System.out.println("Size limit exceeded for Tribune");
				FileWriter fw2 = new FileWriter("Tribune_Epaper.txt");
				fw2.append("Size limit exceeded".toString());
				fw2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

					boolean asposeResult = false;
					
						System.out.println("compressing pdf...");
					try {
						asposeResult = ReduceSize.manipulatePdfUsingAspose("FinancialExpress_Epaper");
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (asposeResult == true) {
						m.sendMail("FinancialExpress_Epaper");
					}
				}
	}
}