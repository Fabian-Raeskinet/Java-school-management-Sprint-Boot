package modele;

import entities.ClasseEntity;
import entities.EleveEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.List;

public class ModeleEleve implements InterfModeleEleve {
    private static final String PERSISTENCE_UNIT_NAME = "projetUnit";
    private static EntityManagerFactory factory;
    private EntityManager em;

    public ModeleEleve() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
    }

    @Override
    public void create(EleveEntity o) throws Exception {
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
    public EleveEntity read(int id) throws Exception {
        EleveEntity e = em.find(EleveEntity.class, id);
        if (e == null) throw new Exception("identifiant eleve inconnu");
        else em.refresh(e);
        return e;
    }

    @Override
    public void update(EleveEntity e, EleveEntity ne) throws Exception {

        try {
            e = read(e.getIdEleve());
            em.getTransaction().begin();
            if (ne.getMatricule() != null) e.setMatricule(ne.getMatricule());
            if (ne.getNom() != null) e.setNom(ne.getNom());
            if (ne.getPrenom() != null) e.setPrenom(ne.getPrenom());
            if (ne.getTel() != null) e.setTel((ne.getTel()));
            if (ne.getDateNaissance() != null) e.setDateNaissance(ne.getDateNaissance());
            if (ne.getClasseByIdClasse() != null) e.setClasseByIdClasse(ne.getClasseByIdClasse());
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new Exception("erreur de mise à jour : " + ex.getMessage());
        }
    }

    @Override
    public void del(EleveEntity o) throws Exception {

        try {
            o = read(o.getIdEleve());
            em.getTransaction().begin();
            em.remove(o);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("erreur d'effacement : " + e.getMessage());
        }
    }

    @Override
    public List<EleveEntity> all() {
        Query q = em.createQuery("select c from EleveEntity c");
        return (List<EleveEntity>) q.getResultList();
    }

    @Override
    public EleveEntity rech(String matricule) throws Exception {
        Query q = em.createQuery("select c from EleveEntity c where c.matricule= :matricule");
        q.setParameter("matricule", matricule);
        List result = q.getResultList();
        if (result.isEmpty()) throw new Exception("aucun record trouvé");
        EleveEntity e = (EleveEntity) result.get(0);
        return e;
    }
    @Override
    public List<EleveEntity> rechNom(String nom) throws Exception {
        Query q = em.createQuery("select c from EleveEntity c where c.nom = :nom");
        q.setParameter("nom", nom);
        List result = q.getResultList();
        if (result.isEmpty()) throw  new Exception("aucun record trouvé");
        return result;
    }
    @Override
    public List<EleveEntity> rechDate(Date date) throws Exception {
        Query q = em.createQuery("select c from EleveEntity c where c.dateNaissance >= :date");
        q.setParameter("date", date);
        List result = q.getResultList();
        if (result.isEmpty()) throw  new Exception("aucun record trouvé");
        return result;
    }

    @Override
    public void close() {
        em.close();
    }
}
