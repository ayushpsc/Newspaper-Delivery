package com.apsc.newspaperwala.newspapers;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NBTNews {
	public static void downloadNBT() {
		String commonURL = "http://image.mepaper.navbharattimes.com/epaperimages//";
		LocalDateTime d = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedDate = df.format(d);
		System.out.println(formattedDate);
		PDFMergerUtility ut = new PDFMergerUtility();
		for (int i = 1; i <= 16; i++) {
			try {
				URL u = new URL(commonURL + formattedDate + "//" + formattedDate + "-md-de-" + i + ".pdf");
				InputStream is = u.openStream();
				Files.copy(is, Paths.get("temppdf_" + i + ".pdf"), StandardCopyOption.REPLACE_EXISTING);

				ut.addSource("temppdf_" + i + ".pdf");
				System.out.println(i + "th page downloaded");

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		ut.setDestinationFileName("Navbharat_EPaper.pdf");

		try {
			ut.mergeDocuments();
			System.out.println("File written to disk.. Ready for sending mails");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
