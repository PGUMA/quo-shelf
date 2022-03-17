package lab.pguma.quoshelf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class QuoSelfApplication

fun main(args: Array<String>) {
    runApplication<QuoSelfApplication>(*args)
}