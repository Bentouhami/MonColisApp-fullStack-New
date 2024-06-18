package com.moncolisapp.backend.service;

import com.moncolisapp.backend.entities.Transport;
import com.moncolisapp.backend.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class TransportService {

    @Autowired
    private TransportRepository transportRepository;

    public boolean checkTransportAvailability(BigDecimal poidsTotal, BigDecimal volumeTotal) {
        // Assumons qu'il n'y a qu'un seul transport
        Transport transport = transportRepository.findById(1).orElseThrow(() -> new RuntimeException("Transport not found"));

        BigDecimal poidsDisponible = transport.getPoidsDeBase().subtract(transport.getPoidsActuel().add(poidsTotal));
        BigDecimal volumeDisponible = transport.getVolumeDeBase().subtract(transport.getVolumeActuel().add(volumeTotal));

        return poidsDisponible.compareTo(BigDecimal.ZERO) >= 0 && volumeDisponible.compareTo(BigDecimal.ZERO) >= 0;
    }

    public LocalDate calculateNextAvailableDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextTuesday = getNextTuesday(currentDate);
        return nextTuesday.plusWeeks(2); // Date du prochain envoi après deux semaines
    }

    private LocalDate getNextTuesday(LocalDate currentDate) {
        while (currentDate.getDayOfWeek() != DayOfWeek.TUESDAY) {
            currentDate = currentDate.plusDays(1);
        }
        return currentDate;
    }

    public void updateTransport(BigDecimal poidsTotal, BigDecimal volumeTotal) {
        Transport transport = transportRepository.findById(1).orElseThrow(() -> new RuntimeException("Transport not found"));

        transport.setPoidsActuel(transport.getPoidsActuel().add(poidsTotal));
        transport.setVolumeActuel(transport.getVolumeActuel().add(volumeTotal));

        transportRepository.save(transport);
    }
}
