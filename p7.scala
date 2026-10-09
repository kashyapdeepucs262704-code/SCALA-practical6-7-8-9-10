import breeze.linalg._
import scala.io.Source

object Practical7 {

  def euclideanDistance(
                         v1: DenseVector[Double],
                         v2: DenseVector[Double]
                       ): Double = {
    norm(v1 - v2)
  }

  def main(args: Array[String]): Unit = {

    val file = "data.csv"

    val data = Source.fromFile(file)
      .getLines()
      .drop(1)
      .map { line =>

        val parts = line.split(",")

        val x1 = parts(0).trim.toDouble
        val x2 = parts(1).trim.toDouble
        val className = parts(2).trim

        val vector = DenseVector(x1, x2)

        (vector, className)
      }
      .toList

    val query = DenseVector(3.0, 4.0)

    println("======================================")
    println("  EUCLIDEAN DISTANCE")
    println("  NEAREST NEIGHBOR CLASSIFICATION")
    println("======================================")

    println()
    println("Query Vector: " + query)
    println()

    val distances = data.map {
      case (vector, className) =>

        val distance = euclideanDistance(query, vector)

        println(
          "Vector: " + vector +
            " | Class: " + className +
            " | Distance: " + distance
        )

        (className, distance)
    }

    val nearest = distances.minBy(_._2)

    println()
    println("======================================")
    println("Nearest Neighbor Classification")
    println("======================================")

    println("Class: " + nearest._1)
    println("Minimum Distance: " + nearest._2)

    println("======================================")
  }
} 