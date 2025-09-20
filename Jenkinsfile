pipeline {
    agent any

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh './mvnw clean package'
            }
        }babaissandiaye@babaissandiaye-HP-ProBook-440-14-inch-G10-Notebook-PC:~$ mvn --version
Apache Maven 3.8.7
Maven home: /usr/share/maven
Java version: 17.0.16, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "6.14.0-29-generic", arch: "amd64", family: "unix"
babaissandiaye@babaissandiaye-HP-ProBook-440-14-inch-G10-Notebook-PC:~$













        stage('Build & Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_Java', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                        def appName = 'appsenelec' // Nom de l'application
                        def branchName = env.BRANCH_NAME ?: env.GIT_BRANCH ?: 'latest'
                        def safeTag = branchName.replaceAll('[^A-Za-z0-9._-]', '-')
                        def dockerImage = "${DOCKER_USER}/${appName}:${safeTag}"

                        sh """
                            set -e
                            echo "Building Docker image: ${dockerImage}"
                            docker build -t "${dockerImage}" .

                            echo "Logging into Docker Hub..."
                            echo "${DOCKER_PASS}" | docker login -u "${DOCKER_USER}" --password-stdin

                            echo "Pushing Docker image: ${dockerImage}"
                            docker push "${dockerImage}"
                        """
                    }
                }
            }
        }

        stage('Deploy to Render') {
            steps {
                withCredentials([string(credentialsId: 'RENDER_HOOK', variable: 'RENDER_HOOK_URL')]) {
                    sh 'curl -X POST "$RENDER_HOOK_URL"'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
