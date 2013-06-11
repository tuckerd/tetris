/*
 * Danielle Tucker
 * TCSS 305 - December 2012 
 * Project Tetris
 */

package tetris.audio;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import tetris.board.Board;
import tetris.board.BoardEvent;
import tetris.board.BoardEventType;

/**
 * Class to play tetris sound and music.
 * @author Danielle Tucker
 * @version 2012 December
 */
public class TetrisAudio extends SoundPlayer implements Observer
{
  /**
   * Song file name for music.
   */
  private static final String SONG_FILE = "tetris/audio/Nicotine60.wav";

  /**
   * Song file name for rotate event.
   */
  private static final String ROTATE_SOUND = "tetris/audio/pop.wav";

  /**
   * Song file name for new piece event.
   */
  private static final String NEW_PIECE_SOUND = "tetris/audio/laser.wav";

  /**
   * Song file name for game over event.
   */
  private static final String GAME_OVER = "tetris/audio/GameOver.wav";
  
  /**
   * Flag if sound is not available due to exceptions.
   */
  private boolean my_sound_avaliable;
  /**
   * Flag for if music is not available due to exceptions.
   */
  private boolean my_music_avaliable;
  
  /**
   * Flag for turning on and off the music.
   */
  private boolean my_music_on = true;
  
  /**
   * Flag for turning on or off the sound effect.
   */
  private boolean my_sound_on = true;

  /**
   * Constructor for playing Tetris audio.
   * @param the_game_board the board which will be listened to for events
   */
  public TetrisAudio(final Board the_game_board)
  {
    super();
    my_sound_avaliable = true;
    my_music_avaliable = true;
    the_game_board.addObserver(this);
  }
  
  /**
   * Play the "rotate piece" sound.
   */
  public void playRotate()
  {
    if (my_sound_avaliable && my_sound_on)
    { 
      try
      {
        play(ROTATE_SOUND);
      }
      catch (final IllegalArgumentException|NullPointerException e)
      {
        errorMessage();
        my_sound_avaliable = false;
      }
    }
  }
  
  /**
   * Play the "new piece" sound.
   */
  public void playNewPiece()
  {
    if (my_sound_avaliable && my_sound_on)
    { 
      try
      {
        play(NEW_PIECE_SOUND);
      }
      catch (final IllegalArgumentException|NullPointerException e)
      {
        errorMessage();
        my_sound_avaliable = false;
      }
    }
  }

  /**
   * Play the game over sound.
   */
  public void playGameOver()
  {
    try
    {
      if (my_music_avaliable && my_sound_on)
      {
        play(GAME_OVER);
      }
    }
    catch (final IllegalArgumentException|NullPointerException e)
    {
      errorMessage();
      my_sound_avaliable = false;
    }
  }

  /**
   * Start the music.
   */
  public void startMusic()
  {
    try
    {
      if (my_music_avaliable && my_music_on)
      {
        loop(SONG_FILE);
      }
    }
    catch (final IllegalArgumentException|NullPointerException e)
    {
      errorMessage();
      my_music_avaliable = false;
    }
  }

  /**
   * Pause any music which is playing.
   */
  public void pauseMusic()
  {
    try
    {
      if (my_music_avaliable && my_music_on)
      {
        pause(SONG_FILE);
      }
    }
    catch (final IllegalArgumentException|NullPointerException e)
    {
      errorMessage();
    }
  }

  /**
   * Stop any music which is playing.
   */
  public void stopMusic()
  {
    try
    {
      if (my_music_avaliable && my_music_on)
      {
        stop(SONG_FILE);
      }
    }
    catch (final IllegalArgumentException|NullPointerException e)
    {
      errorMessage();
    }
  }

  /**
   * Toggles the music on/off.
   */
  public void toggleMusic()
  {
    if (my_music_on)
    {
      stopMusic();
      my_music_on = false;
    }
    else
    {
      my_music_on = true;
      startMusic();
    }
  }
  
  /**
   * Toggles the sound on/off.
   */
  public void toggleSound()
  {
    my_sound_on ^= true;
  }

  /**
   * Pops up a option pane with the credits for the music.
   */
  public void showCredits()
  {
    final StringBuilder sb = new StringBuilder("Audio Credits Go To:\n\n");
    sb.append("Music:\n  Nicotine 60 \n  freeplaymusic.com\n\n");
    sb.append("Game Over:\n  Timbre \n  ");
    sb.append("(Creative Commons CC BY-NC 3.0) www.freesound.org\n\n");
    sb.append("New Piece:\n  wws.grsites.com/acrhive/sounds/ (freeware)\n");
    sb.append("Rotate:\n   Herbert Boland\n");
    sb.append("(Creative Commons CC BY-NC 3.0)\n");    
    JOptionPane.showMessageDialog(null, sb.toString(),
                                  "Audio Credits", JOptionPane.INFORMATION_MESSAGE);
  }
  
  /**
   * Notify the audio when events have occurred on the board.
   * 
   * @param the_observed the object which is being observed.
   * @param the_arg the argument which is passed when notifyObservers() is called.
   */
  public void update(final Observable the_observed, final Object the_arg)
  {
    if (the_arg instanceof BoardEvent)
    {
      final BoardEventType event = ((BoardEvent) the_arg).getEventType();
      switch(event)
      {
        case NEW_NEXT_PIECE:
          playNewPiece();
          break;
        case GAME_OVER:
          stopAll();
          playGameOver();
          break;
        default:
          break;
      }
    }
  }

  /**
   * Show an error message if the media cannot be played.
   */
  private void errorMessage()
  {
    JOptionPane.showMessageDialog(null,
                "Error: Unable to play sound/music.",
                "Audio Error",
                JOptionPane.ERROR_MESSAGE);
  }
}
