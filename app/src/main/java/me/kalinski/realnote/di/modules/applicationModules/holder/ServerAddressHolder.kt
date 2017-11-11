package me.kalinski.realnote.di.modules.applicationModules.holder

import javax.inject.Inject

data class ServerAddressHolder @Inject constructor(
        val string: String
)