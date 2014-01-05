import javax.swing.*
import groovy.swing.SwingBuilder
import java.awt.event.KeyEvent
import java.awt.event.*;
 
class SpinModel extends SpinnerNumberModel {
    SpinModel(int value, int min, int max, int step) {super(value, min, max, step)}
    Object getNextValue() { if (value == maximum) { return minimum } else { return value + stepSize; }}
    Object getPreviousValue() { if (value == minimum) { return maximum } else { return value - stepSize; }}
}
  
def swing = new SwingBuilder()

//SpinnerNumberModel
 sModel = new SpinModel(12, 6, 72, 1)

def dopickfg = swing.action(
  name:'Text Color    ',
  closure:this.&doPickText,
  mnemonic:'t')

swing.frame(title: 'Cyclic Spinner', defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE,  size: [200, 60], show: true, locationRelativeTo: null) {
    lookAndFeel('system')
    menuBar {
                menu(text:'Settings') 
                {
                         menuItem() 
                         {
                             action(dopickfg)
                         }
                } // end of menu    
    }
    spinner1 = spinner(size:[10, 10], model: sModel)
}

def doPickText(event)
{
    println "\nspinner1="+spinner1+"\n"+sModel.getValue();    
}

