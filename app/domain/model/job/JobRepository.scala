package domain.model.job

trait JobRepository {

  def save(job: Job): Job

  def save(finishedJob: FinishedJob): FinishedJob

  def find(id: JobId): Option[Job]

  def findFinishedJob(id: JobId): Option[FinishedJob]
}
