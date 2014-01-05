import groovy.swing.SwingBuilder 
import javax.swing.WindowConstants as WC 
import java.awt.FlowLayout
import java.awt.*
import java.awt.BorderLayout
import javax.swing.*
import javax.swing.JComboBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.UIManager.*;
import javax.swing.UIManager.LookAndFeelInfo

import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

// themes from http://www.codeproject.com/Articles/2541/Java-look-feel-themes
// user selects theme - jumble 
MetalTheme normal = new JumbleTheme();

// user selects theme - Moody Blues 
MetalTheme theme = new MoodyBlueTheme();  

// set the chosen theme
//MetalLookAndFeel.setCurrentTheme(theme);


try
{
    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    SwingUtilities.updateComponentTreeUI(this);
}
catch(Exception e)
{
    System.out.println(e);
}

//System.setProperty("apple.awt.brushMetalLook", "true");
/*

try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            println "l&F lookup:"+info.getClassName()
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (Exception e) { println "failed to find Nimbus"
    // If Nimbus is not available, you can set the GUI to another look and feel.
}

try
{
    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    SwingUtilities.updateComponentTreeUI(this);
}
catch(Exception e)
{
    println "failed to find Metal:"+e.toString();
    //System.out.println(e);
}
----------
*/


swing = new SwingBuilder()
def flag = true

list=['aaa','bbb','ccc']



def cb = swing.comboBox(id: 'cb', items:list, maximumSize:[200,40] )
    cb.addItemListener(new ItemListener()
    {
        public void itemStateChanged(ItemEvent e)
        {
            System.out.println(e.getItem() + " and state change:" + e.getStateChange() );
            //processNewFile(e.getItem().trim());
            }
    });
    
gui = swing.frame(id:'gui',title:'JComboBox', layout:new BorderLayout(),pack:true, show:true, 
minimumSize:[300,100], defaultCloseOperation:WC.DISPOSE_ON_CLOSE)
{
  vbox{
    hbox{
    label "JComboBox : "
        widget(cb)
    } // end of hbox


    hbox{
        button(id: "button", text: "Press", 
            actionPerformed : 
            {
                    gui.background = (flag) ? java.awt.Color.RED : java.awt.Color.WHITE
                    flag = !flag 
                    list += "xxx"
                    cb.addItem( "yyy" );
                    gui.repaint();                       
            }) // end of button constructor


        button(id: "button2", text: "Themes", 
            actionPerformed : 
            {
                MetalLookAndFeel.setCurrentTheme(normal);
                gui.repaint();                       
            }) // end of button constructor



    } // end of hbox
    
  } // end of vbox
  
} // end of gui

//gui.setDefaultLookAndFeelDecorated(true);