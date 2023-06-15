package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadLstFile {
	
	public List<String> readFile(String filePath) {
		try {
			 List<String> lines = new ArrayList<>();
		     BufferedReader reader = new BufferedReader(new FileReader(filePath));
		     String line;
		     
		        while ((line = reader.readLine()) != null) {
		            lines.add(line);
		        }

		        reader.close();
		        return lines;
		} catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
}

