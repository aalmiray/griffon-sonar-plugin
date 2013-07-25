griffon.project.dependency.resolution = {
    inherits 'global'
    log 'warn'
    repositories {
        mavenCentral()
    }
    dependencies {
        build 'org.codehaus.sonar-plugins:sonar-ant-task:2.1'
    }
}

log4j = {
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p %c - %m%n')
    }

    error 'org.codehaus.griffon',
          'org.springframework',
          'org.apache.karaf',
          'groovyx.net'
    warn  'griffon'
}