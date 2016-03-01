package application

import java.util.UUID
import javax.inject.Singleton

import com.google.inject.Inject
import domain.model.job.{FinishedJob, Job, JobId}
import port.Logger
import port.secondary.InMemoryJobRepository

@Singleton
class JobApplicationService @Inject()(jobRepository: InMemoryJobRepository, log: Logger) {

  def createNewJob(): JobId = {
    val job = Job.create()
    jobRepository.save(job)
    job.id
  }

  def status(id: String): Either[FinishedJob, Option[Job]] = {
    val maybeJob: Option[Job] = jobRepository.find(jobId(id))
    if (maybeJob.isDefined) {
      val job = maybeJob.get
      if (job.isRunning) {
        log.debug(s"${job.id} -> Still work to do for ${job.seconds} seconds.")
        Right(maybeJob)
      }
      else {
        val finishedJob = FinishedJob(job)
        jobRepository.save(finishedJob)
        Left(finishedJob)
      }
    } else
      Right(maybeJob)
  }

  def showFinishedJob(id: String): Option[FinishedJob] = {
    jobRepository.findFinishedJob(jobId(id))
  }

  private def jobId(id: String): JobId = {
    JobId(UUID.fromString(id))
  }
}
