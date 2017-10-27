package vm.db.po;

public class TaskStatus {

	private String statusID;

	private long ctime;

	private long utime;

	private String note;

	private int flag;

	private String statusName;

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	//toString()
	public String toString(){
		String taskStatus = "statusID=" + this.getStatusID() + " ctime=" + this.getCtime() + " utime=" +this.getUtime() + " "
				+ "note=" + this.getNote() + " flag=" + this.getFlag() + " statusName=" + this.getStatusName();
		return taskStatus;
	}
	
}
