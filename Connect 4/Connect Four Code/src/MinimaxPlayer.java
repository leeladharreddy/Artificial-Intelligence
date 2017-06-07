// MinimaxPlayer.java

 

public class MinimaxPlayer extends DefaultPlayer
{
	

	
	private int depth = 1;
	private Player minPlayer;


  
	public MinimaxPlayer(String name, int number, Player minPlayer) 
	{
		super(name, number);
		
		this.minPlayer = minPlayer;
	}
	
	
	public int getDepth()
	{
		return depth;
	}
	
	
	public void setDepth(int anInt)
	{
		depth = anInt;
	}
	
	
	public Move getMove(Board b)
	{
		MinimaxCalculator calc = new MinimaxCalculator(b,this,minPlayer);
		return calc.calculateMove(depth);
	}
	
	
}
final class MinimaxCalculator
{


	private int moveCount = 0;
	private long startTime;
	
	private Player minPlayer;
	private Player maxPlayer;
	private Board board;
	
	private final int MAX_POSSIBLE_STRENGTH;
	private final int MIN_POSSIBLE_STRENGTH;


	MinimaxCalculator(Board b, Player max, Player min)
	{
		board = b;
		maxPlayer = max;
		minPlayer = min;
		
		MAX_POSSIBLE_STRENGTH = board.getBoardStats().getMaxStrength();
		MIN_POSSIBLE_STRENGTH = board.getBoardStats().getMinStrength();
	}
	

	public Move calculateMove(int depth)
	{
		startTime = System.currentTimeMillis();
		
		if(depth == 0)
		{
			System.out.println("Error, 0 depth in minumax player");
			Thread.dumpStack();
			return null;
		}
		
		Move[] moves = board.getPossibleMoves(maxPlayer);
		int maxStrength = MIN_POSSIBLE_STRENGTH;
		int maxIndex = 0;
		
		for(int i = 0; i < moves.length; i++)
		{
			if(board.move(moves[i]))
			{
				moveCount++;
				
				int strength = expandMinNode(depth -1, maxStrength);
				if(strength > maxStrength)
				{
					maxStrength = strength;
					maxIndex = i;
				}
				board.undoLastMove();
			}//end if move made
			
			
			if(Thread.currentThread().isInterrupted())
			{
				return null;
			}
			
		}//end for all moves
		
		long stopTime = System.currentTimeMillis();
		System.out.print("MINIMAX: Number of moves tried:" + moveCount);
		System.out.println(" Time:" + (stopTime -  startTime) + " milliseconds");
		
		return moves[maxIndex];
		
	}
	
	
	private int expandMaxNode(int depth, int parentMinimum)
	{
		//base step
		if(depth == 0 || board.isGameOver())
		{
			return board.getBoardStats().getStrength(maxPlayer);
		}
		
		//recursive step
		Move[] moves = board.getPossibleMoves(maxPlayer);
		int maxStrength = MIN_POSSIBLE_STRENGTH;
		
		for(int i = 0; i < moves.length; i++)
		{
			if(board.move(moves[i]))
			{
				moveCount++;
				int strength = expandMinNode(depth -1, maxStrength);

				if(strength > parentMinimum)
				{
					board.undoLastMove();
					return strength;
				}
				if(strength > maxStrength)
				{
					maxStrength = strength;
				}
				board.undoLastMove();
			}//end if move made
			
		}//end for all moves
		
		return maxStrength;
	
	}//end expandMaxNode
	
	
	
	private int expandMinNode(int depth, int parentMaximum)
	{
		//base step
		if(depth == 0 || board.isGameOver())
		{
			return board.getBoardStats().getStrength(maxPlayer);
		}
		
		//recursive step
		Move[] moves = board.getPossibleMoves(minPlayer);
		int minStrength = MAX_POSSIBLE_STRENGTH;
		
		for(int i = 0; i < moves.length; i++)
		{
			if(board.move(moves[i]))
			{
				moveCount++;
				int strength = expandMaxNode(depth -1, minStrength);
				
				if(strength < parentMaximum)
				{
					board.undoLastMove();
					return strength;
				}
				if(strength < minStrength)
				{
					minStrength = strength;
				}
				board.undoLastMove();
			}//end if move made
			
		}//end for all moves
		
		return minStrength;
	
	}//end expandMaxNode
	
}