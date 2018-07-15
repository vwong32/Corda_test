package com.lemonTea.schema

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * The family of schemas for TransactionState.
 */
object TransactionSchema

/**
 * An TransactionState schema.
 */
object TransactionSchema1 : MappedSchema(
        schemaFamily = TransactionSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentIOU::class.java)) {
    @Entity
    @Table(name = "iou_states")
    class PersistentIOU(
            @Column(name = "receiver")
            var receiver: String,

            @Column(name = "issuer")
            var issuer: String,

            /*@Column(name= "product")
            var productName: String,

            @Column(name= "action")
            var action: String,*/

            @Column(name = "value")
            var value: Int,

            @Column(name = "linear_id")
            var linearId: UUID
    ) : PersistentState() {
        // Default constructor required by hibernate.
        //constructor(): this("", "", "",0, UUID.randomUUID())
        constructor(): this("", "", 0, UUID.randomUUID())
    }
}