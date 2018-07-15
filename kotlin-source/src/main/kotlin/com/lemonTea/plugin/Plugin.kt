package com.lemonTea.plugin

import com.lemonTea.api.mainApi
import net.corda.core.messaging.CordaRPCOps
import net.corda.webserver.services.WebServerPluginRegistry
import java.util.function.Function

class Plugin : WebServerPluginRegistry {
    /**
     * A list of classes that expose web APIs.
     */
    override val webApis: List<Function<CordaRPCOps, out Any>> = listOf(Function(::mainApi))

    /**
     * A list of directories in the resources directory that will be served by Jetty under /web.
     */
    override val staticServeDirs: Map<String, String> = mapOf(
            // This will serve the exampleWeb directory in resources to /web/lemonTea
            "lemonTea" to javaClass.classLoader.getResource("exampleWeb").toExternalForm()
    )
}