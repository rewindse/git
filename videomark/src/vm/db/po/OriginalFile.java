package vm.db.po;

public class OriginalFile {
	private String originalFileID;

	private long ctime;

	private long utime;

	private String note;

	private int flag;

	private String path;

	private String taskID;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	private String name;

	private String pid;

	private boolean isParent;

	public String getOriginalFileID() {
		return originalFileID;
	}

	public void setOriginalFileID(String originalFileID) {
		this.originalFileID = originalFileID;
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

	@Override
	public String toString() {
		String orifile = "originalFileID=" + originalFileID + ", ctime=" + ctime + ", utime=" + utime + ", note=" + note
				+ ", flag=" + flag + ", path=" + path + ", taskID=" + taskID;
		return orifile;
	}

}
