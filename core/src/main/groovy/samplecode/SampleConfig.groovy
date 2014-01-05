// SampleConfig.groovy, a simple groovy script
def SampleConfig = """
person {
    firstName = "Ted"
    address {
        city = "Minneapolis"
    }
}
"""
    
    
// TestConfiguration.groovy
def configObject = new ConfigSlurper().parse(SampleConfig)
configObject.put('key',['k1':'v1']);
println "the size:"+configObject.size()
configObject.values().each{v-> println " = "+v};

new File( 'newout.groovy' ).withWriter{ writer ->
    configObject.writeTo( writer )
}

// One disadvantage of this method is that the highest level structure must be a map, since that's what ConfigObject inherits from.
def config = new ConfigSlurper().parse(new File('newout.groovy').toURL())
println config


println "thats all, folks"
/*  newout.groovy=
person {
    firstName='Ted'
    address.city='Minneapolis'
}
key='va1'

*/