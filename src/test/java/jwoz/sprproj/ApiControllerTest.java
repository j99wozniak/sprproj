package jwoz.sprproj;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.HttpHeaders;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/hello")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello, this should be a public endpoint!"));
    }

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("Aleander:pass".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello, your name is Aleander :)[ROLE_USER]"));
    }

    @Test
    public void testErrorGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}