<% include '/WEB-INF/includes/header.gtpl?title=Download' %>
<% def vname = "1.0" %>

<h1>Download</h1>

<div id="sidebox" class="roundPinkBorder">
    <table>
        <tr>
            <td><a href="/downloads/freetext-template-project-<%= vname %>.zip"><img src="/images/icon-download.png" alt="Template project"></a></td>
            <td><a href="/downloads/freetext-template-project-<%= vname %>.zip">Template project v<%= vname %>.zip</a></td>
        </tr>
        <tr>
            <td><a href="/downloads/freetext-<%= vname %>.jar"><img src="/images/icon-download.png" alt="FreeText JAR"></a></td>
            <td><a href="/downloads/freetext-<%= vname %>.jar">FreeText JAR v<%= vname %></a></td>
        </tr>
        <tr>
            <td><a href="https://github.com/freetext/freetext/tarball/master"><img src="/images/icon-download.png" alt="freetext JAR"></a></td>
            <td><a href="https://github.com/freetext/freetext/tarball/master">FreeText Repo from Github as tar.gz</a></td>
        </tr>
        <tr>
            <td><a href="https://github.com/freetext/freetext/zipball/master"><img src="/images/icon-download.png" alt="freetext JAR"></a></td>
            <td><a href="https://github.com/freetext/freetext/zipball/master">FreeText Repo from Github as .zip</a></td>
        </tr>
    </table>
</div>


<p>You can use the <b>FreeText</b> JAR in combination with Groovy 2.1 (The latest version was tested with Groovy 2.1.5)</p>

<p>But to get you started quickly, you may use a ready-made template project which bundles and configures everything.</p>

<h2>Version <%= vname %></h2>

<h3>Initial version</h3>

<h3>Artifacts</h3>
<ul>
    <li>FreeText JAR: <a href="/downloads/freetext-<%= vname %>.jar">freetext-<%= vname %>.jar</a></li>
    <li>FreeText template project: <a href="/downloads/freetext-template-project-<%= vname %>.zip">freetext-template-project-<%= vname %>.zip</a></li>
</ul>

<h3>FreeText In Action</h3>
<p>Here is a sample <b>FreeText</b> template we've deployed. This will give you an idea of the starting point you have with <b>FreeText</b>.
<a href="http://freetexttemplate.cfapps.io/">Click here.</a></p>
<% include '/WEB-INF/includes/footer.gtpl' %>
