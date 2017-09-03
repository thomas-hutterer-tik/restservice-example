package restservice;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import restservice.domain.User;

@SpringBootTest
public class UserUnitTest {

	@Test
	public void test() {
		User user = new User();
		
		user.setId(0L);
		user.setFirstName("Max");
		user.setLastName("Maier");
		assertThat(user.getId(), is(0L));
		assertThat(user.getFirstName(), is("Max"));
		assertThat(user.getLastName(), is("Maier"));
	}

}
