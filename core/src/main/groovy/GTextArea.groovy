import groovy.swing.SwingBuilder 
import java.awt.*
import javax.swing.*
import javax.swing.JTextArea.AccessibleJTextArea;
import java.awt.Toolkit
import java.awt.Toolkit.*
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import TextUtilities;

public class GTextArea extends JTextArea 
{
	UndoManager undoManager;
	InputMap im;
	ActionMap am;
	String story = "";
	Font font;
	
    public GTextArea()
    {
		super(20,60);
		// In the constructor
		undoManager = new UndoManager();

		// undo edit logic follows, including two actions to undo / redo possible edit changes to 
		// the document in JTextArea
		this.getDocument().addUndoableEditListener(undoManager);
		im = this.getInputMap(JComponent.WHEN_FOCUSED);
		am = this.getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");
		    
		am.put("Undo", new AbstractAction() 
		{
    		@Override
    		public void actionPerformed(ActionEvent e) 
    		{
        		try 
	        	{
    	        	if (undoManager.canUndo()) 
        	    	{
            	    	undoManager.undo();
            		}
        		} 
        		catch (CannotUndoException exp) 
        		{
            		exp.printStackTrace();
        		}	
    		} // end of action
		}); // end of parms
	
		am.put("Redo", new AbstractAction() 
		{
    		@Override
    		public void actionPerformed(ActionEvent e) 
    		{
        		try 
        		{
            		if (undoManager.canRedo()) 
            		{
                		undoManager.redo();
            		}
        		} 
        		catch (CannotUndoException exp) 
        		{
            		exp.printStackTrace();
        		}
    		} // end of action
		});


		BlockCaret blockCaret = new BlockCaret();
		blockCaret.setBlinkRate(200);   //this.getCaret().getBlinkRate());
		this.setCaret(blockCaret);

		this.setForeground(Color.GREEN)
		this.setBackground(Color.BLACK)
		font = this.getFont();
		println "---> the this font looks like this:"+font.toString();
		//float size = font.getSize() + 0.5f;
		//this.setFont( font.deriveFont(size) );

		this.setEditable(true)
		this.setLineWrap(false)
		this.setWrapStyleWord(true)
		this.setBorder(BorderFactory.createEtchedBorder())
		//this.setPreferredSize(new Dimension(300, 120))
		//this.setText(story)
		this.setCaretPosition(0)
		this.setCaretColor(Color.RED)
		this.setSelectedTextColor(Color.BLUE)
		this.setSelectionColor(Color.YELLOW) 
		font = changeFont(12);
		//this.setFont(font);
		println "---> and now the this font looks like this:"+font.toString();

/* -------------------------------------
		//The KeyListener interface is implemented as an anonymous
		//inner class using the addKeyListener method.
		this.addKeyListener(new KeyListener()
		{
              //When any key is pressed and released then the 
              //keyPressed and keyReleased methods are called respectively.
              //The keyTyped method is called when a valid character is typed.
              //The getKeyChar returns the character for the key used. If the key
              //is a modifier key (e.g., SHIFT, CTRL) or action key (e.g., DELETE, ENTER)
              //then the character will be a undefined symbol.
              @Override 
              public void keyPressed(KeyEvent e)
              {
	  		      //rowcol.setText(getTextAreaCursorPosition());    
                  //feedbackText.append("Key Pressed: " + e.getKeyChar() + "\n");
              }

			@Override
            public void keyReleased(KeyEvent e)
          	{
	        	getTextAreaCursorPosition();    
	        	//println "keyReleased rowcol="+rowcol;
                //feedbackText.append("Key Released: " + e.getKeyChar() + "\n");
            }
              
            @Override
            public void keyTyped(KeyEvent e)
            {
            	//The getKeyModifiers method is a handy way to get a String representing the modifier key.
		  		//rowcol.setText(getTextAreaCursorPosition());    
                //feedbackText.append("Key Typed: " + e.getKeyChar() + " " + KeyEvent.getKeyModifiersText(e.getModifiers()) + "\n");
            }
		});
------------------------------------
*/


		this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		this.requestFocus();
		this.requestFocusInWindow();
    } // end of constructor


//===================================================================    

	public setStory(String content)
	{
		story = content;
		this.setText(story)
	} // end of method
		
	def changeFont(int sz)
	{
		try
		{
			font = new Font("MONOSPACED", Font.PLAIN, sz);
			setFont(font);
			return font;
		} 
		catch(Exception x)
		{
			font = new Font("MONOSPACED", Font.PLAIN, 12);
			return font
		} 
	} // end of get...

/*
        //Attach the MouseListener to mouseLabel 
        //as an anonymous inner class.
        //Each method changes the test of mouseLabel and
        //logs the mouse event in the JTextArea
        .addMouseListener(new MouseListener()
        {
            
            @Override
            public void mouseClicked(MouseEvent e)
            {
                append("MouseClicked Event");
                append(e.getClickCount() + " click(s)\n");
                append("Xpos: " + e.getX() + " Ypos: " + e.getY() + "\n");
                
            }
            @Override
            public void mousePressed(MouseEvent e)
            {
                append("MousePressed Event\n");
            }
            
            @Override
            public void mouseExited(MouseEvent e)
            {
                append("MouseExited Event\n");
            }
            
            @Override
            public void mouseEntered(MouseEvent e)
            {
                append("MouseEntered Event\n");
            }
            
            @Override
            public void mouseReleased(MouseEvent e)
            {
                append("MouseReleased Event\n");
            }
            
        });
*/

} // end of class