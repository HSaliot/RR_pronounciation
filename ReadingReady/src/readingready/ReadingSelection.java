package readingready;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "R2_READINGSELECTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReadingSelection.findAll", query = "SELECT r FROM ReadingSelection r"),
    @NamedQuery(name = "ReadingSelection.findByTitle", query = "SELECT r FROM ReadingSelection r WHERE r.title = :title")})
public class ReadingSelection implements Serializable { 
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "selection")
    private List<Evaluation> evaluations;
    
    private String title;

    public ReadingSelection() {
    }
    
    public ReadingSelection(Integer id) {
        this.id = id;
    }

    public ReadingSelection(String title) {
        this.title = title;
    }
    
    public ReadingSelection(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String toString(){
        return title;
    }
}
