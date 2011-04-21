package diofanto.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;

public class Container extends Block {
	List<Block> blocks = new ArrayList<Block>();

	@Override
	public double contentsWidth(Context2d c2d) {
		
		double sum = 0;
		for(Block block : blocks) {
			sum += block.width;
		}
		return sum;
	}

	@Override
	public double contentsHeight() {
		
		double max = 0;
		for(Block block : blocks) {
			double height = block.height;
			if (height > max)
				max = height;
		}
		return max;
	}

	// Depth-first search.
	public Block findBlock(double x, double y) {
		for(Block block: blocks) {
			if (block instanceof Container) {
				Block found = ((Container) block).findBlock(x, y);
				if (found != null)
					return found;
			}
			else if (block.pointInside(x,y)) {
				return block;
			}
		}
		if (this.pointInside(x, y))
			return this;
		return null; // not found.
	}

	@Override
	public void resize(Context2d c2d) {
		for(Block block : blocks) {
			block.resize(c2d);
		}
		super.resize(c2d);
	}

	@Override
	public void display(Context2d c2d) {
		for(Block block : blocks) {
			block.display(c2d);
		}
		super.display(c2d);
	}

	@Override
	public void moveTo(double x, double y) {
		double relX = x - this.x;
		double relY = y - this.y;
		super.moveTo(x, y);
		for(Block block : blocks) {
			block.moveTo(block.x + relX, block.y + relY);
		}
	}

	public void add(Block block) {
		blocks.add(block);
	}
	
	public void removeAll() {
		blocks.clear();
	}

	public void layout() {
		Block prev = null;
		final int spacing = 0;
		for(Block block: blocks) {
			if (prev != null) {
				block.x = prev.x + prev.width + spacing;
			}
			else {
				block.x = x;
			}
			block.y = y;
			prev = block;
		}
	}

	@Override
	public Container deepCopy() {
		Container cont = new Container();
		for(Block block : blocks) {
			cont.blocks.add(block.deepCopy());
		}
		return (Container)deepCopy(cont);
	}
	
}
