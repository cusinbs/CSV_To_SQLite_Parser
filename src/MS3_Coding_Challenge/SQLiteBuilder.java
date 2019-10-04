package MS3_Coding_Challenge;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVWriter;

public class SQLiteBuilder {
	private String dbName;

	public SQLiteBuilder(String dbName) {
		this.dbName = dbName;
	}

	public void createNewDatabase() {
		String url = "jdbc:sqlite:" + dbName + ".db";
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// System.out.println("Cannot create new database!");
		}
	}

	public void createNewTable() {
		// SQLite connection string
		String url = "jdbc:sqlite:" + dbName + ".db";

		String sql1 = "DROP TABLE IF EXISTS data;\n";
		// SQL statement for creating a new table
		String sql2 = "CREATE TABLE IF NOT EXISTS data (\n" + "    id integer PRIMARY KEY,\n" + "    A text NULL,\n"
				+ "    B text NULL,\n" + "    C text NULL,\n" + "    D text NULL,\n" + "    E text NULL,\n"
				+ "    F text NULL,\n" + "    G text NULL,\n" + "    H text NULL,\n" + "    I text NULL,\n"
				+ "    J text NULL\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql1);
			stmt.execute(sql2);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertCSVToDatabase(ArrayList<String[]> records) {
		ArrayList<String[]> badRecords = new ArrayList<String[]>();
		ArrayList<String[]> goodRecords = new ArrayList<String[]>();
		boolean isFaulty = false;
		
		for (int i = 1; i < records.size(); i++) {
			for (int j = 0; j < 10; j++) {
				if (records.get(i)[j] == null || records.get(i)[j].equals("") || StringUtils.isBlank(records.get(i)[j])) {
					isFaulty = true;
					break;
				}
			}
			if (isFaulty) {
				badRecords.add(records.get(i));
				isFaulty = false;
			} else {
				goodRecords.add(records.get(i));
			}
		}
		
		insertGoodRecords(goodRecords);
		insertBadRecordToCSV(badRecords);
		writeLogFile(records.size() - 1, goodRecords.size(), badRecords.size()-1);
	}

	private void writeLogFile(int numReceived, int numSuccess, int numFail) {
		FileHandler handler = null;
		try {
			handler = new FileHandler(dbName + ".log");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger logger = Logger.getLogger("MS3_Coding_Challenge");
		logger.addHandler(handler);
		logger.info("Number of records received: " + numReceived);
		logger.info("Number of records successful: " + numSuccess);
		logger.info("Number of records failed: " + numFail);
	}

	private void insertBadRecordToCSV(ArrayList<String[]> badRecords) {
		
		String[] header = {"A", "B","C","D","E","F","G","H","I","J"};
		badRecords.add(0, header);
		String fileName = dbName + "-bad.csv";

        try (FileOutputStream fos = new FileOutputStream(fileName);
                OutputStreamWriter osw = new OutputStreamWriter(fos, 
                        StandardCharsets.UTF_8);
                CSVWriter writer = new CSVWriter(osw)) {
            
            writer.writeAll(badRecords);
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertGoodRecords(ArrayList<String[]> records) {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
			conn.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = conn.createStatement();
			String sql;
			for (int i = 0; i < records.size(); i++) {
				sql = "INSERT INTO data " + "VALUES (" + (i + 1) + ",\"" + records.get(i)[0] + "\",\""
						+ records.get(i)[1] + "\",\"" + records.get(i)[2] + "\",\"" + records.get(i)[3] + "\",\""
						+ records.get(i)[4] + "\",\"" + records.get(i)[5] + "\",\"" + records.get(i)[6] + "\",\""
						+ records.get(i)[7] + "\",\"" + records.get(i)[8] + "\",\"" + records.get(i)[9] + "\")";
				stmt.executeUpdate(sql);
			}

			stmt.close();
			conn.commit();
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
	}
}
