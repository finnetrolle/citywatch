package ru.finnetrolle

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.Immutable

@Immutable
class SelfTestResult {

    HealthCheckState status

    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<TestStatus> failedTests

}
