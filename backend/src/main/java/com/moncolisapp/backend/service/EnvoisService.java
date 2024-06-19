package com.moncolisapp.backend.service;

import com.moncolisapp.backend.dto.AddressDTO;
import com.moncolisapp.backend.dto.EnvoisRequestDTO;
import com.moncolisapp.backend.entities.*;
import com.moncolisapp.backend.mapper.*;
import com.moncolisapp.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EnvoisService {

    @Autowired
    private EnvoisRepository envoisRepository;

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private TransportService transportService;

    @Autowired
    private ColisMapper colisMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private DestinataireRepository destinataireRepository;

    @Autowired
    private DestinataireMapper destinataireMapper;

    @Autowired
    private TransportMapper transportMapper;

    @Autowired
    private TarifMapper tarifMapper;

    @Autowired
    private AddressMapper addressMapper;


    @Autowired
    private AuthService authService;

    @Autowired
    private AddressRepository addressRepository;

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

    @Autowired
    private TarifRepository tarifRepository;

     @Autowired
    private TransportRepository transportRepository;

    public boolean validerEnvoi(EnvoisRequestDTO envoisRequestDTO, String userEmail) {
        try {
            // Fetch the client by email
            Client client = clientRepository.findClientByEmail(userEmail);
            if (client == null) {
                throw new RuntimeException("Client not found");
            }


            // Create and save Envois, Colis, Destinataire, etc.
            Envois envois = new Envois();

            // pass the connected client to the envois object
            envois.setIdClient(client);

            // generate the tracking code
            envois.setCodeDeSuivi(generateTrackingCode(envoisRequestDTO, client));

            // set the envois datas
            envois.setDateEnvoi(envoisRequestDTO.getDateEnvoiPrevu());
            envois.setDateLivraisonPrevu(envoisRequestDTO.getDateLivraisonPrevu());
            envois.setPoidsTotal(envoisRequestDTO.getPoidsTotal());
            envois.setVolumeTotal(envoisRequestDTO.getVolumeTotal());
            envois.setPrixTotal(envoisRequestDTO.getPrixTotal());

            // set the envois status
            envois.setStatut(envoisRequestDTO.getStatut());

            // get and set the departure and the arrival agency
            Agence agenceArrivee = agenceRepository.findAgencesByNomAgence(envoisRequestDTO.getAgenceArrive());
            envois.setIdAgenceArrivee(agenceArrivee);

            // get and set the departure and the arrival agency
            Agence agenceDepart = agenceRepository.findAgencesByNomAgence(envoisRequestDTO.getAgenceDepart());
            envois.setIdAgenceDepart(agenceDepart);

            // no coupon is required for this envoi
            envois.setIdCoupon(null);

            // create the destinataire object
            Destinataire destinataire = new Destinataire();

            // set the destinataire email and name from the request
            destinataire.setEmail(envoisRequestDTO.getIdDestinataire().email());
            destinataire.setNomPrenom(envoisRequestDTO.getIdDestinataire().nomPrenom());
            destinataire.setTelephone(envoisRequestDTO.getIdDestinataire().telephone());


            // create an address object from the request
            Address destinataireAddress = getDestinataireAddress(envoisRequestDTO);

            // convert the address object to a DTO
            AddressDTO addressDTO = addressMapper.toDto(destinataireAddress);

            // check if the address already exists in the database
            boolean isAddressExisting = authService.exitsAddress(addressDTO);

            // if the address already exists in the database get the address from the database
            if (isAddressExisting) {
                destinataireAddress = authService.findAddress(addressDTO);

            } else {
                // set the address object parameters
                destinataireAddress.setRue(addressDTO.rue());
                destinataireAddress.setNumero(addressDTO.numero());
                destinataireAddress.setVille(addressDTO.ville());
                destinataireAddress.setCodepostal(addressDTO.codepostal());
                destinataireAddress.setPays(addressDTO.pays());

                // save the address in the database and get the address from the database
                destinataireAddress = addressRepository.save(destinataireAddress);
            }

            // set the address object to the destinataire object
            destinataire.setIdAddress(destinataireAddress);

            Destinataire createdDestinataire = destinataireRepository.save(destinataire);

            // save the destinataire in the database and set if to the envois object
            envois.setIdDestinataire(createdDestinataire);

            // get Tarif object by id
            Tarif tarif = tarifRepository.findById(envoisRequestDTO.getIdTarif()).get();
            // set the tarif from the request
            envois.setIdTarif(tarifRepository.findById(envoisRequestDTO.getIdTarif()).orElseThrow(() -> new RuntimeException("Tarif not found")));

            // get Transporteur object by id
            Transport transport = transportRepository.findById(1).get();
            // set the transport from the request
            envois.setIdTransport(transport);


            Envois savedEnvois = envoisRepository.save(envois);

            List<Colis> colisList = envoisRequestDTO.getColis().stream()
                    .map(colisDTO -> {
                        Colis colis = colisMapper.toEntity(colisDTO);
                        colis.setIdEnvoi(savedEnvois);
                        return colis;
                    })
                    .collect(Collectors.toList());

            colisRepository.saveAll(colisList);

            // Update transport
            transportService.updateTransport(envoisRequestDTO.getPoidsTotal(), envoisRequestDTO.getVolumeTotal());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  get the destinataire address from the request
     * @param envoisRequestDTO the request from my frontend
     * @return the address object
     */
    private static Address getDestinataireAddress(EnvoisRequestDTO envoisRequestDTO) {
        Address destinataireAddress = new Address();
        destinataireAddress.setRue(envoisRequestDTO.getIdDestinataire().idAddress().rue());
        destinataireAddress.setNumero(envoisRequestDTO.getIdDestinataire().idAddress().numero());
        destinataireAddress.setVille(envoisRequestDTO.getIdDestinataire().idAddress().ville());
        destinataireAddress.setCodepostal(envoisRequestDTO.getIdDestinataire().idAddress().codepostal());
        destinataireAddress.setPays(envoisRequestDTO.getIdDestinataire().idAddress().pays());
        return destinataireAddress;
    }

    private String generateTrackingCode(EnvoisRequestDTO simulationData, Client client) {
        String paysDepart = simulationData.getPaysDepart().substring(0, 2).toUpperCase();
        String expInitials = client.getNom().substring(0, 1).toUpperCase() + client.getPrenom().substring(0, 1).toUpperCase();
        String alphanumCode = generateAlphanumCode(6);
        int sequence = SEQUENCE.getAndIncrement();
        String paysDestination = simulationData.getPaysDestination().substring(0, 2).toUpperCase();

        return String.format("%s-%s-%s-%d-%s", paysDepart, expInitials, alphanumCode, sequence, paysDestination);
    }

    private String generateAlphanumCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
