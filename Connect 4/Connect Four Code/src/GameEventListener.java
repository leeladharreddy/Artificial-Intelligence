// GameEventListener.java

 

public interface GameEventListener 
{
  
	
	public void gameStarted();
  
	/**
	 * The game has stopped
	 */
	public void gameStoped();
  
	/**  
	 * The game has been restarted.
	 */
	public void gameRestarted();
  
	/** 
	 * A player has moved.
	 */
	public void moveMade(Move aMove);
  
}
