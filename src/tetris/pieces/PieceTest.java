/*
 * Danielle Tucker 
 * TCSS 305 - October 2012 
 * Project Tetris
 */

package tetris.pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test for immutable Tetris Piece class.
 * 
 * @author Danielle Tucker
 * @version October 2012
 */
public class PieceTest
{
  /**
   * The array of points used to create each unique piece.
   */
  private static List<Point> temp_array = new ArrayList<Point>();

  /**
   * An I Piece.
   */
  private static Piece i_piece;

  /**
   * A J Piece.
   */
  private static Piece j_piece;

  /**
   * An L Piece.
   */
  private static Piece l_piece;

  /**
   * A S Piece.
   */
  private static Piece s_piece;

  /**
   * An O Piece.
   */
  private static Piece o_piece;

  /**
   * A T Piece.
   */
  private static Piece t_piece;

  /**
   * An Z Piece.
   */
  private static Piece z_piece;

  /**
   * J Piece translated right.
   */
  private static Piece j_left;

  /**
   * J Piece translated down.
   */
  private static Piece j_down;

  /**
   * A Piece.
   */
  private static Piece piece;

  /**
   * A location on a board.
   */
  private static Point board_location = new Point(10, 10);

  /**
   * Initializes the set of static pieces that are used for testing.
   */
  @BeforeClass
  public static void createPieces()
  {
    j_piece = new Piece(board_location, ClassicPiece.J.blockLocations());
    j_down = new Piece(new Point(10, 9), ClassicPiece.J.blockLocations());
    j_left = new Piece(new Point(9, 10), ClassicPiece.J.blockLocations());

    i_piece = new Piece(board_location, ClassicPiece.I.blockLocations());
    l_piece = new Piece(board_location, ClassicPiece.L.blockLocations());
    s_piece = new Piece(board_location, ClassicPiece.S.blockLocations());
    z_piece = new Piece(board_location, ClassicPiece.Z.blockLocations());
    o_piece = new Piece(board_location, ClassicPiece.O.blockLocations());
    t_piece = new Piece(board_location, ClassicPiece.T.blockLocations());

    // Start by Creating I Pieces
    piece = new Piece(board_location, ClassicPiece.I.blockLocations());
  }

  /**
   * Testing initial positions for I piece are accurate.
   */
  @Test
  public void testIInitialLocations()
  {
    // I Piece Test
    temp_array.clear();
    temp_array.add(new Point(3, 1));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(0, 1));
    final List<Point> actual_blocks = i_piece.blockLocations();

    assertEquals("I Piece Initial Equals", new Piece(board_location, temp_array), i_piece);
    assertTrue("I Piece Initial Locations Block", actual_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks active in I", 4, actual_blocks.size());
  }

  /**
   * Testing initial positions for J Piece are accurate.
   */
  @Test
  public void testJInitialLocations()
  {
    // J Piece Test
    temp_array.clear();
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(2, 3));
    final List<Point> actual_blocks = j_piece.blockLocations();
    assertEquals("J Piece Initial Equals()", new Piece(board_location, temp_array), j_piece);
    assertTrue("J Piece Initial Locations Block", actual_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks active in J", 4, actual_blocks.size());
  }

  /**
   * Testing initial positions for L Piece are accurate.
   */
  @Test
  public void testLInitialLocations()
  {
    // L Piece Test
    temp_array.clear();
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(1, 3));
    temp_array.add(new Point(2, 1));
    final List<Point> actual_blocks = l_piece.blockLocations();
    assertEquals("L Piece Initial Locations", new Piece(board_location, temp_array), l_piece);
    assertTrue("L Piece Initial Locations Block", actual_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks active in L", 4, actual_blocks.size());
  }

  /**
   * Testing initial positions for O Piece are accurate.
   */
  @Test
  public void testOInitialLocations()
  {
    // O Piece Test
    temp_array.clear();
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 1));
    final List<Point> actual_blocks = o_piece.blockLocations();
    assertEquals("O Piece Initial Locations", new Piece(board_location, temp_array), o_piece);
    assertTrue("O Piece Initial Locations Block", actual_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks active in O", 4, actual_blocks.size());
  }

  /**
   * Testing initial positions for S Piece are accurate.
   */
  @Test
  public void testSInitialLocations()
  {
    // S Piece Test
    temp_array.clear();
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(3, 2));
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(1, 1));
    final List<Point> actual_blocks = s_piece.blockLocations();
    assertEquals("S Piece Initial Locations", new Piece(board_location, temp_array), s_piece);
    assertTrue("S Piece Initial Locations Block", actual_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks active in S", 4, actual_blocks.size());
  }

  /**
   * Testing initial positions for T Piece are accurate.
   */
  @Test
  public void testTInitialLocations()
  {
    // T Piece Test
    temp_array.clear();
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(3, 1));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(2, 1));
    final List<Point> actual_blocks = t_piece.blockLocations();
    assertEquals("T Piece Initial Locations", new Piece(board_location, temp_array), t_piece);
    assertTrue("T Piece Initial Locations Block", actual_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks active in T", 4, actual_blocks.size());
  }

  /**
   * Testing initial positions for Z Piece are accurate.
   */
  @Test
  public void testZInitialLocations()
  {
    // Z Piece Test
    temp_array.clear();
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(0, 2));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 1));
    final List<Point> actual_blocks = z_piece.blockLocations();
    assertEquals("Z Piece Initial Locations", new Piece(board_location, temp_array), z_piece);
    assertTrue("Z Piece Initial Locations Block", actual_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks active in Z", 4, actual_blocks.size());
  }

  /**
   * Test translating pieces left.
   */
  @Test
  public void movePiecesLeft()
  {
    Piece moved_piece;

    moved_piece = i_piece.moveLeft();
    assertEquals("I moveLeft(): x", 9, moved_piece.x());
    assertEquals("I moveLeft(): y", 10, moved_piece.y());
    
    moved_piece = j_piece.moveLeft();
    assertEquals("J moveLeft(): x", 9, moved_piece.x());
    assertEquals("J moveLeft(): y", 10, moved_piece.y());

    moved_piece = l_piece.moveLeft();
    assertEquals("L moveLeft(): x", 9, moved_piece.x());
    assertEquals("L moveLeft(): y", 10, moved_piece.y());

    moved_piece = o_piece.moveLeft();
    assertEquals("O moveLeft(): x", 9, moved_piece.x());
    assertEquals("O moveLeft(): y", 10, moved_piece.y());

    moved_piece = s_piece.moveLeft();
    assertEquals("S moveLeft(): x", 9, moved_piece.x());
    assertEquals("S moveLeft(): y", 10, moved_piece.y());
    
    moved_piece = t_piece.moveLeft();
    assertEquals("T moveLeft(): x", 9, moved_piece.x());
    assertEquals("T moveLeft(): y", 10, moved_piece.y());

    moved_piece = z_piece.moveLeft();
    assertEquals("Z moveLeft(): x", 9, moved_piece.x());
    assertEquals("Z moveLeft(): y", 10, moved_piece.y());
  }

  /**
   * Test translating pieces right.
   */
  @Test
  public void movePiecesRight()
  {
    Piece moved_piece;

    moved_piece = i_piece.moveRight();
    assertEquals("I moveRight(): x", 11, moved_piece.x());
    assertEquals("I moveRight(): y", 10, moved_piece.y());
    
    moved_piece = j_piece.moveRight();
    assertEquals("J moveRight(): x", 11, moved_piece.x());
    assertEquals("J moveRight(): y", 10, moved_piece.y());

    moved_piece = l_piece.moveRight();
    assertEquals("L moveRight(): x", 11, moved_piece.x());
    assertEquals("L moveRight(): y", 10, moved_piece.y());

    moved_piece = o_piece.moveRight();
    assertEquals("O moveRight(): x", 11, moved_piece.x());
    assertEquals("O moveRight(): y", 10, moved_piece.y());

    moved_piece = s_piece.moveRight();
    assertEquals("S moveRight(): x", 11, moved_piece.x());
    assertEquals("S moveRight(): y", 10, moved_piece.y());
    
    moved_piece = t_piece.moveRight();
    assertEquals("T moveRight(): x", 11, moved_piece.x());
    assertEquals("T moveRight(): y", 10, moved_piece.y());

    moved_piece = z_piece.moveRight();
    assertEquals("Z moveRight(): x", 11, moved_piece.x());
    assertEquals("Z moveRight(): y", 10, moved_piece.y());
  }

  /**
   * Test translating pieces down.
   */
  @Test
  public void movePiecesDown()
  {
    Piece moved_piece;

    moved_piece = i_piece.moveDown();
    assertEquals("I moveDown(): x", 10, moved_piece.x());
    assertEquals("I moveDown(): y", 9, moved_piece.y());
    
    moved_piece = j_piece.moveDown();
    assertEquals("J moveDown(): x", 10, moved_piece.x());
    assertEquals("J moveDown(): y", 9, moved_piece.y());

    moved_piece = l_piece.moveDown();
    assertEquals("L moveDown(): x", 10, moved_piece.x());
    assertEquals("L moveDown(): y", 9, moved_piece.y());

    moved_piece = o_piece.moveDown();
    assertEquals("O moveDown(): x", 10, moved_piece.x());
    assertEquals("O moveDown(): y", 9, moved_piece.y());

    moved_piece = s_piece.moveDown();
    assertEquals("S moveDown(): x", 10, moved_piece.x());
    assertEquals("S moveDown(): y", 9, moved_piece.y());
    
    moved_piece = t_piece.moveDown();
    assertEquals("T moveDown(): x", 10, moved_piece.x());
    assertEquals("T moveDown(): y", 9, moved_piece.y());

    moved_piece = z_piece.moveDown();
    assertEquals("Z moveDown(): x", 10, moved_piece.x());
    assertEquals("Z moveDown(): y", 9, moved_piece.y());
  }

  /**
   * Test rotating I pieces.
   */
  @Test
  public void rotateIPieces()
  {
    //Creating I Pieces
    piece = new Piece(board_location, ClassicPiece.I.blockLocations());

    //Create 1st Rotate
    temp_array.clear();
    temp_array.add(new Point(2, 3));
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(2, 0));

    List<Point> new_blocks = piece.rotate().blockLocations();
    assertTrue("Rotate I Once: Block Locations", new_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks showing after rotate I", 4, new_blocks.size());
    assertEquals("Rotate I Once: Location x", 10, piece.rotate().x());
    assertEquals("Rotate I Once: Location y", 10, piece.rotate().y());
    assertEquals("Rotate I Once then back", i_piece.blockLocations(),
                 i_piece.rotate().rotateClockwise().blockLocations());

    // Test 2nd Rotation
    temp_array.clear();
    temp_array.add(new Point(0, 2));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(3, 2));
    
    new_blocks = piece.rotate().rotate().blockLocations();
    assertTrue("Rotate I Twice: Block Locations", new_blocks.containsAll(temp_array));

    // Test 3rd Rotation
    temp_array.clear();
    temp_array.add(new Point(1, 0));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(1, 3));
    
    new_blocks = piece.rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate I Three Times: Block Locations", new_blocks.containsAll(temp_array));

    //4th Rotation (back to beginning)
    new_blocks = piece.rotate().rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate I 4 Times: Block Locations", 
               new_blocks.containsAll(i_piece.blockLocations()));
  }

  /**
   * Test rotating J pieces.
   */
  @Test
  public void rotateJPieces()
  {
    // Set up J Pieces
    piece = new Piece(board_location, ClassicPiece.J.blockLocations());
    // Test 1st Rotation
    temp_array.clear();
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(0, 2));
 
    List<Point> new_blocks = piece.rotate().blockLocations();
    assertTrue("Rotate J Once: Block Locations", new_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks showing after rotate J", 4, new_blocks.size());
    assertEquals("Rotate J Once: Location x", 10, piece.rotate().x());
    assertEquals("Rotate J Once: Location y", 10, piece.rotate().y());
    assertEquals("Rotate J Once then back", j_piece.blockLocations(),
                 j_piece.rotate().rotateClockwise().blockLocations());

    // Test 2nd Rotation
    temp_array.clear();
    temp_array.add(new Point(1, 0));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 2));
 
    new_blocks = piece.rotate().rotate().blockLocations();
    assertTrue("Rotate J Twice: Block Locations", new_blocks.containsAll(temp_array));

    // Test 3rd Rotation
    temp_array.clear();
    temp_array.add(new Point(3, 1));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(1, 2));
 
    new_blocks = piece.rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate J Three Times: Block Locations", new_blocks.containsAll(temp_array));

    //4th Rotation (back to beginning)
    new_blocks = piece.rotate().rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate J 4 Times: Block Locations", 
               new_blocks.containsAll(j_piece.blockLocations()));
  }

  /**
   * Test rotating L pieces.
   */
  @Test
  public void rotateLPieces()
  {
    // Set up L Pieces
    piece = new Piece(board_location, ClassicPiece.L.blockLocations());

    temp_array.clear();
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(0, 1));
 
    List<Point> new_blocks = piece.rotate().blockLocations();
    assertTrue("Rotate L Once: Block Locations", new_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks showing after rotate L", 4, new_blocks.size());
    assertEquals("Rotate L Once: Location x", 10, piece.rotate().x());
    assertEquals("Rotate L Once: Location y", 10, piece.rotate().y());
    assertEquals("Rotate L Once then back", l_piece.blockLocations(),
                 l_piece.rotate().rotateClockwise().blockLocations());

    //2nd Rotate
    temp_array.clear();
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 0));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(2, 2));
 
    new_blocks = piece.rotate().rotate().blockLocations();
    assertTrue("Rotate L Twice: Block Locations", new_blocks.containsAll(temp_array));

    //3rd Rotate
    temp_array.clear();
    temp_array.add(new Point(3, 2));
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(1, 1));
 
    new_blocks = piece.rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate L Three Times: Block Locations", new_blocks.containsAll(temp_array));

    //4th Rotation (back to beginning)
    new_blocks = piece.rotate().rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate L 4 Times: Block Locations", 
               new_blocks.containsAll(l_piece.blockLocations()));
  }

  /**
   * Test rotating S pieces.
   */
  @Test
  public void rotateSPieces()
  {
    // Set up S Pieces
    piece = new Piece(board_location, ClassicPiece.S.blockLocations());

    //Rotate Once
    temp_array.clear();
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(1, 3));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(2, 2));
    
    List<Point> new_blocks = piece.rotate().blockLocations();
    assertTrue("Rotate S Once: Block Locations", new_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks showing after rotate S", 4, new_blocks.size());
    assertEquals("Rotate S Once: Location x", 10, piece.rotate().x());
    assertEquals("Rotate S Once: Location y", 10, piece.rotate().y());
    assertEquals("Rotate S Once then back", s_piece.blockLocations(),
                      s_piece.rotate().rotateClockwise().blockLocations());
    
    //2nd Rotate
    temp_array.clear();
    temp_array.add(new Point(0, 1));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 2));

    new_blocks = piece.rotate().rotate().blockLocations();
    assertTrue("Rotate S Twice: Block Locations", new_blocks.containsAll(temp_array));

    //3rd Rotate
    temp_array.clear();
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(2, 0));
    temp_array.add(new Point(2, 1));

    new_blocks = piece.rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate S Three Times: Block Locations", new_blocks.containsAll(temp_array));

    //4th Rotation (back to beginning)
    new_blocks = piece.rotate().rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate S 4 Times: Block Locations", 
               new_blocks.containsAll(s_piece.blockLocations()));
  }

  /**
   * Test rotating Z pieces.
   */
  @Test
  public void rotateZPieces()
  {
    // Set up Z Pieces
    piece = new Piece(board_location, ClassicPiece.Z.blockLocations());

    //Rotate Once
    temp_array.clear();
    temp_array.add(new Point(1, 0));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(2, 2));
    
    List<Point> new_blocks = piece.rotate().blockLocations();
    assertTrue("Rotate Z Once: Block Locations", new_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks showing after rotate Z", 4, new_blocks.size());
    assertEquals("Rotate S Once: Location x", 10, piece.rotate().x());
    assertEquals("Rotate S Once: Location y", 10, piece.rotate().y());
    assertEquals("Rotate S Once then back", z_piece.blockLocations(),
                      z_piece.rotate().rotateClockwise().blockLocations());
    
    //2nd Rotate
    temp_array.clear();
    temp_array.add(new Point(3, 1));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 2));

    new_blocks = piece.rotate().rotate().blockLocations();
    assertTrue("Rotate Z Twice: Block Locations", new_blocks.containsAll(temp_array));

    //3rd Rotate
    temp_array.clear();
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(2, 3));
    temp_array.add(new Point(2, 2));

    new_blocks = piece.rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate Z Three Times: Block Locations", new_blocks.containsAll(temp_array));

    //4th Rotation (back to beginning)
    new_blocks = piece.rotate().rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate Z 4 Times: Block Locations", 
               new_blocks.containsAll(z_piece.blockLocations()));
  }

  /**
   * Test rotating O pieces.
   */
  @Test
  public void rotateOPieces()
  {
    // Set up the O Pieces needed.
    piece = new Piece(board_location, ClassicPiece.O.blockLocations());

    temp_array.clear();
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(2, 2));
    
    final List<Point> new_blocks = piece.rotate().blockLocations();
    assertTrue("Rotate O Once: Block Locations", new_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks showing after rotate O", 4, new_blocks.size());
    assertEquals("Rotate O Once: Location x", 10, piece.rotate().x());
    assertEquals("Rotate O Once: Location y", 10, piece.rotate().y());
    assertEquals("Rotate O Once then back", o_piece.blockLocations(),
                      o_piece.rotate().rotateClockwise().blockLocations());
    
  }

  /**
   * Test rotating T pieces.
   */
  @Test
  public void rotateTPieces()
  {
    // Set up T Pieces
    piece = new Piece(board_location, ClassicPiece.T.blockLocations());

    //First Rotate
    temp_array.clear();
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 3));
    temp_array.add(new Point(2, 1));
    temp_array.add(new Point(2, 2));    

    List<Point> new_blocks = piece.rotate().blockLocations();
    assertTrue("Rotate T Once: Block Locations", new_blocks.containsAll(temp_array));
    assertEquals("Only 4 blocks showing after rotate T", 4, new_blocks.size());
    assertEquals("Rotate T Once: Location x", 10, piece.rotate().x());
    assertEquals("Rotate T Once: Location y", 10, piece.rotate().y());
    assertEquals("Rotate T Once then back", t_piece.blockLocations(),
                      t_piece.rotate().rotateClockwise().blockLocations());
    
    //Second Rotate
    temp_array.clear();
    temp_array.add(new Point(2, 2));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(0, 2));

    new_blocks = piece.rotate().rotate().blockLocations();
    assertTrue("Rotate T Twice: Block Locations", new_blocks.containsAll(temp_array));

    //3rd Rotate
    temp_array.clear();
    temp_array.add(new Point(1, 0));
    temp_array.add(new Point(1, 1));
    temp_array.add(new Point(1, 2));
    temp_array.add(new Point(2, 1));

    new_blocks = piece.rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate T Three Times: Block Locations", new_blocks.containsAll(temp_array));

    //4th Rotation (back to beginning)
    new_blocks = piece.rotate().rotate().rotate().rotate().blockLocations();
    assertTrue("Rotate T 4 Times: Block Locations", 
               new_blocks.containsAll(t_piece.blockLocations()));
  }

  /**
   * Test the Absolute Board Locations.
   */
  @Test
  public void absoluteBoardLocations()
  {
    assertEquals("Board Locations Reflexive", j_piece.boardLocations(),
                 j_piece.boardLocations());
    assertEquals("J Piece Board Locations", j_piece.boardLocations(), 
                 j_left.moveRight().boardLocations());
  }

  /**
   * Testing the branches of the equals method which have not been tested yet.
   */
  @Test
  public void equalsMethod()
  {
    assertEquals("Reflexive equals", piece, piece);
    assertFalse("Different objects not equal", j_down.equals(temp_array));
    assertFalse("Same location different shapes", piece.equals(j_piece));
    assertFalse("Same array different y location", j_down.equals(j_piece));
    assertFalse("Same array differen x location", j_left.equals(j_piece));
  }

  /**
   * Testing the toString paths which have not been tested yet.
   */
  @Test
  public void toStringTest()
  {
    temp_array.clear();
    temp_array.add(new Point(-1, 3));
    temp_array.add(new Point(-6, 0));
    temp_array.add(new Point(0, 0));
    temp_array.add(new Point(4, 10));
    final Piece random_squares = new Piece(new Point(0, 6), temp_array);
    assertEquals("Reflexive toString", random_squares.toString(), 
                 random_squares.rotateClockwise().rotate().toString());
    assertEquals("Reflexive toString", piece.toString(), 
                 piece.rotateClockwise().rotate().toString());
  }

  /**
   * Testing hashCode.
   */
  @Test
  public void hashCodeTest()
  {
    assertEquals("Two equal objects have same hashcode", j_piece.hashCode(), 
                 j_piece.rotate().rotateClockwise().hashCode());
  }

  /**
   * Testing the random method in the enumeration.
   */
  @Test
  public void randomEnumTest()
  {
    final List<ClassicPiece> random_pieces = new ArrayList<ClassicPiece>();
    for (int i = 0; i < 100; i++)
    {
      random_pieces.add(ClassicPiece.random());
    }
    assertTrue("Random creates I Piece", random_pieces.contains(ClassicPiece.I));
    assertTrue("Random creates J Piece", random_pieces.contains(ClassicPiece.J));
    assertTrue("Random creates L Piece", random_pieces.contains(ClassicPiece.L));
    assertTrue("Random creates S Piece", random_pieces.contains(ClassicPiece.S));
    assertTrue("Random creates Z Piece", random_pieces.contains(ClassicPiece.Z));
    assertTrue("Random creates O Piece", random_pieces.contains(ClassicPiece.O));
    assertTrue("Random creates T Piece", random_pieces.contains(ClassicPiece.T));
  }
}
