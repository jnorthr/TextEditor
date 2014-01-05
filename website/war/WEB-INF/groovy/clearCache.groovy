html.ul {
    [
            "/",
            "/tutorial",
            "/tutorial/print",
            "/tutorial/setup",
            "/tutorial/views-and-controllers",
            "/tutorial/url-routing",
            "/tutorial/plugins",
            "/tutorial/deploy",
	    "/tutorial/template-project",
            "/download",
            "/roadmap",
            "/search",
            "/documentation",
            "/community"
    ].each {
        redis.del it
        li "Cleared cache for URI: $it"
    }
}
