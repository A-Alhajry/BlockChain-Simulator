package qu.master.blockchain.documentsattestation;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class Logger {
	
	private static final String LogFile = "Logs" + File.separator + "app.log";

	public static void log(String message) {
		
		try {
			File logFile = new File(LogFile);
			FileWriter fileWriter = new FileWriter(logFile, true);
			fileWriter.write("Log Time: " + LocalDateTime.now().toString() + "\n");
			fileWriter.write(message + "\n");
			fileWriter.write("------------------------\n");
			fileWriter.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
