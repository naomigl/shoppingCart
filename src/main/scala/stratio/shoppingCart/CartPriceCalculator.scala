package stratio.shoppingCart

import stratio.shoppingCart.entities.Cart

object CartPriceCalculator {

  def main(args: Array[String]): Unit = {

    val cart = Cart(args)
    cart.printBill()
    cart.printUnregisteredProducts()
  }

}
