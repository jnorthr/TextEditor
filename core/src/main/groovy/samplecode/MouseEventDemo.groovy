import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.* 
 
public class MouseEventDemo extends JFrame implements MouseListener 
{
 
   // Private variables
   private JTextField tfMouseX; // to display mouse-click-x
   private JTextField tfMouseY; // to display mouse-click-y
   private JTextArea ta; // to display mouse-click-y 

   // Constructor - Setup the UI
   public MouseEventDemo() {
      setLayout(new FlowLayout()); // "this" frame sets layout
 
      // Label
      add(new JLabel("X-Click: ")); // "this" frame adds component
 
      // TextField
      tfMouseX = new JTextField(10); // 10 columns
      tfMouseX.setEditable(false);  // read-only
      add(tfMouseX); // "this" frame adds component
 
      // Label
      add(new JLabel("Y-Click: ")); // "this" frame adds component
 
      // TextField
      tfMouseY = new JTextField(10);
      tfMouseY.setEditable(false);  // read-only
      add(tfMouseY); // "this" frame adds component
 
      // TextArea
      ta = new JTextArea(10,90); // 10 columns
      ta.setEditable(true);  // read-only
      //add(ta); // "this" frame adds component - use scroll  pane below

      ta.addMouseMotionListener(new MouseMotionAdapter() 
      {
        public void mouseMoved(MouseEvent evt) 
	{
	    JTextArea source = (JTextArea) evt.getSource();
  	    // Process current position of cursor while all mouse buttons are up.
    	    source.setText(source.getText() + "Mouse moved [" + evt.getPoint().x + "," + evt.getPoint().y + "]\n");
        }

	public void mouseDragged(MouseEvent evt) 
	{
	    JTextArea source = (JTextArea) evt.getSource();
	    // Process current position of cursor while mouse button is pressed.
      	    source.setText(source.getText() + "Mouse dragged [" + evt.getPoint().x + "," + evt.getPoint().y + "]\n");
        }
      }); // end of

      JScrollPane tAreaScrollPane = new JScrollPane(ta);
      tAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      tAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      add(tAreaScrollPane); // "this" frame adds component

      this.addMouseListener(this);
      // "this" frame fires the MouseEvent
      // "this" frame adds "this" object as MouseEvent listener
 
      setTitle("MouseEvent Demo"); // "this" Frame sets title
      setSize(350, 100);      // "this" Frame sets initial size
      setVisible(true);       // "this" Frame shows
   }
 
   public static void main(String[] args) {
      new MouseEventDemo();  // Let the constructor do the job
   }

   // MouseEvent handlers
   @Override
   public void mouseClicked(MouseEvent e) {
      //tfMouseX.setText(e.getX() + "");
      //tfMouseY.setText(e.getY() + "");
	println "mouseClicked: getX()"+e.getX();
	println "mouseClicked: getY()"+e.getY();
        try{	
		println "mouseClicked: getLocationOnScreen()"+e.getLocationOnScreen(); 
        } catch(Exception x) {}

        try{	
		println "mouseClicked: getXOnScreen()"+e.getXOnScreen();
        } catch(Exception x) {}

        try{	
		println "mouseClicked: getYOnScreen()"+e.getYOnScreen();
        } catch(Exception x) {}

        try{	
		println "mouseClicked: translatePoint(0,0)"+e.translatePoint(0,0);
        } catch(Exception x) {}

        try{	
		println "mouseClicked: getClickCount()"+e.getClickCount();
        } catch(Exception x) {}

        try{	
		println "mouseClicked: getPoint()"+e.getPoint();
        } catch(Exception x) {}

        try{	
		println "mouseClicked: getModifiersEx()"+e.getModifiersEx();
		println " which button ? "+e.getButton();
		 JFrame editor = (JFrame) e.getSource();
		Point pt = new Point(e.getX(), e.getY());
    		int pos = editor.viewToModel(pt);        
		println "... got pos:"+pos
	} catch(Exception x) { println e.toString(); }





	println "----\n"
   }
 
   @Override
   public void mousePressed(MouseEvent e) { }
 
   @Override
   public void mouseReleased(MouseEvent e) { println "mouse button released" }
 
   @Override
   public void mouseEntered(MouseEvent e) { }
 
   @Override
   public void mouseExited(MouseEvent e) { }
 
} // end of class