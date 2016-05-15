package org.pwr.crawler.utils.htmlUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileLoader {

	public static List<File> getFiles() {
		File dir = new File("/home/hadoopuser/data/");

		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".html");
			}
		});

		return Arrays.asList(files);
	}

}
