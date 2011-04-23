package diofanto.client;

import com.google.gwt.canvas.dom.client.Context2d;

public class DropTarget extends Block {
	
	boolean visible = true;
	
	Command cmd;
	
	public DropTarget(Command cmd) {
		this.cmd = cmd;
		margins.left = margins.right = 0;
		border.left = border.right = 0;
		padding.left = padding.right = 0;
	}

	public void display(Context2d c2d) {
		resize(c2d);
		if (visible) {
			c2d.setFillStyle("#06C");
			double startX = x + margins.left + border.left + padding.left;
			double startY = y + margins.top + border.top + padding.top;
			c2d.fillRect(startX, startY, contentsWidth(c2d), contentsHeight());
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public double contentsWidth(Context2d c2d) {
		return 3;
	}

	@Override
	public double contentsHeight() {
		return TextBlock.textHeight;
	}
}
