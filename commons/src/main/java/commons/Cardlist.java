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
@Table(name = "cardlists")
public class Cardlist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name ="cardlist_id")
  public long id;

  @Column(name ="cardlist_name")
  public String cardlistName;

  @ManyToOne(targetEntity = Board.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "boardCardlist_fk", referencedColumnName = "cardList_id")
  public Board board;

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
