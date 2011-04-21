package diofanto.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.canvas.dom.client.TextMetrics;

public class TextBlock extends Block {

	private String text;
	
	public static final double textHeight = 30;
	
	public TextBlock(String text) {
		this.text = text;
	}

	@Override
	public double contentsWidth(Context2d c2d) {
		if (text != null) {
			c2d.setFont(textHeight+"px Arial");
			TextMetrics metrics = c2d.measureText(text);
			return metrics.getWidth();
		}
		return 0;
	}

	@Override
	public double contentsHeight() {		
		return textHeight * 1.2;
	}
		
	@Override
	public void display(Context2d c2d) {
		super.display(c2d);
		c2d.setFillStyle("#111");
		c2d.setTextAlign(TextAlign.LEFT);
		c2d.setTextBaseline(TextBaseline.TOP);
		c2d.setFont(textHeight+"px Arial");
		
		double startX = x + margins.left + border.left + padding.left;
		double startY = y + margins.top + border.top + padding.top;
		
		c2d.fillText(text, startX, startY);
		
	}

	@Override
	public void clicked() {
		System.out.println("'"+text+"' was clicked");
	}
	
	public TextBlock deepCopy() {
		TextBlock block = new TextBlock(text);
		return (TextBlock)deepCopy(block);
	}
	
}
