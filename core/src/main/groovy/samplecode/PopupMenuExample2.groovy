import groovy.swing.SwingBuilder

new groovy.swing.SwingBuilder().frame(title:'v1.1',pack:true,show:true){
    panel(mousePressed: {e->
        if (e.isPopupTrigger()) {
            popupMenu {
                menuItem{
                    action(name:'Exit', closure:{System.exit(0)})
                }
            }.show(e.getComponent(), e.getX(), e.getY())
        }
    }){
        label("Right click here to get a popup menu to Exit")
    }
}