import java.text.SimpleDateFormat as SDF
import org.ocpsoft.pretty.time.PrettyTime
import groovy.json.JsonSlurper

try{
def content = "https://api.github.com/repos/caelyf/caelyf/issues".toURL().text

def struct = new JsonSlurper().parseText(content)

//def sdf = new SDF("yyyy/MM/dd HH:mm:ss Z", Locale.US)
def sdf = new SDF("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)

html.ul {
	struct.each { entry ->
		li {
			a href: entry.html_url, entry.title
        	def time = entry.created_at.replaceAll(/(-|\+)(\d\d):(\d\d)/, '$1$2$3')
		    def upt = entry.updated_at.replaceAll(/(-|\+)(\d\d):(\d\d)/, '$1$2$3')
		    def prettyTime = new PrettyTime().format(sdf.parse(time))
		    def prettyTime2 = new PrettyTime().format(sdf.parse(upt))
            i " opened ${prettyTime}"
            i " by ${entry.user.login}"
            i " ( recent update ${prettyTime2} )"    
		}
	  } // end of each
	} // end of html.ul
	
}	 // end of try 

	catch (Exception x) 
	{
		    def msg3 = x.message;
	    	log.info msg3;	
		    html.div 
		    {
		        span "A major failure happened in latestissues.groovy, please go directly to the"
		        a href: "http://groups.google.com/group/caelyf", "Caelyf Google Group"
		        span " instead. Or try again later."
		        span " Failure:${msg3}"
		    }
	} // end of catch

