import java.util.Date
import configuration._
import twitter4j.{Paging, TwitterFactory}
import twitter4j.auth.AccessToken
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

package object functions {
  val twitter = new TwitterFactory().getInstance()
  twitter.setOAuthConsumer(consumerKey, consumerSecret)
  twitter.setOAuthAccessToken(new AccessToken(access_key, access_token))

  def getTotalTweets(): Future[Int] = Future {
    twitter.showUser(searchParameter).getStatusesCount
  }

  def search(toSearch: String): Future[List[String]] = Future {
    val result = twitter.getUserTimeline(searchParameter, new Paging(initial,limit))
    val statusList = for {
      i <- 0 until result.size
      if result.get(i).getText.contains(toSearch)
    } yield result.get(i).getText
    statusList.toList
  }

  //noinspection ScalaDeprecation
  def averageTweets(): Future[String] = Future {
    val totalTweets = twitter.showUser(searchParameter).getStatusesCount
    val timePassed = (new Date().getTime - twitter.showUser(searchParameter).getCreatedAt.getTime) / (1000 * 60 * 60 * 24)
    s"\nAverage Tweets Per Day : ${totalTweets/timePassed}"
  }

  def getLikesAndReTweets(): Future[String] = Future {
    val tweets = twitter.getUserTimeline(searchParameter, new Paging(initial,limit))
    val reTweets = for (i <- 0 until tweets.size) yield tweets.get(i).getRetweetCount
    val favourites = for (i <- 0 until tweets.size) yield tweets.get(i).getFavoriteCount
    s"\nAverage Re-Tweets: ${reTweets.sum / tweets.size} per tweet\nAverage Favourites: ${favourites.sum / tweets.size} per tweet"
  }
}
