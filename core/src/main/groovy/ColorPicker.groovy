import java.awt.*
import javax.swing.*
import groovy.swing.SwingBuilder
import groovy.swing.*


public class ColorPicker
{
  JFrame frame = null;
  String hex = null;
  Color color = null;
  String title = "Choose Background Color";

  boolean audit = true;
  def say(tx) { if (audit) {println tx;} }
    
  public ColorPicker(JFrame f) {
      frame = f;
  } // end of constructor
  
  public ColorPicker() {
      frame = new JFrame();
  } // end of constructor
  
  public setTitle(String tl)
  {
	this.title = tl;
  }
  
  /**
   * 
   * @param 
   * @return String #CC00CC
   */
  def getColor()
  {
     color = JColorChooser.showDialog(
             frame,   //null,    //ColorChooserDemo2.this,
             title,
             frame.getBackground()  // initial default value for color chooser map
    );
    
    if (color!=null)
    {                    
       hex = Integer.toHexString( color.getRGB() & 0xffffff ).padLeft(6,'0');
       //hex= hex.substring(2,hex.length());
       hex="#"+hex;
    } // end of if 
                               
    say "answer in hex:"+hex+" as "+color.toString()
    return hex;
  } // end of def


  /**
   * 
   * @param colorStr in hex as e.g. "#CC00CC"
   * @return java.awt.Color[r=204,g=0,b=204]
   */
   def hex2Rgb(String colorStr) 
   {
      return new Color(
            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) 
      );
   } // end of method


    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("Hello World!");
        ColorPicker cp = new ColorPicker(frame);

        frame.setLayout(new GridBagLayout());
        final GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1d;
        gc.weighty = 1d;

        //frame.add(buildPanel(), gc);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setLocation(200, 100);
        frame.setBackground(Color.white);               

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        String nc = cp.getColor();
        println "===> new color:"+nc.toString();

        def rgb = null;
        if (nc!=null)
        {
            rgb = cp.hex2Rgb(nc); 

            println "===> new color:"+nc.toString()+" as "+rgb;
            if (rgb != null) 
            {
                frame.setBackground(rgb);
            } // end of if
        } // end of if
        
        println "--- the end ---"        
    } // end of main


} // end of class