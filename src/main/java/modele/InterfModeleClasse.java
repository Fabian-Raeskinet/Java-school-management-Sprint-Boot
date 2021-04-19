package modele;

import entities.ClasseEntity;

import java.util.List;

public interface InterfModeleClasse  extends InterfModele<ClasseEntity>{
    ClasseEntity rech(String sigle) throws Exception;
}
