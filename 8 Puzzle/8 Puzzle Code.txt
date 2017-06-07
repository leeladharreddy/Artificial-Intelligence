class tiles
{
      public static int newTable [] ;
      public static int table [] = {2,3,1,4,5,0,6,7,8};
      public static int goalTable[] ={0,1,2,3,4,5,6,7,8};    	   
      public static int blankSpot;   
      public static int a=0, b=0, c=0, x;
      public static int numCorrect=0;
      public static int origNum, leftNum,rightNum,upNum,downNum;

         public static void main(String args[])
               {
   	        	     puzzSolver();
   	     }

//Solves puzzle
      static void puzzSolver()
      {
   	     int a;
   	     printinitvalues();        //Prints the puzzle

   	     for (a=0; a<5; ++a)
   	           {
   	        	     countTiles();
   	        	     branch();
   	        	     System.out.println(numCorrect);
   	           }
      }

//Solves puzzle


      static void branch()
      {
   	     if (numCorrect<9)
   	           {
   	        	     countTiles();
   	        	     locateSpace();         	     //Locates the position of the blank space
   	        	     //System.out.println(numCorrect);
   	        	     checkNum();
   	        	     getLarge();
   	        	     //System.out.println(x);

   	        	     if(x==leftNum)
   	        	           changeTableLeft();
   	        	        	     else if(x==rightNum)
   	        	        	           changeTableRight();
   	        	        	        	     else if(x==upNum)
   	        	        	        	           changeTableUp();
   	        	        	        	        	     else
   	        	        	        	        	           changeTableDown();
   	        	     printinitvalues();
   	           }
      }

//Counts tiles in correct placement
      static void countTiles()
      {
   	     int i;
   	     numCorrect =0;
   	     for (i=0; i<9; ++i)
   	           {
   	        	     if (newTable[i]==goalTable[i])
   	        	     {
   	        	           numCorrect = numCorrect + 1;
   	        	     }
   	           }
      }
//Check correct placement after each possible move

      static void checkNum()

      {
   	     makeMoveLeft();
   	     //printNewValues();              //Prints the puzzle
   	     locateSpace();         	     //Locates the position of the blank space
   	     countTiles();
   	     leftNum = numCorrect;
   	     //System.out.println(leftNum);
   	     resetTable();

   	     makeMoveUp();
   	     //printNewValues();              //Prints the puzzle
   	     locateSpace();         	     //Locates the position of the blank space
   	     countTiles();
   	     upNum = numCorrect;
   	     //System.out.println(upNum);
   	     resetTable();

   	     makeMoveRight();
   	     //printNewValues();              //Prints the puzzle
   	     locateSpace();         	     //Locates the position of the blank space
   	     countTiles();
   	     rightNum = numCorrect;
   	     //System.out.println(rightNum);
   	     resetTable();

   	     makeMoveDown();
   	     //printNewValues();              //Prints the puzzle
   	     locateSpace();         	     //Locates the position of the blank space
   	     countTiles();
   	     downNum = numCorrect;
   	     //System.out.println(downNum);
   	     resetTable();
      }

//Checks which move made greatest impact

      static void getLarge()
      {
   	     x=leftNum;

   	     if (x<rightNum)
   	           {
   	        	     x=rightNum;
   	           }

   	     if (x<upNum)
   	           {
   	        	     x=upNum;
   	           }

   	     if (x<downNum)
   	           {
   	        	     x=downNum;
   	           }
      }

      static void changeTableLeft()
      {
   	     makeMoveLeft();
   	     int a;
   	     for (a=0; a<9; ++a)
   	     {
   	           table[a] = newTable[a];
   	     }
      }
      static void changeTableRight()
      {
   	     makeMoveRight();
   	     int a;
   	     for (a=0; a<9; ++a)
   	           {
   	        	     table[a] = newTable[a];
   	           }
      }
      static void changeTableDown()
      {
   	     makeMoveDown();
   	     int a;
   	     for (a=0; a<9; ++a)
   	           {
   	        	     table[a] = newTable[a];
   	           }
      }
      static void changeTableUp()
      {
   	     makeMoveUp();
   	     int a;
   	     for (a=0; a<9; ++a)
   	           {
   	        	     table[a] = newTable[a];
   	           }
      }
//Makes moves of blank tiles to the left--does error checking to ensure move is allowed
      static void makeMoveLeft()
      {
   	     if(blankSpot!=0)
   	     {
   	           if (blankSpot !=3)
   	           {
   	        	     if (blankSpot !=6)
   	        	     {
   	        	           int temp;
   	        	           temp = table[blankSpot-1];
   	        	        	     if (temp != goalTable[blankSpot-1])
   	        	        	           {
   	        	        	        	     newTable[blankSpot-1]=table[blankSpot];
   	        	        	        	     newTable[blankSpot] = temp;
   	        	        	        	     countTiles();
   	        	        	           }
   	        	     }
   	           }
   	     }
   	     //else makeMoveUp();
      }
//Makes moves of blank tiles to right
      static void makeMoveRight()
      {
   	     if(blankSpot!=2)
   	           {
   	        	     if (blankSpot !=5)
   	        	           {
   	        	        	     if (blankSpot !=8)
   	        	        	           {
   	        	        	        	     int temp;
   	        	        	        	     temp = table[blankSpot+1];
   	        	        	        	     if (temp != goalTable[blankSpot+1])
   	        	        	        	           {
   	        	        	        	        	     newTable[blankSpot+1]=table[blankSpot];
   	        	        	        	        	     newTable[blankSpot] = temp;
   	        	        	        	        	     return;
   	        	        	        	           }
   	        	        	           }
   	        	           }
   	           }

      }
//Makes moves of blank tiles up

      static void makeMoveUp()
      {
   	     if(blankSpot!=0)
   	           {
   	        	     if (blankSpot !=1)
   	        	           {
   	        	        	     if (blankSpot !=2)
   	        	        	           {
   	        	        	        	     int temp;
   	        	        	        	     temp = table[blankSpot-3];
   	        	        	        	     if (temp != goalTable[blankSpot-3])
   	        	        	        	           {
   	        	        	        	        	     newTable[blankSpot-3]=table[blankSpot];
   	        	        	        	        	     newTable[blankSpot] = temp;
   	        	        	        	           }
   	        	        	           }
   	        	           }
   	           }
      }
//Makes moves of blank tiles down
      static void makeMoveDown()
      {
   	     if(blankSpot!=6)
   	           {
   	        	     if (blankSpot !=7)
   	        	           {
   	        	        	     if (blankSpot !=8)
   	        	        	           {
   	        	        	        	     int temp;
   	        	        	        	     temp = table[blankSpot+3];
   	        	        	        	     if (temp != goalTable[blankSpot+3])
   	        	        	        	           {
   	        	        	        	        	     newTable[blankSpot+3]=table[blankSpot];
   	        	        	        	        	     newTable[blankSpot] = temp;
   	        	        	        	           }
   	        	        	           }
   	        	           }
   	           }
      }
/*I used this method to print the values that are stored for the puzzle*/
      static void printinitvalues()
   	     {
   	           int t, i=1;
   	           for (t=0; t<9; ++t)
   	        	     {
   	        	           System.out.print(table [t] + "  ");
   	        	        	     if (i == 3)         	        	        	     //I use this loop to create a new row
   	        	        	           {
   	        	        	        	     System.out.println();
   	        	        	        	     i = i - 3;
   	        	        	           }
   	        	           i = i + 1;
   	        	     }
   	        	           System.out.println();
   	        	           System.out.println();
   	     }
//Printing the Goal State
      static void printGoalvalues()
      {
   	           int t, i=1;
   	           for (t=0; t<9; ++t)
   	        	     {
   	        	           System.out.print(goalTable [t] + "  ");
   	        	        	     if (i == 3)         	        	        	  //I use this loop to create a new row
   	        	        	           {
   	        	        	        	     System.out.println();
   	        	        	        	     i = i - 3;
   	        	        	           }
   	        	           i = i + 1;
   	        	     }
   	        	           System.out.println();
   	        	           System.out.println();
      }
//Prints the modified table
      static void printNewValues()
   	     {
   	           int t, i=1;
   	           for (t=0; t<9; ++t)
   	        	     {
   	        	           System.out.print(newTable [t] + "  ");
   	        	        	     if (i == 3)         	        	         //I use this loop to create a new row
   	        	        	           {
   	        	        	        	     System.out.println();
   	        	        	        	     i = i - 3;
   	        	        	           }
   	        	           i = i + 1;
   	        	     }
   	        	           System.out.println();
   	        	           System.out.println();
   	     }

//I used this method to locate the blank space in the puzzle
      static void locateSpace()
      {
   	     int t;
   	     for (t=0; t<9; ++t)
   	           {
   	        	     if (table[t]==0)
   	        	           {
   	        	        	     blankSpot = t;
   	        	        	     return;
   	        	           }
   	           }
      }
//End of method
//Resets the testing table
      static void resetTable()
      {
   	     int i;
   	     for (i=0; i<9; ++i)
   	     {
   	           newTable[i]=table[i];
   	     }
      }
