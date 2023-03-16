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
@Table(name = "tags")
=======
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
<<<<<<< HEAD
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
=======
  public long id;

  public String backgroundColour = "#FFFFFF";

  public String fontColour = "#000000";

  public String tagName;

  @ManyToOne
  @JoinColumn(name = "board_id")
  public Board board;

  @ManyToOne
  @JoinColumn(name = "card_id")
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
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
