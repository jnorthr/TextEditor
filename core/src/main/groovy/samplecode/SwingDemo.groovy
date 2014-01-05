//=========================
import java.awt.BorderLayout
import javax.swing.BorderFactory
import groovy.swing.SwingBuilder
import groovy.transform.Canonical
import java.awt.event.*;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu

class Car {
 int numberOfDoors=4;
 String model="Skoda"
 String brand="Octavia"
 boolean automatic=true
 double price=12900.00
 boolean select=false
 String text="fred"; 
 String toString() {
     "[Car details => brand: '${brand}', model: '${model}', #doors: '${numberOfDoors}', automatic: '${automatic}', price: '${price}']"
 }
}
//----------------------------------------------
// see: http://stackoverflow.com/questions/766956/how-do-i-create-a-right-click-context-menu-in-java-swing
// myChild.setInheritsPopupMenu(true); <-- needed somewhere, also popup trigger choice not shown
public class PopUpDemo extends JPopupMenu {
    JMenuItem anItem;
    public PopUpDemo(){
        super("hi kids");
        def anItem = new JMenuItem("Item A");    //action(name:"Menu Item a", closure:{ println("clicked on the new menu item!") }) );
        add(anItem);
        def bItem = new JMenuItem("Menu Item b");
        add(bItem);
        addSeparator()
        def cItem = new JMenuItem("Menu Item c");
        add(cItem);
    }
} // end of popup
//------------------------------------------------
// mouse listener
class PopClickListener extends MouseAdapter {
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUpDemo menu = new PopUpDemo();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
} // end of class


//===============================================
class SwingTest {

    def model = new Car();
    def swing = new SwingBuilder()
    def frame
    Support su = new Support();

// pull up all prior edited files
def getList()
{
	
    def logEntries = [];
    def map = su.process();
    map.each{k,me->
	println "me="+me.filename.toString()
	def v1 = me.time+" "+me.filename
	logEntries+=v1;
    }

    logEntries.sort();
    return logEntries.reverse();
    //return logEntries;
} // end of keeper
    
    static void main(args) {
        def demo = new SwingTest()
        demo.run()
    }

    void run() {
        def frame = swing.frame(
            title:'This is a Frame',
            location:[100,100],
            size:[800,400],
            defaultCloseOperation:javax.swing.WindowConstants.EXIT_ON_CLOSE) {

            menuBar {
                menu(text:'File') {
                    menuItem() {
                        action(name:'New', closure:{ println("clicked on the new menu item!") })
                    }
                    menuItem() {
                        action(name:'Open', closure:{ println("clicked on the open menu item!") })
                    }
                    separator()
                    menuItem() {
                        action(name:'Save', enabled:false, closure:{ println("clicked on the Save menu item!") })
                    }
                }
                menu(text:'Demos') {
                    menuItem() {
                        action(name:'Simple TableModel Demo', closure:{ showGroovyTableDemo() })
                    }
                    menuItem() {
                        action(name:'MVC Demo', closure:{ showMVCDemo() })
                    }
                    menuItem() {
                        action(name:'TableLayout Demo', closure:{ showTableLayoutDemo() })
                    }
                }
                menu(text:'Help') {
                    menuItem() {
                        action(name:'About', closure:{ showAbout() })
                    }
                }
            }
            splitPane {
                panel(id:'p1',border:BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), 'titled border')) {
                    p1.setComponentPopupMenu(new PopUpDemo());                    
                    borderLayout()
                    vbox(constraints:NORTH) {
                    
                        panel {
                            flowLayout()
                            vbox 
                            {
                                comboBox(id: 'cb', items: getList(), selectedItem: bind(target: model, targetProperty: 'select'))

                                textField(columns:10,text: bind(target: model, targetProperty: 'text'),
                                            enabled: bind(source: cb, sourceEvent: 'itemStateChanged', sourceValue: 
                                            {
                                                model.select == 'enable'
                                            }
                                        )
                                    )
                            }// end of vbox                    
                        } // end of panel

        cb.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                System.out.println(e.getItem() + " " + e.getStateChange() );
            }
        });
                        
                        panel {
                            borderLayout()
                            label(text:'Name', constraints:WEST, toolTipText:'This is the name field')
                            textField(text:'James', constraints:CENTER, toolTipText:'Enter the name into this field')
                        }
                        panel {
                            borderLayout()
                            label(text:'Location', constraints:WEST, toolTipText:'This is the location field')
                            comboBox(items:['Atlanta', 'London', 'New York'], constraints:CENTER, toolTipText:'Choose the location into this field')
                        }
                        button(text:'Click My Button', actionPerformed:{event -> println("closure fired with event: " + event) })
                    }
                    scrollPane(constraints:CENTER, border:BorderFactory.createRaisedBevelBorder()) 
                    {
                        textArea(id:'tf1',text:'Some text goes here', toolTipText:'This is a large text area to type in text')
                        //tf1.addMouseListener(new PopClickListener());    
                    }
                }
            }
        }

        frame.show()
    }
    
    void showAbout() {
        // this version doesn't auto-size & position the dialog
        /*
        def dialog = swing.dialog(owner:frame, title:'About GroovySwing') {
            optionPane(message:'Welcome to the wonderful world of GroovySwing')
        }
        */
         def pane = swing.optionPane(message:'Welcome to the wonderful world of GroovySwing')
         def dialog = pane.createDialog(frame, 'About GroovySwing')
         dialog.show()
    }
    
    void showGroovyTableDemo() {
    }

    void showMVCDemo() {
    }

    void showTableLayoutDemo() {
    }
}    

        def demo = new SwingTest()
        demo.run()
println "--- the end ---"