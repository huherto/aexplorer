	 	package diofanto.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Equation implements BlockEventHandler {
	
	List<Term> left = new ArrayList<Term>();
	private TextBlock equal = new TextBlock("=");
	List<Term> right = new ArrayList<Term>();
	
	Container container = new Container();
	Set<DropTarget> dropTargets = null;

	public Equation() {
		equal.addHandler(this);
	}
	
	public void addLeft(Term term) {
		left.add(term);
		term.block.addHandler(this);		
	}
	
	public void addRight(Term term) {
		right.add(term);
		term.block.addHandler(this);		
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
		int count = 0;
		for(Term  term : left) {
			if (count > 0 || term.negative) {
				if (term.negative)
					container.add(new TextBlock("-"));
				else 
					container.add(new TextBlock("+"));					
			}
			container.add(term.block);				
			count++;
		}
		if (left.size() == 0) {
			Block block = new TextBlock("0");
			block.addHandler(this);
			container.add(block);
		}
		container.add(equal);
		count = 0;
		for(Term  term : right) {
			if (count > 0 || term.negative) {
				if (term.negative)
					container.add(new TextBlock("-"));
				else 
					container.add(new TextBlock("+"));					
			}
			container.add(term.block);
			count++;
		}
		if (right.size() == 0) {
			Block block = new TextBlock("0");
			block.addHandler(this);
			container.add(block);
		}
		container.hasBorder = false;
	}

	private Term findLeftTerm(Block block) {
		for(Term term : left) {
			if (term.block == block) {
				return term;
			}
		}		
		return null;
	}

	private Term findRightTerm(Block block) {
		for(Term term : right) {
			if (term.block == block) {
				return term;
			}
		}		
		return null;
	}

	@Override
	public void onBeforeMove(Block block, DiofantoCanvas canvas) {
		if (dropTargets == null) {
			Term term = findLeftTerm(block);
			if (term == null) {
				term = findRightTerm(block);
				Assert.notNull(term);
			}
			dropTargetsForTerm(term);
		}
	}

	@Override
	public void onMove(Block block, DiofantoCanvas canvas) {
		canvas.displayAll();
		DropTarget target = closest(block);
		if (target != null)
			canvas.displayDropTarget(target);
	}

	@Override
	public void onDrop(Block block, DiofantoCanvas canvas) {
		DropTarget target = closest(block);
		if (target != null) {
			System.out.println("apply cmd");
			target.cmd.apply();
		}
		dropTargets = null;				
		layout(canvas);
		canvas.displayAll();
	}
	
	private void dropTargetsForTerm(Term term) {
		dropTargets = new HashSet<DropTarget>();
		// Left side
		for(Term refTerm:left) {
			if (term != refTerm) {
				DropTarget target = moveLeft(term, refTerm);
				Block block = refTerm.block;
				target.moveLeftTo(block);
				dropTargets.add(target);
			}
		}
		// After right side.
		{   
			DropTarget target = appendTerm(term, left);
			target.moveLeftTo(equal);
			dropTargets.add(target);
		}
		// Right side
		for(Term refTerm:right) {
			if (term != refTerm) {
				DropTarget target = moveLeft(term, refTerm);
				Block block = refTerm.block;
				target.moveLeftTo(block);
				dropTargets.add(target);
			}
		}
		// Far right
		DropTarget target = appendTerm(term, right);
		if (right.size() == 0)
		{
			target.moveRightTo(equal);
		}
		else {
			Term lastTerm = right.get(right.size() - 1);
			target.moveRightTo(lastTerm.block);
		}
		dropTargets.add(target);			
	}
	
	private DropTarget appendTerm(Term term, List<Term> toList) {
		List<Term> fromList = left;
		int fromIdx = fromList.indexOf(term);
		if (fromIdx < 0) {
			fromList = right;
			fromIdx = fromList.indexOf(term);
			Assert.isTrue(fromIdx >= 0);
		}
		int toIdx = toList.size();
		if (fromList == toList) {
			if (fromIdx < toIdx) {
				toIdx--;
				Assert.isTrue(toIdx >= 0);
			}
		}
		Command cmd = new MoveTerm(this, fromList, fromIdx, toList, toIdx);
		return new DropTarget(cmd);
	}

	private DropTarget moveLeft(Term term, Term refTerm) {
		List<Term> fromList = left;
		int fromIdx = fromList.indexOf(term);
		if (fromIdx < 0) {
			fromList = right;
			fromIdx = fromList.indexOf(term);
			Assert.isTrue(fromIdx >= 0);
		}
		List<Term> toList = left;
		int toIdx = toList.indexOf(refTerm);
		if (toIdx < 0) {
			toList = right;
			toIdx = toList.indexOf(refTerm);
			Assert.isTrue(toIdx >= 0);
		}
		if (fromList == toList) {
			if (fromIdx < toIdx) {
				toIdx--;
			}
		}
		Command cmd = new MoveTerm(this, fromList, fromIdx, toList, toIdx);
		return new DropTarget(cmd);
	}

	private DropTarget closest(Block block) {
		if (dropTargets == null)
			return null;
		DropTarget closest = null;
		double minDistance = Double.MAX_VALUE;
		for(DropTarget target:dropTargets) {
			double distance = block.centerXDistance(target);
			if (distance < minDistance) {
				minDistance = distance;
				closest = target;
			}
		}
		return closest;
	}
}
