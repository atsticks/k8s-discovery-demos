package org.jacpfx.kube;

import javax.ejb.Remote;
import java.util.Collection;


/**
 * Created by atsticks on 20.03.17.
 */
@Remote
public interface PersonService {

    Collection<Person> getAll();

    Person get(String name);

    void create(Person person);

    void delete(Person person);
}
