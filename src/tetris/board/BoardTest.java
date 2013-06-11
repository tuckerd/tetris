/*
 * Danielle Tucker 
 * TCSS 305 - November 2012 
 * Project Tetris
 */

package tetris.board;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import tetris.pieces.ClassicPiece;
import tetris.pieces.Piece;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test for Board class to check removing rows.
 * 
 * @author Danielle Tucker
 * @version November 2012
 */
public class BoardTest
{
  /**
   * The array of pieces to assign to play.
   */
  private static Piece[] pieces = new Piece[34];
  
  /**
   * A board to test.
   */
  private static Board board;
  
  /**
   * The frozen pieces after Step 44 in the game.
   */
  private static List<Color[]> game_step_44;

  /**
   * The frozen pieces after Step 104 in the game.
   */
  private static List<Color[]> game_step_104;

  /**
   * The frozen pieces after Step 160 in the game.
   */
  private static List<Color[]> game_step_160;

  /**
   * The frozen pieces after Step 198 in the game.
   */
  private static List<Color[]> game_step_198;

  /**
   * The frozen pieces after Step 255 in the game.
   */
  private static List<Color[]> game_step_255;

  /**
   * A list of frozen block locations in the expected board.
   */
  private static List<Color[]> expected_board = new LinkedList<Color[]>();
  
  /**
   * Initializes the set of static pieces that are used for testing.
   */
  @BeforeClass
  public static void createPiecesAndBoard()
  {
    // Two Rows cleared
    pieces[0] = new Piece(new Point(0, 6), ClassicPiece.I.blockLocations());
    pieces[1] = new Piece(new Point(0, 8), ClassicPiece.I.blockLocations());
    pieces[2] = new Piece(new Point(6, 8), ClassicPiece.I.blockLocations());
    pieces[3] = new Piece(new Point(6, 7), ClassicPiece.I.blockLocations());
    pieces[4] = new Piece(new Point(3, 7), ClassicPiece.O.blockLocations());
    // 1 row cleared
    pieces[5] = new Piece(new Point(0, 8), ClassicPiece.I.blockLocations());
    pieces[6] = new Piece(new Point(3, 8), ClassicPiece.T.blockLocations());
    pieces[7] = new Piece(new Point(7, 7), ClassicPiece.Z.blockLocations());
    pieces[8] = new Piece(new Point(7, 7), ClassicPiece.J.blockLocations()).rotate();
    pieces[9] = new Piece(new Point(3, 7), ClassicPiece.L.blockLocations()).rotateClockwise();
    pieces[10] = new Piece(new Point(0, 7), ClassicPiece.S.blockLocations());
    pieces[11] = new Piece(new Point(0, 7), ClassicPiece.Z.blockLocations()).
                            rotate().moveLeft();
    // Clear 3 Rows
    pieces[12] = new Piece(new Point(2, 3), ClassicPiece.T.blockLocations()).rotate().rotate();
    pieces[13] = new Piece(new Point(3, 7), ClassicPiece.J.blockLocations()).rotate();
    pieces[14] = new Piece(new Point(0, 7), ClassicPiece.O.blockLocations());
    pieces[15] = new Piece(new Point(7, 7), ClassicPiece.L.blockLocations()).rotate();
    pieces[16] = new Piece(new Point(6, 7), ClassicPiece.O.blockLocations());
    pieces[17] = new Piece(new Point(4, 7), ClassicPiece.I.blockLocations()).rotate();
    pieces[18] = new Piece(new Point(2, 7), ClassicPiece.T.blockLocations());
    pieces[19] = new Piece(new Point(7, 7), ClassicPiece.T.blockLocations()).rotate();
    pieces[20] = new Piece(new Point(0, 7), ClassicPiece.I.blockLocations()).
                            rotate().moveLeft().moveLeft();
    // 2 Rows with one gap in the middle.
    pieces[21] = new Piece(new Point(0, 7), ClassicPiece.S.blockLocations());
    pieces[22] = new Piece(new Point(4, 7), ClassicPiece.Z.blockLocations());
    pieces[23] = new Piece(new Point(-1, 7), ClassicPiece.O.blockLocations());
    pieces[24] = new Piece(new Point(4, 7), ClassicPiece.S.blockLocations()).rotate();
    pieces[25] = new Piece(new Point(6, 7), ClassicPiece.J.blockLocations()).rotate().rotate();
    // 4 Rows Cleared
    pieces[26] = new Piece(new Point(0, 7), ClassicPiece.J.blockLocations()).rotate();
    pieces[27] = new Piece(new Point(-1, 7), ClassicPiece.T.blockLocations());
    pieces[28] = new Piece(new Point(2, 7), ClassicPiece.I.blockLocations()).rotate();
    pieces[29] = new Piece(new Point(6, 7), ClassicPiece.Z.blockLocations());
    pieces[30] = new Piece(new Point(7, 7), ClassicPiece.S.blockLocations()).rotate();
    pieces[31] = new Piece(new Point(4, 7), ClassicPiece.T.blockLocations());
    pieces[32] = new Piece(new Point(7, 7), ClassicPiece.J.blockLocations()).rotate();
    pieces[33] = new Piece(new Point(1, 7), ClassicPiece.I.blockLocations()).rotate();
            
    board = new Board(pieces);
    board.getBoard();
    
    stepPlayNTimes(44);
    game_step_44 = board.getBoard();
    
    stepPlayNTimes(60);
    game_step_104 = board.getBoard();

    stepPlayNTimes(56);
    game_step_160 = board.getBoard();
    
    stepPlayNTimes(38);
    game_step_198 = board.getBoard();

    stepPlayNTimes(57);
    game_step_255 = board.getBoard();

    expected_board = new Board().getBoard();
  }
 
 /**
   * Advance the board n steps.
   * @param the_n the number of steps to advance the board
   */
  private static void stepPlayNTimes(final int the_n)
  {
    for (int i = 0; i < the_n; i++)
    {
      board.step();
    }
  }

/**
   * Test clearing two adjacent rows after 44 steps of the dropped pieces.
   * (Dropped 2 I, O, 2 I pieces which should end up with a clear board)
   */
  @Test
  public void clearTwoRows()
  {
    for (int row = 0; row < expected_board.size(); row++)
    {
      assertArrayEquals("Clear row after 44 steps: row " + row, 
                        expected_board.get(row), game_step_44.get(row));     
    }
  }

  /**
   * Test clearing one row after 104 steps of the dropped pieces.
   * So now need to iterate 104 - 44 = 60 steps 
   */
  @Test
  public void clearOneRow()
  {
    //Expected Board after 104 steps:
    final int[] row_layout_2 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
    final int[] row_layout_1 = {1, 1, 1, 0, 1, 1, 0, 1, 1, 1};
    final int[] row_layout = {1, 1, 1, 1, 1, 1, 1, 0, 1, 1};
    
    //Put in the configurations of the rows
    expected_board.set(0, makeRow(row_layout));
    expected_board.set(1, makeRow(row_layout_1));
    expected_board.set(2, makeRow(row_layout_2));
    
    for (int row = 0; row < expected_board.size(); row++)
    {
      assertArrayEquals("Clear row after 104 steps: row " + row, 
                        expected_board.get(row), game_step_104.get(row));     
    }
    
    //Clear up expected board for next test
    clearExpectedBoard(3);
  }

  /**
   * Test clearing three adjacent rows after 160 steps of the dropped pieces.
   * So now need to iterate 160 - 104 = 56 steps 
   */
  @Test
  public void clearThreeRows()
  {
    //Expected Board after 160 steps
    final int[] row_2_board_160 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
    final int[] row_1_board_160 = {1, 0, 0, 0, 1, 0, 0, 0, 1, 1};
    final int[] row_0_board_160 = {1, 1, 1, 1, 1, 1, 1, 0, 1, 1};

    //Put in the configurations of the rows
    expected_board.set(2, makeRow(row_2_board_160));
    expected_board.set(1, makeRow(row_1_board_160));
    expected_board.set(0, makeRow(row_0_board_160));
    
    for (int row = 0; row < expected_board.size(); row++)
    {
      assertArrayEquals("Clear row after 160 steps: row " + row, 
                        expected_board.get(row), game_step_160.get(row));     
    }

    //Clear up expected board for next test
    clearExpectedBoard(3);
  }

  /**
   * Test clearing non-adjacent rows after 198 steps of the dropped pieces.
   * So now need to iterate 198 - 160 = 38 steps 
   */
  @Test
  public void clearNonAdjacentRows()
  {
    //Expected Board after 198 steps
    final int[] row_2_board_198 = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
    final int[] row_1_board_198 = {1, 1, 0, 0, 0, 1, 1, 0, 0, 0};
    final int[] row_0_board_198 = {1, 1, 1, 0, 1, 1, 1, 1, 1, 1};

    expected_board.set(2, makeRow(row_2_board_198));
    expected_board.set(1, makeRow(row_1_board_198));
    expected_board.set(0, makeRow(row_0_board_198));

    for (int row = 0; row < expected_board.size(); row++)
    {
      assertArrayEquals("Clear row after 198 steps: row " + row, 
                        expected_board.get(row), game_step_198.get(row));     
    }

    //Clear up expected board for next test
    clearExpectedBoard(3);
  }

  /**
   * Test clearing 4 rows after 255 steps of the dropped pieces.
   * So now need to iterate 255 - 198 = 57 steps 
   */
  @Test
  public void clearFourRows()
  {
    //Expected Board after 255 steps
    final int[] row_0_board_255 = {0, 1, 0, 0, 1, 0, 1, 1, 1, 1};
  
    expected_board.set(0, makeRow(row_0_board_255));
  
    for (int row = 0; row < expected_board.size(); row++)
    {
      assertArrayEquals("Clear row after 255 steps: row " + row, 
                        expected_board.get(row), game_step_255.get(row));     
    }

    //Clear up expected board for next test
    clearExpectedBoard(1);
  }

  /**
   * Test end of game flag.
   * So now need to iterate 255 - 198 = 57 steps 
   */
  @Test
  public void endOfGame()
  {
    final Board random_board = new Board();
    assertTrue("Game is in progress", random_board.gameInProgress());

    for (int i = 0; i < 300; i++)
    {
      random_board.step();
    }
    assertFalse("Game is over", random_board.gameInProgress());
  }
  
  
  /**
  * Creates an array (row) of Color.BLUE to be placed in the comparison
  * board. A 1 will fill that location with a color, otherwise it will be null. 
  * @param the_row_layout the row layout.
  * @return a Color[] which has the given places filled with a color.
  */
  private Color[] makeRow(final int[] the_row_layout)
  {
    final Color[] result = new Color[the_row_layout.length];
    for (int i = 0; i < the_row_layout.length; i++)
    {
      if (the_row_layout[i] == 1)
      {
        result[i] = Color.BLUE; //Set with default color of the pieces
      }
    }
    return result;
  }

  /**
   * Clear up the expected board for use in the next test.
   * @param the_n the number of rows to clear
   */
  private void clearExpectedBoard(final int the_n)
  {
    final Color[] clear_row = new Color[10];

    for (int i = 0; i < the_n; i++)
    {
      expected_board.set(i, clear_row);
    }
  }
}
