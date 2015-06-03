package visualization;

public class KValueListener implements TextFieldListener{
	
	TextField displayField;
	
	public KValueListener(TextField displayField) {
		this.displayField = displayField;
	}
	
	@Override
	public void trigger(TextField f) {
		int val = (f.text.equals("")) ? 0 : Integer.parseInt(f.getValue());
		displayField.text = "" + (val + 2);
		
	}

}
