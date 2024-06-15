package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class Envois {
    private Integer id;

    private String codeDeSuivi;

    private LocalDate dateEnvoi;

    private LocalDate dateLivraisonPrevu;

    private BigDecimal poidsTotal;

    private BigDecimal volumeTotal;

    private BigDecimal prixTotal;

    private String statut;

    private Agence idAgenceArrivee;

    private Agence idAgenceDepart;

    private Client idClient;

    private Coupon idCoupon;

    private Destinataire idDestinataire;

    private Tarif idTarif;

    private Transport idTransport;

    @Id
    @ColumnDefault("nextval('envois_id_envoi_seq'")
    @Column(name = "id_envoi", nullable = false)
    public Integer getId() {
        return id;
    }

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_agence_arrivee", nullable = false)
    public Agence getIdAgenceArrivee() {
        return idAgenceArrivee;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_agence_depart", nullable = false)
    public Agence getIdAgenceDepart() {
        return idAgenceDepart;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_client", nullable = false)
    public Client getIdClient() {
        return idClient;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_coupon")
    public Coupon getIdCoupon() {
        return idCoupon;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_destinataire", nullable = false)
    public Destinataire getIdDestinataire() {
        return idDestinataire;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_tarif", nullable = false)
    public Tarif getIdTarif() {
        return idTarif;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_transport", nullable = false)
    public Transport getIdTransport() {
        return idTransport;
    }

}