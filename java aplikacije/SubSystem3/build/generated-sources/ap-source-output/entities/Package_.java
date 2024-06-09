package entities;

import entities.Subscribtion;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-16T10:14:24")
@StaticMetamodel(Package.class)
public class Package_ { 

    public static volatile SingularAttribute<Package, Integer> price;
    public static volatile SingularAttribute<Package, Integer> packageID;
    public static volatile SingularAttribute<Package, String> name;
    public static volatile SingularAttribute<Package, Subscribtion> subscribtion;

}