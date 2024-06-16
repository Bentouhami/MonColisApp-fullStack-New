package com.moncolisapp.backend.service;

import com.moncolisapp.backend.dto.AddressDTO;
import com.moncolisapp.backend.entities.Address;
import com.moncolisapp.backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;



    public ResponseEntity<List<AddressDTO>> getAllAddress() {

        List<Address> addressList = addressRepository.findAll();
        return getListResponseEntity(addressList);
    }

    /**
     * Get all addresses in the database for a specific country
     * @param pays String country name
     * @return List of AddressDTO objects
     */
    public ResponseEntity<List<AddressDTO>> getAddress(String pays) {
        List<Address> addressList = addressRepository.findByPays(pays);
        return getListResponseEntity(addressList);

    }


    /**
     * Get all addresses in the database for a specific country
     * @param addressList List of Address objects
     * @return List of AddressDTO objects
     */
    private ResponseEntity<List<AddressDTO>> getListResponseEntity(List<Address> addressList) {
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for (Address address : addressList) {
            AddressDTO addressDTO = new AddressDTO(
                    address.getRue(),
                    address.getNumero(),
                    address.getVille(),
                    address.getCodepostal(),
                    address.getPays());
            addressDTOList.add(addressDTO);
        }
        return ResponseEntity.ok(addressDTOList);
    }


}
