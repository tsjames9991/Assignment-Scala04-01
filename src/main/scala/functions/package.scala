import java.util.Date

import configuration._
import org.apache.log4j.Logger
import twitter4j.{Query, Status, TwitterFactory}
import twitter4j.auth.AccessToken

import scala.collection.JavaConverters._
import scala.concurrent.Future

package object functions {
  val twitter = new TwitterFactory().getInstance()
  twitter.setOAuthConsumer(consumerKey, consumerSecret)
  twitter.setOAuthAccessToken(new AccessToken(access_key, access_token))
  val log = Logger.getLogger(this.getClass)

  def getTotalTweets() = Future {
    val totalTweets = twitter.getHomeTimeline.asScala
    log.info(s"\nNumber of Tweets : ${totalTweets.size}")
  }

  def search(toSearch: String) = Future {
    val query = new Query(toSearch)
    val result = twitter.search(query)
    log.info(s"\nTweets Found : ")
    for (status: Status <- result.getTweets.asScala) {
      log.info("\n" + status.getText)
    }
  }

  //noinspection ScalaDeprecation
  def averageTweets()=Future {
  val totalTweets = twitter.getHomeTimeline
    val pastDate = new Date(2018,1,1)
    val timePassed = new Date().getTime - pastDate.getTime

  }
}
