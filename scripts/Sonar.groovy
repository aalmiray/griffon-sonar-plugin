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

includeTargets << griffonScript('TestApp')

target(name: 'sonar',
       description: "The description of the script goes here!",
       prehook: null, posthook: null) {
   if (!argsMap['skip-tests']) depends(testApp)
   runSonar()
}

setDefaultTarget('sonar')

private void runSonar() {
    ant.taskdef(name: 'sonar', classname: 'org.sonar.ant.SonarTask')

    File jardir = new File(ant.antProject.replaceProperties(buildConfig.griffon.jars.destDir))
    def runtimeJars = setupRuntimeJars().collect { it.absolutePath }.join(',')

    def sources = []
    List excludedPaths = ['resources', 'i18n', 'conf']
    for (dir in new File("${basedir}/griffon-app").listFiles()) {
        if (!excludedPaths.contains(dir.name) && dir.isDirectory())  sources << dir
    }
    for (dir in griffonSettings.sourceDir.listFiles()) {
        if (dir.isDirectory()) sources << dir
    }
    buildConfig.griffon.compiler.additional.sources.each { path ->
        if (!(path instanceof File)) path = new File(path.toString())
        if (path.exists()) sources << dir
    }
    sources = sources.collect { f ->
        f.absolutePath.startsWith(basedir) ? f.absolutePath - basedir - File.separator : f
    }.join(', ')

    Map SONAR_PROPERTIES = [
        'sonar.host.url':               'http://localhost:9000',
        'sonar.jdbc.url':               'jdbc:h2:tcp://localhost:9092/sonar',
        'sonar.jdbc.driverClassName':   'org.h2.Driver',
        'sonar.jdbc.username':          'sonar',
        'sonar.jdbc.password':          'sonar',
        'sonar.projectKey':             "griffon:${Metadata.current.getApplicationName()}",
        'sonar.projectName':            Metadata.current.getApplicationName(),
        'sonar.projectDescription':     Metadata.current.getApplicationName(),
        'sonar.projectVersion':         Metadata.current.getApplicationVersion(),
        'sonar.projectBaseDir':         basedir,
        'sonar.working.directory':      "$projectTargetDir/sonar",
        'sonar.dynamicAnalysis':        'reuseReports',
        'sonar.language':               'grvy',
        'sonar.surefire.reportsPath':   griffonSettings.testReportsDir,
        'sonar.tests':                  'test/unit, test/integration',
        'sonar.binaries':               "$projectMainClassesDir, ${griffonSettings.testClassesDir}/unit, ${griffonSettings.testClassesDir}/integration",
        'sonar.sourceEncoding':         System.getProperty('file.encoding'),
        'sonar.projectDate':            null,
        'sonar.exclusions':             null,
        'sonar.tests.exclusions':       null,
        'sonar.inclusions':             null,
        'sonar.tests.inclusions':       null,
        'sonar.global.exclusions':      null,
        'sonar.skippedModules':         null,
        'sonar.includedModules':        null,
        'sonar.branch':                 null,
        'sonar.profile':                null,
        'sonar.skipDesign':             null,
        'sonar.phase':                  null,
        'sonar.host.connectTimeoutMs':  null,
        'sonar.host.readTimeoutMs':     null,
        'sonar.showProfiling':          null,
        'sonar.showSql':                null,
        'sonar.showSqlResults':         null,
        'sonar.verbose':                null,
    ]

    Map sonarProperties = [:]
    SONAR_PROPERTIES.each { key, defaultValue ->
        def value = getConfigurationValue(key, defaultValue)
        if (value != null) sonarProperties[key] = value
    }
    sonarProperties['sonar.sources'] = sources
    sonarProperties['sonar.libraries'] = runtimeJars

    if (argsMap.verbose) {
        println '\n  Sonar properties:'
        sonarProperties.each { println it }
    }

    sonarProperties.each { k, v -> ant.property(name: k, value: v) }

    ant.sonar()
}

private getConfigurationValue(String key, defaultValue) {
    def value = System.getProperty(key)
    if (value != null) return value
    value = buildConfig.flatten([:])[key]
    value != null ? value : defaultValue
}