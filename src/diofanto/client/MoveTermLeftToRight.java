package diofanto.client;

public class MoveTermLeftToRight extends Command {
	
	Term term;
	int idx;
	
	public MoveTermLeftToRight(Equation eq, int idx) {
		super(eq);
		this.idx = idx;
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
