package com.pawan.choure.kotlin

import com.pawan.choure.kotlin.handler.TradeHandler
import com.pawan.choure.kotlin.handler.TradeHandlerSimple
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.messaging.SubscribableChannel

@SpringBootApplication
class KotlinMain

fun main(args: Array<String>) {
    SpringApplication.run(KotlinMain::class.java, *args)
}
