// refactor as a class then add main method witha args, first one being name external command line name of file to open
import groovy.swing.SwingBuilder 
import javax.swing.WindowConstants as WC 
import java.awt.FlowLayout
import java.awt.*
import java.awt.BorderLayout
import javax.swing.*
import javax.swing.border.*
import java.io.*
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
import java.awt.image.BufferedImage
import javax.imageio.*
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import TextUtilities;
import GTextArea;
import javax.swing.text.NumberFormatter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import groovy.beans.Bindable
import javax.swing.SwingUtilities
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionListener;

support = new Support();
editList = support.getListOfEditedFiles();

def lastEdited = support.mostRecentlyEdited
println "\n\n--> Most Recently Edited was '${lastEdited.filename}'"


// free text config properties
fn = "resources/freetext.properties"
ct = new ConfigTool(fn,true);

tracker = new ComponentTracker();
// what does property file say about frame size and location ?
dims = ct.get("window");

swing = new SwingBuilder()
log="";	
home = System.getProperty("user.home")
logName = home+"/.TextEditor.log"
String story = "";
rowcol = new JLabel("xxx");

def suppressItemStateChanged = false

gta = new GTextArea();
gta.setStory(story);
this.getTextAreaCursorPosition();    
//gta.addMouseListener(new PopClickListener());

gta.setComponentPopupMenu(new PopUpDemo());    
gta.addKeyListener(new KeyListener()
{
	@Override 
	public void keyPressed(KeyEvent ke)
    {
		boolean f = false

		if (ke.isShiftDown()) 
		{
			f = true
		} // end of if

		switch (ke.getKeyCode()) 
		{
			case KeyEvent.VK_F3:  // move x coordinate left
			if (f)
			{
				println "F15 key pressed"
			} // end of shift

			else
			{
				println "F3 key pressed"
				exitText(ke);
			} // end of 
			break;
		}
    }

			@Override
            public void keyReleased(KeyEvent e)
          	{          	
	        	this.getTextAreaCursorPosition();    
	        	//println "keyReleased rowcol="+rowcol;
            }
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

});

boolean saveflag = false;
logCount=-1;
lastedited=new Cell();
def logEntries=[]
gui=null;


// Apple Mac bits follow ---
System.setProperty("apple.laf.useScreenMenuBar", "true")
System.setProperty("com.apple.mrj.application.apple.menu.about.name", "TextEditor")
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
def createShortcutWithModifier = 
{ key, modifier -> KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | modifier) }

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
Dimension ms = new Dimension(400, 30);
jtf.setMaximumSize(ms);

chooser = new JFileChooser()
chooser.setFileSelectionMode(JFileChooser.FILES_ONLY)

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

dofont = swing.action(
  name:'Change Font',
  closure:this.&doFont,
  mnemonic:'u',
  accelerator: createShortcut(KeyEvent.VK_U)
)

doselect = swing.action(
  name:'Select Item',
  closure:this.&doSelect
)


JScrollPane scrollPane = new JScrollPane(gta)
scrollPane.setMinimumSize(new java.awt.Dimension(300,500))
scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED)
scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
TextLineNumber tln = new TextLineNumber(gta);
scrollPane.setRowHeaderView( tln );

/*
BufferedImage myPicture = ImageIO.read(new File("resources/help.png"));
JLabel picLabel = new JLabel(new ImageIcon(myPicture));
widget(picLabel)
*/

// set up two hboxes to go into one vbox which appears at top of frame
def box1 = swing.hbox(background : java.awt.Color.GREEN,  opaque:false) {
	label ' Currently Editing This File : '
	widget(jtf)    //,maximumSize:[380,36])
	glue();
}

// , selectedIndex:0
def box2 = swing.hbox{
	widget(new JLabel(' Recently Edited : '))
	widget(cb = comboBox(id: 'cb', items: editList ) );  // , minimumSize:[100,30],  maximumSize:[300,60], maximumSize:[660,40] ));
	cb.addItemListener(new ItemListener()
	{
		public void itemStateChanged(ItemEvent e)
		{	println "itemStateChanged:suppressItemStateChanged="+suppressItemStateChanged
			if (!suppressItemStateChanged)
			{
	        		System.out.println(e.getItem() + "<+>" + e.getStateChange() );
				processNewFile(e.getItem().trim());
			} // end of if
        	}
	});
	glue();
} // end of box2

// , selectedIndex:0
def box3 = swing.hbox{
	widget(rowcol)
	glue();
}

Dimension maxSize = new Dimension(400, 100);
def tallbox = swing.vbox(preferredSize:maxSize,background : java.awt.Color.BLUE,  opaque:false ){
	widget(box1)
	widget(box2)
	glue()
}

// add constraints to lower pane
panelx.add(scrollPane, c);
def tallbox2 = swing.vbox{ 
	widget(panelx)  // jtextarea within scrollpane
	widget(box3)    // row column
} // end of tallbox2

//Create a split pane with the two scroll panes in it.
splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tallbox, tallbox2);
//splitPane.setResizeWeight(0.9D); // <-- here
splitPane.setOneTouchExpandable(true);
splitPane.setDividerLocation(60);
splitPane.setDividerSize(15);


//Provide minimum sizes for the two components in the split pane
Dimension minimumSize = new Dimension(600, 360);
panelx.setMinimumSize(minimumSize);


def toolbar = new JToolBar(JToolBar.VERTICAL);
ImageIcon cup = new ImageIcon("resources/stop16.png");
toolbar.add(new JButton(cup));


cup = new ImageIcon("resources/edit16.png");
toolbar.add(new JButton(cup));
cup = new ImageIcon("resources/editcopy16.png");
toolbar.add(new JButton(cup));

cup = new ImageIcon("resources/help.png");
toolbar.add(new JButton(cup));







JFrame.setDefaultLookAndFeelDecorated(true);


gui = swing.frame(id:'gui',title:fileName, layout:new BorderLayout(),minimumSize:[300,300], preferredSize:[dims.width as int,dims.height as int], pack:false, show:false, location:[dims.left as int,dims.top as  int], defaultCloseOperation:WC.EXIT_ON_CLOSE)
{
        menuBar {
                menu(text:'File',  mnemonic:'F')   
			//,accelerator: createShortcut(KeyEvent.VK_F))   
			//, accelerator:'ctrl F' ) 
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
                         menuItem() 
                         {
                             action(dofont)
                         }
                } // end of menu

        } // end of menubar


	container(splitPane);
} // end of gui

// tracks frame position and dimensions
gui.addComponentListener(tracker);
gui.add(toolbar, BorderLayout.EAST);
gui.pack()
gui.show()
updateForegroundColor();
updateBackgroundColor();
updateFont();
def fi = new File(lastEdited.filename);
if (fi.exists())
{
	getText(fi)
} // end of if




// ====================================

// dropdown select text 
def doSelect(itemEvent)
{
	println(itemEvent.toString() );
}


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
                	gta.setBackground(rgb);
			updateConfig('background', nc.toString())
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
                	gta.setForeground(rgb);
			updateConfig('foreground', nc.toString())
        	    } // end of if
        	} // end of if
} // end of pick


	def updateForegroundColor()
	{
		def nc = ct.get("foreground");
		println "updateForegroundColor() got:"+nc.toString()
		if (nc!=null)
		{

            	    def rgb = Color.decode(nc.toString()); 
	            println "===> new fore ground color:"+nc.toString()+" as "+rgb;
        	    if (rgb != null) 
                    {
                	gta.setForeground(rgb);
        	    } // end of if

		} // end of if

	} // end of update


	def updateBackgroundColor()
	{
		def nc = ct.get("background");
		println "updateBackgroundColor() got:"+nc.toString()
		if (nc.size()>0)
		{

            	    def rgb = Color.decode(nc.toString()); 
	            println "===> new background color:"+nc.toString()+" as "+rgb;
        	    if (rgb != null) 
                    {
                	gta.setBackground(rgb);
        	    } // end of if

		} // end of if

	} // end of update

	def updateFont()
	{
		def ff = ct.get("fontfamily");
		println "updateFont got:"+ff
		if (ff.size()>0)
		{
		   int plainsize = 10;
		   int plainstyle = 0;

		   def sz = ct.get("fontsize");
		   if (sz.size()>0)
		   {
			plainsize = sz as int
		   }

		   def st = ct.get("fontstyle");
		   if (st.size()>0)
		   {
			plainstyle = st as int
		   }


		   Font font = new Font(ff, plainstyle, plainsize);
		   // style is int:0=plain, 1=bold, 2=italic, 3=bold italic
		   gta.setFont(font);

		} // end of if

	} // end of update



def updateConfig(String k, String v)
{
        ct.set(k,v)
	println "   ${k} now="+ct.get(k);
	ct.rewriteConfig()        
} // end of def

// change text 
def doFont(event)
{
			JFontChooser fontChooser = new JFontChooser();
			fontChooser.setSelectedFont(gta.getFont());
			int result = fontChooser.showDialog(null);
			if (result == JFontChooser.OK_OPTION)
			{
				this.font = fontChooser.getSelectedFont(); 
				System.out.println("Selected New Font : " + font); 
				println("- New Font Family:" + font.family+" name:"+font.name); 
				println("- New Font Style:" + font.style+" size:"+font.size); 
				// style is int:0=plain, 1=bold, 2=italic, 3=bold italic
                                gta.setFont(font);

				updateConfig('fontfamily', font.name)
				updateConfig('fontsize', font.size.toString())
				updateConfig('fontstyle', font.style.toString())
			} // end of if            
} // end of change font


def doNew(event)
{
	saveflag=false;
	swing.mi1.enabled=saveflag;

	def getProperty = System.&getProperty  
	def home = getProperty("user.home")
	fileName = ""
	jtf.text = ""
	gui.title = ""
	gta.setText("")
	gta.setCaretPosition(0)
	gta.requestFocus();
	gta.requestFocusInWindow();
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
	rowcol.setText("");
	gta.setText("")
	gta.setCaretPosition(0)
	gta.requestFocus();
	gta.requestFocusInWindow();
}


def processNewFile(String s)
{
	def sub = s.trim();
	def i2 = sub.indexOf(' ');
	if ( i2 > -1 )
	{
		sub = s.substring(i2+1)		
		println "sub="+sub
		i2 = sub.indexOf(' ');
		sub = sub.substring(i2).trim()
		println "--> set sub=<"+sub+">  as i2="+i2
	} // end of if

	def fh2 = new File(sub);
	if ( !( fh2.exists() ) )
	{
		JOptionPane.showMessageDialog(null, "Cannot find ${s} to re-open", "Missing File", JOptionPane.ERROR_MESSAGE);
		return;
	}
	else
	{
		getText(fh2);
		support.keeper(sub, 0);
		editList = support.getListOfEditedFiles();
		cb = new JComboBox(editList)

		//cb.removeAllItems();
		//editList.each{l-> cb.addItem(l) } 
	} // end of if
} // end of processNewFile



def reopenText(event) {
	String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Choose a file to re=open or ESC",
                    "Re-Open Dialog",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    editList,
                    editList[0]);
  
	if (s==null) return;
	processNewFile(s);
	return;

/* ---
	// chooser.setFileFilter(filter1);
	int returnValue = chooser.showOpenDialog(gui);
	if ((returnValue == javax.swing.JFileChooser.APPROVE_OPTION)) 
	{  
		def fh = chooser.getSelectedFile();
		getText(fh);
		gui.repaint();
	} // end of if
-- */

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
				if (fnlc.equals(nalc)) {                             //println "... they match "+v; 
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
		gta.setText(story)
		gta.setCaretPosition(at);
	        this.getTextAreaCursorPosition();    

		gta.requestFocus();
		gta.requestFocusInWindow();
} // end of getText


def getMetadata(def fh)
{
	String sz = fh.length();
	String r = (fh.canRead()) ? 'read' : '';
	String w = (fh.canWrite()) ? '/write' : '';
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
   		def fh = chooser.getSelectedFile(); // chooser returns a file handle
		//File f = fh.getSelectedFile();
		def path = fh.getAbsolutePath();
		println "openText chose file "+fh.toString()+" in folder "+path;

		getText(fh);
		support.keeper(fh.toString(), 0);
		editList = support.getListOfEditedFiles();
		cb = new JComboBox(editList)
	} // end of if
} // end of openText


def saveText(event) 
{ 
	fileName = jtf.text
	def f = new File(fileName);
	def path = f.getAbsolutePath();
	println "saveText chose to keep file "+fileName+" in folder "+path;
	dosave( gta.getText(), fileName ); 
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
	dosave( gta.getText(), fileName ); 
}

def dosave(String story, String outName)
{ 
	println "dosave="+outName
	int n = 0;
	File fo = new File(outName)
	if ( fo.exists() )  
	{
		//default icon, custom title
		n = JOptionPane.showConfirmDialog(null,
	    	"Would you like to replace this file ?\n${outName}",
    		"An Existing File Will Be Over-written",
    		JOptionPane.YES_NO_OPTION);
		println "JOptionPane gave $n as the answer"
		if (n==0) { fo.delete();}
	} // end of if

	if (n==0)
	{
		int j = gta.getCaretPosition()
		println "===> Writing ${outName} with caret at $j"

		try{
			saveflag=false;
			fo.write(story)
			suppressItemStateChanged = true;
			support.keeper( outName, j );
			editList = support.getListOfEditedFiles();
			cb = new JComboBox(editList)

			suppressItemStateChanged = false;
			println "... end of dosave()"
 		}
 
	        catch(Exception ex)
		{	
			//ex.printStackTrace()
			JOptionPane.showMessageDialog(null, "Cannot save this :\n${outName}\nPlease correct path or filename ", "Failed to Save", JOptionPane.ERROR_MESSAGE);
		}
	} // end of if

	println "dosave end"

} // end of dosave



def exitText(event) 
{
	System.exit(0);
}


	def getTextAreaCursorPosition() 
	{
 		int c = TextUtilities.getColumnAtCaret(gta); 
 		int r = TextUtilities.getLineAtCaret(gta);  
 		rowcol.setText("row:$r  col:$c");
 		//println rc;
	} // end of method




