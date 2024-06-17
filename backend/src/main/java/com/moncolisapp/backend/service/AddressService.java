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


    /**
     * Get all addresses in the database
     *
     * @return List of AddressDTO objects
     */
    public ResponseEntity<List<AddressDTO>> getAllAddress() {

        List<Address> addressList = addressRepository.findAll();
        return getListResponseEntity(addressList);
    }

    /**
     * Get all pays in the database
     *
     * @return List of pays in the database
     */
    public ResponseEntity<List<String>> getAllPays() {
        List<String> paysList = addressRepository.findAllPays();

//        List<String> paysListString = new ArrayList<>();
//        for (Address address : paysList) {
//            paysListString.add(address.getPays());
//        }
        return ResponseEntity.ok(paysList);

    }

    public ResponseEntity<List<String>> getCitiesByCountry(String pays) {

        List<String> villesList = addressRepository.findDistinctByVille(pays);
//
//        List<String> paysListString = new ArrayList<>();
//        for (Address address : villesList) {
//            if (address.getPays().equals(pays)) {
//                paysListString.add(address.getVille());
//            }
//
//        }
        return ResponseEntity.ok(villesList);
    }


    /**
     * Get all addresses in the database for a specific country
     *
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
