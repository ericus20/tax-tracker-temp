/*

Script to reset jenkins build history then reset build number to 1.

item = Jenkins.instance.getItemByFullName("tax-tracker")
//THIS WILL REMOVE ALL BUILD HISTORY
item.builds.each() { build ->
  build.delete()
}
item.updateNextBuildNumber(1)

*/






pipeline {
    agent any
    environment {
        gradleHome = tool name: 'gradle', type: 'gradle'
        sonarUrl = 'sonar.host.url=https://server.ericopoku.com/sonarqube'
        ENCRYPT_KEY_STORE_PASSWORD = credentials('ENCRYPT_KEY_STORE_PASSWORD')
        ENCRYPT_KEY_STORE_ALIAS = credentials('ENCRYPT_KEY_STORE_ALIAS')
        ENCRYPT_KEY_STORE_SECRET = credentials('ENCRYPT_KEY_STORE_SECRET')
    }
    stages {

        stage('Scm Checkout') {
            steps {
                git branch: 'develop', url: 'git@github.com:ericus20/tax-tracker.git'
            }
        }

        stage('Build Project') {
            steps {
                sh "${gradleHome}/bin/gradle clean build -Pspring.profiles.active=dev"
            }
        }

        // currently disable check for sonar
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    withCredentials([string(credentialsId: 'sonarqubeTokenTaxTracker', variable: 'sonarqubeTokenTaxTracker')]) {
                        sh "${gradleHome}/bin/gradle sonarqube -D${sonarUrl}  -Dsonar.login=${sonarqubeTokenTaxTracker}"
                    }
                }
            }
        }

        /*
        stage("SonarQube Quality Gate Status Check") {
            steps {
                sleep(120)
                timeout(time: 2, unit: 'MINUTES') {
                    withSonarQubeEnv('sonarqube') {
                        script {
                            def gateStatus = waitForQualityGate()
                            if (gateStatus.status != 'OK') {
                              error "Pipeline aborted due to quality gate failure: ${gateStatus.status}"
                            }
                        }
                    }
                }
            }
         }
         */
    }

    post {
        failure {
            emailext body: '$DEFAULT_BODY', recipientProviders: [culprits(), upstreamDevelopers()], subject: '$DEFAULT_SUBJECT', to: '$DEFAULT_RECIPIENTS'
        }
    }
}