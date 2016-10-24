package tk.monnef.rpi_sensor_test

import java.net.URL
import com.sksamuel.scrimage.Image
import com.sksamuel.scrimage.canvas.{Canvas, DrawableString}
import org.joda.time.{DateTime, LocalTime}
import scalaj.http._
import spray.json._
import DefaultJsonProtocol._
import spray.json.lenses.JsonLenses._

object Main extends App {
  val CameraPictureUrl = "http://i.imgur.com/M0NnxDc.png"
  val UploadsImApiUrl = "http://uploads.im/api?upload"
  val TargetSize = (640, 480)

  def nowAsString(): String = LocalTime.now().toString()

  def log(msg: String): Unit = {println(nowAsString() + " " + msg)}

  def measure[A](msg: String)(code: => A): A = {
    val time = nowAsString()
    val start = System.currentTimeMillis()
    val ret = code
    val stop = System.currentTimeMillis()
    println(s"$time ${stop - start}ms: $msg")
    ret
  }

  def getImage(): Image = {
    val is = new URL(CameraPictureUrl).openStream()
    val img = Image.fromStream(is)
    is.close()
    img
  }

  def getSensorData(): String = {
    Thread.sleep(50)
    "25°C, 45% hum."
  }

  def uploadImage(img: Image, fileName: String): String = {
    val response = Http(UploadsImApiUrl).postMulti(MultiPart("file", fileName, "image/jpeg", img.bytes)).asString
    if (response.code != 200) {
      println(s"body = \n${response.body}")
      throw new RuntimeException(s"Upload failed with status = ${response.code}")
    }
    val json = response.body.parseJson
    val urlLens = 'data / 'img_url
    json.extract[String](urlLens)
  }

  log("Starting sensor processing program.")
  val fileName = DateTime.now().toString("yyyy_MM_dd") + ".jpg"
  val img = measure("Downloading image...") {getImage()}
  val sensorData = measure("Getting sensor data...") {getSensorData()}
  val smallImg = measure(s"Scaling from ${img.dimensions} to $TargetSize") {img.cover(TargetSize._1, TargetSize._2)}
  val smallImgWithText = measure("Adding text from sensor.") {Canvas(smallImg).draw(DrawableString(sensorData, 20, 20)).image}
  measure("Saving file...") {smallImgWithText.output(fileName)}
  measure("Uploading file...") {
    val imgUrl = uploadImage(smallImgWithText, fileName)
    println(s"Image URL: $imgUrl")
  }
  log("Halting.")
}
