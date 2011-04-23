package diofanto.client;

public interface BlockEventHandler {
	public void onDrop(Block block, DiofantoCanvas canvas);
	public void onMove(Block block, DiofantoCanvas canvas);
	public void onBeforeMove(Block block, DiofantoCanvas canvas);
}
