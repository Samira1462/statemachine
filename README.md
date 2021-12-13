

WorkMotion (www.workmotion.com) is a global HR platform enabling companies to hire & onboard their employees internationally, at the push of a button. It is our mission to create opportunities for anyone to work from anywhere. As work is becoming even more global and remote, there has never been a bigger chance to build a truly global HR-tech company.


As a part of our backend engineering team, you will be responsible for building our core platform including an  employees managment system.

The employees on this system are assigned to different states, Initially when an employee is added it will be assigned "ADDED" state automatically .


The other states (State machine) for A given Employee are:
- ADDED
- IN-CHECK
- APPROVED
- ACTIVE

The allowed state transitions are:

ADDED -> IN-CHECK <-> APPROVED -> ACTIVE

Our backend stack is:
- Java 11 
- Spring Framework 



Your task is to build  Restful API doing the following:
- An Endpoint to support adding an employee with very basic employee details including (name, contract information, age, you can decide.) With initial state "ADDED" which incidates that the employee isn't active yet.

- Another endpoint to change the state of a given employee to any of the states defined above in the state machine respecting the transition rules 

- An Endpoint to fetch employee details


Please provide a solution with the  above features with the following consideration.

- Being simply executable with the least effort Ideally using Docker and docker-compose or any smailiar approach.
- For state machine could be as simple as of using ENUM or by using https://projects.spring.io/spring-statemachine/ 
- Please provide testing for your solution.
- Providing an API Contract e.g. OPENAPI spec. is a big plus


## Deliverables:
http://localhost:8080/api-docs/
{"openapi":"3.0.1","info":{"title":"OpenAPI definition","version":"v0"},"servers":[{"url":"http://localhost:8080/","description":"Generated server url"}],"paths":{"/checkin/{employee_id}":{"post":{"tags":["employee-service-controller"],"operationId":"checkin","parameters":[{"name":"employee_id","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"/":{"schema":{"type":"string"}}}}}}},"/approve/{employee_id}":{"post":{"tags":["employee-service-controller"],"operationId":"approve","parameters":[{"name":"employee_id","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"/":{"schema":{"type":"string"}}}}}}},"/add":{"post":{"tags":["employee-service-controller"],"operationId":"addEmployee","requestBody":{"content":{"application/json":{"schema":{"$ref":"#/components/schemas/Employee"}}},"required":true},"responses":{"200":{"description":"OK","content":{"/":{"schema":{"type":"string"}}}}}}},"/active/{employee_id}":{"post":{"tags":["employee-service-controller"],"operationId":"active","parameters":[{"name":"employee_id","in":"path","required":true,"schema":{"type":"integer","format":"int64"}}],"responses":{"200":{"description":"OK","content":{"/":{"schema":{"type":"string"}}}}}}}},"components":{"schemas":{"Contract":{"type":"object","properties":{"id":{"type":"integer","format":"int64"},"salary":{"type":"number"}}},"Employee":{"type":"object","properties":{"id":{"type":"integer","format":"int64"},"creationOn":{"type":"string","format":"date-time"},"firstName":{"type":"string"},"lastName":{"type":"string"},"email":{"type":"string"},"phoneNumber":{"type":"string"},"age":{"type":"integer","format":"int32"},"employeeState":{"type":"string","enum":["ADD","IN_CHECK","APPROVED","ACTIVE","SECURITY_CHECK_STARTED","SECURITY_CHECK_FINISHED","WORK_PERMIT_CHECK_STARTED","WORK_PERMIT_CHECK_FINISHED"]},"contract":{"$ref":"#/components/schemas/Contract"}}}}}}




