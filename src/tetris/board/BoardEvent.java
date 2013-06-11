/*
 * Danielle Tucker
 * TCSS 305 - November 2012 
 * Project Tetris
 */

package tetris.board;

/**
 * A wrapper class which packages together a board event type with the
 * associated item which has been changed on the board.
 * @author Danielle Tucker
 * @version 2012 November
 */
public class BoardEvent
{
  /**
   * The type of event which occurs on this board.
   */
  private final BoardEventType my_event;
  
  /**
   * The object which has changed.
   */
  private final Object my_object;
  
  /**
   * blah.
   * @param the_event the type of board event
   * @param the_object the object which changed because of this board event
   */
  public BoardEvent(final BoardEventType the_event, final Object the_object)
  {
    my_event = the_event;
    my_object = the_object;
  }
  
  /**
   * blah.
   * @return the type of event which occured.
   */
  public BoardEventType getEventType()
  {
    return my_event;
  }
  
  /**
   * blah.
   * @return the object which changed.
   */
  public Object getObject()
  {
    return my_object;
  }
}
