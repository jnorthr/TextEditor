import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BasicText {

    public static JPanel buildPanel() {

        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints gc = new GridBagConstraints();

        final JTextArea text = new JTextArea("Text");
        final JScrollPane scrollPane = new JScrollPane(text); 
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1d;
        gc.weighty = 1d;        

        // Add the textarea -> scroll pane -> to the panel -> to the jframe                       
        panel.add(scrollPane, gc);        
        return panel;
    }

    public static void main(String[] args) {


        JFrame frame = new JFrame("Hello World!");
        frame.setLayout(new GridBagLayout());
        final GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;

        frame.add(buildPanel(), gc);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setLocation(200, 100);
        frame.setBackground(Color.white);               

        frame.pack();
        frame.setVisible(true);
    }

} // End of the class //