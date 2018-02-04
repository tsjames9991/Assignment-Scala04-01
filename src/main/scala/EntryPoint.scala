import functions._
import configuration._
import org.apache.log4j.Logger
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global

object EntryPoint extends App {
  val Log = Logger.getLogger(this.getClass)
  Thread.sleep(sleepTime)
  Log.info(s"\n\n1. Total Tweets :\n${getTotalTweets()}")
  Log.info(s"\n\n2. Search Tweets for Hash-Tag :\n")
  val parameter: String = StdIn.readLine()
  Thread.sleep(sleepTime)
  search(parameter).foreach { result: List[String] => result.foreach { (status: String) => Log.info(s"$status\n") } }
  Thread.sleep(sleepTime)
  Log.info(s"\n\n3. Average Tweets/Day :\n${averageTweets()}")
  Thread.sleep(sleepTime)
  Log.info(s"\n\n4. Average Re-tweets and Favourite / Tweet :\n${getTotalTweets()}")
}
