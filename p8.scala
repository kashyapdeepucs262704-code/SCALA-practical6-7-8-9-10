import com.github.tototoshi.csv._
import scala.io.Source
import java.io.File

object Pract_8_ReadCSVStats {

  def main(args: Array[String]): Unit = {

    val file = new File(
      "C:\\Users\\Shubham\\OneDrive\\Desktop\\deepu\\Scala\\Practical No 7\\practical.8\\heart.csv"
    )

    if (!file.exists()) {
      println("Error: heart.csv file not found!")
      println("Please place heart.csv in the practical.8 project folder.")
      return
    }

    val reader = CSVReader.open(file)

    try {
      val rows = reader.all()

      println("CSV Data:")
      rows.take(10).foreach(println)

      if (rows.nonEmpty) {
        println("\nTotal rows: " + rows.length)
        println("Total columns in first row: " + rows.head.length)
      }

      if (rows.length > 1) {
        println("\nFirst data row:")
        println(rows(1))
      }

    } finally {
      reader.close()
    }
  }
}
