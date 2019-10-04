package MS3_Coding_Challenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter input-filename: ");
		String dbName = in.nextLine();
		dbName = dbName.replaceAll(" ", "_"); //replace all the space inside file name to avoid error
		SQLiteBuilder db = new SQLiteBuilder(dbName); //initialize the SQLTbuilder object
		db.createNewDatabase(); //create db
		db.createNewTable(); //create table
		ArrayList<String[]> records = ParseCSV.parseCSVToArray("ms3Interview"); //parse the csv file into ArrayList of records
		db.insertCSVToDatabase(records); //insert records into db, csv, and generate log file
		
	}

}
