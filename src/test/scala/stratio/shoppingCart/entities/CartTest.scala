package stratio.shoppingCart.entities

import org.scalatest.{FunSpec, Matchers}

class CartTest extends FunSpec with Matchers {

  val priceProduct1 = 1.0
  val taxesProduct1 = 1
  val product1 = new ValidProduct("product1", priceProduct1, taxesProduct1)
  val priceProduct2 = 2.0
  val taxesProduct2 = 2
  val product2 = new ValidProduct("product2", priceProduct2, taxesProduct2)
  val unregisteredProduct = new UnregisteredProduct("unexistentProduct", None, None)
  val numProduct1 = 1
  val numProduct2 = 2
  val numUnregisteredProduct = 1
  val productsInCart = Vector(product1 -> numProduct1, product2 -> numProduct2, unregisteredProduct -> numUnregisteredProduct)
  val cartMock = Cart(productsInCart)
  val cartEmptyMock = Cart(Vector())

  describe("Cart calculations test") {
    it("subtotal should be the quantity of each product multiplied by its price and all sum") {
      val ex = priceProduct1 * numProduct1 + priceProduct2 * numProduct2
      val res = cartMock.subtotal

      res shouldBe ex
    }

    it("base should be subtotal when no discounts are applied") {
      val ex = priceProduct1 * numProduct1 + priceProduct2 * numProduct2
      val res = cartMock.base

      res shouldBe ex
    }

    it("taxes should be tax of each product / 100 multiplied by the price and quantity of each product") {
      val ex = taxesProduct1.toDouble / 100 * priceProduct1 * numProduct1 +
        taxesProduct2.toDouble / 100 * priceProduct2 * numProduct2
      val res = cartMock.taxes

      res shouldBe ex
    }

    it("total should be subtotal - discounts (0)  + taxes") {
      val subtotal = priceProduct1 * numProduct1 + priceProduct2 * numProduct2
      val taxes = taxesProduct1.toDouble / 100 * priceProduct1 * numProduct1 +
        taxesProduct2.toDouble / 100 * priceProduct2 * numProduct2
      val ex = subtotal + taxes // No discount applied
      val res = cartMock.total

      res shouldBe ex
    }

    it("everything should be 0 when cart is empty") {
      val zero = 0.0
      val resSubtotal = cartEmptyMock.subtotal
      val resDiscounts = cartEmptyMock.discounts
      val resBase = cartEmptyMock.base
      val resTotal = cartEmptyMock.total

      resSubtotal shouldBe zero
      assert(resDiscounts.isEmpty)
      resBase shouldBe zero
      resTotal shouldBe zero
    }
  }

  describe("Cart stringify test") {
    it("should retrieve \"(No offers available)\" when no discounts are available ") {
      val ex = "(No offers available)"
      val res = cartMock.discountsToString

      res shouldBe ex
    }
  }

  describe("Cart unregistered products retrieval test") {
    it("should retrieve unexistentProduct") {
      val ex = Vector(unregisteredProduct -> numUnregisteredProduct)
      val res = cartMock.unregisteredProducts

      res shouldBe ex
    }
  }

  describe("Cart initialization with products test") {
    it("should retrieve correct number of products in list") {
      val exNumProduct1 = 2
      val exNumProduct2 = 1
      val exNumUnregisteredProduct = 3
      val listOfProducts = Array(product1.name, product2.name, product1.name, unregisteredProduct.name, unregisteredProduct.name, unregisteredProduct.name)
      val res = Cart(listOfProducts).products

      res.count(_._1.name == product1.name) shouldBe 1
      res.filter(_._1.name == product1.name).map(_._2).head shouldBe exNumProduct1

      res.count(_._1.name == product2.name) shouldBe 1
      res.filter(_._1.name == product2.name).map(_._2).head shouldBe exNumProduct2

      res.count(_._1.name == unregisteredProduct.name) shouldBe 1
      res.filter(_._1.name == unregisteredProduct.name).map(_._2).head shouldBe exNumUnregisteredProduct

    }

    it("should retrieve empty list of products when no products are given") {
      val listOfProducts = Array[String]()
      val res = Cart(listOfProducts).products

      assert(res.isEmpty)
    }
  }
}
