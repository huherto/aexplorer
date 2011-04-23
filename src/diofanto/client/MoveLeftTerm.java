package diofanto.client;

class MoveLeftTerm extends Command {
	
	int fromIdx;
	int toIdx;
	Term term;

	public MoveLeftTerm(Equation eq, int fromIdx, int toIdx) {
		super(eq);
		this.fromIdx = fromIdx;
		this.toIdx = toIdx;
	}

	@Override
	public void apply() {
		term = eq.left.remove(fromIdx);
		eq.left.add(toIdx, term);
	}

	@Override
	public void undo() {
		if (eq.left.remove(toIdx) != term) {
			throw new IllegalStateException("");
		}
		eq.left.add(fromIdx, term);
	}
}