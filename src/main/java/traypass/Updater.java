package traypass;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import traypass.tools.ToolDownload;
import traypass.tools.ToolFile;

public class Updater {

	public static final String TRAYPASS_BAT = "TrayPass.bat";

	public static final String UPDATE_ZIP_NAME = "TrayPass.zip";

	public static void main(String[] args) throws Exception {

		try {
			// Getting the url from arguments
			String url = args[0];

			// Downloading the zip file
			String dlPath = ToolDownload.downloadFile(url + UPDATE_ZIP_NAME, ToolFile.getNewTempFile());

			// Removing the old lib
			File libFolder = new File("lib");
			if (libFolder.isDirectory()) {
				for (File file : libFolder.listFiles()) {
					file.delete();
				}
			}

			// Decompressing the archive
			ZipInputStream zis = new ZipInputStream(new FileInputStream(new File(dlPath)));
			ZipEntry ze;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					new File(ze.getName()).mkdirs();
				} else {
					File f = new File(ze.getName());
					OutputStream fos = new BufferedOutputStream(new FileOutputStream(f));

					try {
						try {
							final byte[] buf = new byte[8192];
							int bytesRead;
							while (-1 != (bytesRead = zis.read(buf))) {
								fos.write(buf, 0, bytesRead);
							}
						} finally {
							fos.close();
						}
					} catch (final IOException ioe) {
						f.delete();
						throw ioe;
					}
				}
			}

			new File(dlPath).delete();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Runtime.getRuntime().exec(TRAYPASS_BAT).waitFor();
		}
	}
}
