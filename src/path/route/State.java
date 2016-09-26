package path.route;

import java.util.Collection;

public class State extends path.State {
	@Override
	public boolean isGoalState() {
		// TODO
		return false;
	}
	
	@Override
	public Collection<? extends core.State> determineChildren() {
		// TODO
		return null;
	}
}
