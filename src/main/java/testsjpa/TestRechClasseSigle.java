package testsjpa;

import entities.ClasseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class TestRechClasseSigle {
    private static final String PERSISTENCE_UNIT_NAME = "projetUnit";
    private static EntityManagerFactory factory;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        System.out.println("sigle recherché :");
        String sigle = sc.nextLine();
        Query q = em.createQuery("select c from ClasseEntity c where c.sigle= :sigle");
        q.setParameter("sigle", sigle);
        List<ClasseEntity> result = q.getResultList();
        if (result.isEmpty()) System.out.println("aucun record trouvé");
        for (ClasseEntity cl : result) {
            System.out.println(cl.toString());
        }
        em.close();
    }
}
