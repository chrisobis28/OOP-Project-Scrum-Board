package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cardlist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;

  public String cardlistName;

  @ManyToOne
  @JoinColumn(name = "board_id")
  public Board board;

  @OneToMany(mappedBy = "cardlist", cascade = CascadeType.ALL)
  public Set<Card> cardSet = new HashSet<>();

  public Cardlist(){

  }

  public Cardlist(String cardlistName){
    this.cardlistName = cardlistName;
  }

  public void setCardlistName(String cardlistName) {
    this.cardlistName = cardlistName;
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
