package pl.edu.pw.ii.btwardowsk

import breeze.linalg.DenseMatrix
import scala.math.pow

/**
 * Created by bartlomiej.twardowsk on 22/09/2014.
 */
object Main {


  def matrixFactorization(r:DenseMatrix[Double], xInit:DenseMatrix[Double], yInit:DenseMatrix[Double], k:Int, steps:Int = 5000, alpha:Double = 0.0002, beta:Double = 0.02, errorThreshold:Double = 0.001): (DenseMatrix[Double], DenseMatrix[Double]) = {

    var e = Double.PositiveInfinity
    var x = xInit
    var y = yInit.t

    for( step <- 1 to steps if e > errorThreshold) {

      //update matrix
      r.findAll( _ > 0 ).foreach{ case (i,j) =>

        val eij = r(i, j) - (x(i, ::) * y(::, j))

        //gradient
        for (k_ <- 0 until k) {
          x(i, k_) += alpha * ((2 * eij * y(k_, j)) - (beta * x(i, k_)))
          y(k_, j) += alpha * ((2 * eij * x(i, k_)) - (beta * y(k_, j)))
        }

      }

      //calculate error
      e=0

      r.findAll(_ > 0).foreach { case (i, j) =>
        val eij = r(i, j) - (x(i, ::) * y(::, j))
        e += pow((r(i, j) - eij), 2)
        for (k_ <- 0 until k) {
          e += (beta / 2) * (pow(x(i, k_), 2) + pow(y(k_, j), 2))
        }
      }

    }

    (x,y)
  }

  def main(args: Array[String]) {
    val r = DenseMatrix(
      (4.0, 4.0, 0.0, 1.0),
      (3.0, 0.0, 0.0, 0.0),
      (1.0, 1.0, 0.0, 5.0),
      (2.0, 0.0, 0.0, 0.0),
      (0.0, 2.0, 5.0, 4.0)
    )

    println(s"R: \n$r")

    val u = r.rows
    val i = r.cols
    val k = 2

    val xInit = DenseMatrix.rand(u, k)
    val yInit = DenseMatrix.rand(i, k)

    println(s"Xinit: \n$xInit")
    println(s"Yinit: \n$yInit")

    val (x, y) = matrixFactorization(r, xInit, yInit, k)

    println(s"X: \n$x")
    println(s"Y: \n$y")


    println(s"X*Y: \n${ x*y }")
  }



}
