package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
      // nothing really
  }

  public Card(String cardName, String cardDescription, Cardlist cardlist){
    this.cardName = cardName;
    this.cardDescription = cardDescription;
    this.cardlist = cardlist;
    tagList = new ArrayList<>();
    taskList = new ArrayList<>();
  }

  public String getCardName() {
    return cardName;
  }

  @JsonBackReference
  public Cardlist getCardlist() {
    return cardlist;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public long getId() {
    return id;
  }

  public void setCardList(Cardlist cardlist){
    this.cardlist = cardlist;
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
