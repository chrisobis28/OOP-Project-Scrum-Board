package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;

  public String backgroundColour = "#FFFFFF";

  public String fontColour = "#000000";

  public String tagName;

  @ManyToOne
  @JoinColumn(name = "board_id")
  public Board board;

  @ManyToOne
  @JoinColumn(name = "card_id")
  public Card card;

  public Tag(){}

  public Tag(String tagName){
    this.tagName = tagName;
  }

  public void setBackgroundColour(String backgroundColour) {
    this.backgroundColour = backgroundColour;
  }

  public void setFontColour(String fontColour) {
    this.fontColour = fontColour;
  }

  public Board getBoard() {
    return board;
  }

  @JsonBackReference
  public Card getCard() {
    return card;
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
