package ru.finnetrolle

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/check")
class CheckController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    CheckResult check(@RequestBody CheckRequest request) {
        Asker.check(request)
    }

}
