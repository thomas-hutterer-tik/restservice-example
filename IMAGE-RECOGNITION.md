
# Image Recognition Backend

Provide a REST Api to front end applications that allows to invoke image recognition and retrieve all recognized images from previous session with their associated recongnition results.

## Stateless recognition service on CRP:

Invoke with this command:
(echo -n '{"data": "'; base64 pill_bottle.jpg; echo '"}') | curl -X POST -H "Content-Type: application/json" --insecure -d @- https://aml-image-recognition-aml.apps.crp.allianz

Result:
  "predictions": [
    {
      "description": "cliff",
      "label": "n09246464",
      "probability": 99.72506165504456
    },
    {
      "description": "alp",
      "label": "n09193705",
      "probability": 0.14329797122627497
    },
    {
      "description": "valley",
      "label": "n09468604",
      "probability": 0.05048720631748438
    }
  ]
}


## Prerequisites on Allianz Windows Laptop:
 
* Download and install 
	* STS: 			https://spring.io/tools/sts/all
	* Git Bash: 	https://git-scm.com/download/win
	* JDK 8: 		http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
	* Lombok: 		https://projectlombok.org/download
* Configure STS to use JDK instead of JRE