package me.user.library

import Repro.FAPI

class CinteropUseCase {
    private val api = FAPI()

    @Throws(Exception::class)
    fun subscribe() {
        // causes a crash, returns typealias Subscriber
        api.subscribe("SomeUnusedString") { value ->

        }
    }

    @Throws(Exception::class)
    fun subscribeSafe() {
        // does not cause a crash, returns class ReturnType
        api.subscribeSafe("SomeUnusedString") { value ->

        }
    }
}