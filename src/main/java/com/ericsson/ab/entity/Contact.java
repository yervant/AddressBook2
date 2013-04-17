/**
 * File name: Contact.java Author: Willard Wang Create date: Apr 16, 2013
 */
package com.ericsson.ab.entity;

/**
 * Represents a contact entry in {@link com.ericsson.ab.entity.AddressBook}
 * 
 * @author deathspeeder
 * 
 */
public class Contact {
    private int age;
    private String mobile;
    private String address;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
