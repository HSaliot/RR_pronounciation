package readingready;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hannah Saliot
 */
@Entity
@Table(name = "R2_EVALUATIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluation.findAll", query = "SELECT e FROM Evaluation e")
    , @NamedQuery(name = "Evaluation.findById", query = "SELECT e FROM Evaluation e WHERE e.id = :id")
    , @NamedQuery(name = "Evaluation.findByDatedone", query = "SELECT e FROM Evaluation e WHERE e.dateDone = :dateDone")})
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Temporal(TemporalType.DATE)
    private Date dateDone;
    
    @JoinColumn(name = "STUDENT", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Student student;
    
    @JoinColumn(name = "SELECTION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ReadingSelection selection;

    public Evaluation() {
    }

    public Evaluation(Integer id) {
        this.id = id;
    }

    public Evaluation(Integer id, Date dateDone) {
        this.id = id;
        this.dateDone = dateDone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatedone() {
        return dateDone;
    }

    public void setDatedone(Date dateDone) {
        this.dateDone = dateDone;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    @Override
    public String toString() {
        return "readingready.Evaluation[ id=" + id + " ]";
    }
    
}
