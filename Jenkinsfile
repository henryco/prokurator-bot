def BRANCH = "${BRANCH_NAME}".toLowerCase()

static boolean branchMatch(String branch, String... array) {
	// NO! I don't want to simplify this to one-liner!
	for (def example: array) {
		if (branch == example) return true;
		if (branch.startsWith("${example}-")) return true;
		if (branch.endsWith("-${example}")) return true;
	}
	return false;
}

pipeline {
  agent any
  
  tools {
    maven 'Default'
  }
    
  stages {

    stage('Build') {
      steps {
          sh 'mvn install'
      }
    }

    stage('Deploy') {
			when {
		    expression {
		      return branchMatch(BRANCH, "master");
		    }
		  }
      steps {
        // $(ls -p | grep -v / | grep .jar | grep -v .jar.original | grep -v sources.jar)
        sh """ssh -i /var/jenkins_home/.ssh/id_rsa root@tindersamurai.dev 'bash -s' < CD/.Prepare"""
        sh """scp -i /var/jenkins_home/.ssh/id_rsa target/bot-0.0.1-SNAPSHOT.jar root@tindersamurai.dev:deployments/Prokurator/bot"""
        sh """scp -i /var/jenkins_home/.ssh/id_rsa CD/* root@tindersamurai.dev:deployments/Prokurator/bot"""
        sh """ssh -i /var/jenkins_home/.ssh/id_rsa root@tindersamurai.dev 'bash -s' < CD/.Deploy"""
      }
    }
  }

}
