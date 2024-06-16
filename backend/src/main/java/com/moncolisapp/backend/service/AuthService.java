package com.moncolisapp.backend.service;

import com.moncolisapp.backend.auth.UserLoginResponse;
import com.moncolisapp.backend.auth.UserRegisterRequest;
import com.moncolisapp.backend.dto.AddressDTO;
import com.moncolisapp.backend.dto.ClientDTO;
import com.moncolisapp.backend.entities.Address;
import com.moncolisapp.backend.entities.Client;
import com.moncolisapp.backend.mapper.ClientMapper;
import com.moncolisapp.backend.repository.AddressRepository;
import com.moncolisapp.backend.repository.ClientRepository;
import com.moncolisapp.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService implements IAuthService {


    /*region private variables*/
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


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
        if (existsClientByEmail(userRegisterRequest.getEmail())) {
            return "User already exists";
        }

        // create the addresse object
        AddressDTO addressDTO = new AddressDTO(
                userRegisterRequest.getRue(),
                userRegisterRequest.getNumero(),
                userRegisterRequest.getVille(),
                userRegisterRequest.getCodepostal(),
                userRegisterRequest.getPays()
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
        }

        // create the clientDTO object
        ClientDTO clientDTO = getClientDTO(userRegisterRequest, addressDTO);

        // create the client object
        Client newClient = clientMapper.toEntity(clientDTO);
        newClient.setIdAddress(address);
//        newClient.setMotDePasse(passwordEncoder.encode(userRegisterRequest.getPassword()));
        this.clientRepository.save(newClient);
        // create the user
        return "User created";
    }


    /**
     * Login a user in the database with the given userLoginRequest object
     *
     * @param email    email of the user to log in
     * @param password password of the user to log in
     * @return String response
     */
    @Override
    public ResponseEntity<Map<String, Object>> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();

        boolean existsUser = clientRepository.existsClientByEmail(email);
        if (!existsUser) {
            response.put("success", false);
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Client client = clientRepository.findClientByEmail(email);
        if (client == null) {
            response.put("success", false);
            response.put("message", "Client not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!passwordEncoder.matches(password, client.getMotDePasse())) {
            response.put("success", false);
            response.put("message", "Invalid password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Address address = addressRepository.findById(client.getIdAddress().getId()).orElse(null);
        if (address == null) {
            response.put("success", false);
            response.put("message", "Address not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String jwt = jwtUtil.generateToken(email);

        UserLoginResponse loginResponse = new UserLoginResponse(
                client.getNom(),
                client.getPrenom(),
                client.getDateDeNaissance().toString(),
                StringToBoolean(client.getSexe()),
                client.getTelephone(),
                client.getEmail(),
                address.getRue(),
                address.getNumero(),
                address.getVille(),
                address.getCodepostal(),
                address.getPays()
        );

        response.put("success", true);
        response.put("data", loginResponse);
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }

    private Integer getAddressIdByClientID(Integer id) {
        return clientRepository.findAddressIdByClientID(id);
    }

    private String StringToBoolean(Boolean sexe) {
        return sexe ? "male" : "female";
    }

    private String getSexe(Boolean sexe) {
        return sexe ? "male" : "female";
    }

    private Client findClientByEmail(String email) {
        if (clientRepository.existsClientByEmail(email)) {
            return clientRepository.findClientByEmail(email);
        }
        return null;
    }


    /**
     * Create the clientDTO object
     *
     * @param userRegisterRequest UserRegisterRequest object to register a new user in the database
     * @param addressDTO          AddressDTO object to register a new user in the database
     * @return ClientDTO object
     */
    private ClientDTO getClientDTO(UserRegisterRequest userRegisterRequest, AddressDTO addressDTO) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setNom(userRegisterRequest.getNom());
        clientDTO.setPrenom(userRegisterRequest.getPrenom());
        clientDTO.setEmail(userRegisterRequest.getEmail());
        clientDTO.setDateDeNaissance(getLocalDateFromString(userRegisterRequest.getDateDeNaissance()));
        clientDTO.setSexe(getBooleanFromString(userRegisterRequest.getSexe()));
        clientDTO.setTelephone(userRegisterRequest.getTelephone());
        clientDTO.setIdAddress(addressDTO);
        clientDTO.setMotDePasse(passwordEncoder.encode(userRegisterRequest.getPassword()));
        return clientDTO;
    }

    private LocalDate getLocalDateFromString(String s) {
        return LocalDate.parse(s);
    }

    /**
     * Converts a string to a boolean
     *
     * @param sexe the string to convert
     * @return the boolean
     */
    private Boolean getBooleanFromString(String sexe) {
        return sexe.equalsIgnoreCase("male");
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
    public boolean existsClientByEmail(String userEmail) {
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
