package vm.db.po;

public class ResultFile {

	private String resultFileID;

	private long ctime;

	private long utime;

	private String note;

	private int flag;

	private String path;

	private String taskID;

	private String statusID;

	public String getResultFileID() {
		return resultFileID;
	}

	public void setResultFileID(String resultFileID) {
		this.resultFileID = resultFileID;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	//toString
	public String toString() {
		String rf = this.getResultFileID() + " " + this.getCtime() + " " + this.getUtime() + " "
				+ "" + this.getNote() + " " + this.getNote() + " " + this.getPath() + " " + this.getStatusID() + " "
						+ "" + this.getStatusID();  
		return rf;
	}
}
