import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DownloadYahooTest {

	public static void main(String[] args) throws IOException {
		// Read an http resource in to a stream
		String tAddress = "http://finance.yahoo.com/d/quotes.csv?s=GOOG,IBM,NX,MM,AAPL&f=l1n0";
		URL tDocument = new URL(tAddress);
		URLConnection tConnection = tDocument.openConnection();
		tConnection.connect();
		BufferedReader tBufferedReader = new BufferedReader(
				new InputStreamReader(tDocument.openStream()));

		String line;
		while ((line = tBufferedReader.readLine()) != null) {
			String[] splittedLine = line.split(",");
			
			try {
				String Wert = splittedLine[0];
				String name = splittedLine[1];
				System.out.println(Wert + "  " + name);
			} catch (Exception e) {
				System.out.println("Yaaay (k)ein Fehler!!");
			} 

		}

	}
}
