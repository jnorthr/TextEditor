//http://www.codeproject.com/Articles/328417/Java-Console-apps-made-easy#Makingacustomblockcaret.3
// By David MacDermot, 10 Feb 2012
// also see: http://www.java2s.com/Code/Java/Swing-JFC/Fanciercustomcaretclass.htm

import javax.swing.*;
import javax.swing.plaf.TextUI;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class BlockCaret extends DefaultCaret {

    private static final long serialVersionUID = 1L;

    /**
     * @brief Class Constructor
     */
    public BlockCaret() {
        setBlinkRate(500); // half a second
    }

    /* (non-Javadoc)
     * @see javax.swing.text.DefaultCaret#damage(java.awt.Rectangle)
     */
    protected synchronized void damage(Rectangle r) {
        if (r == null)
            return;

        // give values to x,y,width,height (inherited from java.awt.Rectangle)
        x = r.x;
        y = r.y;
        height = r.height;
        // A value for width was probably set by paint(), which we leave alone.
        // But the first call to damage() precedes the first call to paint(), so
        // in this case we must be prepared to set a valid width, or else
        // paint()
        // will receive a bogus clip area and caret will not get drawn properly.
        if (width <= 0)
            width = getComponent().getWidth();
        
        repaint();  //Calls getComponent().repaint(x, y, width, height) to erase 
        repaint();  // previous location of caret. Sometimes one call isn't enough.
    }

    /* (non-Javadoc)
     * @see javax.swing.text.DefaultCaret#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
        JTextComponent comp = getComponent();

        if (comp == null)
            return;

        int dot = getDot();
        Rectangle r = null;
        char dotChar;
        try {
            r = comp.modelToView(dot);
            if (r == null)
                return;
            dotChar = comp.getText(dot, 1).charAt(0);
        } catch (BadLocationException e) {
            return;
        }

        if(Character.isWhitespace(dotChar)) dotChar = '_';

        if ((x != r.x) || (y != r.y)) {
            // paint() has been called directly, without a previous call to
            // damage(), so do some cleanup. (This happens, for example, when
            // the text component is resized.)
            damage(r);
            return;
        }

        g.setColor(comp.getCaretColor());
        g.setXORMode(comp.getBackground()); // do this to draw in XOR mode

        width = g.getFontMetrics().charWidth(dotChar);
        if (isVisible())
            g.fillRect(r.x, r.y, width, r.height);
    }
}