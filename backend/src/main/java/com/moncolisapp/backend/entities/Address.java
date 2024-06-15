package com.moncolisapp.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    private Integer id;

    private String rue;

    private String numero;

    private String ville;

    private String codepostal;

    private String pays;

//    @Id
//    @ColumnDefault("nextval('addresses_id_address_seq'")
//    @Column(name = "id_address", nullable = false)
//    public Integer getId() {
//        return id;
//    }

    @Column(name = "rue", nullable = false)
    public String getRue() {
        return rue;
    }

    @Column(name = "numero", nullable = false, length = 10)
    public String getNumero() {
        return numero;
    }

    @Column(name = "ville", nullable = false, length = 50)
    public String getVille() {
        return ville;
    }

    @Column(name = "codepostal", length = 10)
    public String getCodepostal() {
        return codepostal;
    }

    @Column(name = "pays", nullable = false, length = 50)
    public String getPays() {
        return pays;
    }

}