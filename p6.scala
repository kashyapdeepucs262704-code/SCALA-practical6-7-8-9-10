import breeze.linalg._

object SubMatrixSum {
  def main(args: Array[String]): Unit = {

    val matrix = DenseMatrix(
      (1, 2, 3, 4),
      (5, 6, 7, 8),
      (9, 10, 11, 12),
      (13, 14, 15, 16)
    )

    println(s"Original Matrix:\n$matrix")

    val subMatrix = matrix(1 to 2, 1 to 3)

    println(s"\nSub-Matrix (rows 1-2, cols 1-3):\n$subMatrix")

    val rowSums = sum(subMatrix(*, ::))
    println(s"\nRow Sums: $rowSums")

    val colSums = sum(subMatrix(::, *))
    println(s"Column Sums: $colSums")
  }
}
