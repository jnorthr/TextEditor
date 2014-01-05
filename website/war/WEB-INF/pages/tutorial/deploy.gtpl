<% if (!params.nowrap) include '/WEB-INF/includes/header.gtpl?title=Setting up your project' %>

<h1>Deployment to Cloud Foundry</h1>

<a name="assemble"></a>
<h2>Assemble the application</h2>

<p>
Use gradle to build the project and make sure it is up-to-date:
</p>

<pre>
cd /Volumes/FHD-XS/caelyf-v1.1/caelyf/template-project

iMac:template-project jim\$ ./gradlew clean build war copyWar
Parallel project execution is an incubating feature. Enjoy it and let us know how it works for you.
:clean
:compileJava UP-TO-DATE
:compileGroovy UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:war
:assemble
:compileTestJava UP-TO-DATE
:compileTestGroovy UP-TO-DATE
:processTestResources UP-TO-DATE
:testClasses UP-TO-DATE
:test
:check
:build
:copyWar

BUILD SUCCESSFUL

Total time: 22.326 secs
iMac:template-project jim\$
</pre>

<a name="cf"></a>
<h2>Deploy with CF command line tool</h2>


<p>
Now use the CF command line interface to deploy the application.  Note that you need to push the application from the "war" project sub-directory. The <b>Caelyf</b> caching service is supported by Redis so we need to bind the application to a redis service (provision a new redis instance if you must).  If you do not bind the application to a redis service, the application may fail to start with an exception like: "java.lang.RuntimeException: Impossible to get redis credentials (Cannot invoke method getAt() on null object)".
</p>

<p>Here's a sample deployment session when we deployed the <b>Caelyf</b> template to the Pivotal PaaS :</p>
<pre>
iMac:template-project jim\$ pwd
/Volumes/FHD-XS/caelyf-v1.1/caelyf/template-project
iMac:template-project jim\$ cd war
iMac:war jim\$ cf push caelyftemplate
Instances> 1

1: 128M
2: 256M
3: 512M
4: 1G
Memory Limit> 256M

Creating caelyftemplate... OK

1: caelyftemplate
2: none
Subdomain> caelyftemplate

1: cfapps.io
2: none
Domain> cfapps.io

Binding caelyftemplate.cfapps.io to caelyftemplate... OK

Create services for application?> y

1: blazemeter n/a, via blazemeter
2: cleardb n/a, via cleardb
3: cloudamqp n/a, via cloudamqp
4: elephantsql n/a, via elephantsql
5: mongolab n/a, via mongolab
6: newrelic n/a, via newrelic
7: rediscloud n/a, via garantiadata
8: sendgrid n/a, via sendgrid
9: treasuredata n/a, via treasuredata
What kind?> 7

Name?> rediscloud-8e73d

1: 20mb: Lifetime Free Tier
Which plan?> 1

Creating service rediscloud-8e73d... OK
Binding rediscloud-8e73d to caelyftemplate... OK
Create another service?> n

Bind other services to application?> n

Save configuration?> y

Saving to manifest.yml... OK
Uploading caelyftemplate... OK
Starting caelyftemplate... OK
-----> Downloaded app package (8.9M)
Downloading JDK...
Copying openjdk-1.7.0_25.tar.gz from the buildpack cache ...
Unpacking JDK to .jdk
Downloading Tomcat: apache-tomcat-7.0.41.tar.gz
Copying apache-tomcat-7.0.41.tar.gz from the buildpack cache ...
Unpacking Tomcat to .tomcat
Copying mysql-connector-java-5.1.12.jar from the buildpack cache ...
Copying postgresql-9.0-801.jdbc4.jar from the buildpack cache ...
-----> Uploading droplet (47M)
Checking caelyftemplate...
Staging in progress...
Staging in progress...
  0/1 instances: 1 starting
  1/1 instances: 1 running
OK
iMac:war jim\$ 

</pre>

<p>
Now try access this application at the Application Deployed URL of <your sub-domain name here>.cfapps.io from the push command. <a href="http://caelyftemplate.cfapps.io/">Click here</a> to see the caelyf template from the prior session.
</p>


<% if (!params.nowrap) include '/WEB-INF/includes/footer.gtpl' %>