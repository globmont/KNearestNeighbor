package visualization;

public class InstructionsListener implements ButtonListener {

	Visualization parent;
	
	public InstructionsListener(Visualization parent) {
		this.parent = parent;
	}
	
	@Override
	public void trigger() {
		parent.i.active = true;
	}

}
