package path;

public abstract class State extends core.State {
	State predecessor;
	
	public State getPredecessor() {
		return predecessor;
	}
	
	public void setPredecessor(State predecessor) {
		this.predecessor = predecessor;
	}
}
