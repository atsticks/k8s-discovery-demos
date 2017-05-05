package org.jacpfx.kube;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by atsticks on 20.03.17.
 */
@Stateless
public class PersonServiceBean implements PersonService {

    private static Map<String, Person> persons = new ConcurrentHashMap<>();


    public PersonServiceBean(){
        for(Person p: new Person[]{
                new Person("1", "Eric", "Foo"),
                new Person("2", "Raymond", "Bar"),
                new Person("3", "Paul", "Baz")}) {
            persons.put(p.getId(), p);
        }
    }

    @Override
    public Collection<Person> getAll() {
        return persons.values();
    }

    @Override
    public Person get(String id) {
        return persons.get(id);
    }

    @Override
    public void create(Person person) {
        Person existing = persons.putIfAbsent(person.getId(), person);
        if(existing!=null){
            throw new IllegalStateException("Person already existing: " + person);
        }
    }

    @Override
    public void delete(Person person) {
        persons.remove(person.getId());
    }
}
