/**
 * 记录是哪位user发起的request
 */

public class Event implements Comparable<Event>{
	private int userID; //发起者id
	private Double time;	//发起时间
	private Connection connection;
	private int blockTime=0;

	public Event(int userID, Double time, Connection ongoingConnection) {
		this.userID = userID;
		this.time = time;
		this.connection = ongoingConnection;
	}

	public int getUserID() {
		return userID;
	}

	public Double getTime(){
		return time;
	}

	public Connection getConnection() {
		return connection;
	}

	public int getBlockTime() {
		return blockTime;
	}

	public void addBlockTime() {
		this.blockTime++;
	}

	public int compareTo(Event e){
		return this.time.compareTo(e.getTime());
	}
	
}
