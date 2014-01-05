<% include '/WEB-INF/includes/header.gtpl?title=Roadmap' %>
<h1>Road Map</h1>
<div id="sidebox" class="roundPinkBorder">
    <table>
        <tr>
            <td><img src="/images/caelyf.png"></td>
            <td><a href="#">Version 1.0 - Complete</a></td>
        </tr>
        <tr>
            <td><img src="/images/incubating.png"></td>
            <td><a href="#">Version 1.1 - Incubating</a></td>
        </tr>
        <tr>
            <td><img src="/images/proposed.png"></td>
            <td><a href="#">Version 1.2 - Proposed</a></td>
        </tr>
    </table></div>

<p>Here are some ideas we have to make Caelyf even more useful.</p>

<h2>Version 1.0</h2>

<h3>Complete</h3>
<ul>
    <li>Update versions of all .jars - <span class="done">complete</span></li>
    <li>Add more documentation - <span class="done">complete</span></li>
    <li>Add README, github pages and wiki - <span class="done">complete</span></li>
    <li>Change references to obsolete VMC deployment tool in favour of the CF tool - <span class="done">complete</span></li>
    <li>Update all references within Caelyf to version 1.0 - <span class="done">complete</span></li>
</ul>
 
<h2>Version 1.1</h2>

<h3>Incubating</h3>
<ul>
    <li>Adopt @Annotations for Logs and Log frameworks</li>
    <li>Update PDF and single page contents to reference Cloud Foundry V2.0 - <span class="done">done</span></li>
    <li>Identify new home for caelyf jar, templates and web site - <span class="done">done</span></li>
    <li>Modify cache processing to abstract from persistence layer - <span class="done">in progress</span></li>
    <li>Include GPARS 1.0 parallel and asynch examples in Caelyf sample template - <span class="done">complete</span></li>
    <li>Provision Caelyf on <a href="http://www.cloudfoundry.com/">Cloud Foundry from Pivotal</a> (uses redis service) - <span class="done">complete - <a href="http://caelyf.cfapps.io/">view here</a></span></li>
    <li>Provision Caelyf on <a href="http://www.anynines.com/">Anynines.com - an EU only PaaS</a> - <span class="done">on hold - pending https fix on community pg<a href="http://caelyf.de.a9sapp.eu/">view here</a></span></li>
    <li>Update all references within Caelyf to version 1.1 - <span class="done">complete</span></li>
    <li>Provision Caelyf on <a href="http://www.cloudbees.com/platform/how-it-works.cb">www.cloudbees.com</a> (requires redis service) - <span class="done">complete - <a href="http://caelyf.jnorthr.cloudbees.net/">view here</a></span></li>
    <li>Provision Caelyf on <a href="https://www.appfog.com/">appfog.com</a> (requires redis-2.2 service) - <span class="done">complete - <a href="http://mycaelyf.hp.af.cm/">view here</a></span></li>
</ul>

<h2>Version 1.2</h2>

<h3>Proposed</h3>
<ul>
    <li>Add Spaces and Organization variables to the enhanced binding</li>
    <li>Add walk-thru examples for tutorial</li>
    <li>Provide plug-in examples</li>
    <li>Develop custom template generation</li>
    <li>Test provision of Redis for caching else fallback to  H2mem, in-memory cache. This will allow caelyf to support environments where no redis has been provisioned or is unsupported.</li>
</ul>

<h2>Version 1.3</h2>

<h3>Future</h3>
<ul>
    <li>Java EE 7
    <ul>
    <li>JCache - JVM provisioned feature</li>
    <li>Web Sockets - potential feature</li>
    </ul>
    </li>
    <li>Provision Caelyf on <a href="https://www.openshift.com/">OpenShift by RedHat</a> (no vcap_service declarations available)</li>
    <li>Provision Caelyf on <a href="http://ironfoundry.org/">Ironfoundry.org - Cloud Foundry for .NET</a> (no redis service available)</li>
    <li>Provision Caelyf on <a href="http://www.tier3.com/">Tier 3 - IaaS and PaaS</a> (expensive)</li>
    <li>Provision Caelyf on <a href="http://www.ibm.com/cloud-computing/us/en/paas.html">The IBM SmartCloud partner ecosystem</a> if demand warrants</li>

</ul>
<br />
<hr />


<% include '/WEB-INF/includes/footer.gtpl' %>
