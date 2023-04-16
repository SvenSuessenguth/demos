package org.cc.testing.dbunit.service;

import org.cc.testing.dbunit.model.Gender;
import org.cc.testing.dbunit.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.cc.testing.dbunit.model.Gender.*;

class PersonsRepositoryIT extends AbstractDbUnitJpaIT {

  private PersonsRepository repository;

  @BeforeEach
  public void beforeAll() throws Exception {
    super.initDatabase("test-data.xml");
    repository = new PersonsRepository(entityManager);
  }

  @Test
  void read_byId() {
    var person = repository.read(1L);
    assertThat(person.getGender()).isEqualTo(MALE);
  }

  public static Stream<Arguments> read_byGender() {
    return Stream.of(
      Arguments.of(MALE, 2),
      Arguments.of(UNKNOWN, 0),
      Arguments.of(FEMALE, 3)
    );
  }

  @ParameterizedTest
  @MethodSource
  void read_byGender(Gender gender, int expected) {
    var allPersons = repository.read(0, 100, gender);
    assertThat(allPersons).hasSize(expected);
  }

  @Test
  void create() {
    var person = new Person("firstName", "lastName");
    repository.create(person);
    assertThat(person.getId()).isNotNull();
  }

  @Test
  void count() {
    var count = repository.count();
    assertThat(count).isEqualTo(5);
  }
}