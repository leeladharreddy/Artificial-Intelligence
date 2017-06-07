//C4Board.java


/** 
 *
 * @author  Sean Bridges
 * @version 1.0
 */

import java.util.Vector;

public final class C4Board implements Board 
{


	
	public final static int NULL_PLAYER_NUMBER = -1;
	public final static int FIRST_PLAYER_NUMBER = 0;
	public final static int SECOND_PLAYER_NUMBER = 1;
	
	public static final int NUMBER_OF_ROWS = 6; 
	public static final int NUMBER_OF_COLUMNS = 8;  
	public static final int NUMBER_OF_SLOTS = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS;
	
	

	
	private C4Slot[] slots;

	
	private int[] numberOfChipsInColumn = new int[NUMBER_OF_COLUMNS];

	
	int moveHistoryLength = 0;
	int[] moveHistory = new int[NUMBER_OF_SLOTS];
	
	private C4Stats stats = new C4Stats();
	private Vector rows;

	
	
	private Move[] firstPlayerMoves;
	private Move[] secondPlayerMoves;
	


	
	
	public C4Board(Player firstPlayer, Player secondPlayer) 
	{
		initSlots();
		
	    //create the moves arrays, these are all the possible moves
		firstPlayerMoves = new Move[NUMBER_OF_COLUMNS];
		secondPlayerMoves = new Move[NUMBER_OF_COLUMNS];
		
		for(int i = 0; i < NUMBER_OF_COLUMNS; i++)
		{
			firstPlayerMoves[i] = new C4Move(firstPlayer, i);
			secondPlayerMoves[i] = new C4Move(secondPlayer, i);
		}
		
		
		
	}

	
	private void initSlots()
	{
		slots = new C4Slot[NUMBER_OF_SLOTS];
		
		//create the slots
		for(int i = 0; i < NUMBER_OF_SLOTS; i++)
		{
			slots[i] = new C4Slot();
		}
		
		//create the rows
	
		rows = new Vector(84);	
		
	
		int totalNumber = 0;
		
		
		for(int row = 0; row <NUMBER_OF_ROWS; row++)
		{
			for(int column = 0; column < NUMBER_OF_COLUMNS; column++)
			{
				for(int delRow = -1; delRow <= 1; delRow++)
				{
					for(int delColumn = -1; delColumn <= 1; delColumn++)
					{
						if(
							( 
							((delRow == 1) && (delColumn == 0)) ||
							((delRow == 1) && (delColumn == 1)) ||
							((delRow == 0) && (delColumn == 1)) ||
							((delRow == -1) && (delColumn == 1))
							) &&
							(inbounds( row + (3*delRow), column + (3*delColumn) )) &&
							(inbounds(row,column))
						   
						   )
						{
							C4Row newRow = new C4Row(
												  getSlot(row,column),
												  getSlot(row + delRow,column + delColumn),
												  getSlot(row + (2 *delRow), column + (2 * delColumn)),
												  getSlot(row + (3 *delRow), column + (3 * delColumn)),
												  stats
												  );	
							rows.addElement(newRow);				
							
						}
						   
					}//end for delColumn
					
				}//end for delRow
														
				
			}//end for column
		}//end for row
	
	}
	
//--------------------------------------
	//slot access

	/**
	 * Returns the number of slots in a given column.
	 * column should be in 0..NUMBER_OF_COLUMNS -1 inclusive.
	 */
	public int numerOfChipsInColumn(int column)
	{
		return numberOfChipsInColumn[column];
	}
	
	
	private boolean inbounds(int row, int column)
	{
		return ((row >= 0) &&
			   (column >= 0) &&
			   (row < NUMBER_OF_ROWS) &&
			   (column < NUMBER_OF_COLUMNS));
	}
	
	
	private C4Slot getSlot(int row, int column)
	{
		return getSlot((row * NUMBER_OF_COLUMNS) + column);
	}
	

	private C4Slot getSlot(int index)
	{
		return slots[index];
	}

	private void resetGame()
	{
		for(int i = 0; i < NUMBER_OF_SLOTS; i++)
		{
			if(slots[i].getContents() != NULL_PLAYER_NUMBER )
			{
				slots[i].setContents(NULL_PLAYER_NUMBER);
			}
		}
		
		for(int i = 0; i < NUMBER_OF_COLUMNS; i++)
		{
			numberOfChipsInColumn[i] = 0;
		}
		
		moveHistoryLength = 0;
	}
	


	
	public BoardStats getBoardStats() 
	{
		return stats;
	}
	
	
	public boolean move(Move m) 
	{
		//System.out.println(m);
		int column = m.toInt();
		int columnCount = numberOfChipsInColumn[column];
		
		if(columnCount < NUMBER_OF_ROWS)
		{
			//make the move
			slots[(columnCount * NUMBER_OF_COLUMNS) + column].setContents(m.maker().getNumber());
			numberOfChipsInColumn[column] = columnCount + 1;
			
			//update the history
			moveHistory[moveHistoryLength] = column;
			moveHistoryLength++;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	/** Undo the last move made.
	 */
	public void undoLastMove() 
	{
		//System.out.println("undoing last move");
		moveHistoryLength --;
		int column = moveHistory[moveHistoryLength];
		
		numberOfChipsInColumn[column]--;
		int row = numberOfChipsInColumn[column];
		
		slots[(row * NUMBER_OF_COLUMNS) + column].setContents(NULL_PLAYER_NUMBER);
		
	}
	
	/**
	 * Get the list of moves that are possible for the given
	 * player.
	 * For the C4Board this always returns the same array for each player.
	 * Some of the moves may not be legal, but the legal moves is a subset
	 * of the moves returned.
	 */
	public Move[] getPossibleMoves(Player aPlayer) 
	{
		
		
		if(aPlayer.getNumber() == FIRST_PLAYER_NUMBER )
		{
			return firstPlayerMoves;
		}
		else
		{
			return secondPlayerMoves;
		}
	}
	
	/** Whether or not the game is over.
	 */
	public boolean isGameOver() 
	{
		//game over if someone has won or if all the slots are full.
		return( (moveHistoryLength == NUMBER_OF_SLOTS ) || stats.hasSomeoneWon());
		
	}
	
	/** Called by the game master when the game is started.
	 */
	public void gameStarted() 
	{
		resetGame();
	}
	/** Called by the game master when the game is restarted.
	 */
	public void gameRestarted() 
	{
		resetGame();
	}
	/** Called by the game master when the game is stopped.
	 */
	public void gameStoped() 
	{
		resetGame();
	}
	/** A board must be cloneable since a copy of it is sent
	 * to players when we ask them for their move.
	 */
	public Object clone() 
	{
		C4Board clone = null;
		
		try
		{
			clone = (C4Board) super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			//we should never get here
			System.out.println(e);
		}

		
		clone.stats = new C4Stats();
		clone.numberOfChipsInColumn = (int[]) numberOfChipsInColumn.clone();		
		clone.moveHistory = (int[]) moveHistory.clone();
		
		clone.initSlots();

		for(int i = 0; i < NUMBER_OF_SLOTS; i++)
		{
			clone.slots[i].setContents( this.slots[i].getContents() );
		}
		
		return clone;
		
	}

//-------------------------------------------
	//printing
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("|0|1|2|3|4|5|6|7|");
		for(int row = NUMBER_OF_ROWS - 1; row >= 0; row--)
		{
			buf.append("\n|");
			
			for(int column = 0; column < NUMBER_OF_COLUMNS; column++)
			{
				if( getSlot(row,column).getContents() == NULL_PLAYER_NUMBER)
				{
					buf.append(" ");
				}
				else
				{
					buf.append(getSlot(row,column).getContents() );
				}
				buf.append("|");
			}//end for column
		}//end for row
		
				
		
		return buf.toString();
	}

}//end class C4Board