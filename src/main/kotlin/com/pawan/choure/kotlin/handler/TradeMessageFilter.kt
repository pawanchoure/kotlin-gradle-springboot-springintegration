package com.pawan.choure.kotlin.handler

import org.springframework.integration.MessageRejectedException
import org.springframework.integration.core.MessageSelector
import org.springframework.messaging.Message

class TradeMessageFilter:MessageSelector {
    open override fun accept(message: Message<*>?): Boolean {
        val payload = message!!.payload
        return if (payload is String) {
            true
        } else {
            throw MessageRejectedException(message, "Unknown Data Type has been received")
        }
    }
}