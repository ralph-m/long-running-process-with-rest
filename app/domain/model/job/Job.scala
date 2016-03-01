package domain.model.job

import org.joda.time.{DateTime, Seconds}
import play.api.libs.json.Json

import scala.util.Random


case class Job(id: JobId, startTime: String, status: String = "RUNNING", duration: Int = Random.nextInt(30) + 5) {

  private val start: DateTime = DateTime.parse(startTime)

  def isRunning: Boolean = {
    !start.plusSeconds(duration).isBeforeNow
  }

  def seconds: Int = Seconds.secondsBetween(DateTime.now(), start.plusSeconds(duration)).getSeconds

}

object Job {

  def create(): Job = Job(JobId.newId(), DateTime.now().toString)

  implicit val writes = Json.writes[Job]
}
