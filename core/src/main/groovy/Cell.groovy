import groovy.transform.Canonical
@Canonical
class Cell
{
    String filename = ""
    String time = "";
    int cursorAt = 0;
    boolean decoded = false;
    boolean found = false; // confirmed that file named by filename does still exist
    
    public Cell()
    {
    } // end of constructor
    
    public Cell(txt)
    {
        decoded = split(txt);
    } // end of constructor
    
    boolean match(inp) { (inp.toLowerCase().equals(filename.toLowerCase()) ) } 
    
    //  /Volumes/Media/Users/ww:=55; 2013-11-09 22:28:41CET
    boolean split(txt)
    {
        int i = txt.indexOf(":=");
        boolean flag = ( i >- 1 )
        
        if (flag)
        {        
            int j = txt.substring(i).indexOf(";");
            if (j > -1)
            {
                j += i;
                
                def f = txt.substring(0,i).trim() 
                def c = txt.substring(i+2,j).trim();
                def t = txt.substring(j+1).trim();
                filename = f;
                try{ cursorAt = c as int; } catch(Exception e) {}
                time = t;
                decoded = true;
                found =  new File( filename ).exists();
            } // end of if
            else
            {
                flag = false;
            }
        } // end of if
        
        return flag;
        
    } // end of split
    
} // end of class
