package com.cdsap.talaiot.configuration

class HybridPublisherConfiguration : PublisherConfiguration {

    /**
     * name of the publisher
     */
    override var name: String = "hybrid"

    override var publishBuildMetrics: Boolean = true
    override var publishTaskMetrics: Boolean = true

    var taskPublisher: PublisherConfiguration? = null

    var buildPublisher: PublisherConfiguration? = null

}
