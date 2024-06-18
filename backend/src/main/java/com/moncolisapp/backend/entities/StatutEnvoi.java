package com.moncolisapp.backend.entities;

public enum StatutEnvoi {
    EN_ATTENTE_DE_CONFIRMATION("En Attente de Confirmation d'envoi"),
    CONFIRME("Confirmé"),
    EN_COURS("En Cours"),
    LIVRE("Livré");

    private final String displayName;

    StatutEnvoi(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
