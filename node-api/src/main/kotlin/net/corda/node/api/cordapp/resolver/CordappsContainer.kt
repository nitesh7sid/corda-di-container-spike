package net.corda.node.api.cordapp.resolver

import net.corda.node.api.cordapp.Cordapp

interface CordappsContainer {

    val cordapps: Set<Cordapp>

    //    TODO add events for loading / unloading Cordapps, with a stubbed AuditService logging them
}