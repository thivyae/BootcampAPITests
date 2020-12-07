pipeline {
  agent any
  stages {
    stage('BuildApp') {
      steps {
        sh '''mvn clean install
echo "Building App"'''
      }
    }

  }
}