package diofanto.client;

class MoveRightTerm extends Command {
	
	int fromIdx;
	int toIdx;
	Term term;

	public MoveRightTerm(Equation eq, int fromIdx, int toIdx) {
		super(eq);
		this.fromIdx = fromIdx;
		this.toIdx = toIdx;
	}

	@Override
	public void apply() {
		term = eq.right.remove(fromIdx);
		eq.right.add(toIdx, term);
	}

	@Override
	public void undo() {
		if (eq.right.remove(toIdx) != term) {
			throw new IllegalStateException("");
		}
		eq.right.add(fromIdx, term);
	}
}