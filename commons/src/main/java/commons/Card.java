package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;

  public String cardName;
  public String cardDescription;

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
  public List<Task> taskList;

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
  public List<Tag> tagList;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cardlist_id")
  public Cardlist cardlist;

  public Card(){

  }

  public Card(String cardName, String cardDescription){
    this.cardName = cardName;
    this.cardDescription = cardDescription;
    tagList = new ArrayList<>();
    taskList = new ArrayList<>();
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public void setCardDescription(String cardDescription) {
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
