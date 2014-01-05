// http://vortex7.wordpress.com/tag/popupmenu/
import groovy.swing.SwingBuilder
import javax.swing.*

def frame
swing = new SwingBuilder()

showAbout = {
    def pane = swing.optionPane(message:'Testing Menus v1.2')
    def dialog = pane.createDialog(frame, 'About Dialog')
    dialog.show()
}

frame = swing.frame(title:'Main Frame',location:[100,100],size:[500,150]){
    menuBar 
    {
        menu(text:'File', mnemonic:'F') 
        { 
            menuItem(action(name:'Exit',  mnemonic:'E', closure:{System.out.println("hi kids");} )) 
            
        }
        separator()
        menu(text:'Help', mnemonic:'H') { menuItem(action(name:'About', mnemonic:'A', closure:{showAbout()} ))
        }
    }
    panel {
        label(text: "A label with a message")
    }
}
frame.show()