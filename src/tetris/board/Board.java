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
import java.util.Observable;
import java.util.Random;

import tetris.pieces.ClassicPiece;
import tetris.pieces.Piece;

/**
 * Representation of the Playing surface of a Tetris Board.
 * 
 * @author Danielle Tucker
 * @version 2012 November
 */
public class Board extends Observable
{

  /**
   * The number of playable rows on the board.
   */
  public static final int NUM_ROWS = 20;

  /**
   * The number of playable columns on the board.
   */
  public static final int NUM_COLS = 10;

  /**
   * When doing random removal the top 5 rows will be not be chosen.
   */
  private static final int ROW_REMOVAL_BOUNDARY = 5;

  /**
   * The symbol for the EDGE of the board in the toString().
   */
  private static final char EDGE = '#';
  
  /**
   * A new line for the toString().
   */
  private static final String NEW_LINE = "\n";
  
  /**
   * The Bonus multiplier for each row cleared.
   */
  private static final int LINE_CLEAR = 100;

  /**
   * The points for each piece placed.
   */
  private static final int PIECE_PLACE = 5;

  /**
   * The location where pieces are dropped from on default.
   */
  private final Point my_drop_point = new Point(NUM_COLS / 2 - 2, NUM_ROWS + 1);

  /**
   * The representation of the frozen pieces on the board.
   */
  private final List<Color[]> my_board = new LinkedList<Color[]>();
  
  /**
   * The Random number generator for the random removal of pieces.
   */
  private final Random my_random = new Random();

  /**
   * The sequence of pieces for tournament play.
   */
  private Piece[] my_piece_sequence;

  /**
   * The current piece being played.
   */
  private Piece my_current_piece;

  /**
   * My next piece to play.
   */
  private Piece my_next_piece;

  /**
   * Indicates if the pieces are generated randomly.
   */
  private boolean my_random_play = true;

  /**
   * Keeps track of the current place in the sequence of user-defined pieces.
   */
  private int my_piece_counter;

  /**
   * Whether or not the last piece frozen was above the board indicating game over.
   */
  private boolean my_game_in_progress = true;

  /**
   * The current score.
   */
  private int my_score;

  /**
   * Creates an empty board with random piece selection.
   */
  public Board()
  {
    super();
    setUpBoard();
    my_current_piece = getNext();
    my_piece_counter = 0;
    my_next_piece = getNext();
  }

  /**
   * Creates a board with the sequence of pieces specified.
   * If the_piece is null then the game is set up with random play.
   * 
   * @param the_pieces the sequence of pieces to play.
   */
  public Board(final Piece[] the_pieces)
  {
    super();
    setUpBoard();
    // Copy the array of pieces to play
    my_piece_sequence = new Piece[the_pieces.length];
    for (int i = 0; i < the_pieces.length; i++)
    {
      my_piece_sequence[i] = new Piece(the_pieces[i]);
    }

    // Set up current and next piece
    if (my_piece_sequence != null && my_piece_sequence.length > 0)
    {
      my_current_piece = my_piece_sequence[0];
      my_piece_counter = 1;
      my_random_play = false;
    }
    else
    // If piece array is empty or null set up for random play
    {
      my_current_piece = getNext();
    }
    my_next_piece = getNext();
  }

  /**
   * Attempts to rotate the current active piece on the board.
   * 
   * @return whether or not this object successfully rotated.
   */
  public boolean rotate()
  {
    boolean result = true;
    final Piece moved_piece = (Piece) my_current_piece.rotate();
    for (Point p : moved_piece.boardLocations())
    {
      if (p.x < 0 || p.x >= NUM_COLS || p.y < 0) // piece is past the boundary
      {
        result = false;
      }
      else if (p.y < NUM_ROWS && my_board.get(p.y)[p.x] != null)
      // piece is trying to occupy a space which is full
      {
        result = false;
      }
    }
    if (result)
    {
      my_current_piece = moved_piece;
      setChanged();
      notifyObservers(new BoardEvent(BoardEventType.CHANGE_CURRENT_PIECE, getPiece()));
    }
    return result;
  }

  /**
   * Attempts to move the current active piece on the board left.
   * 
   * @return whether or not this object successfully moved.
   */
  public boolean moveLeft()
  {
    boolean result = true;
    final Piece moved_piece = (Piece) my_current_piece.moveLeft();
    for (Point p : moved_piece.boardLocations())
    {
      if (p.x < 0) // piece is past the left of the board
      {
        result = false;
        break;
      }
      else if (p.y < NUM_ROWS - 1 && my_board.get(p.y)[p.x] != null)
      // piece is trying to occupy a space which is full
      {
        result = false;
        break;
      }
    }
    if (result)
    {
      my_current_piece = moved_piece;
      setChanged();
      //notifyObservers(my_current_piece);
      notifyObservers(new BoardEvent(BoardEventType.CHANGE_CURRENT_PIECE, getPiece()));
    }
    return result;
  }

  /**
   * Attempts to move the current active piece on the board right.
   * 
   * @return whether or not this object successfully moved.
   */
  public boolean moveRight()
  {
    boolean result = true;
    final Piece moved_piece = my_current_piece.moveRight();
    for (Point p : moved_piece.boardLocations())
    {
      if (p.x >= NUM_COLS) // piece is past the right of the board
      {
        result = false;
        break;
      }
      else if (p.y < NUM_ROWS - 1 && my_board.get(p.y)[p.x] != null)
      // piece is trying to occupy a space which is full
      {
        result = false;
        break;
      }     
    }
    if (result)
    {
      my_current_piece = moved_piece;
      setChanged();
      notifyObservers(new BoardEvent(BoardEventType.CHANGE_CURRENT_PIECE, getPiece()));
    }
    return result;
  }

  /**
   * Attempts to move the current active piece on the board down.
   * If piece is outside playable area it will not move.
   * @return whether or not this object successfully moved.
   */
  public boolean moveDown()
  {
    boolean result = true;
    final Piece moved_piece = (Piece) my_current_piece.moveDown();
    for (Point p : moved_piece.boardLocations())
    {
      if (p.y < 0 || p.x < 0 || p.x > NUM_COLS - 1) 
        // piece is past the playable area of the board
      {
        result = false;
        break;
      }
      else if (p.y < NUM_ROWS && my_board.get(p.y)[p.x] != null)
      // piece is trying to occupy a space which is full
      {
        result = false;
        break;
      }
    }
    if (result)
    {
      my_current_piece = moved_piece;
      setChanged();
      notifyObservers(new BoardEvent(BoardEventType.CHANGE_CURRENT_PIECE, getPiece()));
    }
    return result;
  }

  /**
   * Causes the current piece to move down by one row. If moving down fails the
   * block freezes and becomes part of the board's grid.
   */
  public void step()
  {
    if (!my_game_in_progress || moveDown())
    {
      return;
    }

    // Add my current piece to the board
    for (Point p : my_current_piece.boardLocations())
    {
      if (p.y < NUM_ROWS)
      {
        (my_board.get(p.y))[p.x] = my_current_piece.getColor();
      }
      else
      // Trying to freeze a piece above playable area so game over
      {
        my_game_in_progress = false;
        setChanged();
        notifyObservers(new BoardEvent(BoardEventType.GAME_OVER, gameInProgress()));
      }
    }
    // Set up the next piece to be played and notify observers
    my_current_piece = my_next_piece;
    setChanged();
    notifyObservers(new BoardEvent(BoardEventType.CHANGE_CURRENT_PIECE, getPiece()));

    my_next_piece = getNext();
    setChanged();
    notifyObservers(new BoardEvent(BoardEventType.NEW_NEXT_PIECE, getNextPiece()));

    //Clear Rows
    final int rows_cleared = clearRows();
    
    my_score = my_score + (LINE_CLEAR * rows_cleared * rows_cleared) + PIECE_PLACE;
    setChanged();
    
    notifyObservers(new BoardEvent(BoardEventType.NEW_SCORE, getScore()));

    // Notify observers that the board layout has been altered.
    setChanged();
    notifyObservers(new BoardEvent(BoardEventType.CHANGE_ROWS, getBoard()));
  }

  /**
   * Resets board, score, pieces, etc for replay of game.  In random mode it will not
   * respawn the same pieces but in tournament mode (where the pieces
   * have been given to the constructor) will reset to piece 0.
   */
  public void resetBoard()
  {
    my_board.clear();
    setUpBoard();
    setChanged();
    notifyObservers(new BoardEvent(BoardEventType.CHANGE_ROWS, getBoard()));
    
    if (my_random_play)
    {
      my_current_piece = getNext();
    }
    else
    {
      my_current_piece = my_piece_sequence[0];
      my_piece_counter = 1;
    }
    setChanged();
    notifyObservers(new BoardEvent(BoardEventType.CHANGE_CURRENT_PIECE, getPiece()));
    
    my_next_piece = getNext();
    setChanged();
    notifyObservers(new BoardEvent(BoardEventType.NEW_NEXT_PIECE, getNextPiece()));
    
    my_score = 0;
    setChanged();
    notifyObservers(new BoardEvent(BoardEventType.NEW_SCORE, getScore()));
    
    my_game_in_progress = true;
    
  }
  
  /**
   * Randomly removes pieces from the board.
   */
  public void randomRemoval()
  {
    final int column = my_random.nextInt(NUM_COLS);
    final int row = my_random.nextInt(NUM_ROWS - ROW_REMOVAL_BOUNDARY);
    final Color[] color_row = my_board.get(row);
    color_row[column] = null;
    my_board.set(row, color_row);

    // Notify observers that the board layout has been altered.
    setChanged();
    notifyObservers(new BoardEvent(BoardEventType.CHANGE_ROWS, getBoard()));
  }
  /**
   * Provides a copy of the current piece on the board.
   * @return a copy of the current piece on the board.
   */
  public Piece getPiece()
  {
    return new Piece(my_current_piece);
  }

  /**
   * Provides a copy of the next piece on the board.
   * @return a copy of the next piece on the board.
   */
  public Piece getNextPiece()
  {
    return new Piece(my_next_piece);
  }
  
  /**
   * Provides a copy of the sequence of pieces to be used in this board.
   * @return the sequence of pieces to be used in this board. If on random play
   * it will return an empty array.
   */
  public Piece[] getSequence()
  {
    Piece[] result;
    if (my_random_play)
    {
      result = new Piece[0];
    }
    else
    {
      result = new Piece[my_piece_sequence.length];
      for (int i = 0; i < my_piece_sequence.length; i++)
      {
        result[i] = new Piece(my_piece_sequence[i]);
      }
    }
    return result;
  }

  /**
   * Get another copy of the current board.
   * @return a copy of the current board.
   */
  public List<Color[]> getBoard()
  {
    final List<Color[]> result = new LinkedList<Color[]>();
    for (Color[] row: my_board)
    {
      result.add(row.clone());
    }
    return result;
  }

  /**
   * Returns if the game is in progress.
   * @return if the game is in progress
   */
  public boolean gameInProgress()
  {
    return my_game_in_progress;
  }
  
  /**
   * Returns the current score.
   * @return the current score.
   */
  public int getScore()
  {
    return my_score;
  }
  
  /**
   * Represents the current state of the board.  The current piece
   * is displayed if its position is within the boundary of the board. 
   * @return a string of the current state of the board.
   */
  public String toString()
  {
    final List<Point> piece = my_current_piece.boardLocations();
    final Point temp_point = new Point(0, 0);
  
    final StringBuilder sb = new StringBuilder();
    // Top Boundary
    for (int c = 0; c < NUM_COLS + 2; c++)
    {
      sb.append(EDGE);
    }
    sb.append(NEW_LINE);
  
    // Build a Row
    for (int row = NUM_ROWS - 1; row >= 0; row--)
    {
      // SideBoundary
      sb.append(EDGE);
      // RowFill
      final Color[] current_row = my_board.get(row);
      for (int col = 0; col < current_row.length; col++)
      {
        //Piece in motion is on the board here
        temp_point.move(col, row);
        if (piece.contains(temp_point))
        {
          sb.append('o');
        }
        //Read off frozen blocks
        else if (current_row[col] == null)
        {
          sb.append('.');
        }
        else
        {
          sb.append('x');
        }
      }
      // SideBoundary
      sb.append(EDGE);
      sb.append(NEW_LINE);
    }
    // Bottom Boundary
    for (int c = 0; c < NUM_COLS + 2; c++)
    {
      sb.append(EDGE);
    }
    sb.append(NEW_LINE);
  
    return sb.toString();
  
  }

  /**
   * Sets up a blank board.
   */
  private void setUpBoard()
  {
    // Set up the empty board
    for (int r = 0; r < NUM_ROWS; r++)
    {
      my_board.add(new Color[NUM_COLS]);
    }
  }

  /**
   * Clears any rows after a piece has been frozen.
   * @return the number of rows which have been cleared.
   */
  private int clearRows()
  {
    // Clear up any rows which are filled
    Color[] row_check;
    int rows_cleared = 0;
    for (int r = NUM_ROWS - 1; r >= 0; r--)
    {
      boolean row_full = true;
      row_check = my_board.get(r);
  
      for (int c = 0; c < row_check.length; c++)
      {
        if (row_check[c] == null)
        {
          row_full = false;
        }
      }
      if (row_full)
      {
        my_board.remove(r);
        my_board.add(new Color[NUM_COLS]);
        rows_cleared++;
      }
    }
    return rows_cleared;
  }

  /**
   * Set up the next piece to be played.
   * 
   * @return the next piece to be played.
   */
  private Piece getNext()
  {
    Piece result;
    if (my_random_play)
    {
      final ClassicPiece new_piece = ClassicPiece.random();
      result = new Piece(my_drop_point, new_piece.blockLocations(), new_piece.getColor());
    }
    else
    {
      if (my_piece_counter < my_piece_sequence.length)
      {
        result = my_piece_sequence[my_piece_counter];
        my_piece_counter++;
      }
      else
      {
        result = my_piece_sequence[0];
        my_piece_counter = 1;
      }
    }
    return result;
  }

}

