package commons;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cardlist{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String cardlistName;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "board_id")
  public Board board;

  @OneToMany(mappedBy = "cardlist", cascade = CascadeType.ALL)
  public Set<Card> cardSet = new HashSet<>();

  public Cardlist(){

  }

  public Cardlist(String cardlistName){
    this.cardlistName = cardlistName;
  }

  /**
   * Constructor for a card list with a given id and title.
   *
   * @param id The id for the list.
   * @param cardlistName The title of the list.
   */
  public Cardlist(long id, String cardlistName){
    this.id = id;
    this.cardlistName = cardlistName;
  }

  /**
   * Constructor for a card list attached to a board.
   *
   * @param cardlistName
   * @param board
   */
  public Cardlist(String cardlistName, Board board){
    this.board = board;
    this.cardlistName = cardlistName;
  }

  public void setCardlistName(String cardlistName) {
    this.cardlistName = cardlistName;
  }

  /**
   * Getter for the card list title.
   *
   * @return the card list title.
   */
  public String getCardlistName() {
    return cardlistName;
  }

  /**
   * Getter for the Id of the card list.
   *
   * @return Card list Id.
   */
  public long getId() {
    return id;
  }

  /**
   * Getter for the set of cards in the list.
   *
   * @return the set of cards.
   */
  @JsonManagedReference
  public Set<Card> getCardSet() {
    return cardSet;
  }

  /**
   * Getter for the board of the card list.
   *
   * @return board in which the card list resides.
   */
  @JsonBackReference
  public Board getBoard() { return board; }

  public void removeCard(Card card){
    cardSet.remove(card);
  }

  public void addCard(Card card){
    cardSet.add(card);
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
