/*
 * Danielle Tucker
 * TCSS 305 - October 2012
 * Project Tetris
 */

package tetris.pieces;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An enumeration and associated functionality for classic Tetris pieces. See
 * Wikipedia.org Tetris page for full description of pieces.
 * (http://en.wikipedia.org/wiki/Tetris Accessed: October 2012)
 * Colors used are from The Tetris Company standard colors listed on wiki page
 * @author Danielle Tucker
 * @version 2012 October
 */
public enum ClassicPiece
{
  /**
   * I Piece.
   */
  I(new Point[] {new Point(0, 1), new Point(1, 1),
    new Point(2, 1), new Point(3, 1)}, Color.CYAN),

  /** 
   * J Piece.
   */
  J(new Point[] {new Point(1, 1), new Point(2, 1),
                 new Point(2, 2), new Point(2, 3)}, Color.BLUE),

  /**
   * L Piece.
   */
  L(new Point[] {new Point(1, 3), new Point(1, 2),
                 new Point(1, 1), new Point(2, 1)}, Color.ORANGE),

  /**
   * O Piece.
   */
  O(new Point[] {new Point(1, 1), new Point(1, 2),
                 new Point(2, 1), new Point(2, 2)}, Color.YELLOW),

  /**
   * S Piece.
   */
  S(new Point[] {new Point(1, 1), new Point(2, 1), 
                 new Point(2, 2), new Point(3, 2)}, Color.GREEN),

  /**
   * T Piece.
   */
  T(new Point[] {new Point(1, 1), new Point(2, 1),
                 new Point(2, 2), new Point(3, 1)}, Color.MAGENTA),

  /**
   * Z Piece.
   */
  Z(new Point[] {new Point(0, 2), new Point(1, 1),
                 new Point(1, 2), new Point(2, 1)}, Color.RED);
  
  /**
   * A Random that we use for generating a random ClassicPiece.
   */
  private static final Random RANDOM = new Random();
  
  /**
   * The list corresponding to a particular set of default blocks in a piece.
   */
  private final List<Point> my_piece_list = new ArrayList<Point>();

  /**
   * The color of the classic block.
   */
  private final Color my_color;
  
  // Constructor
  /**
   * Constructs a new Classic Piece array of points.
   * @param the_array the list of points for the classic shape.
   * @param the_color the color of the classic shape.
   */ 
  private ClassicPiece(final Point[] the_array, final Color the_color)
  {
    my_piece_list.clear();
    for (int i = 0; i < the_array.length; i++)
    {
      my_piece_list.add(the_array[i]);
    }
    my_color = the_color;
  }

  // Instance Methods
  /**
   * Returns a random Classic Piece Type.
   * @return a random Classic Piece.
   */
  public static ClassicPiece random()
  {
    return values()[RANDOM.nextInt(values().length)];
  }

  /**
   * Returns a new copy of the local block Points within the piece.
   * @return a deep copy of block locations within the piece.
   */
  public List<Point> blockLocations()
  {
    final List<Point> result = new ArrayList<Point>(my_piece_list.size());
    for (Point points : my_piece_list)
    {
      result.add(new Point(points));
    }
    return result;
  }
  
  /**
   * Provides the color of this Classic Piece.
   * @return the color of this piece.
   */
  public Color getColor()
  {
    return my_color;
  }
}
