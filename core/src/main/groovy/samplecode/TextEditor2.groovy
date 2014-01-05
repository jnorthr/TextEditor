// refactor as a class then add main method witha args, first one being name external command line name of file to open
import groovy.swing.SwingBuilder 
import javax.swing.WindowConstants as WC 
import java.awt.FlowLayout
import java.awt.*
import java.awt.BorderLayout
import javax.swing.*
import javax.swing.border.*
import java.io.*
import java.awt.event.KeyEvent
import java.awt.event.*;
import javax.swing.event.CaretEvent
import java.awt.GridBagConstraints.*
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.awt.Toolkit
import java.awt.Toolkit.*
import javax.swing.JTextArea.AccessibleJTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotUndoException;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;



textArea = new JTextArea(20,60);
log="";	
home = System.getProperty("user.home")
logName = home+"/.TextEditor.log"


// In the constructor
UndoManager undoManager = new UndoManager();

// undo edit logic follows, including two actions to undo / redo possible edit changes to 
// the document in JTextArea
textArea.getDocument().addUndoableEditListener(undoManager);

InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
ActionMap am = textArea.getActionMap();

im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

am.put("Undo", new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        } catch (CannotUndoException exp) {
            exp.printStackTrace();
        }
    }
});
am.put("Redo", new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        } catch (CannotUndoException exp) {
            exp.printStackTrace();
        }
    }
});



boolean saveflag = false;
logCount=-1;
lastedited="";
def logEntries=[]
gui=null;

// Apple Mac bits follow ---
System.setProperty("apple.laf.useScreenMenuBar", "true")
System.setProperty("com.apple.mrj.application.apple.menu.about.name", "TextEditor")
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
def createShortcutWithModifier = { key, modifier -> KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | modifier) }
def createShortcut = { key -> createShortcutWithModifier(key, 0) }
def isMac = { System.getProperty("mrj.version") != null }


GridBagConstraints c = new GridBagConstraints();
// the external padding of the component 
//c.insets = new Insets(0, 1, 0, 1);

// Weights are used to determine how to distribute space among columns (weightx) and among rows (weighty);
// this is important for specifying resizing behavior. Larger numbers indicate that the component's 
// row or column should get more space. Range is 0.0 - 1.0
c.weighty = 1d;
c.weightx = 1d;
//c.gridx = 0; // column number where zero is first column
//c.gridy = 0; // row number where zero is first row

// direction of drift 4 this component when smaller than window 
//c.anchor = GridBagConstraints.WEST; 

// rule to let a component expand both ways when more space is available on resize
// fill. Used when the component's display area is larger than the component's requested size 
c.fill = GridBagConstraints.BOTH; 
//c.gridwidth = GridBagConstraints.RELATIVE

// declare text area panel for bottom of split screen
JPanel panelx = new JPanel(new GridBagLayout());

String fileName = "";
jtf = new JTextField(30)
jtf.text = fileName
swing = new SwingBuilder()
chooser = new JFileChooser()
chooser.setFileSelectionMode(JFileChooser.FILES_ONLY)
//------------
// jcombobox tests
            String[] ls = [ "strawberry", "chocolate", "vanilla","red","green","blue" ];
            JComboBox flavour = new JComboBox( ls );
            // ensure all three choices will be displayed without scrolling.
            flavour.setMaximumRowCount( 3 );
            flavour.setForeground( Color.RED );
            flavour.setBackground( Color.WHITE );
            flavour.setFont( new Font( "Dialog", Font.BOLD, 15 ) );
            // turn off the write-in feature
            flavour.setEditable( false );
            // setting the selection
            flavour.setSelectedIndex( 0 );
            // alternatively, by value.

            // Compares against defined items with. .equals, not ==.
            // For custom objects will want a custom equals method.
            // Selected items work best with enums.
            flavour.setSelectedItem( "chocolate" );

            // add components
            //flavour.addItemListener( theListener );

//------------
// action logic follows:
donew = swing.action(
  name:'New     ',
  closure:this.&doNew,
  mnemonic:'n',
  accelerator: createShortcut(KeyEvent.VK_N)
)

// Uses Cmd-O/Ctrl+O
openAction = swing.action(
    name:'Open        ', closure: this.&openText, mnemonic: 'O', accelerator: createShortcut(KeyEvent.VK_O)
)

reo = swing.action(
  name:'Open Recent    ',
  closure:this.&reopenText,
  mnemonic:'r',
  accelerator: createShortcut(KeyEvent.VK_R)
)

save = swing.action(
  name:'Save    ',
  //closure:{},
  enabled:saveflag,
  closure:this.&saveText,
  mnemonic:'s',
  accelerator: createShortcut(KeyEvent.VK_S)
)

saveAs = swing.action(
  name:'SaveAs   ',
  enabled:true,
  closure:this.&saveAsText,
  mnemonic:'d',
  accelerator: createShortcut(KeyEvent.VK_D)
)

closeup = swing.action(
  name:'Close',
  closure:this.&doClose,
  mnemonic:'w',
  accelerator: createShortcut(KeyEvent.VK_W)
)

exit = swing.action(
  name:'Exit    ',
  closure:this.&exitText,
  mnemonic:'e',
  accelerator: createShortcut(KeyEvent.VK_E)
)

// text and background color settings
dopickbg = swing.action(
  name:'BG Color    ',
  closure:this.&doPick,
  mnemonic:'b',
  accelerator: createShortcut(KeyEvent.VK_B)
)
dopickfg = swing.action(
  name:'Text Color    ',
  closure:this.&doPickText,
  mnemonic:'t',
  accelerator: createShortcut(KeyEvent.VK_T)

)
//------------------------------------------

String story = "";
rowcol = new JLabel('1/1');

//textArea = new JTextArea(20,60)

//JTextAreaCaret blockCaret = new JTextAreaCaret()
BlockCaret blockCaret = new BlockCaret();
//CaretShape blockCaret = new CaretShape()
blockCaret.setBlinkRate(textArea.getCaret().getBlinkRate());
textArea.setCaret(blockCaret);

textArea.setForeground(Color.GREEN)
textArea.setBackground(Color.BLACK)
font = textArea.getFont();
//float size = font.getSize() + 0.5f;
//textArea.setFont( font.deriveFont(size) );


textArea.setEditable(true)
textArea.setLineWrap(false)
textArea.setWrapStyleWord(true)
textArea.setBorder(BorderFactory.createEtchedBorder())
//textArea.setPreferredSize(new Dimension(300, 100))
textArea.setText(story)
textArea.setCaretPosition(0)
textArea.setCaretColor(Color.RED)
textArea.setSelectedTextColor(Color.BLUE)
textArea.setSelectionColor(Color.YELLOW) 
Font font = new Font("MONOSPACED", Font.PLAIN, 11);
textArea.setFont(font);

//The KeyListener interface is implemented as an anonymous
//inner class using the addKeyListener method.
textArea.addKeyListener(new KeyListener()
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
	  		      //rowcol.setText(getTextAreaCursorPosition());    
                  //feedbackText.append("Key Released: " + e.getKeyChar() + "\n");
              }
              
              @Override
              public void keyTyped(KeyEvent e)
              {
                  //The getKeyModifiers method is a handy
                  //way to get a String representing the
                  //modifier key.
		  //rowcol.setText(getTextAreaCursorPosition());    
                  //feedbackText.append("Key Typed: " + e.getKeyChar() + " " + KeyEvent.getKeyModifiersText(e.getModifiers()) + "\n");
              }
});

def getTextAreaCursorPosition() 
{
 int c = TextUtilities.getColumnAtCaret(textArea); 
 int r = TextUtilities.getLineAtCaret(textArea); 
 def cr = "row $r  col $c"
 return cr;
}

JScrollPane scrollPane = new JScrollPane(textArea)

scrollPane.setMinimumSize(new java.awt.Dimension(200,500))
scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED)
scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
TextLineNumber tln = new TextLineNumber(textArea);
scrollPane.setRowHeaderView( tln );
textArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
textArea.requestFocus();
textArea.requestFocusInWindow();

// holds filename
JPanel pane = new JPanel();
pane.add(new JLabel('File name : ')); 
pane.add(jtf)
pane.add(rowcol)
pane.add(flavour);

def u = new URL('http://www.google.com/favicon.ico');
ImageIcon cup = new ImageIcon(u);

Dimension maxSize = new Dimension(280, 35);
pane.setMinimumSize(maxSize);
panelx.setMinimumSize(maxSize);
//pane.setMaximumSize(maxSize);

// add constraints to lower pane
panelx.add(scrollPane, c);

//Create a split pane with the two scroll panes in it.
splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pane, panelx);
splitPane.setResizeWeight(0.33); // <-- here
splitPane.setOneTouchExpandable(true);
splitPane.setDividerLocation(40);

//Provide minimum sizes for the two components in the split pane
Dimension minimumSize = new Dimension(600, 360);
//pane.setMinimumSize(minimumSize);
panelx.setMinimumSize(minimumSize);

//         frame.setPreferredSize(new Dimension(300, 300));


JFrame.setDefaultLookAndFeelDecorated(true)
gui = swing.frame(id:'FR',title:fileName, layout:new BorderLayout(),minimumSize:[500,300], preferredSize:[600,300], pack:false, show:false, location:[200,200], defaultCloseOperation:WC.EXIT_ON_CLOSE)
{
        menuBar {
                menu(text:'File',  mnemonic:'F')   //,accelerator: createShortcut(KeyEvent.VK_F))   //, accelerator:'ctrl F' ) 
                {
                         menuItem() 
                         {
                             action(donew)
                         }
                         menuItem() 
                         {
			     action(openAction)
                         }
                         menuItem() 
                         {
                             action(reo)
                         }
                         separator()
                         menuItem() 
                         {
                             action(closeup)
                         }
                         menuItem(id:'mi1') 
                         {
                             action(save,enabled:saveflag)
                         }
                         menuItem(id:'mi2') 
                         {
                             action(saveAs)
                         }
                         separator()
                         menuItem() 
                         {
                             action(exit)
                         }
                     
                     } // end of menu

                menu(text:'Settings') 
                {
                         menuItem() 
                         {
                             action(dopickfg)
                         }
                         menuItem() 
                         {
                             action(dopickbg)
                         }
                } // end of menu

        } // end of menubar



	container(splitPane);

} // end of gui

gui.pack()
gui.show()

getList();
println "\n\n--> Last Edited was '${lastedited}' logCount="+logCount
def fi = new File(lastedited);
if (fi.exists())
{
	getText(fi)
} // end of if

// ====================================

def doPick(event)
{
        	ColorPicker cp = new ColorPicker();
        	String nc = cp.getColor();
        	println "===> new color:"+nc.toString();

	        def rgb = null;
        	if (nc!=null)
        	{
            	    rgb = cp.hex2Rgb(nc); 
	            println "===> new color:"+nc.toString()+" as "+rgb;
        	    if (rgb != null) 
            	    {
                	textArea.setBackground(rgb);
            	    } // end of if
        	} // end of if

} // end of pick

// get foreground text color
def doPickText(event)
{
        	ColorPicker cp = new ColorPicker();
		cp.setTitle("Choose Text Color");
        	String nc = cp.getColor();
        	println "===> new color:"+nc.toString();

	        def rgb = null;
        	if (nc!=null)
        	{
            	    rgb = cp.hex2Rgb(nc); 
	            println "===> new color:"+nc.toString()+" as "+rgb;
        	    if (rgb != null) 
            	    {
                	textArea.setForeground(rgb);
            	    } // end of if
        	} // end of if


} // end of pick


def doNew(event)
{
	saveflag=false;
	swing.mi1.enabled=saveflag;

	def getProperty = System.&getProperty  
	def home = getProperty("user.home")
	fileName = ""
	jtf.text = ""
	gui.title = ""
	textArea.setText("")
	textArea.setCaretPosition(0)
	textArea.requestFocus();
	textArea.requestFocusInWindow();
}


def doClose(event)
{
	saveflag=false;
	swing.mi1.enabled=saveflag;

	def getProperty = System.&getProperty  
	def home = getProperty("user.home")
	fileName = ""
	jtf.text = ""
	gui.title = ""
	textArea.setText("")
	textArea.setCaretPosition(0)
	textArea.requestFocus();
	textArea.requestFocusInWindow();
}


def reopenText(event) {
	String[] choices = getList();
	choices.each{ e-> println "-->$e";} 

	String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Choose a file to re=open or ESC",
                    "Re-Open Dialog",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]);
  
	if (s==null) return;

	def fh2 = new File(s);
	if ( !( fh2.exists() ) )
	{
		JOptionPane.showMessageDialog(null, "Cannot find ${s} to re-open", "Missing File", JOptionPane.ERROR_MESSAGE);
		return;
	} // end of if


		getText(fh2);
		gui.repaint();
	return;


	// chooser.setFileFilter(filter1);
	int returnValue = chooser.showOpenDialog(gui);
	if ((returnValue == javax.swing.JFileChooser.APPROVE_OPTION)) 
	{  
		def fh = chooser.getSelectedFile();
		getText(fh);
		gui.repaint();
	} // end of if
} // end of reopenText

def int setCursor(fn)
{
	int at = 0;
	def fnlc = fn.toLowerCase();
	log = new File(logName).text;
	log.eachLine{ln ->
		int i = ln.indexOf(":=");
		if (i>-1)
		{
			def na = ln.substring(0,i);
			def nalc = na.toLowerCase();
			def va = ln.substring(i+2).toLowerCase();
			
			int j = va.indexOf(';');
			if (j>-1)
			{
				def v = va.substring(0,j);
				if (fnlc.equals(nalc)) { println "... they match "+v; 
				try{at = v as int;} catch(Exception f) {}  
				} // end of if
			} //end if
			
		} // end of if
		
	} // end of log
	
	return ( at < 1 ) ? 0 : at;
} // end of


def getText(def fh)
{
		saveflag=true;
		swing.mi1.enabled=saveflag;

		fileName = fh.toString()
		int at = setCursor(fileName);
		jtf.text = fileName
		gui.title = getMetadata(fh)
		story = fh.text;            //getText('UTF-8')
		textArea.setText(story)
		textArea.setCaretPosition(at)
		rowcol.setText(getTextAreaCursorPosition());    
		textArea.requestFocus();
		textArea.requestFocusInWindow();
} // end of getText

def getMetadata(def fh)
{
	String sz = fh.length();
	String r = (fh.canRead()) ? 'r' : '';
	String w = (fh.canWrite()) ? 'w' : '';
	long timestamp = fh.lastModified();
	Date when = new Date(timestamp);
	SimpleDateFormat sdf = new SimpleDateFormat( "EEEE yyyy/MM/dd hh:mm:ss aa zz" );
	sdf.setTimeZone(TimeZone.getDefault()); // local time
	String display = sdf.format(when)+"  ${sz} bytes ($r$w)";
	return display;
}

def openText(event) 
{
	//         chooser.setFileFilter(filter1);
	int returnValue = chooser.showOpenDialog(gui);
	if ((returnValue == javax.swing.JFileChooser.APPROVE_OPTION)) 
	{  
   		def fh = chooser.getSelectedFile();
		//File f = fh.getSelectedFile();
		def path = fh.getAbsolutePath();
		println "openText chose file "+fh.toString()+" in folder "+path;

		getText(fh);
		gui.repaint();
	} // end of if
} // end of openText


def saveText(event) 
{ 
	fileName = jtf.text
	def f = new File(fileName);
	def path = f.getAbsolutePath();
	println "saveText chose to keep file "+fileName+" in folder "+path;

	dosave( textArea.getText(), fileName ); 
}

def saveAsText(event) 
{ 
	fileName = jtf.text
        def fc = new JFileChooser()
	fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES)
	fc.setSelectedFile(new File(fileName));

	int returnVal = fc.showSaveDialog(null);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
		fileName = fc.getSelectedFile().toString();
		File f = fc.getSelectedFile();
		def path = f.getAbsolutePath();
		println "saveAsText saved file "+fileName+" into folder "+path;
		jtf.text = fileName
		saveflag=true;
		swing.mi1.enabled=saveflag;
	} // end of if
	dosave( textArea.getText(), fileName ); 
}

def dosave(String story, String outName)
{
	def getProperty = System.&getProperty  
	def home = getProperty("user.home")
	def thisName = home+"/Desktop/sample.txt"
	int n = 0;
	if (new File(outName).exists() )  
	{
		//default icon, custom title
		n = JOptionPane.showConfirmDialog(null,
	    	"Would you like to replace this file ?\n${outName}",
    		"An Existing File Will Be Over-written",
    		JOptionPane.YES_NO_OPTION);
		println "JOptionPane gave $n as the answer"
	} // end of if

	if (n==0)
	{
		int j = textArea.getCaretPosition()
		thisName=outName;
		def out = new File(thisName)
		println "===> Writing ${thisName} with caret at $j"

		out.delete()
		saveflag=false;
		out.write(story)
		keeper(thisName, j)
	} // end of if
} // end of dosave


def keeper(String outName, int at)
{
	def getProperty = System.&getProperty  
	def home = getProperty("user.home")
	def log = new File(logName)

	today = new Date() 
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ssz");
	String formattedDate = formatter.format(today);
	log.append("${outName}:=${at}; ${formattedDate}"+"\n")

	getList();
	println "--> Last Edited (keeper) was "+ lastedited
} // end of keeper


// pull up all prior edited files
def String[] getList()
{
	def getProperty = System.&getProperty  
	def home = getProperty("user.home")
	def logName = home+"/.TextEditor.log"

	// global vars
	lastedited="";
	logCount = 0;
	logEntries=[]

	if ( !( new File( logName).exists() ) ) return logEntries;

	log = new File(logName).text;
	log.eachLine{ln ->
		println "--->"+ln; 
		int i = ln.indexOf(":=");
		if (i>-1)
		{
			lastedited=ln.substring(0,i);
			logEntries.push(lastedited);
       			++logCount; 
		}
	} // end of log

	int ct = (logCount > 20) ? 20  : 0 ;
	int rm = logCount - ct;
	ct=0;
	if (rm > 0) {logEntries.drop(rm);}
	logEntries.reverse(true);
	logEntries.each{e->
		println "logEntries(${++ct}).each=${e}"; 
	} // end of each
	println "---------\n"
	return logEntries;
} // end of keeper


def exitText(event) 
{
	System.exit(0);
}


        flavour.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
      		int selectedIndex = flavour.getSelectedIndex();
            // even though JComboBox is generic, you still need the (String) cast, a legacy quirk.
            String choice = ( String ) flavour.getSelectedItem();
            System.out.println( selectedIndex + " " + choice);   // + " " + e.toString() );
                System.out.println(e.getItem() + " " + e.getStateChange() );
            }
        });



