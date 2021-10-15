package com.apsc.newspaperwala.google.drive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Properties;

import com.apsc.newspaperwala.util.DriveQuickstart;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class FileUpload {

	public String createFileInFolder(String filename) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new java.io.File("params.properties")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String folderId = p.getProperty("google.drive.folder.id");;
		File fileMetadata = new File();
		fileMetadata.setName(filename);
		fileMetadata.setParents(Collections.singletonList(folderId));
		java.io.File filePath = new java.io.File(filename);
		FileContent mediaContent = new FileContent("application/pdf", filePath);
		File file;
		try {
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Drive driveService = new Drive.Builder(HTTP_TRANSPORT, DriveQuickstart.JSON_FACTORY,
					DriveQuickstart.getCredentials(HTTP_TRANSPORT)).setApplicationName(DriveQuickstart.APPLICATION_NAME)
							.build();
			file = driveService.files().create(fileMetadata, mediaContent).setFields("id, parents").execute();
			System.out.println("File ID: " + file.getId());
			return file.getId();
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
