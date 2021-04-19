package testsjpa;

import entities.ClasseEntity;
import entities.EleveEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class TestRechClasseId {
    private static final String PERSISTENCE_UNIT_NAME = "projetUnit";
    private static EntityManagerFactory factory;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        System.out.println("numéro recherché :");
        int id = sc.nextInt();
        ClasseEntity cl = em.find(ClasseEntity.class, id);
        if (cl == null) System.out.println("identifiant client inconnu");
        else {
            System.out.println("sigle =" + cl.getSigle());
            for (EleveEntity co : cl.getElevesByIdClasse()) {
                System.out.println(co.toString());
            }
        }
        em.close();
    }
}
