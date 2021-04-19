package testsjpa;

import entities.ClasseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class TestModifClasse {
    private static final String PERSISTENCE_UNIT_NAME = "projetUnit";
    private static EntityManagerFactory factory;
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        System.out.println("numéro recherché :");
        int id = sc.nextInt();
        sc.skip("\n");
        ClasseEntity cl = em.find(ClasseEntity.class, id);
        if(cl==null) System.out.println("identifiant classe inconnu");
        else {
            System.out.println("Titulaire ="+cl.getTitulaire());
            System.out.println("nouveau titulaire :");
            String titulaire = sc.nextLine();
            try {
                em.getTransaction().begin();
                cl.setTitulaire(titulaire);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.out.println("erreur de mise à jour : "+e.getMessage());
            }
            cl= em.find(ClasseEntity.class, id);
            System.out.println("nom ="+cl.getIdClasse());
        }
        em.close();
    }
}
