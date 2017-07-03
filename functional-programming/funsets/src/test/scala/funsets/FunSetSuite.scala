package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1: Set = singletonSet(1)
    val s2: Set = singletonSet(2)
    val s3: Set = singletonSet(3)
    val longSet1: Predef.Set[Int] = Set(1, 2, 3, 10, 9)
    val longSet2: Predef.Set[Int] = Set(10, 3, 13, 14)
    val longSet3: Predef.Set[Int] = longSet1 ++ Set(14, 50)
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s: Set = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains elements that are in both sets") {
    new TestSets {
      val s: Set = intersect(longSet1, longSet2)
      assert(contains(s, 3))
      assert(contains(s, 10))
      assert(!contains(s, 1))
      assert(!contains(s, 13))
    }
  }

  test("difference contains elements found in the first set that is not in the other set") {
    new TestSets {
      val s: Set = diff(longSet1, longSet2)
      assert(contains(s, 1))
      assert(contains(s, 2))
      assert(contains(s, 9))
      assert(!contains(s, 13))
      assert(!contains(s, 14))
    }
  }

  test("filter returns set of elements contained in predicate") {
    new TestSets {
      val s: Set = filter(s1, Set(1, 2, 3))
      assert(contains(s, 1))
      assert(!contains(s, 2))
      val ss: Set = filter(Set(1, 2, 3), Set(1, 3, 4))
      assert(contains(ss, 1))
      assert(contains(ss, 3))
      assert(!contains(ss, 2))
    }
  }

  test("for all tests whether given predicate is true for all sets") {
    new TestSets {
      assert(forall(longSet1, longSet3))
      assert(!forall(longSet2, longSet3))
    }
  }

  test("exists checks whether there exists a bounded integer within a set that satisfies the predicate") {
    new TestSets {
      assert(exists(longSet1, longSet2))
      assert(exists(s1, longSet1))
      assert(!exists(s1, longSet2))
      assert(!exists(longSet1, i => i == 4))
    }
  }

  test("map returns a new Set of elements with added value") {
    new TestSets {
      val sAdd3: Set = map(s1, i => i + 3)
      assert(contains(sAdd3, 4))
      val sTimes2: Set = map(longSet1, i => i * 2)
      assert(forall(sTimes2, Set(2, 4, 6, 20, 18)))

    }
  }
}
