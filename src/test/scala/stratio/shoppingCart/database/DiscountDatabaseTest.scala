package stratio.shoppingCart.database

import org.scalatest.{FunSpec, Matchers}
import stratio.shoppingCart.entities.{Product, ValidProduct}

class DiscountDatabaseTest extends FunSpec with Matchers {

  describe("Apples discount testing") {

    val appleDiscountDescription = "Apples 10% off"
    val applePrice = 10.0
    val appleProduct = new ValidProduct("Apples", applePrice, 1)
    val notAnAppleProduct = new ValidProduct("NotAnApple", 20.0, 2)

    it("Should not be applied when no Apples are in chart") {

      val noDiscount = Nil

      val productsWithoutApples: Vector[(Product, Int)] = Vector(notAnAppleProduct -> 2)
      val res = DiscountDatabase.applyDiscounts(productsWithoutApples)

      res shouldBe noDiscount
    }

    it("Should be applied once when Apples are in chart") {

      val discountForApples = Vector(appleDiscountDescription -> applePrice * 0.1)

      val productsWithApples: Vector[(Product, Int)] = Vector(appleProduct -> 1)
      val res = DiscountDatabase.applyDiscounts(productsWithApples)

      res shouldBe discountForApples
    }

    it("Should be applied as many times as Apples in chart") {

      val numApples = 5
      val discountForApples = Vector(appleDiscountDescription -> applePrice * numApples * 0.1)

      val productsWithoutSeveralApples: Vector[(Product, Int)] = Vector(appleProduct -> numApples)
      val res = DiscountDatabase.applyDiscounts(productsWithoutSeveralApples)

      res shouldBe discountForApples
    }

    it("Should be applied as many times as Apples in chart even with more products") {

      val numApples = 4
      val discountForApples = Vector(appleDiscountDescription -> applePrice * numApples * 0.1)

      val productsWithoutApplesAndMoreProducts: Vector[(Product, Int)] = Vector(notAnAppleProduct -> 2, appleProduct -> numApples)
      val res = DiscountDatabase.applyDiscounts(productsWithoutApplesAndMoreProducts)

      res shouldBe discountForApples
    }
  }

  describe("Soup and Bread discount testing") {

    val soupAndBreadDiscountDescription = "2 tins of soup and a loaf of bread for half price"
    val breadPrice = 10.0
    val breadProduct = new ValidProduct("Bread", breadPrice, 1)
    val soupProduct = new ValidProduct("Soup", 2, 5)
    val someOtherProduct = new ValidProduct("someOtherProduct", 20.0, 2)

    it("Should not be applied when no Soups nor Bread are in chart") {

      val noDiscount = Nil

      val productsWithoutBreadAndSoup: Vector[(Product, Int)] = Vector(someOtherProduct -> 2)
      val res = DiscountDatabase.applyDiscounts(productsWithoutBreadAndSoup)

      res shouldBe noDiscount
    }

    it("Should not be applied when no Soups but some Bread is in chart") {

      val noDiscount = Nil

      val productsWithSomeBread: Vector[(Product, Int)] = Vector(someOtherProduct -> 2, breadProduct -> 1)
      val res = DiscountDatabase.applyDiscounts(productsWithSomeBread)

      res shouldBe noDiscount
    }

    it("Should not be applied when no Bread but some Soup is in chart") {

      val noDiscount = Nil

      val productsWithSomeSoup: Vector[(Product, Int)] = Vector(someOtherProduct -> 2, soupProduct -> 2)
      val res = DiscountDatabase.applyDiscounts(productsWithSomeSoup)

      res shouldBe noDiscount
    }

    it("Should be applied once when 2 Soups and 1 Bread are in chart") {

      val discountFor2Soup1Bread = Vector(soupAndBreadDiscountDescription -> breadPrice * 0.1)

      val productsWithSomeSoupAndBread: Vector[(Product, Int)] = Vector(breadProduct -> 1, soupProduct -> 2)
      val res = DiscountDatabase.applyDiscounts(productsWithSomeSoupAndBread)

      res shouldBe discountFor2Soup1Bread
    }

    it("Should be applied once when 4 Soups and 1 Bread are in chart") {

      val discountFor2Soup1Bread = Vector(soupAndBreadDiscountDescription -> breadPrice * 0.1)

      val productsWithoutApples: Vector[(Product, Int)] = Vector(breadProduct -> 1, soupProduct -> 4)
      val res = DiscountDatabase.applyDiscounts(productsWithoutApples)

      res shouldBe discountFor2Soup1Bread
    }

    it("Should be applied once when 3 Soups and 2 Bread are in chart") {

      val discountFor2Soup1Bread = Vector(soupAndBreadDiscountDescription -> breadPrice * 0.1)

      val productsWithoutApples: Vector[(Product, Int)] = Vector(breadProduct -> 2, soupProduct -> 3)
      val res = DiscountDatabase.applyDiscounts(productsWithoutApples)

      res shouldBe discountFor2Soup1Bread
    }

    it("Should be applied twice when 4 Soups and 2 Bread are in chart even with some other products") {

      val discountFor2Soup1Bread = Vector(soupAndBreadDiscountDescription -> breadPrice * 0.1 * 2)

      val productsWithoutApples: Vector[(Product, Int)] = Vector(breadProduct -> 2, soupProduct -> 4, someOtherProduct -> 1)
      val res = DiscountDatabase.applyDiscounts(productsWithoutApples)

      res shouldBe discountFor2Soup1Bread
    }
  }

  describe("Several discounts testing") {

    it("Should return both products") {

      val appleDiscountDescription = "Apples 10% off"
      val soupAndBreadDiscountDescription = "2 tins of soup and a loaf of bread for half price"
      val breadPrice = 10.0
      val breadProduct = new ValidProduct("Bread", breadPrice, 1)
      val applePrice = 5.0
      val appleProduct = new ValidProduct("Apples", applePrice, 1)
      val soupProduct = new ValidProduct("Soup", 2, 5)
      val someOtherProduct = new ValidProduct("someOtherProduct", 20.0, 2)

      val bothDiscounts = Vector(
        appleDiscountDescription -> applePrice * 0.1,
        soupAndBreadDiscountDescription -> breadPrice * 0.1
      )

      val products: Vector[(Product, Int)] = Vector(
        breadProduct -> 1, soupProduct -> 2,
        appleProduct -> 1,
        someOtherProduct -> 1)
      val res = DiscountDatabase.applyDiscounts(products)

      res.sorted == bothDiscounts.sorted
    }
  }
}
