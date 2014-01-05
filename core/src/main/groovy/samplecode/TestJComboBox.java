import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public final class TestJComboBox
    {
    // ------------------------------ CONSTANTS ------------------------------

    private static final Color FOREGROUND_FOR_LABEL = new Color( 0x0000b0 );

    // --------------------------- main() method ---------------------------

    /**
     * Debugging harness for a Frame
     *
     * @param args command line arguments are ignored.
     */
    public static void main( String args[] )
        {
        SwingUtilities.invokeLater( new Runnable()
        {
        public void run()
            {
            final JFrame jFrame = new JFrame();
            final Container contentPane = jFrame.getContentPane();
            contentPane.setLayout( new FlowLayout() );
            final JComboBox flavour = new JComboBox( new String[] {
                    "strawberry", "chocolate", "vanilla","red","green","blue" } );
            // ensure all three choices will be displayed without scrolling.
            flavour.setMaximumRowCount( 3 );
            flavour.setForeground( FOREGROUND_FOR_LABEL );
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

            // No multiple selections permitted, ever, even though this is called a combo box.
            // so there is nothing we need to do to prevent them.
            final ItemListener theListener = new ItemListener()
            {
            /**
             * Called whenever the value of the selection changes. Will
             * be called twice for every change.
             * @param e the event that characterizes the change.
             */
            public void itemStateChanged( ItemEvent e )
                {
                final int selectedIndex = flavour.getSelectedIndex();
                // even though JComboBox is generic, you still need the (String) cast, a legacy quirk.
                final String choice = ( String ) flavour.getSelectedItem();
                System.out.println( selectedIndex + " " + choice);   // + " " + e.toString() );
                }
            };
            // add components
            flavour.addItemListener( theListener );
            contentPane.add( flavour );
            // build frame
            jFrame.setSize( 100, 100 );
            jFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            jFrame.validate();
            jFrame.setVisible( true );
            // experiment with JComboBox.setSelectedIndex
            // This generates TWO itemStateChanged events.
            // One with item=old value stateChange=DESELECTED
            // and one with item=new value and stateChange=SELECTED
            flavour.setSelectedIndex( 2 );
            // suppress itemChangedEvents
            flavour.removeItemListener( theListener );
            // generates no itemChangedEvents
            flavour.setSelectedIndex( 1 );
            // turn itemChangedEvents back ot
            flavour.addItemListener( theListener );
            // generates two itemChangedEvents, usual per usual
            flavour.setSelectedIndex( 0 );
            }
        } );
        }// end main
    }