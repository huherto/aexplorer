package diofanto.client;

public class MoveTermRightToLeft extends Command {

	
	Term term;
	int idx;
	
	public MoveTermRightToLeft(Equation eq, int idx) {
		super(eq);
		this.idx = idx;
	}
	
	@Override
	public void apply() {
		term = eq.right.remove(idx);
		eq.left.add(term);
		term.toggleSign();
	}

	@Override
	public void undo() {
		eq.left.remove(eq.right.size() - 1);
		eq.right.add(idx, term);
		term.toggleSign();
	}

}
