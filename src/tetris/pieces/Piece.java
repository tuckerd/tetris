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

/**
 * Describes the Immutable Tetris pieces.
 * @author Danielle Tucker
 * @version 2012 October
 */
public class Piece implements ExtendedImmutablePiece
{
  /**
   * The shift required to make rotate methods center around center of mass of piece.
   */
  private static final int SHIFT = 3;
  /**
   * The x location of the piece.
   */
  private final int my_x_location;
  
  /**
   * The y location of the piece.
   */
  private final int my_y_location;
  
  /**
   * The color of the piece.
   */
  private final Color my_color;
  
  /**
   * The array of local (x,y) points where the blocks are located within a piece.
   */
  private final List<Point> my_block_locations;

  /**
   * Create a Tetris piece with the specified location and array of local block locations.
   * Note: (0, 0) is "located" in the lower left hand corner.
   * @param the_location the global (x,y) location of the piece.
   * @param the_block_locations the array of local points (x,y) where the 
   * blocks are located within the piece.
   * @param the_color the color of this piece.
   */ 
  public Piece(final Point the_location, final List<Point> the_block_locations, 
               final Color the_color)
  {
    my_x_location = the_location.x;
    my_y_location = the_location.y;
    my_block_locations = new ArrayList<Point>(the_block_locations.size());
    for (Point points: the_block_locations)
    {
      my_block_locations.add(new Point(points));
    }
    my_color = the_color;   //Color class is immutable.
  }

  /**
   * Create a Tetris piece with the specified location and array of local block locations with 
   * default color of Color.BLUE.
   * Note: (0, 0) is "located" in the lower left hand corner.
   * @param the_location the global (x,y) location of the piece.
   * @param the_block_locations the array of local points (x,y) where the 
   * blocks are located within the piece.  All points must be non-negative.
   */
  public Piece(final Point the_location, final List<Point> the_block_locations)
  {
    this(new Point(the_location.x, the_location.y),
         the_block_locations, Color.BLUE);
  }
  
  /**
   * Creates a new deep copy of the Piece provided.
   * @param the_piece the piece to be copied.
   */
  public Piece(final Piece the_piece)
  {
    this(new Point(the_piece.my_x_location, the_piece.my_y_location), 
         the_piece.my_block_locations, the_piece.my_color);
  }
  
  
  /** 
   * Returns a new Piece that results from moving this Piece one unit to the left.
   * @return the piece that results from moving this piece one unit to the left.
   */
  public Piece moveLeft()
  {
    return new Piece(new Point(my_x_location - 1, my_y_location), my_block_locations,
                     my_color);
  }

  /** 
   * Returns a new Piece that results from moving this Piece one unit to the right.
   * @return the Piece that results from moving this Piece one unit to the right.
   */
  public Piece moveRight()
  {
    return new Piece(new Point(my_x_location + 1, my_y_location), my_block_locations,
                     my_color);
  }

  /** 
   * Returns a new Piece that results from moving this Piece one unit down.
   * @return a new Piece that results from moving this Piece one unit down.
   */
  public Piece moveDown()
  {
    return new Piece(new Point(my_x_location, my_y_location - 1), 
                     my_block_locations, my_color);
  }

  /**
   * Returns the x coordinate of this Piece.
   * @return the x coordinate of this Piece.
   */
  public int x()
  {
    return my_x_location;
  }

  /**
   * Returns the y coordinate of this Piece.
   * @return the y coordinate of this Piece.
   */
  public int y()
  {
    return my_y_location;
  }
  
  /**
   * Returns a copy of the locations of each block in this Piece.
   * @return copy of local point of each block in this Piece.
   */
  public List<Point> blockLocations()
  {
    final List<Point> result = new ArrayList<Point>(my_block_locations.size());
    for (Point points: my_block_locations)
    {
      result.add(new Point(points));
    }
    return result;
  }

  /**
   * Returns the position on the "board" of each block in this Piece.  
   * Found by adding each local point of the piece to the location of the piece on the "board."
   * @return the positions on the "board" of each block in this Piece.
   */
  public List<Point> boardLocations()
  {
    final List<Point> result = new ArrayList<Point>(my_block_locations.size());
    for (Point points: my_block_locations)
    {
      result.add(new Point(points.x + x(), points.y + y()));
    }
    return result;
  }

  /**
   * Returns the Color of the piece.
   * @return the color of this piece.
   */
  public Color getColor()
  {
    return my_color;
  }
  
  
  /**
   * Return Piece rotated counterclockwise by 90 degrees.
   * @return new piece rotated counterclockwise by 90 degrees.
   */
  public Piece rotate()
  {
    final List<Point> new_block_locations = blockLocations();
    for (Point point: new_block_locations)
    {
      point.setLocation(SHIFT - point.getY(), point.getX());
    }
    return new Piece(new Point(my_x_location, my_y_location), new_block_locations,
                     my_color);
  }

  /**
   * Return Piece rotated clockwise by 90 degrees.
   * @return new piece rotated clockwise by 90 degrees.
   */
  public Piece rotateClockwise()
  {
    final List<Point> new_block_locations = blockLocations();
    for (Point point: new_block_locations)
    {
      point.setLocation(point.getY(), SHIFT - point.getX());
    }
    return new Piece(new Point(my_x_location, my_y_location), new_block_locations);
  }
  
  /**
   * String representation of Tetris Piece.
   * @return string representation of the Tetris Piece.
   */
  public String toString()
  {
    final StringBuilder result = new StringBuilder();
    final Point grid = new Point(0, 0);
    int min_x = my_block_locations.get(0).x;
    int max_x = my_block_locations.get(0).x;
    int min_y = my_block_locations.get(0).y;
    int max_y = my_block_locations.get(0).y;
    int temp_x;
    int temp_y;
 
    //Determine the boundaries of the points to display
    for (Point point: my_block_locations)
    {
      temp_x = point.x;
      if (temp_x < min_x)
      {
        min_x = temp_x;
      }
      else if (temp_x > max_x)
      {
        max_x = temp_x;
      }
      temp_y = point.y;
      if (temp_y < min_y)
      {
        min_y = temp_y;
      }
      else if (temp_y > max_y)
      {
        max_y = temp_y;
      }
    }
    //Cycle through points and represent blocks as []
    for (int y = max_y; y >= min_y; y--)
    {
      for (int x = min_x; x <= max_x; x++)
      {
        grid.setLocation(x, y);
        if (my_block_locations.contains(grid))
        {
          result.append("[]");
        }
        else
        {
          result.append("  ");
        }
      }
      result.append('\n');
    }
    return result.toString();
  }

  /**
   * Determines if the object is equivalent of this Tetris Piece.
   * @param the_object the Object to compare to this Piece.
   * @return whether or not the_object is equivalent to this piece.
   */
  public boolean equals(final Object the_object)
  {
    boolean result = false;
    if (this == the_object)
    {
      result = true;
    }
    if (the_object != null && the_object.getClass() == getClass())
    { 
      final Piece other_piece = (Piece) the_object;
      final List<Point> temp = other_piece.blockLocations();
      temp.removeAll(my_block_locations);
      if (x() == other_piece.x() && y() == other_piece.y() &&
          temp.isEmpty())
      {
        result = true;
      }
    }
    return result;
  }
  
  /**
   * Returns the int hash Code value of this Tetris Piece.
   * @return the hashCode of this Tetris Piece.
   */
  public int hashCode()
  {
    return toString().hashCode();
  }
}
