TextEditor
==========
       
A Basic Text Editor for systems supporting Java JVM 1.8+
 
/*
TODO:
 need to clear undo/redo buffer on change of file

 button icons on toolbar

 button to grow/shrink font size
 
 save/restore preferences to config files
 - most recent input folder
 - most recent input file
 - most recent output folder
 - most recent output file and last cursor position
 - font choices as face,type(plain,bold,etc) size, color and b/g color 
   now saved to resources/freetext.properties - done

 - start frame location at last used x/y co-ordinate or properties value - done


 - find/replace keys

 - dropdown does not alway show latest opened/closed

 - see if file timestamp has changed since loaded b4 changing
 - auto-save each x sec.s  - see timer logic in Tracker
 - top jtextfield size too big
 = prompt to save if txt changed

 choose mono-face font - any option to make it bold ? - see next - done

 grow font size - needs spinner logic - added JFontChooser logic -done

 use dropdown on filename choice

 refresh dropdown on open/reopen/save/saveas action 

 multi-tab bottom pane to edit several files

 disallow split pane resize

 row/col not set on restart until 1st keystroke
 row/col not set on mouse focus

 add save/restore of properties like text font color, last file used - done - ~/.FreeText.properties

 new should offer same folder as last opened / saved ?
 save as should offer last folder saved to
 close to clear jtf

 copy/paste to clipboard - uses system functionality - done

 copy icons as buttons

 home & end buttons got ot start /end of document: need shift+home/end to start/end of current line

 if you've already said yes to over-write a file, don't ask again

 rows and columns listener : ---
   int caretpos = textArea.getCaretPosition();
   int column = caretpos - textArea.getLineStartOffset(row);
textArea.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent ae)
        {
		println "textArea action listener"
                //tarea.append(tfield.getText() + "\n");
        }
});

You need to use Utilities.getRowStart along with the caret position as shown below:

To get the row number:

int caretPos = textArea.getCaretPosition();
int rowNum = (caretPos == 0) ? 1 : 0;
for (int offset = caretPos; offset > 0;) {
    offset = Utilities.getRowStart(textArea, offset) - 1;
    rowNum++;
}
System.out.println("Row: " + rowNum);    

To get the column number:

int offset = Utilities.getRowStart(textArea, caretPos);
int colNum = caretPos - offset + 1;
System.out.println("Col: " + colNum);


//---------------------
scroll list of re-open files

 insert/overwrite value functionality - see:http://java-sl.com/tip_overwrite_mode.html
 save as funcionality
 block shape caret : http://java-sl.com/tip_overwrite_mode.html

keyboard F3 intercept - copy from Menus.groovy but need to refactor this as a Class{}
frame header to show filename - done

auto-save every 30 sec.s
*/

/*
		fileName = fh.toString()
		jtf.text = fileName
		gui.title = fileName
		story = fh.text;            //getText('UTF-8')
		textArea.setText(story)
		textArea.setCaretPosition(0)
		saveflag=true;
		swing.mi1.enabled=saveflag;
		textArea.requestFocus();
		textArea.requestFocusInWindow();
*/

see: http://stackoverflow.com/questions/5139995/java-column-number-and-line-number-of-cursors-current-position
def taa = new javax.swing.JTextArea.AccessibleJTextArea();
        // Add a caretListener to the editor. This is an anonymous class because it is inline and has no specific name.
        textArea.addCaretListener(taa 
        {
            // Each time the caret is moved, it will trigger the listener and its method caretUpdate.
            // It will then pass the event to the update method including the source of the event (which is our textarea control)
            public void caretUpdate(CaretEvent e) 
            {
                JTextArea editArea = (JTextArea)e.getSource();

                // Lets start with some default values for the line and column.
                int linenum = 1;
                int columnnum = 1;

                // We create a try catch to catch any exceptions. We will simply ignore such an error for our demonstration.
                try 
		{
                    // First we find the position of the caret. This is the number of where the caret is in relation to the start of the JTextArea
                    // in the upper left corner. We use this position to find offset values (eg what line we are on for the given position as well as
                    // what position that line starts on.
                    int caretpos = editArea.getCaretPosition();
println "got caret at "+caretpos
                    linenum = editArea.getLineOfOffset(caretpos);
println "got caret line at "+linenum

                    // We subtract the offset of where our line starts from the overall caret position.
                    // So lets say that we are on line 5 and that line starts at caret position 100, if our caret position is currently 106
                    // we know that we must be on column 6 of line 5.
                    columnnum = caretpos - editArea.getLineStartOffset(linenum);

                    // We have to add one here because line numbers start at 0 for getLineOfOffset and we want it to start at 1 for display.
                    linenum += 1;
                }
                catch(Exception ex) { }

                // Once we know the position of the line and the column, pass it to a helper function for updating the status bar.
                println "caret update: row=$linenum, col=$columnnum";
            }
        });

//===========================
// see: http://alvinalexander.com/java/java-add-keystroke-swing-application-menu-item
// (1) init
JMenuBar menuBar = new JMenuBar();
JMenu windowMenu = new JMenu();
JMenuItem minimizeWindowMenuItem = new JMenuItem();
 
// (2) in the constructor
windowMenu.setText("Window");
minimizeWindowMenuItem.setText("Minimize Window");
minimizeWindowMenuItem.addActionListener(new ActionListener()
{
  public void actionPerformed(final ActionEvent e)
  {
     minimizeWindowMenuItem_actionPerformed(e);
   }
});
minimizeWindowMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,  Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
  
// (3) some time later
windowMenu.add(minimizeWindowMenuItem);
 
menuBar.add(windowMenu);
this.setJMenuBar(menuBar);

// (4)
public void minimizeWindowMenuItem_actionPerformed(final ActionEvent e) {
  mainFrame.setExtendedState(Frame.ICONIFIED);
}
=======

Java/Groovy simple ascii text editor

