package org.xkit.labs.log.analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogAnalyzer {

	public static final Pattern WKS = Pattern.compile("(\\d+)");

	public static void main(String[] args) {
		File file = new File("/home/guohai/dev/workspace/java/consumer/bin");
		File[] logs = null;
		if (file.isDirectory()) {
			logs = file.listFiles(new FilenameFilter() {
				public boolean accept(File file, String s) {
					String s1 = s.toLowerCase();
					return s1.endsWith(".txt");
				}
			});
		}
		if (null != logs) {
			for (File every : logs) {
				try {
					analyzerLogFile(every);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void analyzerLogFile(File logFile) throws IOException {
		// 第一行中的数字 + 21 == 所有的行数，那么就证明consumer程序没有出错
		String lineOne = null;
		int wks = 0;
		int allLineCount = 0;

		BufferedReader reader = new BufferedReader(new FileReader(logFile));
		String line = reader.readLine();
		for (; line != null; line = reader.readLine()) {
			allLineCount++;
			if (1 == allLineCount) {
				lineOne = line;
				Matcher m = WKS.matcher(lineOne);
				if (m.find()) {
					wks = Integer.valueOf(m.group(1));
				}
			}
		}

		// judge
		if (wks + 21 == allLineCount) {
			System.out.println(logFile.getName() + " run test pass");
		} else {
			System.err.println(logFile.getName() + " run test error");
		}
	}
}
