package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

  public Long cardlistID;
  public Long position;
  public Card(){
      // nothing really
  }

  public Card(String cardName, String cardDescription, Cardlist cardlist){
    this.cardName = cardName;
    this.cardDescription = cardDescription;
    this.cardlist = cardlist;
    tagList = new ArrayList<>();
    taskList = new ArrayList<>();
    this.cardlistID=cardlist.getId();
  }

  public Card(String cardName, String cardDescription){
    this.cardName = cardName;
    this.cardDescription = cardDescription;
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

  public String getCardDescription() {
    return cardDescription;
  }

  public long getId() {
    return id;
  }

  public Long getCardlistID() {
    return cardlistID;
  }

  public void setCardlistID(Long cardlistID) {
    this.cardlistID = cardlistID;
  }

  public Long getPosition() {
    return position;
  }

  public void setPosition(Long position) {
    this.position = position;
  }

  @JsonManagedReference
  public List<Task> getTaskList() {
    return taskList;
  }

  @JsonManagedReference
  public List<Tag> getTagList() {
    return tagList;
  }

  public void setCardList(Cardlist cardlist){
    this.cardlist = cardlist;
    this.cardlistID = cardlist.getId();
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
