
public class Notification {

	private String jobName;
	private String type;
	private String time;
	private String message;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

    public String toString()
    {
        return jobName + "\n" + type + "\n" + time + "\n" + message + "\n" + "========================================";
    }
	
	
}
