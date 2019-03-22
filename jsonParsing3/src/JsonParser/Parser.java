
package JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Parser {
	public static void main(String[] args) {
		try {

			File file = new File("DengueCluster.geojson");
			if (!file.exists()) {

				file.createNewFile();

			}
			PrintWriter pw = new PrintWriter(file);
			pw.println(UrlRequest.dengue);
			pw.close();
			System.out.println("Done printing");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
