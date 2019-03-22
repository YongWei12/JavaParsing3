package JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class UrlRequest {
	public static String dengue = "";
	static String singleParsed = "";

	public static void main(String[] args) {
		
		try {
			
			// this method get the json data from goverment website
			String url = "https://geo.data.gov.sg/dengue-cluster/2019/03/15/geojson/dengue-cluster.geojson";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			// optional default is GET
			con.setRequestMethod("GET");
			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			
			// print in String
			//System.out.println(response.toString());
			JSONObject obj1 = new JSONObject (response.toString());
			JSONArray features = new JSONArray (obj1.getJSONArray("features").toString());
			for(int i =0; i<features.length() ; i++) {
				JSONObject feature = features.getJSONObject(i);
				JSONObject properties = new JSONObject (feature.getJSONObject("properties").toString());
				singleParsed = "Description"+ properties.get("Description") + "\n";
				String substr = singleParsed.substring(singleParsed.indexOf("CASE_SIZE")+19, singleParsed.indexOf("CASE_SIZE")+100);
				String substr2 = substr.substring(0,substr.indexOf('<'));
				
				
				//getting the substradd
				String substradd = singleParsed.substring(singleParsed.indexOf("LOCALITY")+18, singleParsed.indexOf("LOCALITY")+400);
				String substradd2 = substradd.substring(0,substradd.indexOf("</td>"));
				//substradd2.trim();
				
				/*int length2 = substradd.length();
				char[] substradd2 = substradd.toCharArray();
				int c, count2 = 0;
				for(c=0; c<length2 ; c++) {
					if(substradd2[c] == '(', )
				}*/
				
				/*int length = substr.length();
                int a, b, count = 0;
                char []substr1 =substr.toCharArray();
                for( a =  b =0; a < length; a++){
                    if ((substr1[a] != '/' )&& (substr1[a] != '<')) {
                        substr1[b++] = substr1[a];
                    }  else {
                        count++;
                    }

                }
                while(count>0){
                    substr1[b++] ='\0';
                    count--;
                }
                
                //substr is the value for cases
                substr = String.copyValueOf(substr1);
                */
                
                
                obj1.getJSONArray("features").getJSONObject(i).getJSONObject("properties").put("Description",substr2);  
                obj1.getJSONArray("features").getJSONObject(i).getJSONObject("properties").put("Name",substradd2);  
                
                //dengue =dengue +substradd2;
				
				
			}
			dengue = dengue +obj1.toString();
			
			
			//dengue = features.toString();
			System.out.println(dengue);
			
			
			
			
			
			
			//The below method creates a file or update a file of the geoJson
			File file = new File("DengueCluster.geojson");
			if (!file.exists()) {

				file.createNewFile();

			}
			PrintWriter pw = new PrintWriter(file);
			pw.println(dengue);
			pw.close();
			//System.out.println("Done printing");
			
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}
	

}
