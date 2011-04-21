package diofanto.client;

import java.util.ArrayList;
import java.util.List;

public class Equation implements DragDropHanlder {
	
	List<Term> left = new ArrayList<Term>();
	private TextBlock equal = new TextBlock("=");
	List<Term> right = new ArrayList<Term>();
	
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

	public void layout() {
		fillContainer();
		container.layout();
		double x = (DiofantoCanvas.canvasWidth - container.width)/2;
		double y = (DiofantoCanvas.canvasHeight - container.height)/2;
		container.moveTo(x, y);
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
				System.out.println("moving left term");
				moveLeftTerm(term);
				layout(canvas);
				canvas.displayAll();
				return;
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

	private void moveLeftTerm(Term term) {
		int idx = left.indexOf(term);
		double x = term.block.x;
		double y = term.block.y;
		System.out.println("initial x="+x);
		List<Command> cmdList = new ArrayList<Command>();
		for(int i = 0; i < left.size(); i++) {
			Command cmd = new MoveLeftTerm(this, idx, i);
			cmdList.add(cmd);
		}
		
		Command cmd = new MoveTermLeftToRight(this, idx);
		cmdList.add(cmd);
		
		applyBestOption(cmdList, x, y);
	}
	
	protected void applyBestOption(List<Command> cmdList, double x, double y) {
		Command bestOption = null;
		double minDistance = Double.MAX_VALUE;
		for(Command cmd : cmdList) {
			double distance = cmd.calcDistance(x, y);
			if (distance < minDistance) {
				bestOption = cmd;
				minDistance = distance;
			}
		}
		bestOption.apply();
	}
	
	public Equation deepCopy() {
		Equation newEq = new Equation();
		for(Term term : newEq.left) {
			newEq.left.add(term.deepCopy());
		}
		newEq.equal = newEq.equal;
		for(Term term : newEq.right) {
			newEq.right.add(term);
		}		
		return newEq;
	}

}
