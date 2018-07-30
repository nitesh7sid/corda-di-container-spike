package net.corda.node.api.flows.registry

import net.corda.cordapp.api.flows.Flows
import kotlin.reflect.KClass

// This is just to keep a similarity with the current structure in Corda. Ideally, Cordapps should process flows themselves.
interface FlowsRegistry {

    fun <INITIATING : Flows.Initiating<*>> register(initiating: KClass<out INITIATING>, initiated: Flows.Initiated)

    fun <INITIATING : Flows.Initiating<*>> register(initiating: KClass<out INITIATING>, initiatedSet: Set<Flows.Initiated>) = initiatedSet.forEach { register(initiating, it) }

    fun <INITIATING : Flows.Initiating<*>> register(initiating: KClass<out INITIATING>, initiated: Flows.Initiated, vararg additionalInitiated: Flows.Initiated) {

        register(initiating, initiated + setOf(*additionalInitiated))
    }

    private operator fun Flows.Initiated.plus(others: Set<Flows.Initiated>) = setOf(this) + others
}