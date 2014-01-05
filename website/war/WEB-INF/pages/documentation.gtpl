<% include '/WEB-INF/includes/header.gtpl?title=Documentation' %>
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

<h1>Documentation</h1>
<div id="sidebox" class="roundPinkBorder">
    <table>
        <tr>
            <td><a href="/" alt="Home"><img id="img1" src="/images/freetext1.png" alt="FreeText - a lightweight Groovy toolkit for Cloud Foundry"></a></td>
        </tr>
    </table>
</div>
<p>Here are some links to references and documentation that may reduce your learning curve with FreeText.</p>

<p>Resources cover both our github repository, wiki, README and github pages, plus our own FreeText API and internal materials. Enjoy !</p>

<h2>Version 1.0</h2>

<h3>Artifacts</h3>
<ul>
    <li><a href="http://freetext.cfapps.io/">FreeText Website - this site</a></li>
    <li><a href="/freetext.pdf">FreeText Outline PDF - a printable overview of FreeText features</a></li>
    <li><a href="/apis">FreeText API javadocs - application programming interfaces</a></li>
    <li><a href="/build">FreeText build report - most recent results</a></li>
    <li><a href="https://github.com/freetext/freetext/wiki/Welcome-Home">FreeText Wiki - Github</a></li>
    <li><a href="http://freetext.github.io/freetext/">FreeText Github Pages - Reference material</a></li>
    <li><a href="https://github.com/freetext/freetext/blob/master/README.md">FreeText Github README</a></li>
    <li><a href="downloads/Flint.pdf">The Life Story of Leslie Flint</a></li>
</ul>
 
<% include '/WEB-INF/includes/footer.gtpl' %>
