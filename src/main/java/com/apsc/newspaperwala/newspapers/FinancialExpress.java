package com.apsc.newspaperwala.newspapers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FinancialExpress {

	public static void downloadFinancialExpress() {
		URL u = null;
		int issuedId = 0;
		try {
			u = new URL("https://epaper.financialexpress.com/t/227/latest/Delhi");
//            HttpRequest req = new BasicHttpRequest();
//            HttpResponse res = req.doGetRequest(u);
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			connection.setConnectTimeout(5000); // 5 seconds connectTimeout
			connection.setReadTimeout(5000 ); // 5 seconds socketTimeout

			// Connect
			connection.connect(); // Without this line, method readLine() stucks!!!
			// because it reads incorrect data, possibly from another memory area
			
			InputStream is = u.openStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
			Stream<String> lines = bufferedReader.lines();
			List<String> array = lines.collect(Collectors.toList());
			for (String line : array) {
				if (line.contains("'issueId':")) {
					System.out.println(line.substring(19, 26));
					issuedId = Integer.parseInt(line.substring(19, 26));

				}
			}

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		URL u2 = null;
		String finalDownloadURL = null;
		try {
			u2 = new URL("https://epaper.financialexpress.com/download/fullpdflink/newspaper/227/" + issuedId);
//            HttpRequest req2 = new BasicHttpRequest();
//            HttpResponse res2 = req2.doGetRequest(u2);
			HttpURLConnection connection = (HttpURLConnection) u.openConnection();
			connection.setConnectTimeout(5000); // 5 seconds connectTimeout
			connection.setReadTimeout(5000 ); // 5 seconds socketTimeout

			// Connect
			connection.connect(); // Without this line, method readLine() stucks!!!
			// because it reads incorrect data, possibly from another memory area
			
			InputStream is2 = u2.openConnection().getInputStream();

//            GZIPInputStream gzis = new GZIPInputStream(is2);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is2));

			Stream<String> lines = bufferedReader.lines();
			List<String> array = lines.collect(Collectors.toList());
			for (String line : array) {

				JsonObject convertedObject = new Gson().fromJson(line, JsonObject.class);
				JsonElement element = ((JsonObject) convertedObject.get("data")).get("fullpdf");
				finalDownloadURL = element.getAsString();

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		URL u3 = null;

		try {
			System.out.println(finalDownloadURL);
			u3 = new URL(finalDownloadURL);
			URLConnection conn = u3.openConnection();
			conn.setRequestProperty("rwmypublications", "690");
			InputStream is = conn.getInputStream();

			Files.copy(is, Paths.get("FinancialExpress_Epaper" + ".pdf"), StandardCopyOption.REPLACE_EXISTING);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
