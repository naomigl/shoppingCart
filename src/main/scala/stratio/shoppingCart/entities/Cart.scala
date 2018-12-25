package stratio.shoppingCart.entities

import stratio.shoppingCart.database.DiscountDatabase._

case class Cart(products: Vector[(Product, Int)]) {

  lazy val subtotal: Double = {
    products.map{ case (product, quantity) => product.price * quantity}.sum
  }

  lazy val discounts: Vector[(String, Double)] = { applyDiscounts(products) }

  lazy val base: Double = subtotal - discounts.map(_._2).sum

  lazy val taxes: Double = {
    products.map{ case(product, quantity) => product.price * product.taxPercentage /100 * quantity}.sum
  }

  lazy val total: Double = base + taxes

  lazy val unregisteredProducts: Vector[(UnregisteredProduct, Int)] = products
    .filter{case (p, q) => p.isInstanceOf[UnregisteredProduct]}
    .map{case (p, q) => (p.asInstanceOf[UnregisteredProduct], q)}

  lazy val discountsToString: String = {
    discounts.length match {
      case n if n > 0 =>
        discounts.map{case(desc, disc) => f"${desc.replaceAll("%", "%%")}%50s" + ": -" + f"$disc%2.2f"}.mkString("\n", "\n", "")
      case _ => "(No offers available)"
    }
  }

  def printBill(): Unit =
    println(f"""${"Subtotal"}%50s: $subtotal%2.2f $discountsToString
               |${"Base"}%50s: $base%2.2f
               |${"Taxes"}%50s: $taxes%2.2f
               |${"Total"}%50s: $total%2.2f""".stripMargin)

  def printUnregisteredProducts(): Unit =
    if(unregisteredProducts.nonEmpty) {
      println(f"Some products are invalid: \n${
        unregisteredProducts.map{case(p, q) => f" product '${p.name}' with price '${p.priceOption}' and tax percentage '${p.taxPercentageOption}' "
        }.mkString(" - ", "\n - ", "")}")
    }
  }

object Cart {

  def apply(products: Array[String]): Cart = {
    new Cart(
      products.groupBy(identity)
        .mapValues(_.length)
        .map{case (pName, q) => (Product.apply(pName), q)}
        .toList.toVector)
  }

}
