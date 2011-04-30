package diofanto.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Diofanto implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	
		Canvas canvas = Canvas.createIfSupported();
		if (canvas == null) {
			RootPanel
					.get()
					.add(new Label(
							"Sorry, your browser doesn't support the HTML5 Canvas element"));
			return;
		}
		canvas.setStyleName("canvas");
		RootPanel.get("canvas").add(canvas);
		//RootPanel.get().add(canvas);
		DiofantoCanvas diofantoCanvas = new DiofantoCanvas(canvas);
		
		Equation eq = new Equation();
		eq.addLeft(new Term("a"));
		eq.addLeft(new Term("b"));
		eq.addLeft(new Term("c"));
		eq.addRight(new Term("5"));
		eq.layout(diofantoCanvas);
		
		canvas.setFocus(true);
	}
}
