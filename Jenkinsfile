// Jenkinsfile (Declarative Pipeline)
// Works on Windows; runs Maven build + tests and publishes JUnit results.

pipeline {
  agent any

  // Use the names you set in "Manage Jenkins → Global Tool Configuration"
  // If you haven't set them yet, do it and name them exactly Java17 and Maven3
  tools {
    jdk   'Java17'
    maven 'Maven3'
  }

  options {
    timestamps()
    ansiColor('xterm')
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  stages {
    stage('Checkout') {
      steps {
        // Because this job is "Pipeline from SCM", Jenkins already checks out.
        // 'checkout scm' is safe and idempotent.
        checkout scm
      }
    }

    stage('Build') {
      steps {
        bat 'mvn -B -U -DskipTests=true clean install'
      }
    }

    stage('Test') {
      steps {
        bat 'mvn -B test'
      }
    }
  }

  post {
    always {
      // Publish JUnit/Surefire results even if tests fail
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'

      // Keep any logs/screenshots if your framework writes them
      archiveArtifacts allowEmptyArchive: true, artifacts: 'target/**/*.log, target/screenshots/**/*.*'

      cleanWs()
    }
  }
}
