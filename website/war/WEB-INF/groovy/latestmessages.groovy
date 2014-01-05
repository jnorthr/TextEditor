import java.text.SimpleDateFormat as SDF
import org.ocpsoft.pretty.time.PrettyTime
import org.xml.sax.SAXParseException

//def url = "http://groups.google.com/group/caelyf/feed/rss_v2_0_topics.xml".toURL()
def url = "https://groups.google.com/forum/feed/caelyf/topics/rss_v2_0.xml".toURL();
try {
    def slurper = new XmlSlurper()
    def tx = url.getText(requestProperties:['User-agent':'Firefox/2.0.0.4']);  // may need this later: 'UTF-8', connectTimeout: 500, readTimeout:2000)
    def result = slurper.parseText(tx)
    def sdf = new SDF('EEE, dd MMM yyyy HH:mm:ss z', Locale.US)

    html.ul			//div(id:"accordion") 
    {
        result.channel.item.each { item ->
            def prettyTime = new PrettyTime().format(sdf.parse(item.pubDate.text().replace('UT', 'GMT')))
            def who = item.author.text()
            who =  who.replace('\n',' ').trim()

            //h3 item.title.text()         
            li { 			
            	a href: item.link.text(), item.title.text()
            	i "posted ${prettyTime}"                
				i " by ${who}"
            } // end of div
        } // end of each
    } // end of html.ul
    
} // end of try

catch (SAXParseException spe) 
{
    def msg = spe.message;
    html.div {
        span "A problem occurred while parsing the Google Group RSS feed, please go directly to the"
        a href: "http://groups.google.com/group/caelyf", "Caelyf Google Group"
        span " instead. Or try again later."
        span "  Failure:${msg}"
    }
} 
catch (IOException ioe) 
{
    def msg2 = ioe.message;
    html.div {
        span "The Google Group feed could not be fetched, please go directly to the"
        a href: "http://groups.google.com/group/caelyf", "Caelyf Google Group"
        span " instead. Or try again later."
        span " Failure:${msg2}"
    }
}
catch (Exception x) 
{
	    def msg3 = x.message;
	    html.div 
	    {
	        span "A major failure happened in latestmessages.groovy, please go directly to the"
	        a href: "http://groups.google.com/group/caelyf", "Caelyf Google Group"
	        span " instead. Or try again later."
	        span " Failure:${msg3}"
	    }
}
