package stratio.shoppingCart.database

import stratio.shoppingCart.entities.Product

object DiscountDatabase {

  type Discount = Vector[(Product, Int)] => Double

  private val discounts : Vector[(String, Discount)] = Vector(
    "Apples 10% off" -> discountApples,
    "2 tins of soup and a loaf of bread for half price" -> discountSoupAndBread
  )

  /** Buy 2 tins of soup and get a loaf of bread for half price
    *
    * @return discount in € (positive number)
    */
  private def discountSoupAndBread: Discount = {
    products: Vector[(Product, Int)] =>
      val numSoups: Int = products
        .filter(_._1.name == "Soup").map(_._2)
        .headOption.getOrElse(0)

      val (breadPrice: Double, numBreads: Int) = products
        .filter(_._1.name == "Bread").map{case (p, q) => (p.price, q)}
        .headOption.getOrElse((0.0, 0))

      val numDiscounts: Int = Math.min(numBreads, numSoups / 2)

      numDiscounts * breadPrice * 0.1
  }

  /** Apples have a 10% discount off their normal price this week
    *
    * @return discount in € (positive number)
    */
  private def discountApples: Discount = {
    products: Vector[(Product, Int)] =>
      products.map{ case(product, quantity) =>
        product.name match {
          case "Apples" =>
            product.price * quantity * 0.1
          case _ => 0.0
        }
      }.sum
  }

  def applyDiscounts: Vector[(Product, Int)] => Vector[(String, Double)] = {
    products: Vector[(Product, Int)] => discounts.map{case (description, discount) =>
      (description, discount(products))
    }.filter(_._2 > 0)
  }
}
