package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.dto.AddressDTO;
import com.moncolisapp.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;


    /**
     * Get all addresses in the database
     * @return List of AddressDTO objects
     */
    @GetMapping("")
    public ResponseEntity<List<AddressDTO>> getAllAddress() {
        return addressService.getAllAddress();
    }


    /**
     * Get all addresses in the database for a specific country
     * @return List of AddressDTO objects
     */
    @GetMapping("/{pays}")
    public ResponseEntity<List<AddressDTO>> getAddress(@PathVariable String pays) {
        return addressService.getAddress(pays);
    }



}
