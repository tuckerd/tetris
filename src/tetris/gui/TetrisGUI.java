/*
 * Danielle Tucker
 * TCSS 305 - November 2012 
 * Project Tetris
 */

package tetris.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import tetris.audio.TetrisAudio;
import tetris.board.Board;
import tetris.board.BoardEvent;
import tetris.board.BoardEventType;

/**
 * Creates and displays the main GUI of the game Tetris.
 * @author Danielle Tucker
 * @version 2012 November
 */
@SuppressWarnings("serial")
public class TetrisGUI extends JFrame implements Observer
{
  /**
   * The initial delay (in milliseconds) for the drop timer.
   */
  private static final int INITIAL_MOVE_DELAY = 1000;
  
  /**
   * The text of the pause menu.
   */
  private static final String PAUSE_TEXT = "Pause Game";
  
  /**
   * The interval in which the level of difficulty is increased.
   */
  private static final int NEW_LEVEL_BOUND = 1000;
  
  /**
   * The decrease in delay (in milliseconds) in the drop timer
   * for ever new level.
   */
  private static final int NEW_LEVEL_DELAY_DEC = 100;
  
  /**
   * The minimum size of the GUI.
   */
  private static final Dimension DIMENSION = new Dimension(425, 550);
  
  /**
   * The label for the current score.
   */
  private final JLabel my_score = new JLabel(" Score: 0");
  
  /**
   * The label for the current level.
   */
  private final JLabel my_level_label = new JLabel(" Level: 0");

  /**
   * The status of the game (Paused, Over...).
   */
  private final JLabel my_game_status = new JLabel();
  
  /**
   * The game board to play the game.
   */
  private final Board my_game_board;
  
  /**
   * The panel with the current board drawing.
   */
  private final DrawPiecesPanel my_draw_pieces_panel;

  /**
   * The audio for the game.
   */
  private TetrisAudio my_audio;

  /**
   * The timer that controls the drop of the pieces.
   */
  private Timer my_drop_timer;

  /**
   * Whether or not the game is in progress.
   */
  private boolean my_game_in_progress = true;;
  
  /**
   * Used to determine if a piece has been frozen on the board (helper
   * for dropping a piece immediately).
   */
  private boolean my_piece_not_frozen = true;
  
  /**
   * The level of play which also determines the speed of the drops
   * and is used as a score multiplier.
   */
  private int my_level;

  /**
   * The menu item for the pause.
   */
  private JMenuItem my_pause_menu;
  
  /**
   * Game play mode flag.
   */
  private boolean my_evil_mode;

  /**
   * The panel with the current blocks shown.
   */
  private NextPiecePanel my_next_block_frame;
  
  /**
   * Create and set up the panel elements in the GUI.
   */
  public TetrisGUI()
  {
    super("Tetris");
    addKeyListener(new KeyPlayAdapter());
    setFocusable(true);

    setJMenuBar(createMenu());
    
    my_game_board = new Board();
    my_game_board.addObserver(this);

    my_draw_pieces_panel = new DrawPiecesPanel(my_game_board);
    add(my_draw_pieces_panel, BorderLayout.CENTER);
    
    makeStatusPanel();
    
    setUpTimerAndAudio();
    
    setMinimumSize(DIMENSION);
  }

  /**
   * Construct and display the GUI.
   * @param the_args command line arguments (ignored).
   */
  public static void main(final String... the_args)
  {
    try
    {
      // set cross-platform Java look and feel
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (final UnsupportedLookAndFeelException e)
    {
      assert false;
    }
    catch (final ClassNotFoundException e)
    {
      assert false;
    }
    catch (final InstantiationException e)
    {
      assert false;
    }
    catch (final IllegalAccessException e)
    {
      assert false;
    }

    final TetrisGUI panel = new TetrisGUI();
    panel.setDefaultCloseOperation(EXIT_ON_CLOSE);
    panel.getContentPane().setBackground(Color.BLACK);


    panel.pack();
    panel.setVisible(true);
    panel.start();
  }
  
  /**
   * Starts the timer and audio.
   */
  public void start()
  {
    my_drop_timer.start();
    my_audio.startMusic();
  }

  /**
   * Notify the GUI when the game is over, the score has changed or a piece
   * has been added to the board. 
   * 
   * @param the_observed the object which is being observed.
   * @param the_arg the argument which is passed when notifyObservers() is called.
   */
  public void update(final Observable the_observed, final Object the_arg)
  {
    if (the_observed instanceof Board && the_arg instanceof BoardEvent)
    {
      final BoardEventType event = ((BoardEvent) the_arg).getEventType();
      if (event.equals(BoardEventType.GAME_OVER))
      {
        my_game_in_progress = false;
        my_game_status.setText("Game Over");
      }
      else if (event == BoardEventType.NEW_SCORE)
      {
        final int score = (int) ((BoardEvent) the_arg).getObject();
        my_score.setText(" Score: " + score);
        final int level_change = score / NEW_LEVEL_BOUND;
        if (level_change != my_level)
        {
          my_drop_timer.setDelay(my_drop_timer.getDelay() - 
                                 NEW_LEVEL_DELAY_DEC * level_change);
          my_level = level_change;
          my_level_label.setText(" Level: " + my_level);
        }
      }
      else if (event == BoardEventType.CHANGE_ROWS)
      {
        my_piece_not_frozen = false;   //used as in indicator that the "drop" has finished    
      }
      else if (event == BoardEventType.NEW_NEXT_PIECE && my_evil_mode)
      {
        my_game_board.randomRemoval();
      }
    }
  }

  /**
   * Sets up the timers and audio for game play.
   */
  private void setUpTimerAndAudio()
  {
    my_audio = new TetrisAudio(my_game_board);
  
    my_drop_timer = new Timer(INITIAL_MOVE_DELAY, new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_game_board.step();
      }
    });
  }

  /**
   *Create a menu for the GUI display.
   *@return the menu of the GUI display. 
   */
  private JMenuBar createMenu()
  {
    final JMenuBar menu_bar = new JMenuBar();
    
    // Create Menus   
    menu_bar.add(createOptionMenu());

    final JMenu mode_menu = new JMenu("Mode");
    mode_menu.setMnemonic(KeyEvent.VK_M);
    
    final JCheckBoxMenuItem evil_mode = new JCheckBoxMenuItem("Evil Mode");
    evil_mode.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_evil_mode ^= true;
      }
    });
    mode_menu.add(evil_mode);
    
    final JCheckBoxMenuItem invisible_mode = new JCheckBoxMenuItem("Invisible Mode");
    invisible_mode.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_draw_pieces_panel.toggleInvisiblePiece();
        my_next_block_frame.toggleShowCurrent();
      }
    });
    mode_menu.add(invisible_mode);
    menu_bar.add(mode_menu);

    menu_bar.add(mode_menu);
    
    menu_bar.add(createHelpMenu());
    
    return menu_bar;
  }

  /**
   * Creates the Option Menu Items.
   * @return a menu of options
   */
  private JMenu createOptionMenu()
  {
    final JMenu option_menu = new JMenu("Options");
    option_menu.setMnemonic(KeyEvent.VK_O);

    final JMenuItem new_game = new JMenuItem("New Game");
    my_pause_menu = new JMenuItem(PAUSE_TEXT);
    final JMenuItem stop_menu = new JMenuItem("Stop Game");
    final JMenuItem quit_menu = new JMenuItem("Quit", KeyEvent.VK_Q);

    // New Game Option
    new_game.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_game_board.resetBoard();
        my_game_status.setText("");
        my_pause_menu.setEnabled(true);
        my_pause_menu.setText(PAUSE_TEXT);
        if (!my_game_in_progress)
        {
          my_audio.startMusic();
        }
        my_game_in_progress = true;
        my_drop_timer.restart();
      }
    });
    option_menu.add(new_game);

    // Pause Option
    my_pause_menu.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        pauseGame();
      }
    });
    option_menu.add(my_pause_menu);
  
    // Stop Option
    stop_menu.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_drop_timer.stop();
        my_game_status.setText("Forfeit Game");
        my_game_in_progress = false;
        my_pause_menu.setEnabled(false);
        my_audio.stopMusic();
      }
    });
    option_menu.add(stop_menu);

    option_menu.addSeparator();
    
    final JCheckBoxMenuItem music_option = new JCheckBoxMenuItem("Play Music");
    music_option.setSelected(true);
    music_option.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_audio.toggleMusic();
      }
    });
    option_menu.add(music_option);
    
    
    final JCheckBoxMenuItem sound_option = new JCheckBoxMenuItem("Play Sound Effects");
    sound_option.setSelected(true);
    sound_option.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_audio.toggleSound();
      }
    });
    option_menu.add(sound_option);
    
    option_menu.addSeparator();
    
    // Quit Option
    quit_menu.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_event)
      {
        my_drop_timer.stop();
        my_audio.stopMusic();
        dispose();
      }
    });
    option_menu.add(quit_menu);
    
    return option_menu;
  }
 
  /**
   * Creates the Option Menu Items.
   * @return a menu of options
   */
  private JMenu createHelpMenu()
  {
    final JMenu help_menu = new JMenu("Help");
    help_menu.setMnemonic(KeyEvent.VK_H);

    final JMenuItem instructions = new JMenuItem("How to Play", KeyEvent.VK_T);
    instructions.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_action)
      {
        makeHelpPane();
      }
    });
    help_menu.add(instructions);
    
    final JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
    about.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_action)
      {
        final StringBuilder sb = new StringBuilder("Tetris Clone Created By:\n\n");
        sb.append("Danielle Tucker\n\n");
        sb.append("2012 Autumn\n\n");
        sb.append("TCSS 305\n\n");
        sb.append("Dr. Zimmerman (Professor)");
        JOptionPane.showMessageDialog(null, sb.toString(),
                                      "About Game", JOptionPane.INFORMATION_MESSAGE);
      }
    });
    help_menu.add(about);
    
    final JMenuItem credits = new JMenuItem("Audio Credits", KeyEvent.VK_C);
    credits.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent the_action)
      {
        my_audio.showCredits();
      }
    });
    help_menu.add(credits);
    
    return help_menu;
  }
  
  /**
   * Helper method to create How To Play Message Dialogue.
   */
  private void makeHelpPane()
  {
    final StringBuilder sb = new StringBuilder("How to Play Tetris:\n\n");
    sb.append("Goal:\n  Clear as many lines as possible by using the blocks\n");
    sb.append("  to make a complete row.\n\n");
    sb.append("Scoring:\n  For each piece placed you will earn 5 points\n");
    sb.append("  and each row cleared 100 points with a\n");
    sb.append("  multi-row bonus avaliable.\n\n");
    sb.append("Levels:\n  Every 1000 points will cause a level up and\n");
    sb.append("  increase the speed the blocks will drop.\n\n");
    sb.append("Game Over:\n  If you reach the top of the window, the game\n");
    sb.append("  will be over\n\n");
    sb.append("EVIL MODE:\n  Special game mode where after placing a piece\n");
    sb.append("  a random spot may be cleared on the board\n");
    sb.append("INVISIBLE MODE:\n  Special game mode where current piece is not\n");
    sb.append("  displayed on the board");
    JOptionPane.showMessageDialog(null, sb.toString(),
                                  "Instructions", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Create the Status Panel which displays the next piece, the status
   * of the game, and the score.
   */
  private void makeStatusPanel()
  {
    final JPanel status_frame = new JPanel();
    status_frame.setOpaque(false);
    status_frame.setLayout(new BorderLayout());
    
    //North - next piece panel
    my_next_block_frame = new NextPiecePanel(my_game_board);
    my_next_block_frame.setOpaque(false);
    status_frame.add(my_next_block_frame, BorderLayout.NORTH);
    
    //Center - game status, score, spacer
    final JPanel info = new JPanel();
    info.setOpaque(false);
    info.setLayout(new GridLayout(0, 1));

    final Font text_font = new Font("Serif", Font.BOLD, 20);
    my_game_status.setFont(text_font);
    my_game_status.setForeground(Color.RED);
    info.add(my_game_status);

    my_score.setFont(text_font);
    my_score.setForeground(Color.WHITE);
    info.add(my_score);
    my_level_label.setFont(text_font);
    my_level_label.setForeground(Color.WHITE);
    info.add(my_level_label);
    info.add(new JLabel());
    info.add(new JLabel());
    status_frame.add(info, BorderLayout.CENTER);
    
    //South - Info about Key Bindings for movement.
    status_frame.add(makeKeyBindingsPanel(), BorderLayout.SOUTH);
    
    final JPanel status_background_frame = new JPanel();
    status_background_frame.setOpaque(false);
    status_background_frame.add(status_frame);
    final JLabel spacer = new JLabel();
    status_background_frame.add(spacer);
    
    add(status_background_frame, BorderLayout.EAST);
  }

  /**
   * Creats a panel with information about gameplay controls.
   * @return panel which has the information about the key bindings
   * for game play
   */
  private JPanel makeKeyBindingsPanel()
  {
    final JPanel keys = new JPanel();
    keys.setLayout(new GridLayout(0, 2));
    keys.setOpaque(false);
    
    //Set up default key bindings
    final Map<KeyMotion, Integer> key_bindings = new TreeMap<KeyMotion, Integer>();
    
    for (KeyMotion key_motions: KeyMotion.values())
    {
      key_bindings.put(key_motions, key_motions.getDefaultKeyBinding());
    }
    
    //Make Labels
    for (final Map.Entry<KeyMotion, Integer> entry: key_bindings.entrySet())
    { 
      final JLabel description = new JLabel(entry.getKey().toString() + ":");
      description.setForeground(Color.WHITE);      
      keys.add(description);
      
      final JLabel key_bound = new JLabel("  " + KeyEvent.getKeyText(entry.getValue()));
      key_bound.setForeground(Color.WHITE);
      keys.add(key_bound);
    }
    
    keys.add(new JLabel("")); //spacer
    return keys;
  }

  /**
   * Pause the game in progress.
   */
  private void pauseGame()
  {
    if (my_game_in_progress)
    {
      my_drop_timer.stop();
      my_pause_menu.setText("Resume Game");
      my_game_status.setText("Game Paused");
      my_audio.pauseMusic();
    }
    else
    {
      my_drop_timer.start();
      my_game_status.setText("");
      my_pause_menu.setText(PAUSE_TEXT);
      my_audio.startMusic();
    }
    my_game_in_progress ^= true;
  }
  
  /**
   * Class to move the pieces on the board.
   * @author Danielle Tucker
   * @version 2012 November
   */
  private class KeyPlayAdapter extends KeyAdapter
  {
    /**
     * When a key is pressed changes the piece in play.
     * @param the_event the key event.
     */
    public void keyPressed(final KeyEvent the_event)
    {
      final int key_pressed = the_event.getKeyCode();
      if (!my_game_in_progress && key_pressed != KeyEvent.VK_P)
      {
        return;
      }
      switch (key_pressed)
      {
        case KeyEvent.VK_RIGHT:
          my_game_board.moveRight();
          break;
        case KeyEvent.VK_LEFT:
          my_game_board.moveLeft();
          break;
        case KeyEvent.VK_UP:
          my_game_board.rotate();
          my_audio.playRotate();
          break;
        case KeyEvent.VK_DOWN:
          my_game_board.step();
          break;
        case KeyEvent.VK_SPACE:
          while (my_piece_not_frozen)
          {
            my_game_board.step();
          }
          my_piece_not_frozen = true;
          break;
        case KeyEvent.VK_P:
          pauseGame();
          break;
        default:
          break;
      }
    }
  }
}

