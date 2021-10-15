package com.apsc.newspaperwala.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewsFiles {

	public static boolean checkFileEligibleForMail(String file) {
		File f = new File(file);
		try {
			Long size = (Long) Files.getAttribute(f.toPath(), "size", java.nio.file.LinkOption.NOFOLLOW_LINKS);
			if (size / 1024 / 1024 > 25) {
				return false;
			} else {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void deleteFile (String file) {
	
		try {
			Files.delete(Paths.get(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
