# Accession Numbers
This is a Spring Based Maven application. developed to return ordered list of Accession Numbers

Service can be consumed over the REST call as:

1. PUT /orderedlist 
	- Request Body: {
	    "list" : "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1"
}

  - Response Body: {
      "list": "A00000, A0001, ABCDEFG1, DRR2110012, ERR000111-ERR000113, ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001"
  }

## Building and Executing application
1. Application can be build using maven as mvn clean install, copy the war file in tomcat webapp folder.
2. Other way to, import the project in eclipse and build and deploy integrated tomcat in eclipse/idea IntelliJ
3. In order to test the functionality from command line executor.jar file is provided containing minimal set of classes. jar can be run direclty from command line as 
> java -jar executor.jar "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1"

Output will be displayed as
> OutPut: A00000, A0001, ABCDEFG1, DRR2110012, ERR000111-ERR000113, ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001

## Viewing webpage
1. User can navigate to http://`machine-ip-or-name`:`port`/accession/index.html to try enter list of valid Accession Numbers and get ordered list of Accession Numbers as output
	- Ex.: http://localhost:8080/accession/index.html
	
## About backend technologies
- Spring MVC, Junit4, Mockito

## About frontend technologies
- CSS, JQuery, HTML5, JavaScript
