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
 
public class CaretShape extends JTextArea {

    private boolean isInsertMode=false;
    Color oldCaretColor;
    Color insertCaretColor=new Color(254, 254, 254);

    public CaretShape() {
        setText("Press INSERT key to enable/disable replace mode.");
 JTextArea jep = (JTextArea)this;
        MyCaret c = new MyCaret();
        c.setBlinkRate(getCaret().getBlinkRate());
        setCaret(c);
        oldCaretColor=getCaretColor();
        addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e){
                if (isInsertMode()) {
                    processCaretWidth();
                }
            }
        });
 
        Keymap kMap=this.getKeymap();
        Action a=new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setInsertMode(!isInsertMode());
            }

        };
        kMap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,0),a);
    }
 
    public static void main(String[] args) {
        JFrame fr=new JFrame("Custom caret shape for Overwrite mode JTextArea");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(450,270);
        final CaretShape cs=new CaretShape();
        fr.getContentPane().add(new JScrollPane(cs));
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                cs.setInsertMode(true);
            }
        });
 
    }
 
    public boolean isInsertMode() {
        return isInsertMode;
    }
 
    public void setInsertMode(boolean insertMode) {
        isInsertMode = insertMode;
        processMode();
    }
 
    private void processMode() {
        if (isInsertMode()) {
            processCaretWidth();
            setCaretColor(insertCaretColor);
        }

        else {
            setCaretColor(oldCaretColor);
            putClientProperty("caretWidth", 1);
        }
    }
 
    private void processCaretWidth() {
        try {
            int pos=getCaretPosition();
            Rectangle rPos=modelToView(pos)!=null ? modelToView(pos).getBounds() :new Rectangle();
            int caretX=rPos.x;
            int caretEndX=rPos.x;
            if (pos<getDocument().getLength()) {
                Rectangle rNextPos=modelToView(pos+1)!=null ? modelToView(pos+1).getBounds(): new Rectangle();
 
                if (rPos.y==rNextPos.y) {
                    caretEndX=rNextPos.x;
                }
            }
            putClientProperty("caretWidth", Math.max(1, caretEndX-caretX+1));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
	
    public void replaceSelection(String content) {
        if (isEditable() && isInsertMode() && getSelectionStart()==getSelectionEnd()) {
            int pos=getCaretPosition();
            int lastPos=Math.min(getDocument().getLength(), pos+content.length());
            select(pos, lastPos);
        }
        super.replaceSelection(content);
    }
    
    class MyCaret extends DefaultCaret {

        public void paint(Graphics g) {
            if (isInsertMode()) {
                //we should shift to half width because of DefaultCaret rendering algorithm

                AffineTransform old=((Graphics2D)g).getTransform();
                int w=(Integer)getClientProperty("caretWidth");
                g.setXORMode(Color.black);
                g.translate(w/2,0);
                super.paint(g);
                ((Graphics2D)g).setTransform(old);
            }
            else {
                super.paint(g);
            }

        }
        protected synchronized void damage(Rectangle r) {
            if (isInsertMode()) {
                if (r != null) {
                    int damageWidth = (Integer)getClientProperty("caretWidth");
                    x = r.x - 4 - (damageWidth/2 );
                    y = r.y;
                    width = 9 + 3*damageWidth/2;
                    height = r.height;
                    repaint();
                }
            }
            else {
                super.damage(r);
            }
        }
    }
}