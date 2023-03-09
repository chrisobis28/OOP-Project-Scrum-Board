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
@Table(name = "boards")
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name ="board_id")
  public long id;

  @Column(name ="board_name")
  public String boardName;

  @OneToMany(targetEntity = Cardlist.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "boardCardlist_fk", referencedColumnName = "board_id")
  public List<Cardlist> cardlistList;

  @OneToMany(targetEntity = Tag.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "boardTag_fk", referencedColumnName = "board_id")
  public List<Tag> tagList;

  @Column(name = "board_background_colour")
  public String boardBackgroundColour = "#FFFFFF";

  @Column(name = "lists_background_colour")
  public String listsBackgroundColour = "#FFFFFF";

  public void setBoardColour(String boardColour) {
    this.boardBackgroundColour = boardColour;
  }

  public void setListsColour(String listsColour) {
    this.listsBackgroundColour = listsColour;
  }

  public Board(){

  }

  public Board(String boardName){
    this.boardName = boardName;
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
