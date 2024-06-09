package entities;

import entities.Package;
import entities.SubscribtionPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-15T23:36:03")
@StaticMetamodel(Subscribtion.class)
public class Subscribtion_ { 

    public static volatile SingularAttribute<Subscribtion, Date> date;
    public static volatile SingularAttribute<Subscribtion, Integer> price;
    public static volatile SingularAttribute<Subscribtion, SubscribtionPK> subscribtionPK;
    public static volatile SingularAttribute<Subscribtion, Package> package11;

}