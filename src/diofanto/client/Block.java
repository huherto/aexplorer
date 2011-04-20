package diofanto.client;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.Context2d.LineJoin;

public abstract class Block {
	
	static class Sides {
	  public double left;
	  public double top;
	  public double right;
	  public double bottom;
	  
	  public Sides(double value) {
		  left = value;
		  top = value;
		  right = value;
		  bottom = value;
	  }
	  
	  public Sides(double l, double t, double r, double b) {
		  left = l;
		  top = t;
		  right = r;
		  bottom = b;
	  }
	};
	
	double x = 0;
	double y = 0;
	double width = 0;
	double height = 0;
	
	boolean hasBorder = true;
	boolean hasFocus = false;
	// The Box model is similar to the one used in CSS.
	protected Sides margins = new Sides(4);
	protected Sides border  = new Sides(3); 
	protected Sides padding = new Sides(4);
	
	List<BlockEventHandler> handlers = new LinkedList<BlockEventHandler>(); 
	
	final static String BORDER_COLOR = "#DDD";
	final static String BORDER_FOCUS_COLOR = "#00A";
	final static String BOX_MODEL_COLOR = "#F00";
	final static boolean debugBoxModel = false;
	
	public Block() {
	}

	public void resize(Context2d c2d) {
		width = contentsWidth(c2d);
		width += margins.left + border.left + padding.left;
		width += margins.right + border.right + padding.right;
		height = contentsHeight();
		height += margins.top + border.top + padding.top;
		height += margins.right + border.right + padding.right;
	}
	
	abstract public double contentsWidth(Context2d c2d);
	
	abstract public double contentsHeight();
	
	public void paintBorder(Context2d c2d) {
		String borderColor = hasFocus ? BORDER_FOCUS_COLOR : BORDER_COLOR;
		c2d.setLineCap(LineCap.ROUND);
		c2d.setStrokeStyle(borderColor);
		c2d.setLineJoin(LineJoin.ROUND);
		
		double startX = x + margins.left + border.left/2;
		double startY = y + margins.top + border.top/2;
		double endX = x + width  - (margins.right + border.right/2);
		double endY = y + height - (margins.bottom + border.bottom/2);
		
		c2d.beginPath();
		c2d.moveTo(startX, startY);
		c2d.setLineWidth(border.top);
		c2d.lineTo(endX, startY);
		c2d.setLineWidth(border.right);
		c2d.lineTo(endX, endY);
		c2d.setLineWidth(border.bottom);
		c2d.lineTo(startX, endY);
		c2d.setLineWidth(border.left);
		c2d.closePath();
		c2d.stroke();
	}
	
	public void paintBoxModel(Context2d c2d) {
		c2d.setLineCap(LineCap.ROUND);
		c2d.setStrokeStyle(BOX_MODEL_COLOR);
		c2d.setLineWidth(1);
		
		// Box surrounding all.
		double startX = x;
		double startY = y;
		double boxWidth = width;
		double boxHeight = height;
		c2d.strokeRect(startX, startY, boxWidth, boxHeight);
		
		// Box surrounding border.
		startX += margins.left;
		startY += margins.top;
		boxWidth  -= margins.left + margins.right;
		boxHeight -= margins.top + margins.bottom;
		c2d.strokeRect(startX, startY, boxWidth, boxHeight);
		
		// Box surrounding padding.
		startX += border.left;
		startY += border.top;
		boxWidth  -= border.left + border.right;
		boxHeight -= border.top  + border.bottom;
		c2d.strokeRect(startX, startY, boxWidth, boxHeight);
		
		// Box surrounding content.
		startX += padding.left;
		startY += padding.top;
		boxWidth  -= padding.left + padding.right;
		boxHeight -= padding.top  + padding.bottom;
		c2d.strokeRect(startX, startY, boxWidth, boxHeight);
	}
	
	public void display(Context2d c2d) {
		if (hasBorder)
			paintBorder(c2d);		
		if (debugBoxModel)
			paintBoxModel(c2d);
	}

	public boolean pointInside(double posx, double posy) {
		if (posx >= x && posx <= x + width) {
			return posy >= y && posy <= y + height;
		}
		return false;
	}

	public void clicked() {
		System.out.println("Block was clicked");
	}

	public void setFocus(boolean focus) {
		hasFocus = focus;
	}
	
	public void addDragDropHandler(DragDropHanlder handler) {
		handlers.add(handler);
	}
	
	public void onDrop(DiofantoCanvas canvas) {
		for(BlockEventHandler handler : handlers) {
			if (handler instanceof DragDropHanlder) {
				((DragDropHanlder) handler).onDrop(this, canvas);
			}
		}
	}

	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveRightTo(Block other) {
		moveTo(other.x + other.width, other.y);
	}

	public void moveLeftTo(Block other) {
		moveTo(other.x - width, other.y);
	}

}
