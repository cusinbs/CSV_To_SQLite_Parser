package MS3_Coding_Challenge;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

public class ParseCSV {
	
	static ArrayList<String[]> parseCSVToArray(String fileName) {
		ArrayList<String[]> records = new ArrayList<String[]>();
		try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(fileName + ".csv")).withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS).build();) {
		    String[] values = null;
		    while ((values = csvReader.readNext()) != null) {
		        records.add(values);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return records;
	}
	
}
