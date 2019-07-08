package readingready;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hannah Saliot
 */
@Entity
@Table(name = "R2_STUDENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id"),
    @NamedQuery(name = "Student.findByLName", query = "SELECT s FROM Student s WHERE s.lName = :lName"),
    @NamedQuery(name = "Student.findByLevel", query = "SELECT s FROM Student s WHERE s.level = :level")})
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    private String lName;
    private String fNameEtc;
    private int level;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<Evaluation> evaluations;
  
    public Student() {
    }
    
    public Student(Integer id) {
        this.id = id;
    }

    public Student(Integer id, String fNameEtc, int level) {
        this.id = id;
        this.fNameEtc = fNameEtc;
        this.level = level;
    }

    public Student(String lName, String fNameEtc, int level) {
        this.lName = lName;
        this.fNameEtc = fNameEtc;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getFnameetc() {
        return fNameEtc;
    }

    public void setFnameEtc(String fNameEtc) {
        this.fNameEtc = fNameEtc;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "readingready.Student[ " + lName + ", " + fNameEtc + " ]";
    }    
}
