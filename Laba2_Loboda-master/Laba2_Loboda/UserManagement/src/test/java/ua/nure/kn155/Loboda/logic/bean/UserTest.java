package ua.nure.kn155.Loboda.logic.bean;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserTest {

  private User user;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    user = new User();
  }

  @Test
  public void getFullNameShouldReturnFullName() {
    // given
    user.setFirstName("a");
    user.setLastName("b");
    // when-then
    assertEquals("a, b", user.getFullName());
  }

  @Test
  public void getFullNameShouldThrowExceptionOnFirstNameAbsent() {
    // given
    user.setLastName("b");
    // when-then
    expectedException.expect(IllegalStateException.class);
    user.getFullName();
  }

  @Test
  public void getFullNameShouldThrowExceptionOnLastNameAbsent() {
    // given
    user.setFirstName("a");
    // when-then
    expectedException.expect(IllegalStateException.class);
    user.getFullName();
  }

  @Test
  public void getAgeShouldThrowExceptionOnBirthDateIsFuture() {
    // given
    Calendar futureDate = Calendar.getInstance();
    //setting future date
    futureDate.add(Calendar.YEAR, 2);
    user.setDateBirth(futureDate.getTime());
    // when-then
    expectedException.expect(IllegalStateException.class);
    user.getAge();
  }
  
  @Test
  public void getAgeShouldThrowExceptionOnNullBirthDate() {
    // given
    // when-then
    expectedException.expect(IllegalStateException.class);
    user.getAge();
  }
  
  @Test
  public void getAgeShouldReturnValidAge() {
    // given
    Calendar dateBirth = Calendar.getInstance();
    //setting date of birth as current date -2 years. why 2? don't know
    dateBirth.add(Calendar.YEAR, -2);
    user.setDateBirth(dateBirth.getTime());
    // when-then
    assertEquals(2,user.getAge());
  }

  @After
  public void tearDown() throws Exception {
    // nothing to clean
  }
}
