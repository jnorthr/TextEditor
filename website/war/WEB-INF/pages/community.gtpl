<% include '/WEB-INF/includes/header.gtpl?title=Community' %>

<h1>Community</h1>

<p>
In this community section, you'll find links to:
</p>
<ul>
    <li>Participate in the <a href="#discuss">discussions</a> of our Google Group</li>
    <li>Contribute code through GitHub and follow the latest activity in our code <a href="#repository">repository</a></li>
    <li>File feature requests and bugs in our <a href="#bugtracker">bug tracker</a></li>
</ul>


<h1>&nbsp;</h1>
<a name="discuss"></a>
<h2>Discussions</h2>

<p>
If you want to discuss about <b>Caelyf</b>, ask questions, suggest new features, and more,
you can join the <a href="http://groups.google.com/group/caelyf">Caelyf Google Group</a>
</p>

<table id="google-group-subscribe" class="roundPinkBorder" cellspacing="0">
    <tr>
        <td>
            <img src="http://groups.google.com/intl/en/images/logos/groups_logo_sm.gif"
                height=30 width=151 alt="Google Groupes">
        </td>
    </tr>
    <tr>
        <td style="padding-left: 5px"> <b>Subscribe to the Caelyf Google Group</b> </td>
    </tr>
    <form action="http://groups.google.com/group/caelyf/boxsubscribe">
    <tr>
        <td style="padding-left: 5px; border-bottom: 0px;"> Email : <input type=text name=email>
            <input type=submit name="sub" value="Join">
        </td>
    </tr>
    </form>
</table>

<br />

<h3>Latest messages in our <a href="http://groups.google.com/group/caelyf">discussion group</a></h3>

<% include "/latestmessages.groovy" %>

<br />
<h1>&nbsp;</h1>

<h2>Contribute</h2>

<p>
If you wish to contribute to the development of <b>Caelyf</b>:
<ul>
    <li>
        you can do so by forking our <a href="http://github.com/caelyf/caelyf/tree/master">repository on Github</a>
        and by providing patches,
    </li>
    <li>
        you can submit new issues or features in our <a href="http://github.com/caelyf/caelyf/issues">issue tracker</a>.
    </li>
</ul>
</p>

<br />
<a name="repository"></a>
<h3>Latest activity on our <a href="https://github.com/caelyf/caelyf/commits/master">repository</a></h3>

<% include "/latestcommits.groovy" %>

<br />
<h1>&nbsp;</h1>

<a name="bugtracker"></a>
<h2>Issues</h2>
<h3>Open issues in our <a href="https://github.com/caelyf/caelyf/issues">bug tracker</a></h3>

<% include "/latestissues.groovy" %>

<!--
<h3>Look and Feel
    <div id="switcher"></div>
</h3>
-->

<% include '/WEB-INF/includes/footer.gtpl' %>