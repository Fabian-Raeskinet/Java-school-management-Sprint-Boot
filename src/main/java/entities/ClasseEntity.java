package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "classe", schema = "public", catalog = "da3ce2s375ig5")
public class ClasseEntity {
    private int idClasse;
    private String sigle;
    private int annee;
    private String titulaire;
    @JsonIgnoreProperties("classeByIdClasse")
    private Collection<EleveEntity> elevesByIdClasse;


    public ClasseEntity(){

    }
    public ClasseEntity(int idClasse, String sigle, int annee, String titulaire) {
        this.idClasse = idClasse;
        this.sigle = sigle;
        this.annee = annee;
        this.titulaire = titulaire;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classe", nullable = false)
    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    @Basic
    @Column(name = "sigle", nullable = false, length = -1)
    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    @Basic
    @Column(name = "annee", nullable = false)
    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    @Basic
    @Column(name = "titulaire", nullable = true, length = -1)
    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClasseEntity that = (ClasseEntity) o;
        return idClasse == that.idClasse &&
                annee == that.annee &&
                Objects.equals(sigle, that.sigle) &&
                Objects.equals(titulaire, that.titulaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClasse, sigle, annee, titulaire);
    }

    @OneToMany(mappedBy = "classeByIdClasse")
    public Collection<EleveEntity> getElevesByIdClasse() {
        return elevesByIdClasse;
    }

    public void setElevesByIdClasse(Collection<EleveEntity> elevesByIdClasse) {
        this.elevesByIdClasse = elevesByIdClasse;
    }

    @Override
    public String toString() {
        return "ClasseEntity{" +
                "idClasse=" + idClasse +
                ", sigle='" + sigle + '\'' +
                ", annee=" + annee +
                ", titulaire='" + titulaire + '\'' +
                '}';
    }
}
