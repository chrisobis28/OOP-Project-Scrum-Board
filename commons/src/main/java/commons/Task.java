package commons;

<<<<<<< HEAD
import javax.persistence.CascadeType;
import javax.persistence.Column;
=======
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
<<<<<<< HEAD
import javax.persistence.ManyToOne;
import javax.persistence.Table;
=======
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
<<<<<<< HEAD
@Table(name = "tasks")
=======
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
<<<<<<< HEAD
  @Column(name ="task_id")
  public long id;

  @Column(name = "description")
  public String description;

  @Column(name = "completed_status")
  public boolean completed_status;

  @ManyToOne(targetEntity = Card.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "cardTask_fk", referencedColumnName = "task_id")
=======
  public long id;

  public String description;

  public boolean completed_status;

  @ManyToOne
  @JoinColumn(name = "card_id")
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
  public Card card;

  public Task(){

  }

  public Task(String description){
    this.description = description;
    this.completed_status = false;
  }

<<<<<<< HEAD
=======
  public void setDescription(String description) {
    this.description = description;
  }

>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
  public void complete(){
    completed_status = true;
  }

  @Override
  public boolean equals(Object obj){
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode(){
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString(){
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
