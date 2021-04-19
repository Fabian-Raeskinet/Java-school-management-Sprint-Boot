package testsjpa;

import entities.ClasseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class TestCreaClasse {
    private static final String PERSISTENCE_UNIT_NAME = "projetUnit";
    private static EntityManagerFactory factory;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        System.out.println("sigle :");
        String sigle = sc.nextLine();
        System.out.println("annee :");
        int annee = sc.nextInt();
        sc.skip("\n");
        System.out.println("titulaire :");
        String titulaire = sc.nextLine(); //autres infos
        ClasseEntity cl = new ClasseEntity();
        cl.setSigle(sigle);
        cl.setAnnee(annee);
        cl.setTitulaire(titulaire);
        try {
            em.getTransaction().begin();
            em.persist(cl);
            em.getTransaction().commit();
            System.out.println("id classe = " + cl.getIdClasse());
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("erreur : " + e);
        }
        em.close();
    }
}
