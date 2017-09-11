package ru.finnetrolle

import feign.Feign
import feign.FeignException
import feign.Request
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import groovyx.gpars.GParsPool

class Asker {

    static Connectivity selftest(Connectivity connectivity) {
        HealthCheckClient client = Feign.builder()
                .options(new Request.Options(10000, 10000))
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(HealthCheckClient.class, connectivity.host)
        try {
            SelfTestResult result = client.selfcheck()
            if (result == null) {
                return new Connectivity(connectivity.name, connectivity.host, HealthCheckState.CRITICAL, "Null result")
            }
            return new Connectivity(connectivity.name, connectivity.host, result.status, "OK")
        } catch (ConnectException e) {
            return new Connectivity(connectivity.name, connectivity.host, HealthCheckState.CRITICAL, "Connect Exception: ${e.getMessage()}")
        } catch (SocketTimeoutException e) {
            return new Connectivity(connectivity.name, connectivity.host, HealthCheckState.CRITICAL, "Timeout exception")
        } catch (FeignException e) {
            return new Connectivity(connectivity.name, connectivity.host, HealthCheckState.CRITICAL, e.getMessage())
        }
    }

    static CheckResult check(CheckRequest request) {
        List<Connectivity> checkList = []
        request.services.each { service ->
            service.hosts.each { host ->
                checkList << new Connectivity(service.name, "$host:${service.port}", HealthCheckState.OK, "")
            }
        }
        List<Connectivity> results = []
        GParsPool.withPool(100) {
            final List list = checkList.collectParallel {
                println("asking ${it.host}")
                Connectivity c = selftest(it)
                println("done with ${it.host}")
                c
            }
            results.addAll(list)
        }
        new CheckResult(
                results.findAll {it.state == HealthCheckState.OK}.toSorted {a,b -> a.name <=> b.name},
                results.findAll {it.state != HealthCheckState.OK}.toSorted {a,b -> a.name <=> b.name}
        )

    }

}
