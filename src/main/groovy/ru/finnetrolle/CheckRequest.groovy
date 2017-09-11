package ru.finnetrolle

import groovy.transform.Immutable

@Immutable
class CheckRequest {

    List<Service> services

    @Immutable
    static class Service {
        String environment
        String zone
        String name
        List<String> hosts
        int port
    }
}
