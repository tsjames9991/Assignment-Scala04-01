import java.util.Date
import configuration._
import twitter4j.{Paging, TwitterFactory}
import twitter4j.auth.AccessToken
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Functionality of the whole assignment is provided here.
  */
class TwitterFunctions {
  val twitter = new TwitterFactory().getInstance()
  twitter.setOAuthConsumer(consumerKey, consumerSecret)
  twitter.setOAuthAccessToken(new AccessToken(access_key, access_token))

  /**
    * This function returns the number of tweets made by the user.
    */
  def getTotalTweets(): Future[Int] = Future {
    twitter.showUser(searchParameter).getStatusesCount
  }

  /**
    * This function searches the tweets made by the user for a particular hash tag.
    */
  def search(toSearch: String): Future[List[String]] = Future {
    val result = twitter.getUserTimeline(searchParameter, new Paging(initial,limit))
    val statusList = for {
      i <- 0 until result.size
      if result.get(i).getText.contains(toSearch)
    } yield result.get(i).getText
    statusList.toList
  }

  /**
    * This function returns the average number of tweets made by the user.
    * Uses the deprecated Date() function of Java
    */
  def averageTweets(): Future[String] = Future {
    val totalTweets = twitter.showUser(searchParameter).getStatusesCount
    val timePassed = (new Date().getTime - twitter.showUser(searchParameter).getCreatedAt.getTime) / (1000 * 60 * 60 * 24)
    s"Average Tweets Per Day : ${totalTweets/timePassed}"
  }

  /**
    * This function returns the average number of re-tweets and favourites of user.
    */
  def getLikesAndReTweets(): Future[String] = Future {
    val tweets = twitter.getUserTimeline(searchParameter, new Paging(initial,limit))
    val reTweets = for (i <- 0 until tweets.size) yield tweets.get(i).getRetweetCount
    val favourites = for (i <- 0 until tweets.size) yield tweets.get(i).getFavoriteCount
    s"\nAverage Re-Tweets: ${(reTweets.sum) / tweets.size} per tweet\nAverage Favourites: ${favourites.sum / tweets.size} per tweet"
  }
}
