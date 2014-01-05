import java.awt.*
import javax.swing.*
import java.awt.event.*;
import java.util.*
import java.util.Timer
import ConfigTool

public class ComponentTracker implements ComponentListener {

    Dimension screensize;
    boolean changed = false;
    int left = 0;
    int top = 0;
    int wide = 0;
    int high = 0;
    
    public ComponentTracker() 
    {    
           screensize = Toolkit.getDefaultToolkit().getScreenSize();
           use (TimerMethods) {
                def timer = new Timer()
                def task = timer.runEvery(60000, 60000) {
                    println "Task executed at ${new Date()}."
                    if (changed) 
                    {
                        println "things have changed";
                        changed = false;
                        // free text config properties
                        def fn = "resources/freetext.properties"
                        def ct = new ConfigTool(fn,true);
                        ct.set("window.width",wide.toString());
                        ct.set("window.height",high.toString());
                        ct.set("window.left",left.toString());
                        ct.set("window.top",top.toString());
                        ct.rewriteConfig()        

                    } // end if
                }
            }
            println "Current date is ${new Date()}."
    }

    public void displayMessage(msg) {println msg;}

    public void componentHidden(ComponentEvent e) {
        displayMessage(e.getComponent().getClass().getName() + " --- Hidden");
    }

    public void componentMoved(ComponentEvent e) {
        left = e.getComponent().getX();
        top = e.getComponent().getY();
        wide = e.getComponent().getWidth()
        high = e.getComponent().getHeight()
        changed = true;
        displayMessage(e.getComponent().getClass().getName() + " --- Moved getX=${left} getY=${top} w=${wide} h=${high} ");
    }

    public void componentResized(ComponentEvent e) {
        left = e.getComponent().getX();
        top = e.getComponent().getY();
        wide = e.getComponent().getWidth()
        high = e.getComponent().getHeight()
        changed = true;

        displayMessage(e.getComponent().getClass().getName() + " --- Resized getX=${left} getY=${top} w=${wide} h=${high}");            
    }

    public void componentShown(ComponentEvent e) {
        displayMessage(e.getComponent().getClass().getName() + " --- Shown");

    }

    public static void main(String[] args) {
        JFrame frame;
        def ncp = new ComponentTracker();

        println "size.width:"+ncp.screensize.width
        println "size.height:"+ncp.screensize.height

        //Create and set up the window.
        frame = new JFrame("ComponentTracker");
        JPanel panel = new JPanel(new BorderLayout());

        //panel.addComponentListener(ncp);
        frame.addComponentListener(ncp);
        frame.setContentPane(panel);
        frame.show()
    }
}


// File: newtimer.groovy
class GroovyTimerTask extends TimerTask {
    Closure closure
    void run() {
        closure()
    }
}

class TimerMethods {
    static TimerTask runEvery(Timer timer, long delay, long period, Closure codeToRun) {
        TimerTask task = new GroovyTimerTask(closure: codeToRun)
        timer.schedule task, delay, period
        task
    }
}