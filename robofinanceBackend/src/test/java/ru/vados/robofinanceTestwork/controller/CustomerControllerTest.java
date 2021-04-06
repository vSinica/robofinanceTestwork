package ru.vados.robofinanceTestwork.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.vados.robofinanceTestwork.repository.CustomerRepository;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private MockMvc mockMvc;
    ObjectMapper mapper;
    CustomerRepository customerRepository;

    @Autowired
    public CustomerControllerTest(MockMvc mockMvc, ObjectMapper mapper, CustomerRepository customerRepository) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }


    @Test
    public void getSearchByNameTest() throws Exception{
        ObjectNode dataForRequest = mapper.createObjectNode();
        dataForRequest.put("searchString", "vadim");

        mockMvc.perform(post("/getsearchresult")
                .content(mapper.writeValueAsString(dataForRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("city")))
                .andExpect(content().string(containsString("nsk")))
                .andExpect(content().string(containsString("contry")))
                .andExpect(content().string(containsString("russia")))
                .andExpect(content().string(containsString("first_name")))
                .andExpect(content().string(containsString("vadim")))
                .andExpect(content().string(containsString("flat")))
                .andExpect(content().string(containsString("4")))
                .andExpect(content().string(containsString("house")))
                .andExpect(content().string(containsString("21")))
                .andExpect(content().string(containsString("house")))
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("last_name")))
                .andExpect(content().string(containsString("anikanov")))
                .andExpect(content().string(containsString("middle_name")))
                .andExpect(content().string(containsString("sergeevich")))
                .andExpect(content().string(containsString("region")))
                .andExpect(content().string(containsString("syberia")))
                .andExpect(content().string(containsString("sex")))
                .andExpect(content().string(containsString("male")))
                .andExpect(content().string(containsString("street")))
                .andExpect(content().string(containsString("svasistov")));


    }

    @Test
    public void addNewCustomerAndAddressTest() throws Exception{

        Timestamp createdTimestamp = new Timestamp(System.currentTimeMillis());

        int[] addressIdArray = customerRepository.addAddress("japan", "harakasuka", "ebisu",
                "minami", "154", "90",
                createdTimestamp, createdTimestamp);

        Long customerAddressId = Long.parseLong(String.valueOf(addressIdArray[0]));

        customerRepository.addCustomer(customerAddressId,customerAddressId,
                "vadim", "anikanov", "sergeevich", "male");

        ObjectNode dataForRequest = mapper.createObjectNode();
        dataForRequest.put("searchString", "vadim");

        mockMvc.perform(post("/getsearchresult")
                .content(mapper.writeValueAsString(dataForRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("city")))
                .andExpect(content().string(containsString("nsk")))
                .andExpect(content().string(containsString("contry")))
                .andExpect(content().string(containsString("russia")))
                .andExpect(content().string(containsString("first_name")))
                .andExpect(content().string(containsString("vadim")))
                .andExpect(content().string(containsString("flat")))
                .andExpect(content().string(containsString("4")))
                .andExpect(content().string(containsString("house")))
                .andExpect(content().string(containsString("21")))
                .andExpect(content().string(containsString("house")))
                .andExpect(content().string(containsString("id")))
                .andExpect(content().string(containsString("last_name")))
                .andExpect(content().string(containsString("anikanov")))
                .andExpect(content().string(containsString("middle_name")))
                .andExpect(content().string(containsString("sergeevich")))
                .andExpect(content().string(containsString("region")))
                .andExpect(content().string(containsString("syberia")))
                .andExpect(content().string(containsString("sex")))
                .andExpect(content().string(containsString("male")))
                .andExpect(content().string(containsString("street")))
                .andExpect(content().string(containsString("svasistov")));

    }

}
