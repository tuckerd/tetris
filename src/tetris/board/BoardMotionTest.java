/*
 * Danielle Tucker 
 * TCSS 305 - November 2012 
 * Project Tetris
 */

package tetris.board;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import tetris.pieces.ClassicPiece;
import tetris.pieces.Piece;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test for Board class basic motions.
 * 
 * @author Danielle Tucker
 * @version November 2012
 */
public class BoardMotionTest
{
  /**
   * The array of pieces to assign to play.
   */
  private static Piece[] pieces = {new Piece(new Point(1, 20), 
                                             ClassicPiece.J.blockLocations())};
  
  /**
   * A board to test.
   */
  private static Board board;
  
  /**
   * A board to test that pieces are frozen appropriately.
   */
  private static Board freeze_board;
  
  /**
   * An empty list of frozen pieces.
   */
  private static List<Color[]> empty_board;
  
  /**
   * Initializes the set of static pieces that are used for testing.
   */
  @BeforeClass
  public static void createBoard()
  {
    board = new Board(pieces);
    empty_board = board.getBoard();
    freeze_board = new Board(pieces);
    //Shift the play to a point where the piece should have frozen.
    for (int i = 0; i < 22; i++)
    {
      freeze_board.step();
    }   
  }

  /**
   * Advance the board n steps.
   * @param the_n the number of steps to advance the board
   */
  private void stepPlayNTimes(final int the_n)
  {
    for (int i = 0; i < the_n; i++)
    {
      board.step();
    }
  }
  
  /**
   * Test moving a piece left on a board.
   */
  @Test
  public void moveLeft()
  {
    assertTrue("Move a block left in a legal move", board.moveLeft());
    assertEquals("The current piece is in correct location after move",
                 0, board.getPiece().x());
    board.moveLeft();
    assertFalse("Move a block left which is at the edge", board.moveLeft());
    stepPlayNTimes(22); //To reset the board ("Play" this piece)
  }
  
  /**
   * Test moving a piece right on a board.
   */
  @Test
  public void moveRight()
  {
    assertTrue("Move a block Right in a legal move", board.moveRight());
    assertEquals("Check location on move right", 2, board.getPiece().x());
    for (int i = 0; i < 7; i++)
    {
      board.moveRight(); //Shift block over to other edge
    }
    assertFalse("Move a block left which is at the Right Edge", board.moveRight());
    assertEquals("Block has not been moved", 7, board.getPiece().x());
    stepPlayNTimes(22);  //Reset the piece on the board for next test
  }

  /**
   * Test moving a rotating a piece on a board.
   */
  @Test
  public void rotate()
  {
    assertTrue("Rotate a block in a legal move", board.rotate());
    final Point[] block_check = {new Point(0, 2), new Point(1, 2), 
                                 new Point(2, 2), new Point(2, 1)};

    for (Point p: block_check)
    {
      assertTrue("Check block locations on rotate", 
                   board.getPiece().blockLocations().contains(p));
    }

    stepPlayNTimes(22); //Reset the piece on the board for next sub-test
    board.moveLeft();
    board.moveLeft(); //Position for next part of test

    assertFalse("Rotate a block illegally", board.rotate());
    for (Point p: pieces[0].blockLocations())
    { //Should be the same locations as the original piece seeded to game.
      assertTrue("Check block locations on illegal rotate", 
                   board.getPiece().blockLocations().contains(p));
    }
    
    stepPlayNTimes(22);  //Reset the piece on the board for next test
  }

  /**
   * Will attempt to move down which would contain a
   * point outside the playable area. 
   */
  @Test
  public void moveBadPieces()
  {
    final Piece[] bad_pieces = {new Piece(new Point(-2, 20),
                                          ClassicPiece.J.blockLocations())};
      
    final Board bad_board = new Board(bad_pieces);
    assertFalse("Attempting to step with bad piece.", bad_board.moveDown());
  }
  
  /**
   * Test the freezing of a piece.
   */
  @Test
  public void freeze()
  {
    //Create the layout which will occur after a J Piece froze
    final Color fill = freeze_board.getPiece().getColor(); //The color of the pieces
    final Color[] row_2 = {null, null, null, fill, null, null, null, null, null, null};
    final Color[] row_1 = {null, null, null, fill, null, null, null, null, null, null};
    final Color[] row_0 = {null, null, fill, fill, null, null, null, null, null, null};

    empty_board.set(2, row_2);    
    empty_board.set(1, row_1);
    empty_board.set(0, row_0);   

    for (int row = 0; row < empty_board.size(); row++)
    {
      assertArrayEquals("Frozen J Piece: row " + row, 
                        empty_board.get(row), freeze_board.getBoard().get(row));     
    }
    
    //Make sure empty_board is empty again
    empty_board.set(0, new Color[empty_board.get(5).length]);
    empty_board.set(1, new Color[empty_board.get(5).length]);
    empty_board.set(2, new Color[empty_board.get(5).length]);
  }
}
