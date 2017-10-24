package restservice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import restservice.domain.User;

/*
 * Test class that remotely invokes all the REST APis for the User entity
 * 
 * based on: http://websystique.com/spring-boot/spring-boot-rest-api-example/
 */
public class SpringBootRestTestClient {
  
    private static final String REST_SERVICE_URI = "http://localhost:8080";
	private static final RestTemplate restTemplate = new RestTemplate();
      
    public static void createUserTest() {
    		int oldUserCount = getUserCount();
        createUser();
        assertThat(getUserCount(), is(oldUserCount + 1));
    }
  
    @SuppressWarnings("unchecked")
    public static void listAllUsers() {
    		createUser();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/user/", List.class);
        assertThat(usersMap, is(notNullValue()));
        assertThat(usersMap.size(), is(not(0)));
    }

    public static void getUser(){
		createUser();
        Long id = getIdOfFirstUser();
        User user = restTemplate.getForObject(REST_SERVICE_URI+"/user/" + id, User.class);
        assertThat(user, is(notNullValue()));
        assertThat(user.getId(), is(id));
    }
      
    public static void updateUser() {
        Long id = getIdOfFirstUser();
        User origUser = restTemplate.getForObject(REST_SERVICE_URI+"/user/" + id, User.class);
        User user  = new User(id, origUser.getLastName() + "X", origUser.getFirstName());
        restTemplate.put(REST_SERVICE_URI+"/user/" + id, user);
        User newUser = restTemplate.getForObject(REST_SERVICE_URI+"/user/" + id, User.class);
        assertThat(newUser.getLastName(), is(origUser.getLastName() + "X"));
    }
  
    public static void deleteUser() {
		int oldUserCount = getUserCount();
        Long id = getIdOfFirstUser();
        restTemplate.delete(REST_SERVICE_URI+"/user/" + id);
        assertThat(getUserCount(), is(oldUserCount - 1));
    }
  
    public static void deleteAllUsers() {
        restTemplate.delete(REST_SERVICE_URI+"/user/");
        assertThat(getUserCount(), is(0));
    }

    // helper for other tests
    private static URI createUser() {
		User user = new User(null,"Sarah","Brown");
		return restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
    }
    
    @SuppressWarnings("unchecked")
    private static Long getIdOfFirstUser() {
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/user/", List.class);
        if (usersMap!=null && !usersMap.isEmpty()) {
        		return Long.valueOf(usersMap.get(0).get("id").toString());
        }
        return -1L;
    }
    
    @SuppressWarnings("unchecked")
    private static int getUserCount() {
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/user/", List.class);
        if (usersMap != null) {
        		return usersMap.size();
        }
        return 0;
    }
    
    
    public static void main(String args[]){
        listAllUsers();
        getUser();
        createUser();
        listAllUsers();
        updateUser();
        listAllUsers();
        deleteUser();
        listAllUsers();
        deleteAllUsers();
        listAllUsers();
    }

 }