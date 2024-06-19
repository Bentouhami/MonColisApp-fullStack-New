package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "envois", indexes = {
        @Index(name = "idx_envois_id_agence_arrivee", columnList = "id_agence_arrivee"),
        @Index(name = "idx_envois_id_agence_depart", columnList = "id_agence_depart"),
        @Index(name = "idx_envois_id_client", columnList = "id_client"),
        @Index(name = "idx_envois_id_destinataire", columnList = "id_destinataire")
})
public class Envois implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codeDeSuivi;

    private LocalDate dateEnvoi;

    private LocalDate dateLivraisonPrevu;

    private BigDecimal poidsTotal;

    private BigDecimal volumeTotal;

    private BigDecimal prixTotal;

    private String statut;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_agence_arrivee", nullable = false)
    private Agence idAgenceArrivee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_agence_depart", nullable = false)
    private Agence idAgenceDepart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_client", nullable = false)
    private Client idClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_coupon")
    private Coupon idCoupon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_destinataire", nullable = false)
    private Destinataire idDestinataire;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_tarif", nullable = false)
    private Tarif idTarif;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_transport", nullable = false)
    private Transport idTransport;

    @Column(name = "code_de_suivi", nullable = false, length = 20)
    public String getCodeDeSuivi() {
        return codeDeSuivi;
    }

    @Column(name = "date_envoi", nullable = false)
    public LocalDate getDateEnvoi() {
        return dateEnvoi;
    }

    @Column(name = "date_livraison_prevu", nullable = false)
    public LocalDate getDateLivraisonPrevu() {
        return dateLivraisonPrevu;
    }

    @Column(name = "poids_total", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPoidsTotal() {
        return poidsTotal;
    }

    @Column(name = "volume_total", nullable = false, precision = 10, scale = 2)
    public BigDecimal getVolumeTotal() {
        return volumeTotal;
    }

    @Column(name = "prix_total", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    @Column(name = "statut", nullable = false, length = 20)
    public String getStatut() {
        return statut;
    }


}