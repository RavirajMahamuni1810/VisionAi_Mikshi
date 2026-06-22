package pw.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TextCompareUtil

{
	public static double getSimilarity(String expectedText, String actualText) {
		double score = 0.0;

		try {

			ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\MN001752\\git\\repository\\Miksh\\python\\similarity.py", expectedText, actualText);

			pb.redirectErrorStream(true);  
			Process p = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {

			    System.out.println("PYTHON OUTPUT: " + line);

			    
			    if (line != null && line.matches("[-+]?[0-9]*\\.?[0-9]+")) 
			    {
			        score = Double.parseDouble(line.trim());
			    }
			}

			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return score;
	}
}
