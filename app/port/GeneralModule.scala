package port

import com.google.inject.AbstractModule
import domain.model.job.JobRepository
import port.secondary.InMemoryJobRepository

class GeneralModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[JobRepository]).to(classOf[InMemoryJobRepository])
  }
}
