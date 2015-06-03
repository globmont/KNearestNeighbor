package visualization;

public class FocusListener implements ButtonListener {

	Visualization parent;
	int obsNumber = 1;
	
	public FocusListener(Visualization parent) {
		this.parent = parent;
	}
	
	@Override
	public void trigger() {
		parent.o.text = "" + obsNumber;
		parent.r.w.active = false;
		parent.r.trigger();
		
	}

}
