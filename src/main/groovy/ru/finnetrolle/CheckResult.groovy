package ru.finnetrolle

import groovy.transform.Immutable

@Immutable
class CheckResult {

    List<Connectivity> alive
    List<Connectivity> dead

}
