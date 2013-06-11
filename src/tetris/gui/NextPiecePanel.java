/*
 * Danielle Tucker
 * TCSS 305 - November 2012 
 * Project Tetris
 */

package tetris.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import tetris.board.Board;
import tetris.board.BoardEvent;
import tetris.board.BoardEventType;
import tetris.pieces.Piece;

/**
 * Panel to draw the Next Piece which is to be played on the board.
 * @author Danielle Tucker
 * @version 2012 November
 */
@SuppressWarnings("serial")
public class NextPiecePanel extends JPanel implements Observer
{
  /**
   * The max of the height/width of a piece.
   */
  private static final int PIECE_DIM = 4;

  /**
   * The size of one block in a piece.
   */
  private static final int BLOCK_SIZE = 20;

  /**
   * The font to use for labels.
   */
  private static final Font FONT = new Font("Serif", Font.ITALIC, 18);

  /**
   * The list of points of the location of the blocks.
   */
  private List<Point> my_next_piece_points;

  /**
   * The color of the next piece.
   */
  private Color my_color;
  
  /**
   * Flag to show current piece in play.
   */
  private boolean my_show_current_piece;

  /**
   * The list of points for the current piece in play.
   */
  private List<Point> my_current_piece_points;
  
  /**
   * The color for the current piece in play.
   */
  private Color my_current_color;

  /**
   * Sets up the panel to display the next piece. 
   * @param the_board the board which is in play.
   */
  public NextPiecePanel(final Board the_board)
  {
    super();
    final Piece piece = the_board.getNextPiece();
    my_next_piece_points = piece.blockLocations();
    my_color = piece.getColor();

    the_board.addObserver(this);
    
    setPreferredSize(new Dimension(BLOCK_SIZE * (PIECE_DIM + 2), 
                                   BLOCK_SIZE * (PIECE_DIM + 2) * 2));
    final JLabel next_text = new JLabel("Next Block\n");
    next_text.setFont(FONT);
    next_text.setForeground(Color.WHITE);
    add(next_text);
  }

  /**
   * Toggles whether or not to show current piece in play.
   */
  public void toggleShowCurrent()
  {
    my_show_current_piece ^= true;
    repaint();
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
    int start_x;
    int start_y;
    GradientPaint paint;
    
    //Paint each block in the pieces
    for (Point p: my_next_piece_points)
    {
      start_x = (p.x + 1) * BLOCK_SIZE;
      start_y = BLOCK_SIZE * (PIECE_DIM - p.y + 1);
      paint = new GradientPaint(start_x, start_y, my_color, 
                                start_x + BLOCK_SIZE, start_y + BLOCK_SIZE,
                                my_color.darker().darker());
      g2d.setPaint(paint);
      g2d.fillRect(start_x, start_y, BLOCK_SIZE, BLOCK_SIZE);
      g2d.setColor(Color.BLACK);
      g2d.drawRect(start_x, start_y, BLOCK_SIZE, BLOCK_SIZE);
    }
    
    if (my_show_current_piece)
    {
      //Paint current piece
      for (Point p: my_current_piece_points)
      {
        start_x = (p.x + 1) * BLOCK_SIZE;
        start_y = BLOCK_SIZE * (PIECE_DIM - p.y + 1) + BLOCK_SIZE * (PIECE_DIM + 1);
        paint = new GradientPaint(start_x, start_y, my_current_color, 
                                  start_x + BLOCK_SIZE, start_y + BLOCK_SIZE,
                                  my_current_color.darker().darker());
        g2d.setPaint(paint);
        g2d.fillRect(start_x, start_y, BLOCK_SIZE, BLOCK_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(start_x, start_y, BLOCK_SIZE, BLOCK_SIZE);
      }
      g2d.setFont(FONT);
      g2d.setColor(Color.WHITE);
      g2d.drawString("Current Block", BLOCK_SIZE / 2, BLOCK_SIZE * (PIECE_DIM + 2) + 2);
    }
  }

  /**
   * If updated is called by a Board and passed a new Piece then the
   * display is updated.
   * @param the_observed the observable object this is watching
   * @param the_arg the argument the observed passed
   */
  public void update(final Observable the_observed, final Object the_arg)
  {
    if (the_arg instanceof BoardEvent && the_observed instanceof Board)
    {
      final BoardEvent event = (BoardEvent) the_arg;
      if (event.getEventType() == BoardEventType.NEW_NEXT_PIECE &&
          event.getObject() instanceof Piece)
      {
        my_color = ((Piece) event.getObject()).getColor();
        my_next_piece_points = ((Piece) event.getObject()).blockLocations();
        repaint();
      }
      else if (event.getEventType() == BoardEventType.CHANGE_CURRENT_PIECE &&
               event.getObject() instanceof Piece)
      {
        my_current_color = ((Piece) event.getObject()).getColor();
        my_current_piece_points = ((Piece) event.getObject()).blockLocations();
        repaint();
      }
    }
  }
}
