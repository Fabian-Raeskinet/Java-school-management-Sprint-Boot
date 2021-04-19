package modele;

import entities.EleveEntity;

import java.sql.Date;
import java.util.List;

public interface InterfModeleEleve extends InterfModele<EleveEntity> {
    EleveEntity rech(String matricule) throws Exception;
    List<EleveEntity> rechNom(String nom) throws Exception;
    List<EleveEntity> rechDate(Date date) throws Exception;
}
