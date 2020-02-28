node{
   stage('SCM Checkout'){
   git 'https://github.com/Ishnas/BillingLogic'
   }
   
   stage('Compile-Package'){
     def mvnHome = tool name: 'maven', type: 'maven'
      sh "${mvnHome}/bin/mvn package"
   }
}
