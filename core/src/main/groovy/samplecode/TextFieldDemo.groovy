
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import groovy.swing.SwingBuilder
import groovy.swing.*
 
public class TextFieldDemo implements DocumentListener {
     
    private JTextField entry;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JLabel status;
    private JTextArea textArea;
     
    final static Color  HILIT_COLOR = Color.LIGHT_GRAY;
    final static Color  ERROR_COLOR = Color.PINK;
    final static String CANCEL_ACTION = "cancel-search";
     
    final Color entryBg;
    final Highlighter hilit;
    final Highlighter.HighlightPainter painter;
    def swing = new SwingBuilder()
    def InputStreamReader;

    public TextFieldDemo() {
        initComponents();
         
        try {
            //textArea = new JTextArea();
	    textArea.append(new File("/Volumes/FHD-XS/FreeText/TextEditor/core/src/main/groovy/sparecode.txt").text );
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
        textArea.setHighlighter(hilit);
         
        entryBg = entry.getBackground();
        entry.getDocument().addDocumentListener(this);
         
        InputMap im = entry.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = entry.getActionMap();
        im.put(KeyStroke.getKeyStroke("ESCAPE"), CANCEL_ACTION);
        am.put(CANCEL_ACTION, new CancelAction());
    }
     
    /** This method is called from within the constructor to
     * initialize the form.
     */
 
    private void initComponents() {
        entry = new JTextField();
        textArea = new JTextArea();
        status = new JLabel();
        jLabel1 = new JLabel();
 
 
        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        jScrollPane1 = new JScrollPane(textArea);
 
        jLabel1.setText("Enter text to search:");
     
    }
 
    public void search() {
        hilit.removeAllHighlights();
         
        String s = entry.getText();
        if (s.length() <= 0) {
            message("Nothing to search");
            return;
        }
         
        String content = textArea.getText();
        int index = content.indexOf(s, 0);
        if (index >= 0) {   // match found
            try {
                int end = index + s.length();
                hilit.addHighlight(index, end, painter);
                textArea.setCaretPosition(end);
                entry.setBackground(entryBg);
                message("'" + s + "' found. Press ESC to end search");
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        } else {
            entry.setBackground(ERROR_COLOR);
            message("'" + s + "' not found. Press ESC to start a new search");
        }
    }
 
    void message(String msg) {
        status.setText(msg);
    }
 
    // DocumentListener methods
     
    public void insertUpdate(DocumentEvent ev) {
        search();
    }
     
    public void removeUpdate(DocumentEvent ev) {
        search();
    }
     
    public void changedUpdate(DocumentEvent ev) {
    }
     
    class CancelAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            hilit.removeAllHighlights();
            entry.setText("");
            entry.setBackground(entryBg);
        }
    }
     
     
    public static void main(String[] args) 
    {
        def tfd = new TextFieldDemo(); 
	def frame = tfd.swing.frame(title:'TextFieldDemo',pack:true, show:true, size:[400,400], defaultCloseOperation:JFrame.EXIT_ON_CLOSE) 
	{
	   vbox{
		hbox(size:[100,50]){
			widget(tfd.jLabel1)
			widget(tfd.entry)
		}
		container(tfd.jScrollPane1)
		widget(tfd.status)
	    } // end of vbox

	} // end of frame 
    }
    
} // end of class