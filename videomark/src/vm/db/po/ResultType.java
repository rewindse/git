package vm.db.po;

public class ResultType {
	private String resultTypeID;

	private long ctime;

	private long utime;

	private String note;

	private int flag;

	private String typeName;

	public String getResultTypeID() {
		return resultTypeID;
	}

	public void setResultTypeID(String resultTypeID) {
		this.resultTypeID = resultTypeID;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
