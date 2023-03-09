package commons;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "tasks")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name ="task_id")
  public long id;

  @Column(name = "description")
  public String description;

  @Column(name = "completed_status")
  public boolean completed_status;

  @ManyToOne(targetEntity = Card.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "cardTask_fk", referencedColumnName = "task_id")
  public Card card;

  public Task(){

  }

  public Task(String description){
    this.description = description;
    this.completed_status = false;
  }

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
