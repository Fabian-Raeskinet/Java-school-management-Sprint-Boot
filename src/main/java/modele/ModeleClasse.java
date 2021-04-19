package modele;

import entities.ClasseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ModeleClasse implements InterfModeleClasse {
    private static final String PERSISTENCE_UNIT_NAME = "projetUnit";
    private static EntityManagerFactory factory;
    private EntityManager em;

    public ModeleClasse() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
    }

    @Override
    public void create(ClasseEntity o) throws Exception {
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("erreur de création : " + e.getMessage());
        }
    }

    @Override
    public ClasseEntity read(int id) throws Exception {
        ClasseEntity cl = em.find(ClasseEntity.class, id);
        if (cl == null) throw new Exception("identifiant classe onconnu");
        else em.refresh(cl);
        return cl;
    }

    @Override
    public void update(ClasseEntity cl, ClasseEntity ncl) throws Exception {
        cl = read(cl.getIdClasse());
        try {
            em.getTransaction().begin();
            if (ncl.getSigle() != null) cl.setSigle(ncl.getSigle());
            if (ncl.getAnnee() != 0) cl.setAnnee(ncl.getAnnee());
            if (ncl.getTitulaire() != null) cl.setTitulaire(ncl.getTitulaire());
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("erreur de mise à jour : " + e.getMessage());
        }
    }

    @Override
    public void del(ClasseEntity cl) throws Exception {
        cl = read(cl.getIdClasse());
        try {
            em.getTransaction().begin();
            em.remove(cl);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("erreur d'effacement : " + e.getMessage());
        }
    }

    @Override
    public List<ClasseEntity> all() {
        Query q = em.createQuery("select c from ClasseEntity c");
        List<ClasseEntity> classeList = (List<ClasseEntity>) q.getResultList();
        return classeList;
    }
    @Override
    public ClasseEntity rech(String sigle) throws Exception {
        Query q = em.createQuery("select c from ClasseEntity c where c.sigle= :sigle");
        q.setParameter("sigle", sigle);
        List result = q.getResultList();
        if (result.isEmpty()) throw new Exception("aucun record trouvé");
        ClasseEntity cl = (ClasseEntity) result.get(0);
        return cl;
    }


    @Override
    public void close() {
        em.close();
    }
}
