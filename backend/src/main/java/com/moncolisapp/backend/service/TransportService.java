package com.moncolisapp.backend.service;

import com.moncolisapp.backend.repository.TransportRepository;
import com.moncolisapp.backend.entities.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public boolean checkTransportAvailability(BigDecimal poidsTotal, BigDecimal volumeTotal) {
        //  nous n'avons qu'un seul moyen de transport pour le moment
        Transport transport = transportRepository.findById(1).orElseThrow(() -> new RuntimeException("Transport not found"));

        BigDecimal newPoids = transport.getPoidsActuel().add(poidsTotal);
        BigDecimal newVolume = transport.getVolumeActuel().add(volumeTotal);

        // Vérifier si le nouveau poids et volume sont dans les limites
        if (newPoids.compareTo(transport.getPoidsDeBase()) <= 0 && newVolume.compareTo(transport.getVolumeDeBase()) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void updateTransport(BigDecimal poidsTotal, BigDecimal volumeTotal) {
        // Supposons que nous n'ayons qu'un seul moyen de transport pour le moment
        Transport transport = transportRepository.findById(1).orElseThrow(() -> new RuntimeException("Transport not found"));

        transport.setPoidsActuel(transport.getPoidsActuel().add(poidsTotal));
        transport.setVolumeActuel(transport.getVolumeActuel().add(volumeTotal));

        // Mettre à jour la disponibilité
        transport.setEstDisponible(transport.getPoidsActuel().compareTo(transport.getPoidsDeBase()) < 0 && transport.getVolumeActuel().compareTo(transport.getVolumeDeBase()) < 0);

        transportRepository.save(transport);
    }
}
