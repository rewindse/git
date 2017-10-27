package vm.db.po;

public class Task {
	
	private String TaskID;
	
	private String taskName;

	private long ctime;
	
	private long utime;
	
	private String note;
	
	private int flag;
	
	private String userID;
	
	private String statusID;
	
	private String resultTypeID;
	
	public String getTaskID() {
		return TaskID;
	}

	public void setTaskID(String taskID) {
		TaskID = taskID;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public long getUtime() {
		return utime;
	}

	public void setUtime(long utime) {
		this.utime = utime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}

	public String getResultTypeID() {
		return resultTypeID;
	}

	public void setResultTypeID(String resultTypeID) {
		this.resultTypeID = resultTypeID;
	}
	
	
	//toString 
	public String toString() {
		String task = "taskID=" + this.getTaskID() + " taskName=" +this.getTaskName()+ " ctime=" + this.getCtime() + " utime=" + this.getUtime() + " "
				+ "note=" + this.getNote() + " flag=" + this.getFlag() + " userID=" + this.getUserID() + " "
						+ "statusID=" + this.getStatusID() + " resulttypeID=" + this.getResultTypeID();
		return task;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
}
