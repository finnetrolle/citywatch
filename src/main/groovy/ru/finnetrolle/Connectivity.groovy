package ru.finnetrolle

import groovy.transform.Immutable

@Immutable
class Connectivity {

    String name
    String host
    HealthCheckState state
    String message

}
