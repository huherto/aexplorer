package diofanto.client;

import java.util.ArrayList;
import java.util.List;


public class Equation implements DragDropHanlder {
	
	private List<Term> left = new ArrayList<Term>();
	private TextBlock equal = new TextBlock("=");
	private List<Term> right = new ArrayList<Term>();
	
	Container container = new Container();

	public Equation() {
		equal.addDragDropHandler(this);
	}
	
	public void addLeft(Term term) {
		left.add(term);
		term.block.addDragDropHandler(this);		
	}
	
	public void addRight(Term term) {
		right.add(term);
		term.block.addDragDropHandler(this);		
	}

	public void layout(DiofantoCanvas diofantoCanvas) {
		fillContainer();
		diofantoCanvas.root = container;
		diofantoCanvas.resizeAll();
		container.layout();
		double x = (DiofantoCanvas.canvasWidth - container.width)/2;
		double y = (DiofantoCanvas.canvasHeight - container.height)/2;
		container.moveTo(x, y);
		diofantoCanvas.displayAll();
	}

	private void fillContainer() {
		container.removeAll();
		for(Term  term : left) {
			if (!container.blocks.isEmpty()) {
				container.add(new TextBlock("+"));
			}
			container.add(term.block);				
		}
		container.add(equal);
		int count = 0;
		for(Term  term : right) {
			if (count != 0) {
				container.add(new TextBlock("+"));
			}
			container.add(term.block);
			count++;
		}
		container.hasBorder = false;
	}

	@Override
	public void onDrop(Block block, DiofantoCanvas canvas) {
		
		for(Term term : left) {
			if (term.block == block) {
				moveTerm(term);
				layout(canvas);
				canvas.displayAll();
			}
		}
		
		/*
		if (left.block) {
			block.moveRightTo(equal);
		}
		else if (block == right.block) {
			
		}
		else if (block == equal) {
			left.block.moveRightTo(equal);
			right.block.moveLeftTo(equal);
		}
		*/
	}

	private void moveTerm(Term term) {
		left.remove(term);
		left.add(0, term);
	}
	


}
