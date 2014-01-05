import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class JumbleTheme extends DefaultMetalTheme
{
    public String getName() { return "Jumble"; }
    
          // blue shades
    private final ColorUIResource primary1     = 
           new ColorUIResource(0xFF, 0x3C, 0x1F);
    private final ColorUIResource primary2     = 
           new ColorUIResource(0xEE, 0x3D, 0x20);
    private final ColorUIResource primary3     = 
           new ColorUIResource(0xDD, 0x3E, 0x21); 
    
    private final ColorUIResource secondary1   = 
           new ColorUIResource(0xC0, 0x00, 0x80);
    private final ColorUIResource secondary2   = 
            new ColorUIResource(0xFF, 0xFF, 0xFF);
    private final ColorUIResource secondary3   = 
           new ColorUIResource(0x00, 0x00, 0x00);
    
          // the functions overridden from the base 
          // class => DefaultMetalTheme
    
    protected ColorUIResource getPrimary1() { return primary1; }  
    protected ColorUIResource getPrimary2() { return primary2; } 
    protected ColorUIResource getPrimary3() { return primary3; } 
    
    protected ColorUIResource getSecondary1() { return secondary1; }
    protected ColorUIResource getSecondary2() { return secondary2; }
    protected ColorUIResource getSecondary3() { return secondary3; }
}