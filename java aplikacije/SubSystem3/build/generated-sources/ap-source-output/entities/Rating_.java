package entities;

import entities.RatingPK;
import entities.User;
import entities.Video;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-18T11:45:48")
@StaticMetamodel(Rating.class)
public class Rating_ { 

    public static volatile SingularAttribute<Rating, Date> date;
    public static volatile SingularAttribute<Rating, User> user1;
    public static volatile SingularAttribute<Rating, Video> video1;
    public static volatile SingularAttribute<Rating, RatingPK> ratingPK;
    public static volatile SingularAttribute<Rating, Integer> grade;

}