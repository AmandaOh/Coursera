package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (c <= 0 || r <= 1 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    val onlyParentheses = chars.filter(Set('(', ')'))

    def countParentheses(count: Int, onlyParentheses: List[Char]): Int = {
      if (onlyParentheses.isEmpty || count < 0) return count
      if (onlyParentheses.head == '(')
        countParentheses(count + 1, onlyParentheses.tail)
      else
        countParentheses(count - 1, onlyParentheses.tail)
    }

    countParentheses(0, onlyParentheses) == 0
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {

    def countWays(money: Int, coins: List[Int]): Int = {
      if (money == 0) 1
      else if (money > 0 && coins.nonEmpty)
        countWays(money - coins.head, coins) + countWays(money, coins.tail)
      else
        0
    }

    countWays(money, coins.sorted)
  }

}
