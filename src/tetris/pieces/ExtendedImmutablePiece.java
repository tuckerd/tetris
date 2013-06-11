/*
 * Danielle Tucker
 * TCSS 305 - October 2012
 * Project Tetris
 */

package tetris.pieces;

import java.awt.Point;
import java.util.List;

/**
 * This interface extends the required operations of immutable Tetris
 * pieces described in ImmutablePiece interface. 
 * @author Danielle Tucker
 * @version October 2012
 */
public interface ExtendedImmutablePiece extends ImmutablePiece
{
  /** 
   * @return the piece that results from rotating this piece 90
   * degrees clockwise.
   */
  ImmutablePiece rotateClockwise();

  /**
   * 
   * @return the board locations of the piece on a board.
   */
  List<Point> boardLocations();
}
