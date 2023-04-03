package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;

  public String description;

  public boolean completed_status;

  @ManyToOne
  @JoinColumn(name = "card_id")
  public Card card;

  public Task(){}

  public Task(String description){
    this.description = description;
    this.completed_status = false;
  }

  public long getId() {return id;  }

  public void setDescription(String description) {
    this.description = description;
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
