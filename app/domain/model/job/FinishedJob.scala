package domain.model.job

import org.joda.time.DateTime
import play.api.libs.json.Json

case class FinishedJob(job: Job, endTime: String = DateTime.now().toString, status: String = "FINISHED") {
  def id: JobId = job.id
}

object FinishedJob {
  implicit val writes = Json.writes[FinishedJob]
}
