package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "eleve", schema = "public", catalog = "da3ce2s375ig5")
public class EleveEntity {
    private int idEleve;
    private String matricule;
    private String nom;
    private String prenom;
    private String tel;
    private Date dateNaissance;
    @JsonIgnoreProperties("elevesByIdClasse")
    private ClasseEntity classeByIdClasse;

    public EleveEntity() {
    }

    public EleveEntity(int idEleve, String matricule, String nom, String prenom, String tel, Date dateNaissance, ClasseEntity classeByIdClasse) {
        this.idEleve = idEleve;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.dateNaissance = dateNaissance;
        this.classeByIdClasse = classeByIdClasse;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eleve", nullable = false)
    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    @Basic
    @Column(name = "matricule", nullable = false, length = -1)
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @Basic
    @Column(name = "nom", nullable = false, length = -1)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom", nullable = false, length = -1)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "tel", nullable = true, length = -1)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "date_naissance", nullable = false)
    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EleveEntity that = (EleveEntity) o;
        return idEleve == that.idEleve &&
                Objects.equals(matricule, that.matricule) &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom) &&
                Objects.equals(tel, that.tel) &&
                Objects.equals(dateNaissance, that.dateNaissance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEleve, matricule, nom, prenom, tel, dateNaissance);
    }

    @ManyToOne
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false)
    public ClasseEntity getClasseByIdClasse() {
        return classeByIdClasse;
    }

    public void setClasseByIdClasse(ClasseEntity classeByIdClasse) {
        this.classeByIdClasse = classeByIdClasse;
    }

    @Override
    public String toString() {
        return "EleveEntity{" +
                "idEleve=" + idEleve +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", classeByIdClasse=" + classeByIdClasse +
                '}';
    }
}
