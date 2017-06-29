import groovy.swing.SwingBuilder  
import groovy.beans.Bindable  
import static javax.swing.JFrame.EXIT_ON_CLOSE  
import java.awt.*
 
@Bindable
class Address {  
    String street, number, city, state
    String toString() { "address[street=$street,number=$number,city=$city,state=$state]" }
}
  
def address = new Address(street: 'Evergreen Terrace', number: '742', city: 'Springfield',state:'Illinois')
  
def swingBuilder = new SwingBuilder()
swingBuilder.edt {  // edt method makes sure UI is build on Event Dispatch Thread.
    lookAndFeel 'nimbus'  // Simple change in look and feel.
    frame(title: 'Address', size: [350, 280], minimumSize: [330, 248], 
            show: true, locationRelativeTo: null, 
            defaultCloseOperation: EXIT_ON_CLOSE) { 
        borderLayout(vgap: 10)
        
        panel(constraints: BorderLayout.CENTER, minimumSize:[300, 180], 
                border: compoundBorder([emptyBorder(10), titledBorder('Enter your address:')])) {
            tableLayout {
                tr {
                    td {
                        label 'Street:'  // text property is default, so it is implicit.
                    }
                    td {
                        textField address.street, id: 'streetField', columns: 20
                    }
                }
                tr {
                    td {
                        label 'Number:'
                    }
                    td {
                        textField id: 'numberField', columns: 5, text: address.number
                    }
                }
                tr {
                    td {
                        label 'City:'
                    }
                    td {
                        textField id: 'cityField', columns: 20, address.city
                    }
                }

                tr {
                    td {
                        label 'State:'
                    }
                    td {
                        textField id: 'stateField', columns: 20, address.state
                    }
                } // end of tr
                
                
                

            }
            
        }
        
        panel(constraints: BorderLayout.SOUTH,maximumSize:[250, 50],background:Color.YELLOW ) {
        	hbox{
            button text: 'O k', actionPerformed: { println 'ok'; 
				JFontChooser fontChooser = new JFontChooser();
				int result = fontChooser.showDialog(null);
				if (result == JFontChooser.OK_OPTION)
				{
					Font font = fontChooser.getSelectedFont(); 
					System.out.println("Selected Font : " + font); 
				} // end o fif            
            }
            
            button text: 'Save', actionPerformed: { println address; }
            button text: 'Exit', actionPerformed: { System.exit(0); }
            } // end of hbox
        }
        
        // Binding of textfield's to address object.
        bean address, 
            street: bind { streetField.text }, 
            number: bind { numberField.text }, 
            city: bind { cityField.text },
            state: bind { stateField.text }
    }  
}
