package com.moncolisapp.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarifResult {
    private BigDecimal prixTotal;
    private Integer tarifId;

}
