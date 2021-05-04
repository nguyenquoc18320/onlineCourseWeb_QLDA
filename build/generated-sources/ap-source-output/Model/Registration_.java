package Model;

import Model.Course;
import Model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-03-15T21:14:58")
@StaticMetamodel(Registration.class)
public class Registration_ { 

    public static volatile SingularAttribute<Registration, Integer> registrationId;
    public static volatile SingularAttribute<Registration, Course> course;
    public static volatile SingularAttribute<Registration, User> user;

}