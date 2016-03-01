package domain.model.job

import java.util.UUID

import play.api.libs.json.Json

case class JobId(value: UUID)

object JobId {

  def newId(): JobId = JobId(UUID.randomUUID())

  implicit val writes = Json.writes[JobId]
}
