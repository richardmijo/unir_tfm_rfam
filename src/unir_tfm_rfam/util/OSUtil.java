package unir_tfm_rfam.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class OSUtil {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings("unused")
	private void executeCommand(String command) {
		try {
			log(command);
			Process process = Runtime.getRuntime().exec(command);
			logOutput(process.getInputStream(), "");
			logOutput(process.getErrorStream(), "Error: ");
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void logOutput(InputStream inputStream, String prefix) {
		new Thread(() -> {
			Scanner scanner = new Scanner(inputStream, "UTF-8");
			while (scanner.hasNextLine()) {
				synchronized (this) {
					log(prefix + scanner.nextLine());
				}
			}
			scanner.close();
		}).start();
	}

	// Process p = Runtime.getRuntime().exec(command);
	private synchronized void log(String message) {
		System.out.println(format.format(new Date()) + ": " + message);
	}
}
