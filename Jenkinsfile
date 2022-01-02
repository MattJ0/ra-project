//https://www.jenkins.io/doc/book/pipeline/jenkinsfile/
pipeline {
    agent any
    environment {
            TRELLO_KEY = credentials('jenkins-trello-key')
            TRELLO_TOKEN = credentials('jenkins-trello-token')
        }
    stages {
        stage('Build test code') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Execute test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Generate allure report') {  //https://docs.qameta.io/allure/#_jenkins
            steps {
                script {
                    allure([
                            includeProperties: false,
                            jdk              : '',
                            properties       : [],
                            reportBuildPolicy: 'ALWAYS',
                            results          : [[path: 'target/allure-results']]
                    ])
                }
            }
        }
    }


}
