package ru.vados.robofinanceTestwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.robofinanceTestwork.model.Customer;
import ru.vados.robofinanceTestwork.model.EmptyFakeEntityForAccessToJpa;

import java.sql.Timestamp;
import java.util.List;

//Сделал пустой entity, чтобы как привык работать в jpa репозитории, так как вы предоставили свою структуру таблиц
@Repository
public interface CustomerRepository extends JpaRepository<EmptyFakeEntityForAccessToJpa,Long> {

    @Transactional
    @Modifying
    @Query(value ="insert into customer (registred_address_id, actual_address_id, first_name, last_name, middle_name, sex) " +
            "values(:registred_address_id, :actual_address_id, :first_name, :last_name, :middle_name, :sex)", nativeQuery = true)
    void addCustomer(@Param("registred_address_id") Long registredAddress,
                     @Param("actual_address_id") Long actualAddress,
                     @Param("first_name") String firstName,
                     @Param("last_name") String lastName,
                     @Param("middle_name") String middleName,
                     @Param("sex") String sex);

    @Transactional
    @Modifying
    @Query(value ="update customer " +
                  "set actual_address_id = :actual_address_id, first_name = :first_name, last_name = :last_name, middle_name = :middle_name, sex = :sex " +
                  "where id = :id ", nativeQuery = true)
    void updateCustomer(@Param("actual_address_id") Long actualAddress,
                     @Param("first_name") String firstName,
                     @Param("last_name") String lastName,
                     @Param("middle_name") String middleName,
                     @Param("sex") String sex,
                     @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value ="insert into address (contry, region, city, street, house, flat, created, modified) " +
            "values(:contry, :region, :city, :street, :house, :flat, :created, :modified)" +
            "RETURNING id ", nativeQuery = true)
    int[] addAddress(@Param("contry") String contry,
                     @Param("region") String region,
                     @Param("city") String city,
                     @Param("street") String street,
                     @Param("house") String house,
                     @Param("flat") String flat,
                     @Param("created") Timestamp created,
                     @Param("modified") Timestamp modified);



    @Query(value = "select c.id, c.first_name,c.last_name,c.middle_name,c.sex,c.actual_address_id,s.contry,s.region,s.city,s.street,s.flat,s.house" +
            " from customer c join address s on c.actual_address_id = s.id " +
            "where c.first_name like %:searchString% or c.last_name like %:searchString%", nativeQuery = true)
    List<CustomerProjection> getSearchResultByName(@Param("searchString") String searchString);


    @Query(value = "SELECT TRUE From address " +
            "WHERE contry = :contry and region = :region and city = :city and " +
            "street = :street and house = :house and flat = :flat ", nativeQuery = true)
    boolean[] hasAddressExist(@Param("contry") String contry,
                       @Param("region") String region,
                       @Param("city") String city,
                       @Param("street") String street,
                       @Param("house") String house,
                       @Param("flat") String flat);

}
