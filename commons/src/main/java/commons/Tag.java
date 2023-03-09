package commons;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "tags")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name ="tag_id")
  public long id;

  @Column(name = "background_colour")
  public String backgroundColour = "#FFFFFF";

  @Column(name = "font_colour")
  public String fontColour = "#000000";

  @Column(name = "tag_name")
  public String tagName;

  @ManyToOne(targetEntity = Board.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "boardTag_fk", referencedColumnName = "tag_id")
  public Board board;

  @ManyToOne(targetEntity = Card.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "cardTag_fk", referencedColumnName = "tag_id")
  public Card card;

  public Tag(){

  }

  public Tag(String tagName){
    this.tagName = tagName;
  }

  public void setBackgroundColour(String backgroundColour) {
    this.backgroundColour = backgroundColour;
  }

  public void setFontColour(String fontColour) {
    this.fontColour = fontColour;
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
