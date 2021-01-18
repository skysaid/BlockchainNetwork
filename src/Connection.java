import java.util.List;

public class Connection {
	public boolean alive;
	public Request request;
	public Path path;
	public int slots_start;
	public int slots_end;


	public Connection(boolean alive, Request request, Path path, int slots_start, int slots_end) {
		this.alive = alive;
		this.request = request;
		this.path = path;
		this.slots_start = slots_start;
		this.slots_end = slots_end;
	}

	public boolean isAlive() {
		return alive;
	}

	public Request getRequest() {
		return request;
	}

	public Path getPath() {
		return path;
	}

	public int getSlots_start() {
		return slots_start;
	}

	public int getSlots_end() {
		return slots_end;
	}
}