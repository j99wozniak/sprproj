package jwoz.sprproj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import jwoz.sprproj.record.NewPerson;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DbViaApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testSetTableName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/settablename")
                        .param("name", "TestTab")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Setname Done"));
    }

    @Test
    @Order(2)
    public void testCreateTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/settablename")
                        .param("name", "TestTab")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Setname Done"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/createtable")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Create table Done"));
    }

    @Test
    @Order(3)
    public void testCreateRecord() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/settablename")
                        .param("name", "TestTab")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Setname Done"));

        NewPerson newPersonObj = new NewPerson();
        newPersonObj.name = "Faraon";
        newPersonObj.age = 3000;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(newPersonObj);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/create")
                        .content(requestJson)
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Create Done"));
    }

    @Test
    @Order(4)
    public void testShowRecord() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/settablename")
                        .param("name", "TestTab")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Setname Done"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/show")
                        .param("name", "Faraon")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{'id':1,'name':'Faraon','age':3000}]"));
    }

    @Test
    @Order(5)
    public void testDeleteTable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/settablename")
                        .param("name", "TestTab")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Setname Done"));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/deletetable")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString("admin:pass!".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Delete table Done"));
    }

}
