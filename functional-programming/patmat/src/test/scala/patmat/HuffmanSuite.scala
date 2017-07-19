package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
	trait TestTrees {
		val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
		val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
	}


  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }


  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times") {
    assert(times(List('a', 'b', 'a')) === List(('a', 2), ('b', 1)))
  }


  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
    assert(makeOrderedLeafList(List(('a', 10), ('b', 3), ('x', 1), ('h', 5))) === List(Leaf('x', 1), Leaf('b', 3), Leaf('h', 5), Leaf('a', 10)))
  }


  test("combine of some leaf list") {
    new TestTrees {
      val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
      assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
      assert(combine(List(t1)) === List(t1))
    }
  }


  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
      assert(decode(t2, encode(t2)("dab".toList)) === "dab".toList)
      assert(decode(frenchCode, encode(frenchCode)("huffmanestcool".toList)) === "huffmanestcool".toList)
    }
  }

  test("singleton tree list should only contain one code tree in list") {
    new TestTrees {
      assert(singleton(List(t1)))
      assert(!singleton(List(t1, t2)))
    }
  }

  test("decoded secret") {
    assert(decodedSecret === List('h','u','f','f','m','a','n','e','s','t','c','o','o','l'))
  }

  test("quick encode") {
    assert(decode(frenchCode, quickEncode(frenchCode)("huffmanestcool".toList)) === "huffmanestcool".toList)
  }

}
