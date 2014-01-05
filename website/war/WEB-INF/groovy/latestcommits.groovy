import java.text.SimpleDateFormat as SDF
import org.ocpsoft.pretty.time.PrettyTime
import groovy.json.JsonSlurper

try{
def url = "https://api.github.com/repos/caelyf/caelyf/commits".toURL()
def slurper = new JsonSlurper()
def tx = url.getText('UTF-8', useCaches: false, requestProperties: ['User-Agent': 'Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1']);

def results = slurper.parseText(tx)
def sdf = new SDF("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)

html.ul {
	  results.each { result ->	
		li {
			a href: "https://github.com/caelyf/caelyf/commits/${result.sha}", result.commit.message			
			def time = result.commit.committer.date.replaceAll(/(-|\+)(\d\d):(\d\d)/, '$1$2$3')					
			def prettyTime = new PrettyTime().format(sdf.parse(time))
			i "committed ${prettyTime}"
            i "by ${result.commit.author.name}"
		}
	} // end of each
		
  } // end of html.ul
} // end of try
	
catch (Exception x) 
{
	    def msg3 = x.message;
    	log.info msg3;	
	    html.div 
	    {
	        span "A major failure happened in latestcommits.groovy, please go directly to the"
	        a href: "http://groups.google.com/group/caelyf", "Caelyf Google Group"
	        span " instead. Or try again later."
	        span " Failure:${msg3}"
	    }
} // end of catch
