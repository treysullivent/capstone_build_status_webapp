
public class Job {
	
	private String name;
	private String startTime;
	private String targetServer;
	private String pciVersion;
	private String databaseVersion;
	private String pciInstallVersion;
	private String licenseGroup;
	private String endTime;
	private String status;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
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
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
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
		return name + "\n" + startTime + "\n" + targetServer + "\n" +  pciVersion + "\n" + databaseVersion + 
        "\n" + pciInstallVersion + "\n" + licenseGroup + "\n" + endTime + "\n" + status + "\n" + "===================";
	}
}
