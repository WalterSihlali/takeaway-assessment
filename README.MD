# Takeaway Automation Assessment
This is a Maven java project for automation of order meal scenarios on Takeaway.com using selenium and cucumber framework
## Prerequisites and installations
- Install java and maven
- Intellij or Eclipse IDE
- git clone repo to local space
- cd to pom.xml folder
## Running the automated tests
- mvn clean test
- To run test in headless mode - takeaway.properties headless=yes
## Parallel execution tests
- For running the parallel cross browser tests launch project in your IDE and right click on the testng.xml file and run with testng
## Test Reports
- First / Latest report will be located under ./target/cucumber-reports
- Existing test reports will be moved to ./reports directory when a new one is generated
- Screenshot for fail test steps will be attached to the test report from ./target/cucumber-reports/screenshots directory
