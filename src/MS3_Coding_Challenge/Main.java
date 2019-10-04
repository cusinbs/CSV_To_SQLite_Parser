package MS3_Coding_Challenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter input-filename: ");
		String dbName = in.nextLine();
		dbName = dbName.replaceAll(" ", "_");
		SQLiteBuilder db = new SQLiteBuilder(dbName);
		db.createNewDatabase();
		db.createNewTable();
		ArrayList<String[]> records = ParseCSV.parseCSVToArray("ms3Interview");
		db.insertCSVToDatabase(records);
		
	}

}
