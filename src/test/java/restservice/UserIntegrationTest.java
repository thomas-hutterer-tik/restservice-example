package restservice;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import restservice.domain.User;

/**
 * Integration testing that involves starting up an application or a server.
 * Server is started one and then all tests for positive and failure of the REST operations are executed
 * <p>
 * https://www.playframework.com/documentation/2.5.x/JavaFunctionalTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUsers() throws Exception {
        createUser();
        this.mockMvc.perform(get("/users/"))
        		.andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").isNumber());
    }

    @Test
    public void getUser() throws Exception {
		createUser();
        Long id = getIdOfFirstUser();
        User user = getUser(id);
        assertThat(user, is(notNullValue()));
        assertThat(user.getId(), is(id));
    }

    @Test
    public void getUserFailure() throws Exception {
        User user = getUser(-4L);
        assertThat(user, is(isNull()));
    }

    @Test
    public void createUserTest() throws Exception {
    		int oldUserCount = getUserCount();
        createUser();
        assertThat(getUserCount(), is(oldUserCount + 1));
    }
  
    @Test
    public void updateUser() throws Exception {
    		createUser();
        Long id = getIdOfFirstUser();
        User origUser = getUser(id);
        User user  = new User(id, origUser.getLastName() + "X", origUser.getFirstName());
        this.mockMvc.perform(put("/users/" + id)
        		.content(asJsonString(user))
    			.contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk());

        User newUser = getUser(id);
        assertThat(newUser.getLastName(), is(origUser.getLastName() + "X"));
    }
  
    @Test
    public void updateUserFailure() throws Exception {
        User user  = new User(-4L, "X", "X");
        this.mockMvc.perform(put("/users/-4")
        		.content(asJsonString(user))
    			.contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().is4xxClientError());
    }
  
    @Test
    public void deleteUser() throws Exception{
		int oldUserCount = getUserCount();
        Long id = getIdOfFirstUser();
        this.mockMvc.perform(delete("/users/" + id))
        		.andExpect(status().isNoContent());
        assertThat(getUserCount(), is(oldUserCount - 1));
    }
  
    @Test
    public void deleteUserFailure() throws Exception{
		int oldUserCount = getUserCount();
        this.mockMvc.perform(delete("/users/" + -4))
        		.andExpect(status().is4xxClientError());
        assertThat(getUserCount(), is(oldUserCount));
    }
  
    @Test
    public void deleteAllUsers() throws Exception {
    		createUser();
        mockMvc.perform(delete("/users/"))
			.andExpect(status().isNoContent());
        assertThat(getUserCount(), is(0));
    }


    // helper for other tests
    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  

    private void createUser() throws Exception {
		User user = new User(null,"Sarah","Brown");

        this.mockMvc.perform(post("/users/")
        		.content(asJsonString(user))
    			.contentType(MediaType.APPLICATION_JSON)
    			.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isCreated());
    }
    
    private User getUser(Long id) throws Exception {
		MvcResult result = mockMvc.perform(get("/users/" + id)).andReturn();
		String content = result.getResponse().getContentAsString();
		if (result.getResponse().getStatus() == HttpStatus.OK.value()) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(content, User.class);
		}
        return null;
    	
    }
    
    @SuppressWarnings("unchecked")
    private Long getIdOfFirstUser() throws Exception {
		MvcResult result = mockMvc.perform(get("/users/")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		List<LinkedHashMap<String, Object>> usersMap = mapper.readValue(content, List.class);
		
        if (usersMap!=null && !usersMap.isEmpty()) {
        		return Long.valueOf(usersMap.get(0).get("id").toString());
        }
        return -1L;
    }
    
    @SuppressWarnings("unchecked")
    private int getUserCount() throws Exception {
    		MvcResult result = mockMvc.perform(get("/users/")).andExpect(status().isOk()).andReturn();
    		String content = result.getResponse().getContentAsString();
    		ObjectMapper mapper = new ObjectMapper();
    		List<LinkedHashMap<String, Object>> usersMap = mapper.readValue(content, List.class);
    		
        return usersMap.size();
    }
}
