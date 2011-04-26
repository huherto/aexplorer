package diofanto.client;

import java.util.List;

class MoveTerm extends Command {
	
	Term term;
	List<Term> fromList;
	int fromIdx;
	List<Term> toList;
	int toIdx;

	public MoveTerm(Equation eq, List<Term> fromList, int fromIdx, List<Term> toList, int toIdx) {
		super(eq);
		this.fromList = fromList;
		this.fromIdx = fromIdx;
		this.toList = toList;
		this.toIdx = toIdx;
	}

	@Override
	public void apply() {
		term = fromList.remove(fromIdx);
		toList.add(toIdx, term);
		if (fromList != toList) {
			term.toggleSign();
		}
	}

	@Override
	public void undo() {
		if (toList.remove(toIdx) != term) {
			throw new IllegalStateException("");
		}
		fromList.add(fromIdx, term);
		if (fromList != toList) {
			term.toggleSign();
		}
	}
}