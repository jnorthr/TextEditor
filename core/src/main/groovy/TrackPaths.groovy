// see: http://docs.codehaus.org/display/GROOVY/JN2015-Files for details
import groovy.transform.*
// =======

/*
//import groovy.util.logging.Log4j2
//@Log4j2

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

//import groovy.util.logging.Slf4j

@Slf4j  
public class TrackPaths
{
    def fs = File.separator;
    boolean isDir = false
    boolean isFile = false
    boolean present = false;
    String filename ="";
=======
//import groovy.util.logging.Slf4j
@Slf4j  
--- */

public class TrackPaths
{
    def fs = File.separator; // platform-specific file path separator
    boolean isDir = false // set when then the string points to a directory folder not a file
    boolean isFile = false  // set when the filename string points to a data holding file
    boolean present = false; // set if filename string points to a file or folder that actually exists
    String filename =""; // the file of interest

    
    // folder or directory name only without filename also no trailing file separator
    // as: path=/Volumes/PENDRIVE1/caelyf-v1-git/caelyf from:/Volumes/PENDRIVE1/caelyf-v1-git/caelyf/notes.txt
    String path = "";

    // same as path but with trailing separator:canonical path=/Volumes/PENDRIVE1/caelyf-v1-git/caelyf/ 
    String canonicalPath = "";
    
    def say = System.out.&println
    
    public TrackPaths(String fnin)
    {
        String fn = fnin.trim();
        this.filename = fn;
        say "-->"+fn
        assert fn.size() > 0
        
        def fnf =new File(fn)
        present = fnf.exists()
        if (present)
        {
            isDir = fnf.isDirectory()
            isFile = fnf.isFile()
            canonicalPath = fnf.getCanonicalPath()
        } // end of if
        
        say fn+" does it exist ? "+present
        say "is it a folder ? "+isDir
        say "is it a file ? "+isFile
        
        if (isDir)
        {
            say "canonicalPath="+canonicalPath
            path = canonicalPath;
            say " path="+path
            if (!canonicalPath.endsWith(fs)) { canonicalPath += fs; }
        }
            
        if (isFile)
        {
            say "canonicalPath="+canonicalPath
            int i = canonicalPath.lastIndexOf(fs);
            path = canonicalPath.substring(0,i)
            say "i="+i+" path="+path
            canonicalPath=path
            if (!canonicalPath.endsWith(fs)) { canonicalPath += fs; }
        } // end of if       
        
        say "path="+path+"\ncanonical path="+canonicalPath
        
    } // end of constructor



    public static void main(String[] args)
    {
        def fn ="/Volumes/PENDRIVE1/caelyf-v1-git/caelyf/notes.txt"
        def tp = new TrackPaths(fn)

        println " " 
        tp =  new TrackPaths("/Volumes/FHD-XS/FreeText/TextEditor/core")
        assert true == tp.isDir
        assert false == tp.isFile
        assert "/Volumes/FHD-XS/FreeText/TextEditor/core/" == tp.canonicalPath;
        assert "/Volumes/FHD-XS/FreeText/TextEditor/core" == tp.path;
        
        println " " 
        tp =  new TrackPaths("/Volumes/FHD-XS/FreeText/TextEditor/core/")
        assert true == tp.isDir
        assert false == tp.isFile
        assert "/Volumes/FHD-XS/FreeText/TextEditor/core/" == tp.canonicalPath;
        assert "/Volumes/FHD-XS/FreeText/TextEditor/core" == tp.path;
        

        println " " 
        tp =  new TrackPaths("./src/main/groovy/Cell.groovy")

        println " " 
        tp =  new TrackPaths("../README.txt")


        println "\ntry a non-existent file " 
        tp =  new TrackPaths("../DoesNotExists.txt")

    } // end of main

} // end of class