/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author luans
 */
@Getter
@Setter
public class Address {

    private int address_id;
    private int customer_customer_id;
    private String address_street;
    private String address_code;
    private String address_state_abbreviation;
    private String address_number;
    private String address_neighborhood;
    private String address_complement;
    private String address_type;
    private String isActive;

    public Address(int address_id, int customer_customer_id, String address_street, String address_code, String address_state_abbreviation, String address_number, String address_neighborhood, String address_complement, String address_type, String isActive) {
        this.address_id = address_id;
        this.customer_customer_id = customer_customer_id;
        this.address_street = address_street;
        this.address_code = address_code;
        this.address_state_abbreviation = address_state_abbreviation;
        this.address_number = address_number;
        this.address_neighborhood = address_neighborhood;
        this.address_complement = address_complement;
        this.address_type = address_type;
        this.isActive = isActive;
    }

    public Address(int customer_customer_id, String address_street, String address_code, String address_state_abbreviation, String address_number, String address_neighborhood, String address_complement, String address_type, String isActive) {
        this.customer_customer_id = customer_customer_id;
        this.address_street = address_street;
        this.address_code = address_code;
        this.address_state_abbreviation = address_state_abbreviation;
        this.address_number = address_number;
        this.address_neighborhood = address_neighborhood;
        this.address_complement = address_complement;
        this.address_type = address_type;
        this.isActive = isActive;
    }

    public Address(String address_street, String address_code, String address_state_abbreviation, String address_number, String address_neighborhood, String address_complement, String address_type, String isActive) {
        this.address_street = address_street;
        this.address_code = address_code;
        this.address_state_abbreviation = address_state_abbreviation;
        this.address_number = address_number;
        this.address_neighborhood = address_neighborhood;
        this.address_complement = address_complement;
        this.address_type = address_type;
        this.isActive = isActive;
    }
    
    
    public Address() {
    }
    
}
