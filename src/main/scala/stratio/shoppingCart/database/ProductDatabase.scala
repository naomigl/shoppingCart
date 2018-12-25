package stratio.shoppingCart.database

object ProductDatabase {

  type ProductName = String

  private val productPrices: Map[ProductName, Double] = Map(
    "Soup"   -> 0.65,
    "Milk"   -> 1.30,
    "Bread"  -> 0.80,
    "Apples" -> 1.00
  )

  private val productTaxes : Map[ProductName, Int] = Map(
    "Soup"   -> 5,
    "Milk"   -> 2,
    "Bread"  -> 2,
    "Apples" -> 5
  )

  def getProductPrice: ProductName => Option[Double] = {
    product: ProductName => productPrices.get(product)
  }

  def getProductTaxes: ProductName => Option[Int] = {
    product: ProductName => productTaxes.get(product)
  }

}