package commons;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity

public class Board {


    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)

    public long id;


    public String boardName;


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)

    public Set<Cardlist> cardlistList;


    @OneToMany(mappedBy = "board")

    public Set<Tag> tagList;


    public String boardBackgroundColour = "#FFFFFF";


    public String listsBackgroundColour = "#FFFFFF";


    public Board(){


    }


    public Board(String boardName){

        this.boardName = boardName;

        tagList = new HashSet<>();

        cardlistList = new HashSet<>();

    }


    public void setBoardName(String boardName) {

        this.boardName = boardName;

    }



    public void setBoardBackgroundColour(String boardBackgroundColour) {

        this.boardBackgroundColour = boardBackgroundColour;

    }


    public void setListsBackgroundColour(String listsBackgroundColour) {

        this.listsBackgroundColour = listsBackgroundColour;

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


