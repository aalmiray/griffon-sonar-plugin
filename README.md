
Sonar plugin
---------------

Plugin page: [http://artifacts.griffon-framework.org/plugin/sonar](http://artifacts.griffon-framework.org/plugin/sonar)


The Sonar Plugin provides integration with [SonarQube][1] (previously known as Sonar).

Usage
----
The plugin provides a script named 'sonar' that will configure the project and
connect to a running instance of SonarQube using the SonarQube Runner.

Configuration
-------------
The plugin requires no customizations to run if connecting to a SonarQube instance
with default settings. By default it will analyze all Groovy files in

 * src/main
 * griffon-app/controllers
 * griffon-app/models
 * griffon-app/views
 * griffon-app/services

You may specify any [analysis parameters][2] in the build configuration files
(`griffon-app/conf/BuildConfig.groovy` for project local settings, 
 `$USER_HOME/.griffon/settings.groovy` for settings applicable to all projects).
You may override any settings by specifying a value for the appropriate key in the
command line, for example

    griffon -Dsonar.showSql=true sonar

The following is a list of default values applied to analysis parameters

| *Key*                         | *Value*                                            |
| ----------------------------- | -------------------------------------------------- |
| sonar.host.url                | 'http://localhost:9000'                            |
| sonar.jdbc.url                | 'jdbc:h2:tcp://localhost:9092/sonar'               |
| sonar.jdbc.driverClassName    | 'org.h2.Driver'                                    |
| sonar.jdbc.username           | 'sonar'                                            |
| sonar.jdbc.password           | 'sonar'                                            |
| sonar.projectKey              | "griffon:${Metadata.current.getApplicationName()}" |
| sonar.projectName             | Metadata.current.getApplicationName()              |
| sonar.projectDescription      | Metadata.current.getApplicationName()              |
| sonar.projectVersion          | Metadata.current.getApplicationVersion()           |
| sonar.projectBaseDir          | $basedir                                           |
| sonar.working.directory       | $projectTargetDir/sonar                            |
| sonar.dynamicAnalysis         | 'reuseReports'                                     |
| sonar.language                | 'grvy'                                             |
| sonar.surefire.reportsPath    | griffonSettings.testReportsDir                     |
| sonar.tests                   | 'test/unit, test/integration'                      |
| sonar.binaries                | "$projectMainClassesDir, ${griffonSettings.testClassesDir}/unit, ${griffonSettings.testClassesDir}/integration" |
| sonar.sourceEncoding          | System.getProperty('file.encoding')                |

Additional script specific properties may be specified

 * **skip-tests** - tests are run before Sonar. Use this option if test reports
   have been run already before invoking the sonar script, saving some time.
 * **verbose** - outputs all configured Sonar properties 

[1]: http://www.sonarqube.org/
[2]: http://docs.codehaus.org/display/SONAR/Analysis+Parameters