
import com.github.tototoshi.csv._
import java.io.File
import scala.util.Try

object FilterRowThreshold {
  def main(args: Array[String]): Unit = {

    val file = new File("heart.csv")

    if (!file.exists()) {
      println("Error: heart.csv file not found!")
      return
    }

    val reader = CSVReader.open(file)
    val data = try {
      reader.allWithHeaders()
    } finally {
      reader.close()
    }

    println("Column Names:")
    data.headOption.foreach(row => println(row.keys.mkString("\t")))

    println("\nFirst 10 Cholesterol Values:")
    data.take(10).foreach { row =>
      println(row.getOrElse("Cholesterol", "Column not found"))
    }

    val threshold = 200

    val filteredRows = data.filter { row =>
      row.get("Cholesterol")
        .flatMap(value => Try(value.trim.toDouble).toOption)
        .exists(_ > threshold)
    }

    println(s"\nTotal rows with Cholesterol > $threshold: ${filteredRows.length}")

    if (filteredRows.nonEmpty) {
      println("\nFiltered Data:")
      val headers = data.head.keys.toSeq
      println(headers.mkString("\t"))

      filteredRows.foreach { row =>
        println(headers.map(h => row.getOrElse(h, "")).mkString("\t"))
      }
    } else {
      println("\nNo matching rows found.")
      println("Check the cholesterol values printed above.")
    }
  }
}
