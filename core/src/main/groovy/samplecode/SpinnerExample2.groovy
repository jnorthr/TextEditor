import javax.swing.*
import groovy.swing.SwingBuilder
 
class MyModel2 extends SpinnerNumberModel {
    MyModel2(int value, int min, int max, int step) {super(value, min, max, step)}
    Object getNextValue() { if (value == maximum) { return minimum } else { return value + stepSize; }}
    Object getPreviousValue() { if (value == minimum) { return maximum } else { return value - stepSize; }}
}
def swing = new SwingBuilder()
swing.frame(title: 'Cyclic Spinner', defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE,
    size: [200, 60], show: true, locationRelativeTo: null) {
    lookAndFeel('system')
    SpinnerNumberModel sModel = new MyModel2(0, 0, 5, 1)
    spinner1 = spinner(size:[10, 10], model: sModel)
}