import java.sql.Timestamp;    
import java.util.ArrayList;

public class Job {
	
	private int 	id;
	private String 	clientName;
	private String 	serverName;
	private String 	name;
	private java.sql.Timestamp startTime;
	private String 	targetServer;
	private String 	pciVersion;
	private String 	databaseVersion;
	private String 	pciInstallVersion;
	private String 	licenseGroup;
	private java.sql.Timestamp endTime;
	private String 	status;
	
	public String getType()
	{
		return getTypeByName(name);
	}
	
	public static String getTypeByName(String jobName)
	{
		String type = "NULL";
		if (jobName.contains("Download"))
        {
            type = "DOWNLOAD";
        }
        if (jobName.contains("Extract"))
        {
            type = "EXTRACT";
        }
        if (jobName.contains("Install"))
        {
            type = "INSTALL";
        }
		return type;
	}

	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public java.sql.Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(java.sql.Timestamp startTime) {
		this.startTime = startTime;
	}
	public String getTargetServer() {
		return targetServer;
	}
	public void setTargetServer(String targetServer) {
		this.targetServer = targetServer;
	}
	public String getPciVersion() {
		return pciVersion;
	}
	public void setPciVersion(String pciVersion) {
		this.pciVersion = pciVersion;
	}
	public String getDatabaseVersion() {
		return databaseVersion;
	}
	public void setDatabaseVersion(String databaseVersion) {
		this.databaseVersion = databaseVersion;
	}
	public String getPciInstallVersion() {
		return pciInstallVersion;
	}
	public void setPciInstallVersion(String pciInstallVersion) {
		this.pciInstallVersion = pciInstallVersion;
	}
	public String getLicenseGroup() {
		return licenseGroup;
	}
	public void setLicenseGroup(String licenseGroup) {
		this.licenseGroup = licenseGroup;
	}
	public java.sql.Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(java.sql.Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	public String toString()
	{
		return name + "\n" + startTime.toString() + "\n" + targetServer + "\n" +  pciVersion + "\n" + databaseVersion + 
        "\n" + pciInstallVersion + "\n" + licenseGroup + "\n" + endTime.toString() + "\n" + status + "\n" + "===================";
	}
}
