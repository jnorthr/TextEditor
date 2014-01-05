// see: http://groovy.codehaus.org/ConfigSlurper
//  or http://mrhaki.blogspot.fr/2009/08/grassroots-groovy-configuration-with.html

// Had to write as a class as configSlurper won't work as script
// ConfigSlurper is a utility class within Groovy for writing properties file like scripts for performing configuration. 
// Unlike regular Java properties files
// ConfigSlurper scripts support native Java types and are structured like a tree.
// Below is an example of how you could configure Log4j with a ConfigSlurper script:
import groovy.util.ConfigSlurper;
//import groovy.util.logging.*
import java.net.*;
import java.util.*;
import groovy.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.awt.*
//import groovy.util.logging.Log4j
import groovy.util.logging.Log
//import org.apache.log4j.Level
@Log
public class ConfigTool
{

    def say = System.out.&println
    def ef = new File('resources/environment.properties');
    def e = ef.toURI().toURL();
    def uu = new File('resources/log.properties')
    def u = uu.toURI().toURL();
    def conf   // new ConfigSlurper
    def config // parsed config content
    def configname // name of currently open config pointed to by conf
	
/* log.properties
see: http://blog.andresteingress.com/2013/10/24/groovy-quick-tip-the-groovy-util-logging-package/
log4j.appender.stdout = "org.apache.log4j.ConsoleAppender"
log4j.appender."stdout.layout"="org.apache.log4j.PatternLayout"
log4j.rootLogger="error,stdout"
log4j.logger.org.springframework="info,stdout"
log4j.additivity.org.springframework=false
    	log.info "running ConfigTool() default constructor"

//new File('/Volumes/FHD-XS/TextEditor/TextEditor/editordata/log.properties').toURL()
*/

    public ConfigTool()
    { 
        // To load this into a readable config you can do:
        conf = new ConfigSlurper()
        config = conf.parse(u);
	configname = uu.canonicalPath
        println "configname="+configname
        String backColor = config.colors.background;
        Dimension size = new Dimension(config.window.width as int, config.window.height as int);
        say "backColor set to "+backColor;

        assert "info,stdout" == config.log4j.logger.org.springframework
        assert false == config.log4j.additivity.org.springframework
    } // end of constructor
    

    // place content from external property into config variable
    // flag=true if string has name of url/uri
    public ConfigTool(String nm, boolean flag)
    {
        // To load this into a readable config you can do:
        conf = new ConfigSlurper()
	def nmh = new File(nm)
	configname = nmh.canonicalPath
        println "configname="+configname
	def nmf = nmh.toURI().toURL();
        config = conf.parse(nmf);
    } // end of constructor
    

/*
Special "environments" Configuration

The ConfigSlurper class has a special constructor other than the default constructor that takes an "environment" parameter. 
This special constructor works in concert with a property setting called environments. This allows a default setting to 
exist in the property file that can be over-written by a setting in a specific environment closure. This allows multiple 
related configurations to be stored in the same file. Note: the environments closure is not directly parsable. Without 
using the special environment constructor the closure is ignored.
*/
    public ConfigTool(String env)  // "development"
    { 
        // To load this into a readable config you can do:
        conf = new ConfigSlurper(env)
        config = conf.parse(e);
	configname = ef.canonicalPath
        println "configname="+configname

        assert config.sample.foo == "dev_foo"
        assert config.sample.bar == "default_bar"
        
        conf = new ConfigSlurper("test")
        config = conf.parse(e);
        assert config.sample.foo == "default_foo";
        assert config.sample.bar == "test_bar"      
    } // end of constructor 

    /*
        You can merge ConfigSlurper configs to make a single config object. For example:    
    */
    def mergeConfigs()
    {
        def config1 = new ConfigSlurper().parse(u)
        def config2 = new ConfigSlurper().parse(e)
        config1 = config1.merge(config2)    
	configname = uu.canonicalPath
        println "configname="+configname
    } // end of merge


    // put a new set of properties into the config using Map as a parm
    def setConfigProperty(String na, String stuffmap)
    {
	config."$na" = stuffmap
    } // end of put    
    

    // put a new set of properties into the config using Map as a parm
    def setConfigProperty(Map stuffmap)
    {
	config << ['key':'value','font.size':71.4]
	config.putAll( ['key3':'value3','colors.tall':7])
    } // end of put    
    
    /*
        Converting to and from Java properties files
        You can convert ConfigSlurper configs to and from Java properties files. For example:    
    */
    def getJavaProp()
    {
    	try
    	{
            java.util.Properties props = new Properties(); // load from somewhere
            props.load(new FileInputStream("config.properties")); // assumes project root folder

	    // load a properties file from class path, inside static method
    	    // props.load(App.class.getClassLoader().getResourceAsStream("config.properties");));
        
	    // For non-static methods, use this :
    	    // props.load(getClass().getClassLoader().getResourceAsStream("config.properties");));
                
	    //set the properties value
    	    props.setProperty("database", "localhost");
            props.setProperty("dbuser", "jnorthr");
            props.setProperty("dbpassword", "password");
 
            //save properties to project root folder
    	    props.store(new FileOutputStream("config.properties"), null);
        
	    def config2 = new ConfigSlurper().parse(props)
    	    props2 = config2.toProperties();
	    def nh = new File("config.properties")
	    configname = nh.canonicalPath
            println "java props configname="+configname
        } catch(Exception x) {}
    } // end of method
    
    
    /*
        You can write ConfigSlurper configs to files. For example:    "resources/fred.properties"
    */
    def newConfig(String newconfig)
    {
	def nh = new File(newconfig); 
	if (nh.exists())
	{
		println "this property file already exists:"+newconfig;
	        configname = nh.canonicalPath
                println "newConfig exists configname="+configname
		return true
	} // end of if

	def payload =""

	try
	{
	        conf = new ConfigSlurper()
        	config = conf.parse(payload);
        	new File(newconfig).withWriter { writer ->
             		config.writeTo(writer)
        	} // end of write

                nh = new File(newconfig);
	        configname = nh.canonicalPath
                println "newConfig made configname="+configname
		return true
	} 
 	catch (IOException iox) 
	{ conf=null; config=null; return false; }
    } // end of method


    /*
        You can write ConfigSlurper configs to files. 
	For example:    "resources/fred.properties"
    */
    def writeConfig(config, String newconfig)
    {
        //def config = new ConfigSlurper().parse(u)
        new File(newconfig).withWriter { writer ->
             config.writeTo(writer)
        } // end of write

        def nh = new File(newconfig);
	configname = nh.canonicalPath
        println "writeConfig to configname="+configname
    } // end of method


    // this method writes a different config to the same file we opened 
    def rewriteConfig(config)
    {
	writeConfig(config, configname);
        println "rewriteConfig to configname="+configname
    } // end of method

    // this method writes the same config to the same file we opened 
    def rewriteConfig()
    {
	writeConfig(config, configname);
        println "rewriteConfig to configname="+configname
    } // end of method


    // pull in a java property file
    def loader()
    {
    	// P.S If file path is not specified, then this properties file will be stored in your project root folder.
    	Properties prop = new Properties();
 
	try 
    	{
            //load a properties file
            prop.load(new FileInputStream("config.properties"));
	    def nh = new File("config.properties")
	    configname = nh.canonicalPath
            println "java props configname="+configname
 
	    //get the property value and print it out
            System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));
       	    //For non-static method, use this :
       	    //prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"););
	} 
     	catch (IOException ex) 
        {
            ex.printStackTrace();
        }

    } // end of loader
    

    // convenience method
    public void set(String k, def v)
    {
	setConfigProperty(k,v);
    } // end of method

    // convenience method
    public get(String k)
    {
	return config."$k";
    } // end of method


    // test wrapper
    public static void main(String[] args)
    {
        println "-- starting ConfigTool() ---"
            
        println "-- load config ---"    
        def ct = new ConfigTool();

	println "-- get Java Property file --"
	ct.getJavaProp();

        println "-- add font ---"    
        def stuffmap = 'courrier'
        ct.set('font.face',stuffmap)

        println "-- add color ---"    
        ct.set('colors.red','#ff0000')

	println "   color now="+ct.get('colors.red');
        
        println "-- add fred --"    
        def fn = "resources/fred.properties"
        ct = new ConfigTool(fn,true);
        println "-- set fred to blue --"

        // may blitz ant pre-existing property of same name    
        ct.setConfigProperty('colors.red','#ff0000')
        println "-- write config to ${fn} ---"    
        ct.writeConfig(ct.config, fn)        

        println "-- end of default testing --"

        println "-- do it again testing --"
        ct = new ConfigTool(fn,true);
        println "-- add fred ---"    
        ct.set('fred','Mertz')

        println "-- set fred to blue --"
        // may blitz ant pre-existing property of same name    
        ct.setConfigProperty('textcolor','#000000')

	println "   fred now="+ct.get('fred');

        println "-- write config to ${fn} ---"    
        ct.writeConfig(ct.config, fn)        
	println "   fred now="+ct.get('fred');

        println "-- load config for 'development' ---"    
        ct = new ConfigTool("development");

        println "-- loaded config for 'development' ---"    
	println "   fred now="+ct.get('fred');


        fn = "resources/freetext.properties"
        ct = new ConfigTool(fn,true);
        ct.set('font.face','courier')
	println "   font.face now="+ct.get('font.face');
        ct.writeConfig(ct.config, fn)        
	
        fn = "resources/sometext.properties"
	if ( ct.newConfig(fn) )
	{
	        ct.set('name','value 1')
	        ct.rewriteConfig(ct.config)        		
	        ct.set('environment', "{'name':'bloggs'}" )
	        ct.rewriteConfig()        		
	} // end of if

        println "-- the end ---"    
    } // end of method
    
} // end of class