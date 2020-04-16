import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;


public class Parser
{
    
    public static void main(String[] args) throws  FileNotFoundException
    {  
        String fileName = args[0];
        String data = ReadFile(fileName);
        String[] csvStrings = ParseXMLtoCSVs(data);
        
        for (int i = 0; i < csvStrings.length; i++) CSVtoString(csvStrings[i]);
    }
    
    
    
    /**
    *   Produces an array of CSV strings in the format 
    *   "Name, StartTime, TargetServers,PCIVersion,DatabaseVersion, PCIInstallVersion, LicenseGroup, EndTime, Status"
    **/
    public static String[] ParseXMLtoCSVs(String data){
        
        //Parse multiple install records into their parts, keeping them ordered by ArrayList index
        ArrayList<String> pNames = ParseByTag(data, "JOB_NAME");
        ArrayList<String> pStartTimes = ParseByTag(data, "START_TIME");
        ArrayList<String> pTargetServers = ParseByTag(data, "TARGET_SERVERS");       
        ArrayList<String> pPCIVersion = ParseByTag(data, "PCI_VERSION");        
        ArrayList<String> pDatabaseVersion = ParseByTag(data, "DATABASE_VERSION");
        ArrayList<String> pPCIInstallVersion = ParseByTag(data, "PCI_INSTALL_VERSION");      
        ArrayList<String> pLicenseGroup = ParseByTag(data, "LICENSE_GROUP");       
        ArrayList<String> pEndTime = ParseByTag(data, "END_TIME");               
        ArrayList<String> pStatus = ParseByTag(data, "STATUS");
        
        
        String[] csvData = new String[pStatus.size()];
        for (int i = 0; i < pStatus.size(); i ++)
        {
            csvData[i] = pNames.get(i)+ ",";
            csvData[i] += pStartTimes.get(i)+ ",";
            csvData[i] += pTargetServers.get(i)+ ",";
            csvData[i] += pPCIVersion.get(i)+ ",";
            csvData[i] += pDatabaseVersion.get(i)+ ",";
            csvData[i] += pPCIInstallVersion.get(i)+ ",";
            csvData[i] += pLicenseGroup.get(i)+ ",";
            csvData[i] += pEndTime.get(i)+ ",";
            csvData[i] += pStatus.get(i);
        }
        
        return csvData;
        
    }
    
        /**
    *   Converts a CSV of the format
    *   "Name, StartTime, TargetServers,PCIVersion,DatabaseVersion, PCIInstallVersion, LicenseGroup, EndTime, Status"
    *   into a PreparedStatement on the connection 'con' to insert the CSV into the database
    **/
    private static void CSVtoString(String csv)
    {
        String[] tokens = csv.split(",");
    
        //Populate PreparedStatement with data from CSV
        String output = "";

        output += ("Name : " + tokens[0])+ "\n";
        output += ("Start time : " + tokens[1])+ "\n";
        output += ("Target servers : " + tokens[2])+ "\n";
        output += ("PCI version : " + tokens[3])+ "\n";
        output += ("Database version : " + tokens[4])+ "\n";
        output += ("PCI install version : " + tokens[5])+ "\n";
        output += ("License group : " + tokens[6])+ "\n";
        output += ("End time : " + tokens[7])+ "\n";
        output += ("Status : " + tokens[8])+ "\n";
        output += ("==============================================")+ "\n";

        System.out.print(output);
    }
    

    public static String ReadFile(String fileName) throws FileNotFoundException {
        String data = "";
        File xmlRecords = new File(fileName);
        Scanner myReader = new Scanner(xmlRecords);
        while (myReader.hasNextLine()) {
          data += myReader.nextLine();
        }
        myReader.close();
        return data;
    }

    private static ArrayList<String> ParseByTag(String fullText, String tagBody) {
        String openTag = "<" + tagBody +  ">";
        String closeTag = "</" + tagBody +  ">";
        
        
        String[] dataArray = fullText.split(openTag);
        ArrayList<String> pData = new ArrayList<String>();
        for (int i = 1; i < dataArray.length; i ++)
        {
            pData.add(dataArray[i].substring(0, dataArray[i].indexOf(closeTag)));
        }
        return pData;
    }

}
