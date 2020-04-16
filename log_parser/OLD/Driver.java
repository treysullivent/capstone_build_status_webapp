import java.sql.*;    

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;

import java.util.Date;
import java.util.Locale;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Driver {

    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, SQLException, ParseException
    {  
        Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone","root","root");	
        String fileName = args[0];
        String data = Parser.ReadFile(fileName);
        System.out.println(fileName);
        String[] csvStrings = Parser.ParseXMLtoCSVs(data);
        
        int rows = 0;
        for (int i = 0; i < csvStrings.length; i++)
        {
            System.out.println(csvStrings[i]);
            PreparedStatement stmt = CSVtoPrepStat(csvStrings[i], con);
            System.out.println(stmt);
            rows += stmt.executeUpdate();
        }
        System.out.println("Inserted " + rows + "row(s).");

        con.close();
    }
    
    
    /**
    *   Converts a CSV of the format
    *   "Name, StartTime, TargetServers,PCIVersion,DatabaseVersion, PCIInstallVersion, LicenseGroup, EndTime, Status"
    *   into a PreparedStatement on the connection 'con' to insert the CSV into the database
    **/
    private static PreparedStatement CSVtoPrepStat(String csv, Connection con) throws SQLException, ParseException
    {
        //Template for inserting a complete 'Install' row
        final String INSERT_QUERY = "INSERT INTO Installs " + 
            "(Name, StartTime, TargetServer, PCIVersion, DatabaseVersion, PCIInstallVersion, LicenseGroup, EndTime, Status) " + 
            "VALUES (?,?,?,?,?,?,?,?,?);";
        
        //SDF to convert from log file format into milliseconds for timestamp
        SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);


        String[] tokens = csv.split(",");
    
        PreparedStatement ps = con.prepareStatement(INSERT_QUERY);
    
    
        //Populate PreparedStatement with data from CSV
        ps.setString(1, tokens[0]);
        ps.setTimestamp(2, new java.sql.Timestamp(dateParser.parse(tokens[1]).getTime()));
        ps.setString(3, tokens[2]);
        ps.setString(4, tokens[3]);
        ps.setString(5, tokens[4]);
        ps.setString(6, tokens[5]);
        ps.setString(7, tokens[6]);
        ps.setTimestamp(8, new java.sql.Timestamp(dateParser.parse(tokens[7]).getTime()));  
        ps.setString(9, tokens[8]);
        
        return ps;
    }
    


    /*
    DROP DATABASE capstone;
    CREATE DATABASE capstone;
    USE capstone;
    SET SQL_MODE='ALLOW_INVALID_DATES';
    CREATE TABLE Installs (
    ID int NOT NULL AUTO_INCREMENT,
    Name varchar(128) NOT NULL,
    StartTime timestamp NOT NULL,
    TargetServer varchar(128) NOT NULL,
    PCIVersion varchar(16) NOT NULL,
    DatabaseVersion varchar(16) NOT NULL,
    PCIInstallVersion varchar(16) NOT NULL,
    LicenseGroup varchar(128) NOT NULL,
    EndTime timestamp NOT NULL,
    Status varchar(32) NOT NULL,
    PRIMARY KEY (ID)
    );
    */


}
