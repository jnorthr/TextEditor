def stableDuration = 2.hour
def shortContentDuration = 2.minutes
def hotContentDuration = 10.minutes
def version = "1.0"

get "/",            forward: "/WEB-INF/pages/index.gtpl"
get "/tutorial",    forward: "/WEB-INF/pages/tutorial.gtpl",  cache: stableDuration
get "/tutorial/print", forward: "/WEB-INF/pages/tutorial/print.gtpl", cache: stableDuration
get "/tutorial/setup",                  forward: "/WEB-INF/pages/tutorial/setup.gtpl",                  cache: stableDuration
get "/tutorial/views-and-controllers",  forward: "/WEB-INF/pages/tutorial/viewsAndControllers.gtpl",    cache: stableDuration
get "/tutorial/url-routing",            forward: "/WEB-INF/pages/tutorial/flexibleUrlRouting.gtpl",     cache: stableDuration
get "/tutorial/plugins",                forward: "/WEB-INF/pages/tutorial/plugins.gtpl",                cache: stableDuration
get "/tutorial/template-project",       forward: "/WEB-INF/pages/tutorial/templateProject.gtpl",        cache: stableDuration
get "/tutorial/deploy",       		forward: "/WEB-INF/pages/tutorial/deploy.gtpl",        		cache: stableDuration


get "/download",    forward: "/WEB-INF/pages/download.gtpl",  cache: stableDuration
get "/caelyf-${version}.jar",    forward: "/WEB-INF/lib/caelyf-${version}.jar"

// can not cache as caching feature runs out of memory as this .zip > 8MB
get "/caelyf-template-project-${version}.zip",    forward: "/WEB-INF/lib/caelyf-template-project-${version}.zip"
get "/template-project.war",    forward: "/WEB-INF/lib/template-project.war"

get "/community",   forward: "/WEB-INF/pages/community.gtpl", cache: shortContentDuration
get "/search",      forward: "/WEB-INF/pages/search.gtpl",    cache: stableDuration
get "/documentation", forward: "/WEB-INF/pages/documentation.gtpl" //,  cache: stableDuration
get "/roadmap",      forward: "/WEB-INF/pages/roadmap.gtpl",    cache: stableDuration
get "/apis",    redirect: "/api/index.html",    cache: stableDuration 
get "/build",  redirect: "/reports/tests/index.html",    cache: stableDuration

get "/paas",    forward: "/WEB-INF/pages/paas.gtpl"
get "/paas2",    forward: "/WEB-INF/pages/paas2.gtpl"
