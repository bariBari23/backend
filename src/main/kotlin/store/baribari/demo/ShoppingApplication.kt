package store.baribari.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@ConfigurationPropertiesScan
class ShoppingApplication

fun main(args: Array<String>) {
    runApplication<ShoppingApplication>(*args)
}

