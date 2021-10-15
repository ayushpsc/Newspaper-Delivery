package com.apsc.newspaperwala.newspapers;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;
import com.aspose.pdf.internal.imaging.system.io.FileAccess;
import com.aspose.pdf.internal.imaging.system.io.FileMode;
import com.aspose.pdf.internal.imaging.system.io.FileStream;
import com.aspose.pdf.internal.imaging.system.io.MemoryStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NBTNews2 {
	public static void downloadNBT() {
		String dataDir="";
		
		String commonURL = "http://image.mepaper.navbharattimes.com/epaperimages//";
		LocalDateTime d = LocalDateTime.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedDate = df.format(d);
		System.out.println(formattedDate);
		PDFMergerUtility ut = new PDFMergerUtility();
		
		
		for (int i = 1; i <= 16; i++) {
			try {
				URL u = new URL(commonURL + formattedDate + "//" + formattedDate + "-md-de-" + i + ".jpg");
				HttpURLConnection connection = (HttpURLConnection) u.openConnection();
				connection.setConnectTimeout(5000); // 5 seconds connectTimeout
				connection.setReadTimeout(5000 ); // 5 seconds socketTimeout

				// Connect
				connection.connect(); // Without this line, method readLine() stucks!!!
				// because it reads incorrect data, possibly from another memory area
				InputStream is = u.openStream();
				Files.copy(is, Paths.get("tempjpg_" + i + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
				Document doc = new Document("temppdf_" + i + ".pdf");
				doc.getPages().clear();
				Page page = doc.getPages().add();
				
				FileStream fs = new FileStream("tempjpg_" + i + ".jpg", FileMode.Open, FileAccess.Read);
				byte[] tmpBytes = new byte[(int) fs.getLength()];
				fs.read(tmpBytes, 0, (int) fs.getLength());

				MemoryStream mystream = new MemoryStream(tmpBytes);
				InputStream is2 = new ByteArrayInputStream(tmpBytes, 0, (int) fs.getLength());
				// Instantiate BitMap object with loaded image stream
//				Bitmap b = new Bitmap(mystream);

				// Set margins so image will fit, etc.
				page.getPageInfo().getMargin().setBottom(0);
				page.getPageInfo().getMargin().setTop(0);
				page.getPageInfo().getMargin().setLeft(0);
				page.getPageInfo().getMargin().setRight(0);

//				page.setCropBox(new Pdf.Rectangle(0, 0, b.Width, b.Height););
				// Create an image object
				Image image1 = new Image();
				// Add the image into paragraphs collection of the section
				page.getParagraphs().add(image1);
				// Set the image file stream
				image1.setImageStream(is2);
				dataDir = "temppdf_" + i + ".pdf";
				// Save resultant PDF file
				doc.save(dataDir);

				// Close memoryStream object
				mystream.close();
				
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
