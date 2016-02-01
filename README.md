# econceptecommerce

License
============================
The MIT License (MIT)

Copyright (c) 2015 Kai-Ting (Danil) Ko

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


About
============================
Ecommerce Website Sample integrate with Paypal API, Shippfy API and Google reCaptcha with Jquery and Bootstrap Frontend

It utilize Spring, HikariCP and DataNeclus for backend PostgresqlRDBMS Integration

Please note this is a sample, it is by no means a complete solution with any security implication

Compile
============================
On project git repo level, type
```
gradle build
```

Execute
============================
Copy the build controller.war into tomcat 7+ container engine

Following environment variables need to be present
```
GOOGLE_RECAPTCHA_API_ENDPOINT
GOOGLE_RECAPTCHA_API_SECRET

PAYPAL_API_SERVICE_ENDPOINT
PAYPAL_API_CLIENT_ID
PAYPAL_API_CLIENT_SECRET

SHIPPO_CLIENT_API_KEY
```

PostgreSQL 9.2 or up needs to be present with following values or one will need to change the default settings in application.properties within:

WAR/classes/application.properties

A sample docker container that fulfill can be build with

```
docker run -p 127.0.0.1:5432:5432 -d --name postgresql stackbrew/postgres:latest
```
