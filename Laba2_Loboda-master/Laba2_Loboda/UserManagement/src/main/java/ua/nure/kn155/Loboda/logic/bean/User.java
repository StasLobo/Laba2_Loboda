package ua.nure.kn155.Loboda.logic.bean;

import java.util.Calendar;
import java.util.Date;

public class User {

  private Long id;
  private String firstName;
  private String lastName;
  private Date dateBirth;

  public User() {}

  public User(Long id, String firstName, String lastName, Date dateBirth) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateBirth = dateBirth;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getDateBirth() {
    return dateBirth;
  }

  public void setDateBirth(Date dateBirth) {
    this.dateBirth = dateBirth;
  }

  public String getFullName() throws IllegalStateException {
    if (firstName == null || lastName == null)
      throw new IllegalStateException("first/last name must be NOT null");
    StringBuilder fullName = new StringBuilder();
    fullName.append(firstName).append(", ").append(lastName);
    return fullName.toString();
  }

  public int getAge() throws IllegalStateException {
    if(dateBirth == null) {
      throw new IllegalStateException("Birth date is not defined!");
    }
    Calendar currentDate = Calendar.getInstance();
    Calendar dateBirth = Calendar.getInstance();
    dateBirth.setTime(this.dateBirth);

    int years = currentDate.get(Calendar.YEAR) - dateBirth.get(Calendar.YEAR);
    if (years < 0) {
      throw new IllegalStateException("Age is negative!");
    }
    return years;
  }

}
