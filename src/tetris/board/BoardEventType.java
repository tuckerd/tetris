/*
 * Danielle Tucker
 * TCSS 305 - November 2012 
 * Project Tetris
 */

package tetris.board;

/**
 * Describes the Different types of events which can occur on a Board.
 * @author Danielle Tucker
 * @version 2012 November
 */
public enum BoardEventType
{
  /**
   * The next piece has changed.
   */
  NEW_NEXT_PIECE, 
  
  /**
   * The Current Piece has changed (by rotate, left, right, step, or new piece in play).
   */
  CHANGE_CURRENT_PIECE, 
  
  /**
   * The score has been changed.
   */
  NEW_SCORE, 
  
  /**
   * The game is over.
   */
  GAME_OVER, 
  
  /**
   * The board has been changed by adding a piece or clearing rows.
   */
  CHANGE_ROWS;
}