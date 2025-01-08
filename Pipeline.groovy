pipeline {
    agent any

    environment {
         MAVEN_HOME = 'C:/apache-maven-3.8.6-bin/apache-maven-3.8.6'
            JAVA_HOME = 'C:/Program Files/Java/jdk-17/bin'
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                checkout scm
            }
        }

//         stage('Setup') {
//             steps {
//                 script {
//                     // Start Xvfb for headless tests if necessary (Linux)
//                     if (isUnix()) {
//                         sh '''
//                         Xvfb :99 -screen 0 1920x1080x24 &
//                         export DISPLAY=:99
//                         '''
//                     }
//                 }
//             }
//         }

        stage('Build') {
            steps {
                // Clean and build the Maven project
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests in headless mode
                sh 'mvn test'
            }
        }

//         stage('Generate Reports') {
//             steps {
//                 // If using Allure for reporting
//                 sh 'mvn allure:report'
//             }
//         }

        stage('Publish Results') {
            steps {
                // Archive the test reports
                junit '**/target/surefire-reports/*.xml'

                // If Allure is used
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
            // Clean up the workspace after the build
            cleanWs()
        }

        failure {
            // Notify about failures (optional)
            mail to: 'naveehans@gmail.com',
                 subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Check Jenkins for details."
        }
    }
}