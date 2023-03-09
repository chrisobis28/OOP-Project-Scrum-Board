package commons;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "cards")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name ="card_id")
  public long id;

  public String cardName;
  public String cardDescription;

  @OneToMany(targetEntity = Task.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "cardTask_fk", referencedColumnName = "card_id")
  public List<Task> taskList;

  @OneToMany(targetEntity = Tag.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "cardTag_fk", referencedColumnName = "card_id")
  public List<Tag> tagList;

  public Card(){

  }

  public Card(String cardName, String cardDescription){
    this.cardName = cardName;
    this.cardDescription = cardDescription;
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
