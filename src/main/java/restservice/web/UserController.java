package restservice.web;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import restservice.domain.imagerecognition.Image;
import restservice.domain.imagerecognition.PredictionMessage;

import com.google.common.io.ByteStreams;

import restservice.domain.ImageRepository;
import restservice.domain.User;
import restservice.domain.UserRepository;

/**
 * Controller for REST api of the User entity
 * Supports 6 different REST API calls
 * 
 * @author thomas
 */
@RestController
public class UserController {
	
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    UserRepository repository;
    
    @Inject
    ImageRepository imageRepository;
    
    @GetMapping("/users")
    public List<User> getUsers() {
    		if (logger.isInfoEnabled()) 
    			logger.info("Fetching " + repository.count() + " Users");
        
		return repository.findAll();
	}

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") Long id) {
        logger.info("Fetching User with id {}", id);
        User user = repository.findOne(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity<>("User with id " + id 
                    + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // -------------------Create a User------------------------------------------
    @PostMapping("/users")
    public ResponseEntity<String> postUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
 
        repository.save(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a User ------------------------------------------------
    
    @PutMapping("/users/{id}")
    public ResponseEntity<Object> putUser(@PathVariable("id") Long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
 
        User currentUser = repository.findOne(id);
 
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity<>("Unable to upate. User with id " + id + " not found.",
                    HttpStatus.NOT_FOUND);
        }
 
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
 
        repository.save(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
 
    // ------------------- Delete a User-----------------------------------------
 
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);
 
        User user = repository.findOne(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity<>("Unable to delete. User with id " + id + " not found.",
                    HttpStatus.NOT_FOUND);
        }
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 
    // ------------------- Delete All Users-----------------------------
 
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAllUsers() {
        logger.info("Deleting All Users");
 
        imageRepository.deleteAll(); 
        repository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 
    // -------------------Add image to existing User------------------------------------------
    @PostMapping("/users/{id}/images")
    public ResponseEntity<String> postUserImage(@PathVariable("id") long id, @RequestBody Image imageMessage, UriComponentsBuilder ucBuilder) {
    	User user = repository.findOne(id);
    	logger.info("Found User : {}", user);
    	if (user == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	imageMessage.setUser(user);
    	imageMessage.setPrediction(predict(imageMessage.getData()));
    	imageRepository.save(imageMessage);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}/images/{imageId}").buildAndExpand(user.getId(), imageMessage.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/images/sample/predictions")
    public String getSampleImagePrediction() throws Exception {
    	InputStream image = this.getClass().getClassLoader().getResourceAsStream("Sivota2012.JPG");
		byte[] imageBytes = ByteStreams.toByteArray(image);
		String imageBase64UrlEncoded = StringUtils.newStringUtf8(Base64.encodeBase64(imageBytes));
		String result = predict(imageBase64UrlEncoded);
		logger.debug(result);
       
		return result;
	}
    
    @Value("${imageRecognitionUrl}")
    private String imageRecognitionUrl;

	private String predict(String imageBase64UrlEncoded) {
		Image imageMessage = new Image(imageBase64UrlEncoded);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","json"));
		HttpEntity<Image> requestEntity = new HttpEntity<Image>(imageMessage, requestHeaders);
		RestTemplate restTemplate = new RestTemplate();

		logger.debug("URL from config: " + imageRecognitionUrl);
		ResponseEntity<PredictionMessage> responseEntity = restTemplate.exchange(imageRecognitionUrl, HttpMethod.POST, requestEntity, PredictionMessage.class);
		PredictionMessage predictionMessage = responseEntity.getBody();
		
		String result = predictionMessage.toString();
		return result;
	}

    @GetMapping("/users/{id}/images/{imageId}/predictions")
    public String getUserImagePredictions(@PathVariable("id") Long id, @PathVariable("imageId") Long imageId) throws Exception {
    	if (logger.isInfoEnabled()) 
    		logger.info("getUserImagePredictions");
    	// TODO get image.predictions with imageId where user.id = id
    	return "";
    }

    @GetMapping("/users/{id}/images/{imageId}")
    public String getUserImage(@PathVariable("id") Long id, @PathVariable("imageId") Long imageId) throws Exception {
    	if (logger.isInfoEnabled()) 
    		logger.info("getUserImage");
    	
    	// TODO get image with imageId where user.id = id

    	return "";
	}


}
