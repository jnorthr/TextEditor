import javax.swing.*;
import javax.swing.JToggleButton;
import javax.swing.UIManager.*;
import javax.swing.UIManager.LookAndFeelInfo
import groovy.swing.*
import java.awt.FlowLayout;

public class JButtonWithTooltip {
  def swing = new SwingBuilder()
  def gui
    /* FileView icons */
    protected Icon directoryIcon = null;
    protected Icon fileIcon = null;
    protected Icon computerIcon = null;
    protected Icon hardDriveIcon = null;
    protected Icon floppyDriveIcon = null;

    protected Icon newFolderIcon = null;
    protected Icon upFolderIcon = null;
    protected Icon homeFolderIcon = null;
    protected Icon listViewIcon = null;
    protected Icon detailsViewIcon = null;
      
  public JButtonWithTooltip() {

    try
    {
        installIcons();
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        SwingUtilities.updateComponentTreeUI(this);
    }
    catch(Exception e)
    {
        //System.out.println(e);
    }


    JButton b = new JButton("Test");
    b.setToolTipText("Help text for the button");


/*
OptionPane.errorIcon
OptionPane.informationIcon
OptionPane.warningIcon
OptionPane.questionIcon
    "OptionPane.questionIcon"
    "OptionPane.errorIcon"
    "OptionPane.informationIcon"
    "OptionPane.warningIcon"
    "FileView.directoryIcon"
    "FileView.fileIcon"
    "FileView.computerIcon"
    "FileView.hardDriveIcon"
    "FileView.floppyDriveIcon"
    "FileChooser.newFolderIcon"
    "FileChooser.upFolderIcon"
    "FileChooser.homeFolderIcon"
    "FileChooser.detailsViewIcon"
    "FileChooser.listViewIcon"
    "Tree.expandedIcon"
    "Tree.collapsedIcon"
    "Tree.openIcon"
    "Tree.leafIcon"
    "Tree.closedIcon"
    
Tree.collapsedIcon
FileChooser.directoryIcon
FileChooser.detailsViewIcon
OptionPane.questionIcon
FileChooser.newFolderIcon
FileView.floppyDriveIcon
Tree.openIcon
Tree.expandedIcon
OptionPane.informationIcon
Tree.closedIcon
Tree.leafIcon
FileChooser.upFolderIcon
OptionPane.errorIcon
ToolBar.handleIcon
FileChooser.floppyDriveIcon
FileChooser.fileIcon
RadioButton.icon
FileView.fileIcon
*/
    Icon stopIcon = new ImageIcon("/Volumes/Data/dev/FreeText/core/core/resources/stop16.png","pressed");
    JToggleButton button2 = new JToggleButton(UIManager.getIcon("OptionPane.informationIcon"));
    button2.setRolloverIcon(stopIcon);
    button2.setPressedIcon(UIManager.getIcon("OptionPane.errorIcon"));

    JToggleButton button = new JToggleButton(UIManager.getIcon("OptionPane.informationIcon"));
    button.setToolTipText("this is tooltip text");
    
    try
    {
        // Add rollover icon
        ImageIcon rolloverIcon = new ImageIcon("/Volumes/Data/dev/FreeText/core/core/resources/x.png");
        button.setRolloverIcon(rolloverIcon);

        // Add pressed icon
        Icon pressedIcon = new ImageIcon("/Volumes/Data/dev/FreeText/core/core/resources/stop16.png","pressed");
        button.setPressedIcon(pressedIcon);

        // To remove rollover icon, set to null
        //button.setRolloverIcon(null);

        // To remove pressed icon, set to null
        //button.setPressedIcon(null);
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
    
    
    gui = swing.frame(id:'gui',title:"Tooltip tests", layout:new FlowLayout(), pack:false, show:true, defaultCloseOperation:JFrame.DISPOSE_ON_CLOSE)
    {
        widget(b);
        widget(button);
        widget(button2);
    } // end of gui

    
  } // end of constructor

    protected void installIcons() {
    directoryIcon    = UIManager.getIcon("FileView.directoryIcon");
    fileIcon         = UIManager.getIcon("FileView.fileIcon");
    computerIcon     = UIManager.getIcon("FileView.computerIcon");
    hardDriveIcon    = UIManager.getIcon("FileView.hardDriveIcon");
    floppyDriveIcon  = UIManager.getIcon("FileView.floppyDriveIcon");

    newFolderIcon    = UIManager.getIcon("FileChooser.newFolderIcon");
    upFolderIcon     = UIManager.getIcon("FileChooser.upFolderIcon");
    homeFolderIcon   = UIManager.getIcon("FileChooser.homeFolderIcon");
    detailsViewIcon  = UIManager.getIcon("FileChooser.detailsViewIcon");
    listViewIcon     = UIManager.getIcon("FileChooser.listViewIcon");
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JButtonWithTooltip();
            }
        });
    }
}