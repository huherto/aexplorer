package diofanto.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

public class DiofantoCanvas {
	
	protected final Canvas canvas;

	static final int canvasWidth  = 400;
	static final int canvasHeight = 400;
	
	Container root = new Container();
	
//	List<Block> blocks = new ArrayList<Block>();
	Block focused = null;
	Block mouseDownOn = null;
	
	public DiofantoCanvas(Canvas canvas) {
		this.canvas = canvas;
		canvas.setStyleName("mainCanvas");
		canvas.setWidth(canvasWidth + "px");
		canvas.setCoordinateSpaceWidth(canvasWidth);

		canvas.setHeight(canvasHeight + "px");
		canvas.setCoordinateSpaceHeight(canvasHeight);
		
		canvas.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Block block = root.findBlock(event.getX(), event.getY());
				if (block != null) {
					block.clicked();
					setFocus(block);
				}
				displayAll();
			}

		});
		
		canvas.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				System.out.println("Mouse down");
				mouseDownOn = root.findBlock(event.getX(), event.getY());
				if (mouseDownOn != null) {
					//setFocus(mouseDownOn);
				}
			}
		});
		
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				if (mouseDownOn != null) {
					// Center on the mouse position.
					double x = event.getX() - mouseDownOn.width/2;
					double y = event.getY() - mouseDownOn.height/2;
					mouseDownOn.moveTo(x, y);
					displayAll();
				}
			}
		});
		
		final DiofantoCanvas parent = this;
		canvas.addMouseUpHandler(new MouseUpHandler() {
			
			@Override
			public void onMouseUp(MouseUpEvent event) {
				if (mouseDownOn != null) {
					Block block = mouseDownOn;
					mouseDownOn = null;					
					block.onDrop(parent);
				}
			}
		});
	}
	
	public Context2d c2d() {
		return canvas.getContext2d();
	}
		
	public void setFocus(Block block) {
		if (focused != block) {
			if (focused != null)
				focused.setFocus(false);
			block.setFocus(true);
			//block.display(c2d());
		}
		focused = block;
	}

	public void clear() {
		clear(0, 0, canvasWidth, canvasHeight);
	}
	
	protected void clear(double x, double y, double width, double height) {
		Context2d c2d = c2d();
		c2d.setFillStyle("#FFF");
		c2d.fillRect(x, y, width, height);
		//c2d.fill();
	}

	public void resizeAll() {
		root.resize(c2d());
	}
	
	public void displayAll() {
		clear();
		root.display(c2d());
	}

	public void add(Block block) {
		root.add(block);
	}

}
