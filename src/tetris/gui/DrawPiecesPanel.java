/*
 * Danielle Tucker
 * TCSS 305 - November 2012 
 * Project Tetris
 */

package tetris.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import tetris.board.Board;
import tetris.board.BoardEvent;
import tetris.board.BoardEventType;
import tetris.pieces.Piece;

/**
 * Class which draw the pieces in play and the state of the
 * current board.
 * 
 * @author Danielle Tucker
 * @version 2012 November
 */
@SuppressWarnings("serial")
public class DrawPiecesPanel extends JPanel implements Observer
{
  /**
   * The startup size of the panel.
   */
  private static final Dimension DIMENSION = new Dimension(250, 450);

  /**
   * The collection of Colors representing the current frozen pieces on the board.
   */
  private final List<Color[]> my_board_list;
  
  /**
   * The size of blocks for drawing.
   */
  private int my_block_size;

  /**
   * The points of the current piece in play.
   */
  private List<Point> my_piece_points;
  
  /**
   * The color of the current piece.
   */
  private Color my_piece_color;

  /**
   * The image which will be the background of the  box.
   */
  private BufferedImage my_image;
  
  /**
   * A flag for if a game is over.
   */
  private boolean my_game_over;
  
  /**
   * A flag for invisible piece drop mode.
   */
  private boolean my_invisible_mode;

  /**
   * Draws the current frozen Tetris board and piece in play.
   * 
   * @param the_board The board we will paint.
   */
  public DrawPiecesPanel(final Board the_board)
  {
    super();
    my_board_list = the_board.getBoard();
    my_piece_points = the_board.getPiece().boardLocations();
    setPreferredSize(DIMENSION);
    my_block_size = Math.min(getWidth() / (Board.NUM_COLS + 2), 
                             getHeight() / (Board.NUM_ROWS + 2));
    the_board.addObserver(this);
    my_piece_color = the_board.getPiece().getColor();
    setOpaque(false);
    
    try 
    {                
      my_image = ImageIO.read(this.getClass().getResource("darkSky.jpg")); 
    } 
    catch (final IOException|IllegalArgumentException e) 
    { 
      assert true;     // no image is shown in the background
    }
  }

  /**
   * Toggles if the current piece is visible or not.
   */
  public void toggleInvisiblePiece()
  {
    my_invisible_mode ^= true;
  }
  
  /**
   * Paints the all of the pieces on the board.
   * 
   * @param the_graphics The graphics context to use for painting.
   */
  public void paintComponent(final Graphics the_graphics)
  {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
    my_block_size = Math.min(getWidth() / (Board.NUM_COLS + 2), 
                          getHeight() / (Board.NUM_ROWS + 2));

    // Paint Box around playing area and draw background image
    g2d.drawImage(my_image, my_block_size, my_block_size - 1,
                 my_block_size * Board.NUM_COLS + 1,
                 my_block_size * Board.NUM_ROWS + 2, null);
    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(2f));
    g2d.drawRect(my_block_size, my_block_size - 1,
                 my_block_size * Board.NUM_COLS + 1,
                 my_block_size * Board.NUM_ROWS + 2);

    int start_x;
    int start_y;
    GradientPaint paint;

    //Paint Piece in Play
    if (!my_invisible_mode)
    {
      for (Point p: my_piece_points)
      {
        if (p.y < Board.NUM_ROWS)
        {
          start_x = (p.x + 1) * my_block_size;
          start_y = my_block_size * (Board.NUM_ROWS - p.y);
          paint =
              new GradientPaint(start_x, start_y, my_piece_color, start_x + my_block_size,
                                start_y + my_block_size, my_piece_color.darker().darker());
          g2d.setPaint(paint);
          g2d.fillRect(start_x, start_y, my_block_size, my_block_size);

          g2d.setColor(Color.BLACK);
          g2d.setStroke(new BasicStroke(1f));
          g2d.drawRect(start_x, start_y, my_block_size, my_block_size);
        }
      }
    }
    
    //Paint Frozen Pieces
    for (int row = 0; row < my_board_list.size(); row++)
    {
      final Color[] current_row = my_board_list.get(row);
      for (int col = 0; col < current_row.length; col++)
      {
        if (current_row[col] != null)
        {
          start_x = (col + 1) * my_block_size;
          start_y = my_block_size * (Board.NUM_ROWS - row);
          if (my_game_over)
          {
            paint = new GradientPaint(start_x, start_y, Color.WHITE, 
                                      start_x + my_block_size, start_y + my_block_size,
                                      Color.BLACK);
          }
          else
          {
            paint = new GradientPaint(start_x, start_y, current_row[col], 
                                      start_x + my_block_size, start_y + my_block_size, 
                                      current_row[col].darker().darker());
          }
          g2d.setPaint(paint);
          g2d.fillRect(start_x, start_y, my_block_size, my_block_size);
          g2d.setColor(Color.BLACK);
          g2d.setStroke(new BasicStroke(1f));
          g2d.drawRect(start_x, start_y, my_block_size, my_block_size);
        }
      }
    }    
  }

  /**
   * If passed a Board in the_observed and BoardEvent in the_arg the display is updated
   * to reflect the changes which are occurring on the board.
   * 
   * @param the_observed the object being observed
   * @param the_arg the argument passed by the observed when calling notifyObservers()
   */
  public void update(final Observable the_observed, final Object the_arg)
  {
    if (the_observed instanceof Board && the_arg instanceof BoardEvent)
    {
      final Object new_object = ((BoardEvent) the_arg).getObject();
      final BoardEventType event = ((BoardEvent) the_arg).getEventType();
      
      switch(event)
      {
        case CHANGE_CURRENT_PIECE:
          if (new_object instanceof Piece)
          {
            my_piece_color = ((Piece) new_object).getColor();
            my_piece_points = ((Piece) new_object).boardLocations();
            repaint();
          }
          break;
        case CHANGE_ROWS:
          if (new_object instanceof List<?> &&
              ((List<?>) new_object).get(0) instanceof Color[])
          {
            my_board_list.clear();
            for (Object r: (List<?>) new_object)
            {
              my_board_list.add((Color[]) r);
            }
            repaint();
          }
          break;
        case GAME_OVER:
          my_game_over = true;
          repaint();
          break;
        default:
          break;
      }
    }
  }
}
