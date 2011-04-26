package diofanto.client;

import junit.framework.TestCase;


public class TestCommand extends TestCase {
	
	public void testMoveLeftTerm() {
		Equation eq = new Equation();
		eq.addLeft(new Term("a"));
		eq.addLeft(new Term("b"));
		eq.addLeft(new Term("c"));
		
		Command cmd = new MoveTerm(eq, eq.left, 0, eq.left, 0);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		
		cmd = new MoveTerm(eq, eq.left, 0, eq.left, 1);
		cmd.apply();
		assertEquals("b", eq.left.get(0).text);
		assertEquals("a", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveTerm(eq, eq.left, 0, eq.left, 2);
		cmd.apply();
		assertEquals("b", eq.left.get(0).text);
		assertEquals("c", eq.left.get(1).text);
		assertEquals("a", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveTerm(eq, eq.left, 1, eq.left, 0);
		cmd.apply();
		assertEquals("b", eq.left.get(0).text);
		assertEquals("a", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		
		cmd = new MoveTerm(eq, eq.left, 1, eq.left, 1);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveTerm(eq, eq.left, 1, eq.left, 2);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("c", eq.left.get(1).text);
		assertEquals("b", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveTerm(eq, eq.left, 2, eq.left, 0);
		cmd.apply();
		assertEquals("c", eq.left.get(0).text);
		assertEquals("a", eq.left.get(1).text);
		assertEquals("b", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveTerm(eq, eq.left, 2, eq.left, 1);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("c", eq.left.get(1).text);
		assertEquals("b", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveTerm(eq, eq.left, 2, eq.left, 2);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

	}
	
	public void testMoveTermLeftToRight() {
		Equation eq = new Equation();
		eq.addLeft(new Term("a"));
		eq.addLeft(new Term("b"));
		eq.addLeft(new Term("c"));
		eq.addRight(new Term("5"));
		
		Command cmd = new MoveTerm(eq, eq.left, 0, eq.right, eq.right.size());
		cmd.apply();
		assertEquals("b", eq.left.get(0).text);
		assertEquals("c", eq.left.get(1).text);
		assertEquals("5", eq.right.get(0).text);
		assertEquals("a", eq.right.get(1).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		assertEquals("5", eq.right.get(0).text);
		
		cmd = new MoveTerm(eq, eq.left, 1, eq.right, eq.right.size());
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("c", eq.left.get(1).text);
		assertEquals("5", eq.right.get(0).text);
		assertEquals("b", eq.right.get(1).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		assertEquals("5", eq.right.get(0).text);
		
	}

}
