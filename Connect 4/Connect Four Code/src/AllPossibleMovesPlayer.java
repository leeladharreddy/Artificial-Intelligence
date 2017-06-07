
public class AllPossibleMovesPlayer extends DefaultPlayer {

    
	private double fractionOfMovesTried = 1.0;


	public AllPossibleMovesPlayer(String name, int number) 
	{
		super(name,number);
	}


	public AllPossibleMovesPlayer(String name, int number, double fractionOfMovesTried) 
	{
		super(name,number);
		this.fractionOfMovesTried = fractionOfMovesTried;
	}


	public void setFractionOfMovesTried(double f)
	{
		fractionOfMovesTried = f;
	}

	public double getFractionOfMovesTried()
	{
		return fractionOfMovesTried;
	}

	public Move getMove(Board b) 
	{
		Move[] moves = b.getPossibleMoves(this);

		if(moves.length == 0)
		{
			System.err.println("All possibleMovesPlayer queried board, but board says no moves possible");
			throw new ArrayIndexOutOfBoundsException("0 moves possible");
		}
    
		int maxScore = b.getBoardStats().getMinStrength();
		int maxIndex = 0;
    
		int currentScore;
		boolean triedOne = false;
    
		for(int i = 0; i < moves.length; i++)
		{
        	if( Math.random() < fractionOfMovesTried )
			{
				triedOne = true;
				if(b.move(moves[i]))
				{
					currentScore = b.getBoardStats().getStrength(this);
					if(currentScore > maxScore)
					{   
						maxScore = currentScore;
						maxIndex = i;
					}
					b.undoLastMove();
				}
            
			}//end if
		}//end for
    
	
		if(!triedOne)
		{
			return moves[ (int) (moves.length * Math.random())];
		}
		else
		{
			return moves[maxIndex];
		}
    

	}//end getMove

}// end class AllPossibleMovesPlayer
