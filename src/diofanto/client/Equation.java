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
		for(Term  term : left) {
			if (!container.blocks.isEmpty()) {
				if (term.negative)
					container.add(new TextBlock("-"));
				else 
					container.add(new TextBlock("+"));					
			}
			container.add(term.block);				
		}
		if (left.size() == 0) {
			Block block = new TextBlock("0");
			block.addHandler(this);
			container.add(block);
		}
		container.add(equal);
		int count = 0;
		for(Term  term : right) {
			if (count > 0) {
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
			if (term != null) {
				dropTargetsForLeftMember(term);		
				return;
			}
			term = findRightTerm(block);
			if (term != null) {
				dropTargetsForRightMember(term);		
				return;
			}
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
			target.cmd.apply();
		}
		dropTargets = null;				
		layout(canvas);
		canvas.displayAll();
	}
	
	private void dropTargetsForLeftMember(Term term) {
		dropTargets = new HashSet<DropTarget>();
		int idx = left.indexOf(term);
		for(int i = 0; i < left.size(); i++) {
			Command cmd = new MoveTerm(this, left, idx, left, i);
			DropTarget target = new DropTarget(cmd);
			Block block = left.get(i).block;
			target.moveLeftTo(block);
			dropTargets.add(target);
		}
		if (left.size() > 0) {
			Command cmd = new MoveTerm(this, left, idx, left, left.size());
			DropTarget target = new DropTarget(cmd);
			Block block = left.get(left.size() - 1).block;
			target.moveLeftTo(block);
			dropTargets.add(target);			
		}
		else {
			Command cmd = new MoveTerm(this, left, idx, left, left.size());
			DropTarget target = new DropTarget(cmd);
			target.moveLeftTo(equal);
			dropTargets.add(target);
		}
		for(int i = 0; i < right.size(); i++) {
			Command cmd = new MoveTerm(this, left, idx, right, i);
			DropTarget target = new DropTarget(cmd);
			Block block = right.get(i).block;
			target.moveLeftTo(block);
			dropTargets.add(target);
		}
		if (right.size() > 0) {
			Command cmd = new MoveTerm(this, left, idx, right, right.size());
			DropTarget target = new DropTarget(cmd);
			Block block = right.get(right.size() - 1).block;
			target.moveRightTo(block);
			dropTargets.add(target);			
		}
		else {
			Command cmd = new MoveTerm(this, left, idx, right, 0);
			DropTarget target = new DropTarget(cmd);
			target.moveRightTo(equal);
			dropTargets.add(target);			
		}
	}
	
	private void dropTargetsForRightMember(Term term) {
		dropTargets = new HashSet<DropTarget>();
		int idx = right.indexOf(term);
		for(int i = 0; i < right.size(); i++) {
			Command cmd = new MoveTerm(this, right, idx, right, i);
			DropTarget target = new DropTarget(cmd);
			Block block = right.get(i).block;
			if (i <= idx)
				target.moveLeftTo(block);
			else
				target.moveRightTo(block);
			dropTargets.add(target);
		}
		Command cmd = new MoveTerm(this, right, idx, left, 0);
		DropTarget target = new DropTarget(cmd);
		target.moveLeftTo(equal);
		dropTargets.add(target);
	}

	private DropTarget closest(Block block) {
		if (dropTargets == null)
			return null;
		DropTarget closest = null;
		double minDistance = Double.MAX_VALUE;
		for(DropTarget target:dropTargets) {
			double distance = block.centerDistance(target);
			if (distance < minDistance) {
				minDistance = distance;
				closest = target;
			}
		}
		return closest;
	}
	
}
