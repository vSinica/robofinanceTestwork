package ru.vados.robofinanceTestwork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vados.robofinanceTestwork.repository.CustomerProjection;
import ru.vados.robofinanceTestwork.repository.CustomerRepository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin
    @PutMapping("putcustomer")
    private void addNewCustomerAndAddress(@RequestBody(required = false) HashMap<String,String> customerData){
        System.out.println(customerData.get("contry")+"  "+customerData.get("region"));

        Timestamp createdTimestamp = new Timestamp(System.currentTimeMillis());

        int[] addressIdArray = customerRepository.addAddress(customerData.get("contry"),customerData.get("region"),
                                      customerData.get("city"),customerData.get("street"),
                                      customerData.get("house"),customerData.get("flat"),
                                      createdTimestamp, createdTimestamp);

        Long customerAddressId = Long.parseLong(String.valueOf(addressIdArray[0]));

        customerRepository.addCustomer(customerAddressId,customerAddressId,
                customerData.get("name"), customerData.get("lastName"),
                customerData.get("middleName"), customerData.get("sex"));
    }


    @CrossOrigin()
    @PostMapping("getsearchresult")
    private String getSearchByName(@RequestBody(required = false) HashMap<String,String> searchData) throws JsonProcessingException {
        System.out.println(searchData.get("searchString"));

        List<CustomerProjection> customers = customerRepository.getSearchResultByName(searchData.get("searchString"));
        return objectMapper.writeValueAsString(customers);
    }


    @CrossOrigin
    @PostMapping("editcustomer")
    private void editCustomer(@RequestBody(required = false) HashMap<String,String> customerAddressData) throws JsonProcessingException {

        boolean[] hasAddress = customerRepository.hasAddressExist(
                customerAddressData.get("contry"),customerAddressData.get("region"),
                customerAddressData.get("city"),customerAddressData.get("street"),
                customerAddressData.get("house"),customerAddressData.get("flat"));

        if(hasAddress.length != 0){
            customerRepository.updateCustomer(Long.parseLong(customerAddressData.get("actual_address_id")),
                    customerAddressData.get("name"), customerAddressData.get("lastName"),
                    customerAddressData.get("middleName"), customerAddressData.get("sex"),
                    Long.parseLong(customerAddressData.get("id")));
        }else {
            Timestamp createdTimestamp = new Timestamp(System.currentTimeMillis());

            int[] addressIdArray = customerRepository.addAddress(customerAddressData.get("contry"),customerAddressData.get("region"),
                    customerAddressData.get("city"),customerAddressData.get("street"),
                    customerAddressData.get("house"),customerAddressData.get("flat"),
                    createdTimestamp, createdTimestamp);

            Long customerAddressId = Long.parseLong(String.valueOf(addressIdArray[0]));

            customerRepository.updateCustomer(customerAddressId,
                    customerAddressData.get("name"), customerAddressData.get("lastName"),
                    customerAddressData.get("middleName"), customerAddressData.get("sex"),
                    Long.parseLong(customerAddressData.get("id")));
        }
    }
}
