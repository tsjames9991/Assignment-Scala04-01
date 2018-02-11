import configuration._
import org.apache.log4j.Logger
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object EntryPoint extends App {
  val twitterObject = new TwitterFunctions
  val Log = Logger.getLogger(this.getClass)
  val parameter: String = "Squid"
  val numberOfTweets = twitterObject.getTotalTweets()
  val searchTweets = twitterObject.search(parameter)
  val averageTweets = twitterObject.averageTweets()
  val reTweets = twitterObject.getLikesAndReTweets()
  Thread.sleep(sleepTime)
  Log.info(s"\n\n1. Total Tweets : ")
  numberOfTweets onComplete {
    case Success(number) => Log.info(s"$number")
    case Failure(error) => Log.info(s"\nAn error has occurred: " + error.getMessage())
  }
  Thread.sleep(waitTime)
  Log.info(s"\n\n2. ")
  averageTweets onComplete {
    case Success(number) => Log.info(s"$number")
    case Failure(error) => Log.info(s"An error has occurred: " + error.getMessage())
  }
  Thread.sleep(waitTime)
  Log.info(s"\n\n3. Average Re-tweets and Favourite / Tweet : ")
  reTweets onComplete {
    case Success(number) => Log.info(s"$number")
    case Failure(error) => Log.info(s"\nAn error has occurred: " + error.getMessage())
  }
  Thread.sleep(waitTime)
  Log.info(s"\n\n4. Search Tweets for Hash-Tag : #$parameter")
  searchTweets onComplete {
    case Success(result: List[String]) => result.foreach { (status: String) => Log.info(s"\n$status") }
    case Failure(error) => Log.info(s"\n3. An error has occurred: " + error.getMessage())
  }
}
