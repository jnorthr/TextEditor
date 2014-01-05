<% include '/WEB-INF/includes/header.gtpl?title=Platform as a Service' %>
<script  type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script  type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script  type="text/javascript">
var angle = 0;
setInterval(function(){
      angle+=2;
      \$("#img1").rotate(angle);
},150);
</script>

<script type="text/javascript" src="http://jqueryrotate.googlecode.com/svn/trunk/jQueryRotate.js"></script>

<h1>Platform as a Service - PaaS</h1>
<div id="sidebox" class="roundPinkBorder">
    <table>
        <tr>
            <td><a href="/" alt="Home"><img id="img1" src="/images/caelyf-logo.png" alt="Caelyf - a lightweight Groovy toolkit for Cloud Foundry"></a></td>
        </tr>
    </table>
</div>

<h1>PaaS</h1>

<p>In the cloud era, an application platform can be delivered as a service, often described as Platform as a Service (<a href="http://en.wikipedia.org/wiki/Platform_as_a_service">PaaS</a>). <a href="http://en.wikipedia.org/wiki/Platform_as_a_service">PaaS</a> makes it much easier to deploy, run and scale applications. Often, on someone else's hardware. Sometimes for free, sometimes for a fee.</p>

<p>Not all PaaS offerings are created equal. Some have limited language and framework support, do not deliver key application services needed for cloud applications, and/or restrict deployment to a single cloud. By using an open PaaS, we have a choice of cloud service providers for deployment, frameworks, development and application services to run an application. And if it's an open source project or platform, there's a community contributing and supporting these platforms. One such open and free platform is <a href="http://en.wikipedia.org/wiki/Cloud_Foundry">Cloud Foundry</a>.</p>


<h2>Caelyf on PaaS</h2>

<p>We wanted to make <b>Caelyf</b> capable of working on many PaaS providers. To that end, we've done a rigerous set of tests for several known providers. The choices for you to use <b>Caelyf</b> are shown in the table below. We have plans to add to this list too.</p>

<p>To prove "we eat our own dogfood", here are links to a variety of providers that we know will support <b>Caelyf</b>.</p>

<h2>Providers</h2>
<table>
	<tr>
	<th>Provider</th>
	<th><b>Caelyf</b> Website</th>
	<th>Sample Template</th>
	<th>Target Address</th>
	<th>Notes</th>
	</tr>

    <tr>
    <td><a href="http://www.anynines.com/"><img src="/images/anynines.png" /></a></td>
    <td><a href="http://caelyf.de.a9sapp.eu/" class="none tiny">http://caelyf.de.a9sapp.eu/</a></td>
    <td><a href="http://caelyftemplate.de.a9sapp.eu/" class="none tiny">http://caelyftemplate.de.a9sapp.eu/</a></td>
    <td>cf target https://api.de.a9s.eu</td>
    <td>1, 2, 5, 10, 13</td>
    </tr>
 

    <tr>
    <td><a href="https://www.appfog.com/"><img src="/images/appfog.png" /></a></td>
    <td><a href="http://caelyf.aws.af.cm/" class="none tiny">http://caelyf.aws.af.cm/</a></td>
    <td><a href="http://caelyftemplate.eu01.aws.af.cm/" class="none tiny">http://caelyftemplate.eu01.aws.af.cm/<br />(Europe)</a></td>
    <td>af target https://api.appfog.com</td>
    <td>1, 6, 10, 14<img src="/images/aws.png" align="right"/></td>
    </tr>
 
    <tr>
    <td><a href="https://www.appfog.com/"><img src="/images/appfog.png" /></a></td>
    <td><a href="http://mycaelyf.hp.af.cm/" class="none tiny">http://mycaelyf.hp.af.cm/</a></td>
    <td><a href="http://caelyftemplates.hp.af.cm/" class="none tiny">http://caelyftemplates.hp.af.cm/</a></td>
    <td>af target https://api.appfog.com</td>
    <td>1, 6, 10, 14 <img src="/images/hp.png"  align="right"/></td>
    </tr>

    <tr>
    <td><a href="http://www.cloudbees.com/#slide-1"><img src="/images/cloudbees.png" /></a></td>
    <td><a href="http://caelyf.jnorthr.cloudbees.net/" class="none tiny">http://caelyf.jnorthr.cloudbees.net/</a></td>
    <td><a href="http://caelyftemplate.jnorthr.cloudbees.net/" class="none tiny">http://caelyftemplate.jnorthr.cloudbees.net/</a></td>
    <td>bees target is self-declared</td>
    <td>1, 7, 8, 11, 15</td>
    </tr>    

    <tr>
    <td><a href="http://www.gopivotal.com/pivotal-products/pivotal-application-cloud-fabric/pivotal-cloud-foundry"><img src="/images/pivotal.png" /></a></td>
    <td><a href="http://caelyf.cfapps.io/" class="none tiny">http://caelyf.cfapps.io/</a></td>
    <td><a href="http://caelyftemplate.cfapps.io/" class="none tiny">http://caelyftemplate.cfapps.io/</a></td>
    <td>cf target https://api.run.pivotal.io</td>
    <td>1, 10, 13</td>
    </tr>
    
    <tr>
    <td><a href="http://www.activestate.com/stackato/"><img src="/images/stackato.png" /></a></td>
    <td><a href="http://caelyf.stacka.to/" class="none">http://caelyf.stacka.to/</a></td>
    <td><a href="http://caelyftemplate.stacka.to/" class="none">http://caelyftemplate.stacka.to/</a></td>
    <td>stackato target https://api.stacka.to/</td>
    <td>16, 4, 9, 10, 12</td>
    </tr>
         
</table>

<div>
<ol>
<li>1. Trial period available</li>
<li>2. Some issues with SSL</li>
<li>3. MEMCACHE feature available</li>
<li>4. GPars Parallel and Asynch functions fail to execute; use menu HOME button to restart after 404/500 errors</li>
<li>5. <a href="http://blog.anynines.com/">Anynines Blog</a></li>
<li>6. <a href="http://blog.appfog.com/getting-started-with-appfogs-command-line/">AppFog Getting Started</a></li>
<li>7. <a href="http://wiki.cloudbees.com/bin/view/RUN/BeesSDK#HApplicationCommands">bees commands</a></li>
<li>8. <a href="http://caelyf.caelyf.eu.cloudbees.net/">test EU version in progress</a></li>
<li>9. <a href="http://www.activestate.com/stackato/resources">Stackato Resources</a></li>
<li>10. Uses VCAP_SERVICES environmental variable</li>
<li>11. Uses no environmental variables to access services</li>
<li>12. Uses STACKATO_SERVICES environmental variable and STACKATO client tool </li>
<li>13. CF client tool for app management on target</li>
<li>14. AF client tool for app management on target</li>
<li>15. BEES client tool for app management on target</li>
<li>16. Trial period expired</li>
</ol>
</div>


<% include '/WEB-INF/includes/footer.gtpl' %>
