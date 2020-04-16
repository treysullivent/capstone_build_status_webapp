import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

import java.util.Date;
import java.util.Locale;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ParsedXML {

	private String fileName;
	private String data;
	private ArrayList<Job> jobs;
    private ArrayList<Notification> notifications;
	

	public ParsedXML() throws FileNotFoundException, ParseException {
		this.fileName = "";
		this.data = "";
		this.jobs = new ArrayList<Job>();
		this.notifications = new ArrayList<Notification>();
	}
	
	public ParsedXML(String fileName) throws FileNotFoundException, ParseException {
		this.fileName = fileName;
		this.data = ReadFile(fileName);
		this.jobs = new ArrayList<Job>();
		this.notifications = new ArrayList<Notification>();
		CreateJobs();
        EndJobs();
		CreateNotifications();	
	}

	public ArrayList<Job> GetJobs()
	{
		return jobs;
	}
	
	public ArrayList<Notification> GetNotifications()
	{
		return notifications;
	}
	

	private void CreateJobs() throws ParseException
	{			
		String clientName = "";
		String serverName = "";
		String[] pathPieces = this.fileName.split("/");
		for (int i = 0; i < pathPieces.length; i++)
		{
			if (pathPieces[i].equals("write"))
			{
				clientName = pathPieces[i-1];
				serverName = pathPieces[i+1];
			}
		}
		
		String[] dataArray = this.data.split("<JOB_START>");
        for (int i = 1; i < dataArray.length; i ++)
        {
        	Job newJob = new Job();
			newJob.setClientName(clientName);
			newJob.setServerName(serverName);
        	newJob.setName(ParseTag("JOB_NAME", dataArray[i]));
        	newJob.setStartTime(GetTimestamp(ParseTag("START_TIME", dataArray[i])));
            newJob.setTargetServer(ParseTag("TARGET_SERVERS", dataArray[i]));
            newJob.setPciVersion(ParseTag("PCI_VERSION", dataArray[i]));
            newJob.setDatabaseVersion(ParseTag("DATABASE_VERSION", dataArray[i]));
            newJob.setPciInstallVersion(ParseTag("PCI_INSTALL_VERSION", dataArray[i]));
            newJob.setLicenseGroup(ParseTag("LICENSE_GROUP", dataArray[i]));			
			this.jobs.add(newJob);
        }
	}

    private void EndJobs() throws ParseException
    {
        String[] dataArray = this.data.split("<JOB_END>");
        for (int i = 1; i < dataArray.length; i ++)
        {
            String endName = dataArray[i].substring(14, dataArray[i].indexOf("</END_JOB_NAME>"));
            for (int j = 0; j < this.jobs.size(); j ++)
            {
				Job job = this.jobs.get(j); 
                if (job.getStatus() == null && endName.equals(job.getName()))
                {
					job.setEndTime(GetTimestamp(ParseTag("END_TIME", dataArray[i])));
					job.setStatus(ParseTag("STATUS", dataArray[i]));
                    break;
                }
            }
        }
    }
	
	private void CreateNotifications() throws ParseException
    {		        
		String[] dataArray = this.data.split("<NOTIFICATION>");
        for (int i = 1; i < dataArray.length; i ++)
        {
            Notification notification = new Notification();
            notification.setJobName(ParseTag("JOB_NAME", dataArray[i]));
            notification.setType(ParseTag("TYPE", dataArray[i]));
            notification.setTime(GetTimestamp(ParseTag("TIME", dataArray[i])));
            notification.setMessage(ParseTag("NOTIFICATION_MESSAGE", dataArray[i]));
            this.notifications.add(notification);
        }
    }
	
	private static String ReadFile(String file) throws FileNotFoundException {
        String data = "";
		
		File xmlRecords = new File(file);
        Scanner myReader = new Scanner(xmlRecords);
        while (myReader.hasNextLine()) {
          data += myReader.nextLine();
        }
        myReader.close();
		
		return data;
    }
	
	
	private static String ParseTag(String tag, String jobData) {
		String[] dataArray = jobData.split("<" + tag + ">");
		String dataLine;
		dataLine = dataArray[1].substring(0, dataArray[1].indexOf("</" + tag + ">"));
		return dataLine;
	}
	
	private static java.sql.Timestamp GetTimestamp(String timeString) throws ParseException {
		SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
		java.sql.Timestamp timeStamp = new java.sql.Timestamp(dateParser.parse(timeString).getTime());
		return timeStamp;
	}
}
