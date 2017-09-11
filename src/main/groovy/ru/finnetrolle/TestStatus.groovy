package ru.finnetrolle

import groovy.transform.Immutable

@Immutable
class TestStatus {

    HealthCheckState status;
    String name;
    String error;

}
