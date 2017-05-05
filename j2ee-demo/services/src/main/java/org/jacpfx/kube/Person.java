package org.jacpfx.kube;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * @author Sebastien Deleuze
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Person implements Serializable {

  private String id;

  private String firstname;

  private String lastname;


  public Person() {
  }

  public Person(String id, String firstname, String lastname) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
  }


  public String getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Person person = (Person) o;

    if (id != null ? !id.equals(person.id) : person.id != null) {
      return false;
    }
    if (firstname != null ? !firstname.equals(person.firstname) :
        person.firstname != null) {
      return false;
    }
    return lastname != null ? lastname.equals(person.lastname) :
        person.lastname == null;

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id='" + id + '\'' +
        ", firstname='" + firstname + '\'' +
        ", lastname='" + lastname + '\'' +
        '}';
  }
}