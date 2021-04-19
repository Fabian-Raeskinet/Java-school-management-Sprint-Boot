package testsjpa;

import entities.EleveEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class testAllEleve {
    private static final String PERSISTANCE_UNIT_NAME = "projetUnit";
    private static EntityManagerFactory factory;
    public static void main(String[] args){
        factory = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        Query q = em.createQuery("select e from EleveEntity e");
        List<EleveEntity> result = q.getResultList();
        if(result.isEmpty()) System.out.println("aucun record");
        for(EleveEntity e : result){
            System.out.println(e.toString());
        }
    }
}
