package stratio.shoppingCart.entities

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSpec, Matchers}

class ProductTest extends FunSpec with Matchers with MockFactory {

  describe("check product instantiation given its price and taxes existence") {

    it("should return a valid product when price and taxes are set in DB") {
      val res = Product("Apples")

      assert(res.isInstanceOf[ValidProduct])
      res.name shouldBe "Apples"
      res.price shouldBe 1.0
      res.taxPercentage shouldBe 5

    }

    it("should return an unregistered product when nor price nor taxes are set in DB") {
      val res = Product("unexistentProduct")

      assert(res.isInstanceOf[UnregisteredProduct])
      res.name shouldBe "unexistentProduct"
      res.price shouldBe 0.0
      res.taxPercentage shouldBe 0
      res.asInstanceOf[UnregisteredProduct].priceOption shouldBe None
      res.asInstanceOf[UnregisteredProduct].taxPercentageOption shouldBe None
    }
  }


}
