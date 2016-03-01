package port.secondary

import javax.inject.Singleton

import com.google.inject.Inject
import domain.model.job.{FinishedJob, Job, JobId, JobRepository}
import port.Logger

import scala.collection.mutable

@Singleton
class InMemoryJobRepository @Inject()(log: Logger) extends JobRepository {

  private val _jobs = mutable.Map[JobId, Job]().empty
  private val _finishedJobs = mutable.Map[JobId, FinishedJob]().empty

  override def save(job: Job): Job = {
    _jobs += job.id -> job
    log.debug(s"Job ${job.id} saved")
    job
  }

  override def save(finishedJob: FinishedJob): FinishedJob = {
    _finishedJobs += finishedJob.id -> finishedJob
    log.debug(s"FinishedJob ${finishedJob.id} saved")
    finishedJob
  }

  override def find(id: JobId): Option[Job] = _jobs.get(id)

  override def findFinishedJob(id: JobId): Option[FinishedJob] = _finishedJobs.get(id)

}
