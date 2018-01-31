import twitter4j.{Query, Status, TwitterFactory}
import twitter4j.auth.AccessToken
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class TwitterStream {
  val twitter = new TwitterFactory().getInstance()
  twitter.setOAuthConsumer("bN5iepekOZwVM4Gp99BXX95d4", "DmjyN9zJ9ZMPpPW5uLbCv67ulZx0nCOnklqSnGZqx1h7M6mZpq")
  twitter.setOAuthAccessToken(new AccessToken("83770216-zM4p3zQl7iLxwTozXsQhg7PzE4xQu2sudarGHRF5l", "PlcZagn97UKzcYZpMdpIz7jEOuKpdJ1pL7L2lO4eDldnB"))

  def getStatusNumber(): Future[Int] = Future {
    val number = twitter.getHomeTimeline.lastIndexOf()
    number + 1
  }

  def search(toSearch: String) = Future {
    val query = new Query(toSearch)
    val result = twitter.search(query)
    for (status: Status <- result.getTweets) {
      println(status.getText)
    }
  }
}

object TwitterStream extends App {
val obj=new TwitterStream
  obj.search("Devdas")
}