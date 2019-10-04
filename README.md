# MS3 Coding Exercise: Parsing CSV To Sqlite

## Project Description
1. Write a application that consume a CSV file, parse data, insert valid records into a SQLite database
    - Database should have 1 table with 10 columns A, B, C, D, E, F, G, H, I, J
2. Insert bad records that do not match the column count into a CSV file.
3. Generate a log file to report number of records received, records successful, and records failed.

## Getting It To Run
- The application is written in Java on Eclipse. Please clone the project and make sure all the dependencies are available before running
the application.

## Approach
- The program was divided the application into 6 main problems:
  1. Parsing the CSV file into an ArrayList of String[] (array of String arrays).
      - Creating a class called ParseCSV which has a static function that parses a CSV file into an ArrayList<String[]> by using the CSVReader
      class.
      - CSVReader has an advantage of recognizing if the comma is inside the cell or outside. For example, data:image column may have 
      another comma inside it, which might cause the program to divide it into 2 different columns. CSVReader prevents that from happening.
  2. Creating the database and table.
      - Creating the SQLiteBuilder class which uses the JDBC API to create the SQLite database and table.
  3. Filter the good and bad records.
      - The records will be filtered in the insertCSVToDatabase function in SQLiteBuilder class
        - If a record has a null, empty, or blank cell in the first 10 cells, it will be added to the badRecords ArrayList
        - Other records will be added to goodRecords ArrayList
  4. Inserting good records into SQLite database:
      - At first, I was using for loop to iterate through each records and insert them into the database. However, that was a bad idea since
      each insert will have its own transaction with its own overhead. This method would take the program about 3 minutes to finish inserting
      all 6000 records.
      - Therefore, instead of inserting data set individually, I have wrapped them up in one transaction then commit. This boosted the run-time
      performance significantly. The run-time was taken down from 3 minutes to 2 seconds.
  5. Inserting bad records into CSV file:
      - CSVWriter is used to write the bad records into the CSV file. 
      - The advantage of using CSVWriter is it would know where each elements end and not be confused with additionnal comma in the data.
      - Also CSVWriter will leave the cell blank instead of write null in the cell if the element is null.
  6. Write the log file:
      - I use Logger and FileHandler to generate the log file.
 
## Assumption
    - Take the input-filename for the database from the user
    - The program will name the database, csv, log file based on the input-filename from the user
    - The program will name the table "data"
    - The original CSV file will be named ms3Interview.csv
    - All the files are put inside the project folder
    - The CSV file has 10 columns from A to J
  
          
