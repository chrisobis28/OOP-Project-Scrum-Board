package commons;

<<<<<<< HEAD
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
=======
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
<<<<<<< HEAD
@Table(name = "cards")
=======
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
<<<<<<< HEAD
  @Column(name ="card_id")
=======
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
  public long id;

  public String cardName;
  public String cardDescription;

<<<<<<< HEAD
  @OneToMany(targetEntity = Task.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "cardTask_fk", referencedColumnName = "card_id")
  public List<Task> taskList;

  @OneToMany(targetEntity = Tag.class, cascade = CascadeType.ALL)
  //@JoinColumn(name = "cardTag_fk", referencedColumnName = "card_id")
  public List<Tag> tagList;

=======
  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
  public List<Task> taskList;

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
  public List<Tag> tagList;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cardlist_id")
  public Cardlist cardlist;

>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
  public Card(){

  }

  public Card(String cardName, String cardDescription){
    this.cardName = cardName;
    this.cardDescription = cardDescription;
<<<<<<< HEAD
=======
    tagList = new ArrayList<>();
    taskList = new ArrayList<>();
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
  }

  public void setCardDescription(String cardDescription) {
    this.cardDescription = cardDescription;
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
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
