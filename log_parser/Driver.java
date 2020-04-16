import java.sql.*;    

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.ArrayList;
import java.text.ParseException;



public class Driver {

	private static final boolean DEBUG = true;

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, FileNotFoundException, SQLException, ParseException
    {  
		ArrayList<File> files = new ArrayList<File>();

		String path = args[0];

		File file = new File(path);

		if (file.isFile())
		{
			files.add(file);
		}
		else if (file.isDirectory())
		{
			addAllFiles(path, files);
		}
		 
		
		// Get all files from a directory.
		
			
		System.out.println(files.size());
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pci_database?useSSL=false","root","root");	
			
		PreparedStatement stmt = null;
		
		for(File f : files)
		{
      
			stmt = null;
			String fileName = f.getAbsolutePath();
			
			if(DEBUG)
				System.out.println("Parsing " + fileName);
	
			ParsedXML p = new ParsedXML(fileName);
			
			ArrayList<Job> jobs = p.GetJobs();
			ArrayList<Notification> notifications = p.GetNotifications();
			
			
			int  jobsAdded = 0;
			int  notificationsAdded = 0;
	
			for (Job j : jobs)
			{
				stmt = JobtoPrepStatement(j, con);
				jobsAdded += stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				int automatic_jobID = rs.getInt(1);
				j.setID(automatic_jobID);
	
				if (j.getType().equals("DOWNLOAD"))
				{
					stmt = DownloadtoPrepStatement(j, con);
					stmt.executeUpdate();
				}
				if (j.getType().equals("EXTRACT"))
				{
					stmt = ExtracttoPrepStatement(j, con);
					stmt.executeUpdate();
				}
				if (j.getType().equals("INSTALL"))
				{
					stmt = InstalltoPrepStatement(j, con);
					stmt.executeUpdate();
				}
			}
			
			for (Notification n : notifications)
			{
				stmt = NotificationtoPrepStatement(n, con);
				notificationsAdded += stmt.executeUpdate();
			}
				
				

			if(DEBUG)
				System.out.println(String.format("Inserted %s jobs, %s notifications", jobsAdded, notificationsAdded));
		}
		
		try { stmt.close(); } catch (Exception e) { /* ignore */ }
		try { con.close(); } catch (Exception e) { /* ignore */ }
		
	}
	



	public static void addAllFiles(String directoryPath, ArrayList<File> files) {
		
		File directory = new File(directoryPath);
		
		File[] fList = directory.listFiles();
			if(fList != null)
			{
				for (File file : fList)
				{      
					if (file.isFile())
					{
						files.add(file);
					} 
					else if (file.isDirectory())
					{
						addAllFiles(file.getAbsolutePath(), files);
					}
				}
			}
	    }
    
    private static PreparedStatement JobtoPrepStatement(Job job, Connection con) throws SQLException, ParseException
    {
        final String INSERT_QUERY = "INSERT INTO jobs " + 
        "(Name, Type) " + 
        "VALUES (?,?);";

        PreparedStatement ps = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        
		int i = 1;
        ps.setString(i++, job.getName());
        ps.setString(i++, job.getType());

		
        return ps;
    }

    private static PreparedStatement DownloadtoPrepStatement(Job job, Connection con) throws SQLException, ParseException
    {
        final String INSERT_QUERY = "INSERT INTO download_jobs " +
        "(ID, Name, ClientName, ServerName, StartTime, TargetServer, PCIVersion, DatabaseVersion, PCIInstallVersion, LicenseGroup, EndTime, Status) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement ps = con.prepareStatement(INSERT_QUERY);
		
		int i = 1;
		ps.setInt(i++, job.getID());
        ps.setString(i++, job.getName());
		ps.setString(i++, job.getClientName());
		ps.setString(i++, job.getServerName());
        ps.setTimestamp(i++, job.getStartTime());
        ps.setString(i++, job.getTargetServer());
        ps.setString(i++, job.getPciVersion());
        ps.setString(i++, job.getDatabaseVersion());
        ps.setString(i++, job.getPciInstallVersion());
        ps.setString(i++, job.getLicenseGroup());
        ps.setTimestamp(i++, job.getEndTime());
        ps.setString(i++, job.getStatus());

        return ps;
    }
    
    private static PreparedStatement ExtracttoPrepStatement(Job job, Connection con) throws SQLException, ParseException
    {
        final String INSERT_QUERY = "INSERT INTO extract_jobs " +
        "(ID, Name, ClientName, ServerName, StartTime, TargetServer, PCIVersion, DatabaseVersion, PCIInstallVersion, LicenseGroup) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement ps = con.prepareStatement(INSERT_QUERY);

		int i = 1;
		ps.setInt(i++, job.getID());
        ps.setString(i++, job.getName());
        ps.setString(i++, job.getClientName());
		ps.setString(i++, job.getServerName());
        ps.setTimestamp(i++, job.getStartTime());
        ps.setString(i++, job.getTargetServer());
        ps.setString(i++, job.getPciVersion());
        ps.setString(i++, job.getDatabaseVersion());
        ps.setString(i++, job.getPciInstallVersion());
        ps.setString(i++, job.getLicenseGroup());
        return ps;
    }

    private static PreparedStatement InstalltoPrepStatement(Job job, Connection con) throws SQLException, ParseException
    {
        final String INSERT_QUERY = "INSERT INTO install_jobs " +
        "(ID, Name, ClientName, ServerName, StartTime, TargetServer, PCIVersion, DatabaseVersion, PCIInstallVersion, LicenseGroup, EndTime, Status) " + 
        "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement ps = con.prepareStatement(INSERT_QUERY);

		int i = 1;
		ps.setInt(i++, job.getID());
        ps.setString(i++, job.getName());
        ps.setString(i++, job.getClientName());
		ps.setString(i++, job.getServerName());
        ps.setTimestamp(i++, job.getStartTime());
        ps.setString(i++, job.getTargetServer());
        ps.setString(i++, job.getPciVersion());
        ps.setString(i++, job.getDatabaseVersion());
        ps.setString(i++, job.getPciInstallVersion());
        ps.setString(i++, job.getLicenseGroup());
        ps.setTimestamp(i++, job.getEndTime());
        ps.setString(i++, job.getStatus());
        return ps;
    }
	
	private static PreparedStatement NotificationtoPrepStatement(Notification notification, Connection con) throws SQLException, ParseException
    {
        final String INSERT_QUERY = "INSERT INTO notifications " +
        "(JobName, Type, Message, Time) " + 
        "VALUES (?, ?, ?, ?);";

        PreparedStatement ps = con.prepareStatement(INSERT_QUERY);
	
		/*
		int jobID = GetJobID(n, con);	
		System.out.println(jobID);
		*/
			
		int i = 1;
		//ps.setInt(i++, job_id);
        ps.setString(i++, notification.getJobName());
        ps.setString(i++, notification.getType());
		ps.setString(i++, notification.getMessage());		
        ps.setTimestamp(i++, notification.getTime());

        return ps;
    }
	
	/*
	private static int GetJobID(Notification n, Connection con) throws SQLException, ParseException
	{
		String type = Job.getTypeByName(n.getJobName()).toLowerCase();

		String JOBID_QUERY = String.format("SELECT ID FROM %s WHERE Name = ? AND StartTime <= ? AND (EndTime = DEFAULT(EndTime) OR EndTime >= ?);", type+"_jobs");
	
        PreparedStatement ps = con.prepareStatement(JOBID_QUERY);
		
		int i = 1;
		ps.setString(i++, n.getJobName());
        ps.setTimestamp(i++, n.getTime());
        ps.setTimestamp(i++, n.getTime());
		
		//System.out.println(ps.toString());
		
		ResultSet jobIDResult = ps.executeQuery();
		if (jobIDResult.next()) {    
			return jobIDResult.getInt(1);
		}
		
        return -1;
	}
	*/
	
}

