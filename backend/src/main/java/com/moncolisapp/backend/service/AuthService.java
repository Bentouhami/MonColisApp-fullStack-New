package com.moncolisapp.backend.service;

import com.moncolisapp.backend.auth.UserRegisterRequest;
import com.moncolisapp.backend.dto.AddressDTO;
import com.moncolisapp.backend.dto.ClientDTO;
import com.moncolisapp.backend.entities.Address;
import com.moncolisapp.backend.entities.Client;
import com.moncolisapp.backend.mapper.AddressMapper;
import com.moncolisapp.backend.mapper.ClientMapper;
import com.moncolisapp.backend.repository.AddressRepository;
import com.moncolisapp.backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {


    /*region private variables*/
    private AddressDTO addressDTO;
    @Autowired
    private AddressRepository addressRepository;
    private ClientDTO clientDTO;
    @Autowired
    private  ClientMapper clientMapper;
    @Autowired
    private  ClientRepository clientRepository;
    @Autowired
    private  AddressMapper addressMapper;


    /*endregion*/

    /**
     * Register a new user in the database with the given clientDTO object
     *
     * @param userRegisterRequest UserRegisterRequest object to register a new user in the database
     * @return String response
     */
    @Override
    public String register(UserRegisterRequest userRegisterRequest) {
        // GET

        // verify if the user already exists
        if (exitsClientDTOByEmail(userRegisterRequest.email())) {
            return "User already exists";
        }

        // create the addresse object
        AddressDTO addressDTO = new AddressDTO(
                userRegisterRequest.rue(),
                userRegisterRequest.numero(),
                userRegisterRequest.ville(),
                userRegisterRequest.codepostal(),
                userRegisterRequest.pays()
        );

        // verify if the address already exists
        boolean existsAddress = exitsAddress(addressDTO);
        // create the addresse object
        Address address = new Address();
        if (existsAddress) {
            address = findAddress(addressDTO);

        } else {
//            address.setId_adresse(null);
            address.setRue(addressDTO.rue());
            address.setNumero(addressDTO.numero());
            address.setVille(addressDTO.ville());
            address.setCodepostal(addressDTO.codepostal());
            address.setPays(addressDTO.pays());
            addressRepository.save(address);
            System.out.println("address created");
        }


        // create the client object
        Client newClient = new Client();
        newClient.setIdAddress(address);
        newClient.setNom(clientDTO.getNom());
        newClient.setPrenom(clientDTO.getPrenom());
        newClient.setEmail(clientDTO.getEmail());
        newClient.setDateDeNaissance(clientDTO.getDateDeNaissance());
        newClient.setSexe(clientDTO.getSexe());
        clientRepository.save(newClient);

        this.clientRepository.save(newClient);
        // create the user
        return "User created";
    }


    /**
     * Verify if the address already exists
     *
     * @param addressDTO AddressDTO object to check
     * @return boolean response
     */
    boolean exitsAddress(AddressDTO addressDTO) {
        return this.addressRepository
                .existsAddressByRueAndNumeroAndVilleAndCodepostalAndPays
                        (
                                addressDTO.rue(),
                                addressDTO.numero(),
                                addressDTO.ville(),
                                addressDTO.codepostal(),
                                addressDTO.pays()
                        );
    }

    /**
     * Verify if the client already exists in the database with the given email
     *
     * @param userEmail String email to check
     * @return boolean response
     */
    boolean exitsClientDTOByEmail(String userEmail) {

        return clientRepository.existsClientByEmail(userEmail);
    }

    /**
     * Find the addresse in the database with the given clientDTO object
     *
     * @param addressDTO AddressDTO object to get from the database
     * @return Address object
     */
    Address findAddress(AddressDTO addressDTO) {
        return this.addressRepository.findAddressByRueAndNumeroAndVilleAndCodepostalAndPays
                (
                        addressDTO.rue(),
                        addressDTO.numero(),
                        addressDTO.ville(),
                        addressDTO.codepostal(),
                        addressDTO.pays()
                );
    }

}
