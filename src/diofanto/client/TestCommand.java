package diofanto.client;

import junit.framework.TestCase;


public class TestCommand extends TestCase {
	
	public void testMoveLeftTerm() {
		Equation eq = new Equation();
		eq.addLeft(new Term("a"));
		eq.addLeft(new Term("b"));
		eq.addLeft(new Term("c"));
		
		Command cmd = new MoveLeftTerm(eq, 0, 0);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		
		cmd = new MoveLeftTerm(eq, 0, 1);
		cmd.apply();
		assertEquals("b", eq.left.get(0).text);
		assertEquals("a", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveLeftTerm(eq, 0, 2);
		cmd.apply();
		assertEquals("b", eq.left.get(0).text);
		assertEquals("c", eq.left.get(1).text);
		assertEquals("a", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveLeftTerm(eq, 1, 0);
		cmd.apply();
		assertEquals("b", eq.left.get(0).text);
		assertEquals("a", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		
		cmd = new MoveLeftTerm(eq, 1, 1);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveLeftTerm(eq, 1, 2);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("c", eq.left.get(1).text);
		assertEquals("b", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveLeftTerm(eq, 2, 0);
		cmd.apply();
		assertEquals("c", eq.left.get(0).text);
		assertEquals("a", eq.left.get(1).text);
		assertEquals("b", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveLeftTerm(eq, 2, 1);
		cmd.apply();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("c", eq.left.get(1).text);
		assertEquals("b", eq.left.get(2).text);
		cmd.undo();
		assertEquals("a", eq.left.get(0).text);
		assertEquals("b", eq.left.get(1).text);
		assertEquals("c", eq.left.get(2).text);

		cmd = new MoveLeftTerm(eq, 2, 2);
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
		
		Command cmd = new MoveTermLeftToRight(eq, 0);
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
		
		cmd = new MoveTermLeftToRight(eq, 1);
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
