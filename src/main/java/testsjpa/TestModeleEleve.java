package testsjpa;

import entities.ClasseEntity;
import entities.EleveEntity;
import modele.ModeleClasse;
import modele.ModeleEleve;

import java.time.LocalDate;
import java.sql.Date;

public class TestModeleEleve {
    public static void main(String[] args){
        ModeleClasse mdc = new ModeleClasse();
        ModeleEleve mde = new ModeleEleve();
        ClasseEntity cl = null;
        EleveEntity e = null;

        try{
            cl = new ClasseEntity();
            cl.setSigle("Cl9");
            cl.setAnnee(3);
            cl.setTitulaire("Hugo");
            mdc.create(cl);
            System.out.println("id nouvelle classe = "+ cl.getIdClasse());
            e = new EleveEntity();
            e.setClasseByIdClasse(cl);
            e.setMatricule("E2");
            e.setNom("Raeskinet");
            e.setPrenom("Fabian");
            e.setDateNaissance(new Date(2000,5,17));
            e.setTel("0493447756");
            mde.create(e);
//            //int id = e.getMatricule();
//            System.out.println("matricule eleve : "+matricule);
//            System.out.println("info de l'élève : ");
//            e = mde.read(matricule);

        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
