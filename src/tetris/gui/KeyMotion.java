/*
 * Danielle Tucker
 * TCSS 305 - December 2012 
 * Project Tetris
 */

package tetris.gui;

import java.awt.event.KeyEvent;

/**
 * Enumeration for all of the key motions on a tetris board and
 * the default key bindings.
 * @author Danielle Tucker
 * @version 2012 December
 */
public enum KeyMotion
{
  /**
   * Move Left.
   */
  LEFT("LEFT", KeyEvent.VK_LEFT),

  /** 
   * Move Right.
   */
  RIGHT("RIGHT", KeyEvent.VK_RIGHT),

  /**
   * Rotate.
   */
  ROTATE("ROTATE", KeyEvent.VK_UP),

  /**
   * Move Down.
   */
  DOWN("DOWN", KeyEvent.VK_DOWN),

  /**
   * Drop piece.
   */
  DROP("DROP", KeyEvent.VK_SPACE),

  /**
   * Pause Motion.
   */
  PAUSE("PAUSE", KeyEvent.VK_P);
  
  /**
   * The string description/name of this piece.
   */
  private final String my_descriptor;

  /**
   * The default Key Binding for this motion.
   */
  private final int my_key_binding;
  
  // Constructor
  /**
   * Constructs a new Key Motion for motion in a tetris game.
   * @param the_name the String name / descriptor of this motion.
   * @param the_key_binding the integer default KeyBinding for this motion.
   */ 
  private KeyMotion(final String the_name, final int the_key_binding)
  {
    my_descriptor = the_name;
    my_key_binding = the_key_binding;
  }

  // Instance Methods
  /**
   * Provides the integer key binding for this motion.
   * @return the default key binding for this motion
   */
  public final int getDefaultKeyBinding()
  {
    return my_key_binding;
  }
  
  /**
   * Provides the string name of the motion type.
   * @return the name of this enumeration (a description).
   */
  public String toString()
  {
    return my_descriptor;
  }
}

