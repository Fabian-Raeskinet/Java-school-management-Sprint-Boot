package testsjpa;

import entities.ClasseEntity;
import entities.EleveEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Scanner;

public class TestCreaEleve {
    private static final String PERSISTENCE_UNIT_NAME = "projetUnit";
    private static EntityManagerFactory factory;
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        System.out.println("Entrer le matricule : ");
        String matricule = sc.nextLine();
        System.out.println("Entrer le nom : ");
        String nom = sc.nextLine();
        System.out.println("Entrer le prénom : ");
        String prenom = sc.nextLine();
        System.out.println("Entrer le numéro de tel : ");
        String tel = sc.nextLine();
        System.out.println("Entrer l'année : ");
        int a = sc.nextInt();
        System.out.println("Entrer le mois : ");
        int m = sc.nextInt();
        System.out.println("Entrer le jour : ");
        int d = sc.nextInt();
        sc.skip("\n");

        Date date = new Date(a,m,d);

        int id = sc.nextInt();
        ClasseEntity cl = em.find(ClasseEntity.class, id);
        if(cl==null) System.out.println("identifiant client inconnu");
        else {
            EleveEntity e = new EleveEntity();
            e.setMatricule(matricule);
            e.setNom(nom);
            e.setPrenom(prenom);
            e.setTel(tel);
            e.setDateNaissance(date);
            try {
                em.getTransaction().begin();
                em.persist(e);
                em.getTransaction().commit();
            } catch(Exception ex){
                em.getTransaction().rollback();
                System.out.println("erreur de création : "+ex.getMessage());
            }
        }
        em.close();
    }
}
