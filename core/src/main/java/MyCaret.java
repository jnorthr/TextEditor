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
 
    class MyCaret extends javax.swing.plaf.basic.BasicTextUI.BasicCaret {
        private boolean isInsertMode=false;
	private JTextArea jep;

	public MyCaret(javax.swing.JTextArea ep)
	{
		jep = ep;
	}

        public boolean isInsertMode() {
            return isInsertMode;
        }

        public void paint(Graphics g) {
            if (isInsertMode()) {
                //we should shift to half width because of DefaultCaret rendering algorithm

                AffineTransform old=((Graphics2D)g).getTransform();
                int w=(Integer)jep.getClientProperty("caretWidth");
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
                    int damageWidth = (Integer)jep.getClientProperty("caretWidth");
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