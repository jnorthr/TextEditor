<% if (!params.nowrap) include '/WEB-INF/includes/header.gtpl?title=Tutorial' %>

<h1 style="page-break-before: avoid;">Tutorial</h1>

<% if (!request.requestURI.contains('print')) { %>
<div id="sidebox" class="roundPinkBorder">
    <table>
        <tr>
            <td><a href="/tutorial/print"><img src="/images/icon-printer.png" alt="Printer-friendly"></a></td>
            <td><a href="/tutorial/print">Single page documentation</a></td>
        </tr>
        <tr>
            <td><a href="/freetext.pdf"><img src="/images/icon-pdf.png" alt="Documentation PDF"></a></td>
            <td><a href="/freetext.pdf">PDF documentation</a></td>
        </tr>
    </table>
</div>
<% } %>

<p>The goal of this tutorial is to quickly get you started using <b>FreeText</b> to write
and deploy Groovy applications on Cloud Foundry.</p>

<p>We'll assume you have already setup your local development computer with the CF tooling to deploy your applications to Cloud Foundry on some PaaS service. Refer to our <b>Documents</b> for more details and installation tips for the <b>CF</b> tool.</p>

<p>If you haven't, please do so by reading the 
<a href="http://docs.cloudfoundry.com/docs/dotcom/getting-started.html">instructions</a> from the Cloud Foundry site by Pivotal. It should give
you more ideas for several other PaaS vendors.</p>

<p>
The easiest way to get setup quickly is to download the template project from the <a href="/download">download section</a>.
It provides a ready-to-go project with the right configuration files pre-filled, a gradle build file and an appropriate directory layout:
</p>

<ul>
    <li><code>web.xml</code> preconfigured with the <b>FreeText</b> servlets</li>
    <li>some sample Groovlets and templates</li>
    <li>the needed JARs (Groovy, FreeText, Jedis, GPars)</li>
    <li>optional JARs will depend on your requirements</li>
</ul>

<p><b><a href="http://freetext.cfapps.io/">FreeText</a></b> is powered by the <b><a href="http://www.gradle.org/">Gradle</a></b> build tool, optionally <b><a href="http://gpars.codehaus.org/">GPars</a></b> for advanced parallel multi-tasking and <b><a href="http://groovy.codehaus.org/">Groovy</a></b> - the foundation</p>


<p>You can <a href="/api/index.html">browse the JavaDoc</a> of the classes composing <b>FreeText</b>.
</p>

<h2>Table of Content</h2>

<ul>
    <li><a href="/tutorial/setup">Setting up your project</a></li>
    <ul>
        <li><a href="/tutorial/setup#layout">Directory layout</a></li>
        <li><a href="/tutorial/setup#configuration">Configuration files</a></li>
    </ul>

    <li><a href="/tutorial/template-project">The template project</a></li>
    <ul>
        <li><a href="/tutorial/template-project#build">Gradle build file</a></li>
    </ul>

    <li><a href="/tutorial/views-and-controllers">Views and controllers</a></li>
    <ul>
        <li><a href="/tutorial/views-and-controllers#variables">Variables available in the binding</a></li>
        <ul>
            <li><a href="/tutorial/views-and-controllers#eager">Eager variables</a></li>
            <li><a href="/tutorial/views-and-controllers#lazy">Lazy variables</a></li>
        </ul>
        <li><a href="/tutorial/views-and-controllers#templates">Templates</a></li>
        <ul>
            <li><a href="/tutorial/views-and-controllers#includes">Includes</a></li>
            <li><a href="/tutorial/views-and-controllers#redirect-forward">Redirect and forward</a></li>
        </ul>
        <li><a href="/tutorial/views-and-controllers#groovlets">Groovlets</a></li>
        <ul>
            <li><a href="/tutorial/views-and-controllers#markup-builder">Using MarkupBuilder to render XML or HTML snippets</a></li>
            <li><a href="/tutorial/views-and-controllers#view-delegation">Delegating to a view template</a></li>
        </ul>
        <li><a href="/tutorial/views-and-controllers#logging">Logging messages</a></li>
    </ul>

    <li><a href="/tutorial/url-routing">Flexible URL routing system</a></li>
    <ul>
        <li><a href="/tutorial/url-routing#configuration">Configuring URL routing</a></li>
        <li><a href="/tutorial/url-routing#route-definition">Defining URL routes</a></li>
        <li><a href="/tutorial/url-routing#wildcards">Using wildcards</a></li>
        <li><a href="/tutorial/url-routing#path-variables">Using path variables</a></li>
        <ul>
            <li><a href="/tutorial/url-routing#path-variable-validation">Validating path variables</a></li>
        </ul>
        <li><a href="/tutorial/url-routing#ignore">Ignoring certain routes</a></li>
        <li><a href="/tutorial/url-routing#caching">Caching groovlet and template output</a></li>
    </ul>

    <li><a href="/tutorial/plugins">Simple plugin system</a></li>
    <ul>
        <li><a href="/tutorial/plugins#what">What a plugin can do for you</a></li>
        <li><a href="/tutorial/plugins#anatomy">Anatomy of a plugin</a></li>
        <ul>
            <li><a href="/tutorial/plugins#hierarchy">Hierarchy</a></li>
            <li><a href="/tutorial/plugins#descriptor">The plugin descriptor</a></li>
        </ul>
        <li><a href="/tutorial/plugins#using">Using a plugin</a></li>
        <li><a href="/tutorial/plugins#distribute">How to distribute and deploy a plugin</a></li>
    </ul>

    <li><a href="/tutorial/deploy">Deploy to Cloud Foundry</a></li>
    <ul>
        <li><a href="/tutorial/deploy#assemble">Build and assemble the project</a></li>
        <li><a href="/tutorial/deploy#cf">Deploy to Cloud Foundry using CF</a></li>

    </ul>
</ul>

<h2>Security</h2>
<p>The <b>FreeText</b> toolkit provides your app with the framework to run web apps. It does not come with a security solution <i>'out-of-the-box'</i>. 
As you may choose to offer a public web service that requires no log in or credentials. It's also possible you're doing a 'proof-of-concept' app where logins and credentials are unnecessary.</p>

<p>To deploy your app on a PaaS of your choice, you will need to sign up to a service. Some offer trail periods. Upon registration they will
provide you a user and password. You will need these credentials when using the CF tooling to upload your app.</p>
<p>Your app may also require credentials to use some of the persistence services. These can be obtained programatically. Read the discussion on <b>VCAP_SERVICES</b>
 on <a href="http://blog.cloudfoundry.com/2011/04/19/cloud-foundry-open-paas-deep-dive/">this page</a>.</p>  
 
<h2>Logging</h2>
<p>Please refer to our logging discussion found elsewhere in this tutorial or <a href="/tutorial/views-and-controllers#logging">click here</a>.</p> 
<% if (!params.nowrap) include '/WEB-INF/includes/footer.gtpl' %>