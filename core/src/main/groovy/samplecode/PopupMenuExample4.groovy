import groovy.swing.SwingBuilder
import groovy.swing.*
new groovy.swing.SwingBuilder().frame(title:'v1.1',pack:true,show:true)
{
    panel(mousePressed: {e->
        if (e.isPopupTrigger()) 
        {
            popupMenu {
                menuItem{
                    action(name:'Open', closure:{System.out.println('hi kids')})
                }

                menuItem{
                    action(name:'Re-Open', closure:{System.out.println('hi kids')})
                }
                menuItem{
                    action(name:'New', closure:{System.out.println('hi kids')})
                }
                menuItem{
                    action(name:'Exit', closure:{System.out.println('hi kids')})
                }
            }.show(e.getComponent(), e.getX(), e.getY())




        } // end of if
      } // end of mousePressed closure 
    ) // end of method constructor
    
    // now declare main panel content
    {
        label("Right click here to get a popup menu to Exit")
    }
} // end of .SwingBuilder() closure




println "-- the end ---"