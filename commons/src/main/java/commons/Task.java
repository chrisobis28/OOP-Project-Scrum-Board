package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

  public Task(String description, Card card){
    this.description = description;
    this.card = card;
    this.completed_status = false;
  }

  public Task(String description){
    this.description = description;
  }

  public long getId() {return id;  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void complete(){
    completed_status = !completed_status;
  }

  public boolean isCompleted_status() {
    return completed_status;
  }

  public String getDescription() {
    return description;
  }

  @JsonBackReference
  public Card getCard(){
    return this.card;
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
