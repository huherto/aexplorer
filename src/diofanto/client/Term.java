package diofanto.client;

public class Term {
	String text;
	Block block;
	public Term(String text) {
		this.text = text;
		block = new TextBlock(text);
	}
	
}
