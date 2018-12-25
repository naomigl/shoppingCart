package stratio.shoppingCart.database

import org.scalatest.{FunSpec, Matchers}

class ProductDatabaseTest extends FunSpec with Matchers {

  describe("Check product prices") {
    it("Get Apples price") {
      val res = ProductDatabase.getProductPrice("Apples")
      val ex = Some(1.0)

      res shouldBe ex
    }

    it("Get Bread price") {
      val res = ProductDatabase.getProductPrice("Bread")
      val ex = Some(0.8)

      res shouldBe ex
    }

    it("Get Soup price") {
      val res = ProductDatabase.getProductPrice("Soup")
      val ex = Some(0.65)

      res shouldBe ex
    }

    it("Get Milk price") {
      val res = ProductDatabase.getProductPrice("Milk")
      val ex = Some(1.3)

      res shouldBe ex
    }

    it("Get nonExistent price") {
      val res = ProductDatabase.getProductPrice("NoProduct")
      val ex = None

      res shouldBe ex
    }
  }

  describe("Check product taxes") {
    it("Get Apples taxes") {
      val res = ProductDatabase.getProductTaxes("Apples")
      val ex = Some(5)

      res shouldBe ex
    }

    it("Get Bread taxes") {
      val res = ProductDatabase.getProductTaxes("Bread")
      val ex = Some(2)

      res shouldBe ex
    }

    it("Get Soup taxes") {
      val res = ProductDatabase.getProductTaxes("Soup")
      val ex = Some(5)

      res shouldBe ex
    }

    it("Get Milk taxes") {
      val res = ProductDatabase.getProductTaxes("Milk")
      val ex = Some(2)

      res shouldBe ex
    }

    it("Get nonExistent taxes") {
      val res = ProductDatabase.getProductTaxes("NoProduct")
      val ex = None

      res shouldBe ex
    }
  }

}
