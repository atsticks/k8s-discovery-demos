package org.jacpfx.kube.domain.model;

import java.util.List;

/**
 * Created by amo on 22.06.17.
 */
public class PersonWrapper {
  final List<Person> personList;

  public PersonWrapper(List<Person> personList) {
    this.personList = personList;
  }


  public List<Person> getPersonList() {
    return personList;
  }
}
