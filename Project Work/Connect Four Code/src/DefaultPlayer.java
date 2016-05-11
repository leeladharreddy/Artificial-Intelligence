
public abstract class DefaultPlayer implements Player 
{

	protected String name;
	protected int number;



   
	/** Creates new DefaultPlayer */
	public DefaultPlayer(String name, int number)
	{
		this.name = name;
		this.number = number;
	}



	
	public abstract Move getMove(Board b);


	public String getName() 
	{
		return name;
	}
	
	
	public int getNumber() 
	{
    	return number;
	}

	public String toString()
	{
		return name;
	}

}//end class DefaultPlayer
