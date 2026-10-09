import com.github.tototoshi.csv._
import java.io.File
import scala.util.Try

object Pract_9_MissngValues {

  def main(args: Array[String]): Unit = {

    val inputFile = new File("titanic.csv")

    if (!inputFile.exists()) {
      println("Error: titanic.csv file not found!")
      println("Please place titanic.csv in the project folder.")
      return
    }

    val reader = CSVReader.open(inputFile)
    val allRows = try {
      reader.allWithHeaders()
    } finally {
      reader.close()
    }

    val numericColumns = Seq("Age", "Fare")

    val stats: Map[String, (Double, Int)] =
      numericColumns.map { col =>
        val values = allRows.map(row => row.getOrElse(col, "").trim)
        val validNumbers = values.flatMap(v => Try(v.toDouble).toOption)
        val missingCount = values.count(v => Try(v.toDouble).isFailure)

        val mean =
          if (validNumbers.nonEmpty) validNumbers.sum / validNumbers.size
          else 0.0

        (col, (mean, missingCount))
      }.toMap

    println("\n--- Missing Data Report ---")

    stats.foreach { case (col, (mean, missingCount)) =>
      println(
        f"Column: $col, Missing values: $missingCount, Replaced with mean: $mean%.2f"
      )
    }

    val cleanedRows = allRows.map { row =>
      numericColumns.foldLeft(row) { (accRow, col) =>
        val value = accRow.getOrElse(col, "").trim

        val replaced = Try(value.toDouble).toOption match {
          case Some(_) => value
          case None => f"${stats(col)._1}%.2f"
        }

        accRow.updated(col, replaced)
      }
    }

    val outputFile = new File("titanic_cleaned.csv")
    val writer = CSVWriter.open(outputFile)

    try {
      val headers = allRows.headOption.map(_.keys.toSeq).getOrElse(Seq.empty)
      writer.writeRow(headers)
      cleanedRows.foreach(row => writer.writeRow(headers.map(h => row.getOrElse(h, ""))))
    } finally {
      writer.close()
    }

    println("\nMissing values replaced and saved to titanic_cleaned.csv")
  }
}
