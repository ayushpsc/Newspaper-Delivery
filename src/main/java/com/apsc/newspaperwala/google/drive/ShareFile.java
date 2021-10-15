package com.apsc.newspaperwala.google.drive;

import com.apsc.newspaperwala.util.DriveQuickstart;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Permissions;
import com.google.api.services.drive.model.Permission;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ShareFile {

	public void shareFileByEmail(String fileID, String email) {

		final NetHttpTransport HTTP_TRANSPORT;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Drive d = new Drive(HTTP_TRANSPORT, DriveQuickstart.JSON_FACTORY,
					DriveQuickstart.getCredentials(HTTP_TRANSPORT));
			Permissions permissions = d.new Permissions();
			Permission permission = new Permission();

			permission.setEmailAddress(email);
			permission.setRole("writer");
			permission.setType("user");

			permissions.create(fileID, permission).execute();

		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
