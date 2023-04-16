// Jenkins need to install the following additional plugins:
// https://updates.jenkins.io/download/plugins/
// SonarQube Scanner
// Warnings Next Generation
// NodeJS

pipeline {

  agent any

  options {
    buildDiscarder(logRotator(daysToKeepStr: '3', numToKeepStr: '3', artifactNumToKeepStr: '3'))
    durabilityHint('PERFORMANCE_OPTIMIZED')
  }

  triggers {
    pollSCM('* * * * *')
  }

  tools {
    maven 'apache-maven-3.9'
    jdk 'jdk-17'
  }

  stages {
    stage('compile & test') {
      steps {
        execute('mvn clean compile test -T 4C')
      }
    }

    stage('package') {
      steps {
        execute('mvn package -T 4C -DskipTests')
      }
    }

    stage('verify') {
      steps {
        execute('mvn verify -T 4C')
      }
    }
  }
}

void execute(instruction) {
  if (isUnix()) {
    sh instruction
  } else {
    bat instruction
  }
}