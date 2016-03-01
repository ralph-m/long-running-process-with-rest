# Example Project #
This project serves as an exemplary illustration of how to implement a long running process with REST.

## How to run ##
The application uses Play 2 with Scala. Just start it with `sbt run`.

## How to use ##
Initiate a job and retrieve a processing resource
````
curl -X POST http://localhost:9000/jobs -i
HTTP/1.1 303 See Other
Location: http://localhost:9000/processing/761d91e0-5de8-4790-9e87-209b2de9237b
Date: Tue, 01 Mar 2016 14:25:01 GMT
Content-Length: 0
````
Querying the resource to retrieve the current processing status
````
curl http://localhost:9000/processing/761d91e0-5de8-4790-9e87-209b2de9237b -i
HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Date: Tue, 01 Mar 2016 14:25:11 GMT
Content-Length: 132

{"id":{"value":"761d91e0-5de8-4790-9e87-209b2de9237b"},"startTime":"2016-03-01T15:25:01.642+01:00","status":"RUNNING","duration":30}
````
Querying the resource again will return the status as long as the processing has not finished
````
curl http://localhost:9000/processing/761d91e0-5de8-4790-9e87-209b2de9237b -i
HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Date: Tue, 01 Mar 2016 14:25:29 GMT
Content-Length: 132

{"id":{"value":"761d91e0-5de8-4790-9e87-209b2de9237b"},"startTime":"2016-03-01T15:25:01.642+01:00","status":"RUNNING","duration":30}
````
An extract from the server log shows the job is still in progress
````
Job JobId(761d91e0-5de8-4790-9e87-209b2de9237b) saved
JobId(761d91e0-5de8-4790-9e87-209b2de9237b) -> Still work to do for 20 seconds.
JobId(761d91e0-5de8-4790-9e87-209b2de9237b) -> Still work to do for 15 seconds.
JobId(761d91e0-5de8-4790-9e87-209b2de9237b) -> Still work to do for 2 seconds.
FinishedJob JobId(761d91e0-5de8-4790-9e87-209b2de9237b) saved
````
The job's result resource is returned with a redirect directive after the processing has finished
````
curl http://localhost:9000/processing/761d91e0-5de8-4790-9e87-209b2de9237b -i -L
HTTP/1.1 301 Moved Permanently
Location: http://localhost:9000/job/761d91e0-5de8-4790-9e87-209b2de9237b
Date: Tue, 01 Mar 2016 14:25:32 GMT
Content-Length: 0

HTTP/1.1 200 OK
Content-Type: application/json; charset=utf-8
Date: Tue, 01 Mar 2016 14:25:32 GMT
Content-Length: 202

{"job":{"id":{"value":"761d91e0-5de8-4790-9e87-209b2de9237b"},"startTime":"2016-03-01T15:25:01.642+01:00","status":"RUNNING","duration":30},"endTime":"2016-03-01T15:25:32.807+01:00","status":"FINISHED"}
````