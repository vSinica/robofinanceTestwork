package ru.vados.robofinanceTestwork.model;

public class Customer {

    private long id;
    private long registred_address_id;
    private long actual_address_id;
    private String first_name;
    private String last_name;
    private String middle_name;
    private String sex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRegistred_address_id() {
        return registred_address_id;
    }

    public void setRegistred_address_id(long registred_address_id) {
        this.registred_address_id = registred_address_id;
    }

    public long getActual_address_id() {
        return actual_address_id;
    }

    public void setActual_address_id(long actual_address_id) {
        this.actual_address_id = actual_address_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
