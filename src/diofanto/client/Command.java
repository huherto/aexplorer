package diofanto.client;

abstract class Command {
	Equation eq;
	Command(Equation eq) {
		this.eq = eq;
	}
	public abstract double calcDistance(double x, double y);
	public abstract void apply();
	public abstract void undo();
}