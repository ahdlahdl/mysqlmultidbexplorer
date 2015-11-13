package com.multidbexplorer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

	public static String readFile(String fileName) {
		BufferedReader br = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;

		File file = new File(fileName);
		String temp = "";

		StringBuffer content = new StringBuffer();
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);

			while ((temp = br.readLine()) != null) {
				content.append(temp).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}

}
