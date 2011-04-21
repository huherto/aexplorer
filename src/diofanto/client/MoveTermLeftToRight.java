package diofanto.client;

public class MoveTermLeftToRight extends Command {
	
	Term term;
	int idx;
	

	public MoveTermLeftToRight(Equation eq, int idx) {
		super(eq);
		this.idx = idx;
	}

	@Override
	public double calcDistance(double x, double y) {
		apply();
		eq.layout();
		double distance = Math.abs(term.block.x - x);
		undo();
		return distance;
	}
	
	@Override
	public void apply() {
		term = eq.left.remove(idx);
		eq.right.add(term);
	}

	@Override
	public void undo() {
		eq.right.remove(eq.right.size() - 1);
		eq.left.add(idx, term);
	}

}
