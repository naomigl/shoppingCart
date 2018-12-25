package stratio.shoppingCart.entities

import stratio.shoppingCart.database.ProductDatabase._

sealed abstract class Product(val name: String, val price: Double, val taxPercentage: Int)

class ValidProduct(name: String, price: Double, taxPercentage: Int) extends Product(name, price, taxPercentage)

class UnregisteredProduct(name: String, val priceOption: Option[Double], val taxPercentageOption: Option[Int]) extends Product(name, 0.0, 0)

object Product {

  def apply(name: String): Product = {

    val priceOption: Option[Double] = getProductPrice(name)

    val taxPercentageOption: Option[Int] = getProductTaxes(name)

    (priceOption, taxPercentageOption) match {

      case (Some(price), Some(taxPercentage)) => new ValidProduct(name, price, taxPercentage)

      case _ => new UnregisteredProduct(name, priceOption, taxPercentageOption)

    }
  }

}