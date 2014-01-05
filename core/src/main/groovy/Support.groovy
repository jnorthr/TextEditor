import groovy.util.ConfigSlurper
import java.util.Properties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import  javax.swing.JOptionPane;
import java.text.SimpleDateFormat;

public class Support
{
	def config
	def paths
	def env = [:]
	Properties props
        String logName = getLogName();
	File logfile = new File( logName );

	// non-OS specific parameters for business issues
	String propertyfile = '../editordata/menu.properties'  

	// non-OS specific parameters for business issues
	String pathfile = '../editordata/path.properties'  
        Cell mostRecentlyEdited=new Cell();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ssz");

	boolean audit = false; 
	def say(tx) { if (audit) println tx; }



	def getLogName()
	{
	    	def getProperty = System.&getProperty  
    		def home = getProperty("user.home")
    		return home+"/.TextEditor.log";
	} // end of get

	// class constructor - loads configuration, get system environmental variables;
	public Support()
	{
		// Get all system properties
  		props = System.getProperties()
	       	paths = new ConfigSlurper().parse(new File(pathfile).toURL() )

		// get non-path related static values
	       	config = new ConfigSlurper().parse(new File(propertyfile).toURL())	
       		env = System.getenv()
	} // end of constructor


	// dump content of all map properties
	public void showAll()
	{
		println "System Properties"
		props.each{k,v-> println "key:${k} and value:${v}"}

		println "Config Properties"
		config.each{k,v-> println "key:${k} and value:${v}"}

		println "Path Properties"
		paths.each{k,v-> println "key:${k} and value:${v}"}

		println "Env Properties"
		env.each{k,v-> println "key:${k} and value:${v}"}
	}


	// describe dialog
	public void showDialog(String ti, String tx)
	{
		int messageType = JOptionPane.WARNING_MESSAGE; // no standard icon
		JOptionPane.showMessageDialog(null, "$tx", "${ti}", messageType);
	} // end of showDialog


	// needs more testing to rewrite
	public void writeProperties()
	{
		// make new props file from the 'props'  = System.getProperties()
		File file = new File("../editordata/test2.properties");
		FileOutputStream fileOut = new FileOutputStream(file);
		props.store(fileOut, "Favorite Things"); // adds a title and date to start of text file;			
		fileOut.close();

		// since this call only gets 'development' leg, the output test file has all non-environment props plus
		// only the dev leg; remarks are lost too
                def config = new ConfigSlurper("development").parse(new File(pathfile).toURL())
		new File("../editordata/pathdev.properties").withWriter { writer ->
     			config.writeTo(writer)
		}
                config = new ConfigSlurper().parse(new File(pathfile).toURL())
		new File("../editordata/pathall.properties").withWriter { writer ->
     			config.writeTo(writer)
		}
	} // end of method


	// ---------------------------------
	// pull up all prior edited files; the most recently edited file is last, earliest is first
	// since we're using a map to de-dup, we only keep a single copy of each file that has been
	// edited, and only it's latest date&cursor location. Return a list.
	/*
		--->/Users/jim/core:=21; 2013-11-06 14:59:46CET
		--->/Users/jim/audit2x.txt:=171; 2013-11-06 15:01:14CET
		--->/Users/jim/DerbyClientSession.txt:=410; 2013-11-06 15:10:47CET	
	*/
	public getListOfEditedFiles()
	{
	    def logEntries = [];
    	    def map = process(); // see below
    	    def v1

     	    // walk the map and reproduce a List with time+filename thus making a unique list of all previously edited files
    	    map.each{k,me->
			v1 = me.time+" "+me.filename
			logEntries+=v1;
    	    } // end of each

	    // sort ascending time / filename
	    logEntries.sort();
	    
	    // now make list descending time and return this list, so latest edited file is first
    	    return logEntries.reverse();
	} // end of getListOfEditedFiles()
    


	// get unique map of all files edited before
	def process()
	{
	        say "reading log file "+logName
        	def log

	        if ( logfile.exists() ) 
		{
	        	// read all lines from log file
        		log = logfile.text;
		}
		else
		{
			return [:]
		} // end of else

        	Cell c = new Cell();
	        def logMap = [:]

	        log.eachLine{ln ->
        	    say "\n--->"+ln;     
	            c = new Cell(ln);
            
	            if (c.decoded)
        	    {
                	def flag = (logMap[c.filename.toLowerCase()]) ? true : false;
                	say "c="+c.toString()+" already in logMap ?"+flag;
                	if (!flag)
                	{                
                    		// filename as lowercase key to full line of text
                    		logMap[c.filename.toLowerCase()]= (Cell)c;            
	                } // end of if
                
	                // already in map match dates for which is later
        	        else
                	{
	                    Cell d = logMap[c.filename.toLowerCase()]
        	            say "..... ${c.filename}  c.time="+c.time+"   and d.time="+d.time                
                	    if (c.time > d.time)
                    	    {
	                        logMap[c.filename.toLowerCase()] = (Cell)c;                                
                    	    } // end of if
                    
                	} // end of else
                
	            } // end of if
                
        	} // end of log.each

	        mostRecentlyEdited=new Cell();
        	say "\nlogMap follows:"
        	logMap.each{k,v-> 
            		say "key="+k+"  and value="+v;
            		if ( v.time > mostRecentlyEdited.time )
            		{
		                mostRecentlyEdited = v;
            		}  // end of if
            
        	} // end of log.each
                
	        say "\nThe most recently edited file was "+mostRecentlyEdited.toString();
        	say "\n---- the end ----"
        	return logMap;
	} // end of process
    


	// append name of file just saved to log
   	def keeper(String outName, int at)
   	{
		String formattedDate = formatter.format(new Date());
		logfile.append("${outName}:=${at}; ${formattedDate}"+"\n")
   	} // end of keeper


    
	// test harness for this class
	public static void main(String[] args)
	{	
		println "... started"
		Support su = new Support()
		su.showDialog("This Is A Title", "This is some text.");
		su.showAll()
		su.writeProperties();
		su.keeper("support test",44);
		println "\n----------------\nList of recently edited files"
		def log = su.getListOfEditedFiles();
		println "\nThere are ${log.size()} edited files"
		println "... ended"
	} // end of main

} // end of class



// --------------------------------------------
/*
from: https://bowerstudios.com/node/1066

Create the properties object, and load it from the file system:
Properties props = new Properties()
File propsFile = new File('/usr/local/etc/test.properties')
props.load(propsFile.newDataInputStream())

Take a peek:
println props.getProperty('porcupine')

Write a new random value and persist it to the file system:
Integer rand = new Random().next(4)
props.setProperty('porcupine', rand.toString())
props.store(propsFile.newWriter(), null)

Peek again:
props.load(propsFile.newDataInputStream())
println props.getProperty('porcupine')

//---------------------
// also see: http://groovy.codehaus.org/ConfigSlurper


*/
