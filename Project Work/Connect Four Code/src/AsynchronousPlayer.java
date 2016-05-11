
public class AsynchronousPlayer extends DefaultPlayer
{
  

    
	private Move lastMove = null;

  
	
	public AsynchronousPlayer(String name, int number) 
	{
		super(name, number);
	}
    
	
	public synchronized  Move getMove(Board b)
	{
		try
		{
			wait();
		}
		
		catch(InterruptedException e)
		{
			return null;
		}
  	 
		return lastMove;
	}
  
  
	
	public synchronized void makeMove(Move aMove)
	{
		lastMove = aMove;
		notifyAll();
	}
 
}//end class AsynchronousPlayer
