package com.pawan.choure.model

import java.util.*

data class Trade(
    var type:   String? = null,
    var sub_type:   String? = null,
    var trade_id:   String? = null,
    var pnl_spn:    kotlin.Int? = null,
    var spn:        kotlin.Int? = null,
    var trade_date: Date? = null,
    var price:  kotlin.Double? = null,
    var quantity:   kotlin.Int? = null
)