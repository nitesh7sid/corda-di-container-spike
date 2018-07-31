package net.corda.node

import net.corda.commons.logging.loggerFor
import net.corda.node.api.cordapp.resolver.CordappsContainer
import net.corda.node.api.flows.registry.FlowsRegistry
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Named

@Named
internal class Node @Inject internal constructor(private val cordappsContainer: CordappsContainer, private val flowsRegistry: FlowsRegistry, private val configuration: Configuration) {

    companion object {
        private val logger = loggerFor<Node>()
    }

    @PostConstruct
    private fun start() {

        with(configuration) {
            logger.info("Joining Corda network at address $networkHost:$networkPort.")
        }

        // TODO subscribe to events instead
        val cordapps = cordappsContainer.cordapps()
        cordapps.forEach { cordapp ->

            logger.info("Registering Cordapp ${cordapp.name} with version ${cordapp.version}.")
            cordapp.initiatedFlows.forEach { initiated ->
                initiated.initiatedBy.forEach { initiating ->
                    flowsRegistry.register(initiating, initiated)
                }
            }
        }
    }

    interface Configuration {

        val networkHost: String
        val networkPort: Int
    }
}