// sample code of 3 JOptionPanes to choose prior edited files
import groovy.swing.SwingBuilder

import javax.swing.*

def fi = new File('/Volumes/Media/Users/jim/Desktop/samplemenu.txt');
def lns = fi.text;

def say() {println "================"}
def say(tx) {println tx;}
say()

lns.eachLine{l-> say l;}
say();


// sample add some names to a 'names' list
def names =[]
["able","baker","charlie","delta"].each{n-> names.add(n);}


// construct a typical 'options.txt' file format like: title:=<something>
def fl = ["First:= text 1","Second:= text 2"," Third  :=   more text","2010:=a good year"]


// consume each entry and if it has :=  signature, divide it and pump left title-size of each entry into same 
// 'names' list as above
fl.each{f-> 
def t = f.trim();
int i = t.indexOf(":=");
if (i>-1)
{   f = t.substring(0,i)
    println f;
    names.add(f);
} // end of if

} // end of each


// show all
names.each{n-> println n;}
say()


// since the last entry iun a file is assumed to be the newest, reverse the order of the list
// so that the 'newest' entry appears first
names = names.reverse();
names.each{n-> println n;}
say()


// ok, now read lines from external .txt file like :
/*
/Volumes/Media/groovy/samples/src/main/groovy/hello.groovy:=file99
/Volumes/Media/groovy/samples/src/main/groovy/test.groovy:=file90
*/
// then add them to the sames 'names' list
lns.eachLine{f-> 
def t = f.trim();
int i = t.indexOf(":=");
if (i>-1)
{   f = t.substring(0,i)
    println f;
    names.add(f);
} // end of if

} // end of each

// re-sort names again and prin full list
names = names.reverse();
names.each{n-> println n;}
say()

// construct GUI to show the 3 JOptionPanes
def swingBuilder = new SwingBuilder()
def options = ['1995', '2003', '2007','2010','2013']
// drop-down selector plus OK button
def pane = swingBuilder.optionPane(message:'Groovy starts in ', selectionValues:names, optionType:JOptionPane.OK_CANCEL_OPTION)

def dialog = pane.createDialog(null, 'Create Dialog Header')
dialog.show()
def result = pane.getInputValue()
say()
println =(JOptionPane.UNINITIALIZED_VALUE==result) ? "JOptionPane.UNINITIALIZED_VALUE" : result

say()
if (JOptionPane.CANCEL_OPTION == result)
{
	println "result was JOptionPane.CANCEL_OPTION"
} // end of if

// drop-down selector plus OK and CANCEL button
// close window or CANCEL button returns pane result was:uninitializedValue
pane = swingBuilder.optionPane(message:'Are you sure that Groovy started in ',selectionValues:options, initialSelectionValue:pane.getInputValue(),
messageType:JOptionPane.INFORMATION_MESSAGE, optionType:JOptionPane.CLOSED_OPTION)


dialog = pane.createDialog(null, 'worthless message')
dialog.show()
println =(JOptionPane.UNINITIALIZED_VALUE==result) ? "JOptionPane.UNINITIALIZED_VALUE" : result
println "pane result was:"+pane.getInputValue()
say()

options.add('fred\n')
options.add('ted\n')


say()
def choice =null // answer comes out here or uninitializedValue

// shows each choice as a separate button across page
// answer from prior dialog becomes the high-lighted default button unless prior choice was NOT in this set of choices, then first item is semi-highlighted
// no OK or any other buttons
// close window or CANCEL button returns pane result was:uninitializedValue
def lastPane = swingBuilder.optionPane()
choice = lastPane.showOptionDialog( 
    null,
    'Confirm that Groovy starts in ',
    'Header Message',
    JOptionPane.YES_NO_CANCEL_OPTION,
    JOptionPane.WARNING_MESSAGE,    // symbol to show
    null,
    options as Object[],  // choice of buttons
    pane.getInputValue()  // initially high-lighted button
)

say()
println options[choice]pwd

say()
    int result2 = JOptionPane.showConfirmDialog( null, "Show Confirm Dialog", "alert", JOptionPane.OK_CANCEL_OPTION);
    println "result2:"+ result2;
if (JOptionPane.CANCEL_OPTION == result2)
{
	println "result2 was JOptionPane.CANCEL_OPTION:"+result2
} // end of if
if (JOptionPane.OK_OPTION == result2)
{
	println "result2 was JOptionPane.OK_OPTION"+result2
} // end of if
