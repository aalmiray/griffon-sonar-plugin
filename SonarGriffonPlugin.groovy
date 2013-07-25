/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import griffon.util.Metadata

/**
 * @author Andres Almiray
 */
class SonarGriffonPlugin {
    // the plugin version
    String version = '1.0.0'
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '1.3.0 > *'
    // the other plugins this plugin depends on
    Map dependsOn = [:]
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'Apache Software License 2.0'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, gtk
    List toolkits = []
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    // URL where documentation can be found
    String documentation = ''
    // URL where source can be found
    String source = 'https://github.com/griffon/griffon-sonar-plugin'
    // Install as a framework plugin
    boolean framework = true

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = 'Sonar integration'
    // accepts Markdown syntax. See http://daringfireball.net/projects/markdown/ for details
    String description = '''
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
'''
}
