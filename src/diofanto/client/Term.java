package diofanto.client;

public class Term {
	boolean negative = false;
	String text;
	Block block;
	public Term(String text) {
		this.text = text;
		block = new TextBlock(text);
	}
	
	void toggleSign() {
		negative ^= true;
	}
	
}
