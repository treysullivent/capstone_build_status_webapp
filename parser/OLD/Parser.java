import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
	static String data = "";
	static ArrayList<Job> Jobs = new ArrayList<Job>();
    static ArrayList<Notification> notifications = new ArrayList<Notification>();
	
	public static void main(String[] args) throws FileNotFoundException {
		ReadFile(args[0]);
		CreateJobs();
        EndJobs();
        GetNotifications();
		for (int i = 0; i < Jobs.size(); i++)
		{
			System.out.println(Jobs.get(i));
		}

        for(int i = 0; i < notifications.size(); i ++)
        {
            System.out.println(notifications.get(i));
        }
	}

	public static void CreateJobs()
	{
		String[] dataArray = data.split("<JOB_START>");
        for (int i = 1; i < dataArray.length; i ++)
        {
        	Job newJob = new Job();
        	newJob.setName(Parse("JOB_NAME", dataArray[i]));
        	newJob.setStartTime(Parse("START_TIME", dataArray[i]));
            newJob.setTargetServer(Parse("TARGET_SERVERS", dataArray[i]));
            newJob.setPciVersion(Parse("PCI_VERSION", dataArray[i]));
            newJob.setDatabaseVersion(Parse("DATABASE_VERSION", dataArray[i]));
            newJob.setPciInstallVersion(Parse("PCI_INSTALL_VERSION", dataArray[i]));
            newJob.setLicenseGroup(Parse("LICENSE_GROUP", dataArray[i]));
        	Jobs.add(newJob);
        }
	}

    public static void EndJobs()
    {
        String[] dataArray = data.split("<JOB_END>");
        for (int i = 1; i < dataArray.length; i ++)
        {
            String endName = dataArray[i].substring(14, dataArray[i].indexOf("</END_JOB_NAME>"));
            for (int j = 0; j < Jobs.size(); j ++)
            {
                if (endName.equals(Jobs.get(j).getName()))
                {
                    Jobs.get(j).setEndTime(Parse("END_TIME", dataArray[i]));
                    Jobs.get(j).setStatus(Parse("STATUS", dataArray[i]));
                    break;
                }
            }
        }
    }

    public static void GetNotifications()
    {
        String[] dataArray = data.split("<NOTIFICATION>");
        for (int i = 1; i < dataArray.length; i ++)
        {
            Notification notification = new Notification();
            notification.setJobName(Parse("JOB_NAME", dataArray[i]));
            notification.setType(Parse("TYPE", dataArray[i]));
            notification.setTime(Parse("TIME", dataArray[i]));
            notification.setMessage(Parse("NOTIFICATION_MESSAGE", dataArray[i]));
            notifications.add(notification);
        }
    }
	
	public static void ReadFile(String file) throws FileNotFoundException {
        File xmlRecords = new File(file);
        Scanner myReader = new Scanner(xmlRecords);
        while (myReader.hasNextLine()) {
          data += myReader.nextLine();
        }
        myReader.close();
    }
	
	public static String Parse(String tag, String jobData) {
		String[] dataArray = jobData.split("<" + tag + ">");
		String dataLine;
		dataLine = dataArray[1].substring(0, dataArray[1].indexOf("</" + tag + ">"));
		return dataLine;
	}
}
