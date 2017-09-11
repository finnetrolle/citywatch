package ru.finnetrolle

import feign.Headers
import feign.RequestLine

@Headers("Content-Type: application/json")
interface HealthCheckClient {

    @RequestLine("GET /monitoring/selftest")
    SelfTestResult selfcheck()

    @RequestLine("GET /monitoring/version")
    String version()

}