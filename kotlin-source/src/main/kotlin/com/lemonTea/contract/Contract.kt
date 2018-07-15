package com.lemonTea.contract

import com.lemonTea.state.TransactionState
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.contracts.requireThat
import net.corda.core.transactions.LedgerTransaction

/**
 * A implementation of a basic smart contract in Corda.
 *
 * This contract enforces rules regarding the creation of a valid [TransactionState], which in turn encapsulates an [Transaction].
 *
 * For a new [Transaction] to be issued onto the ledger, a transaction is required which takes:
 * - Zero input states.
 * - One output state: the new [Transaction].
 * - An Create() command with the public keys of both the lender and the borrower.
 *
 * All contracts must sub-class the [Contract] interface.
 */
class Contract : Contract {
    companion object {
        @JvmStatic
        val CONTRACT_ID = "com.lemonTea.contract.Contract"
    }

    /**
     * The verify() function of all the states' contracts must not throw an exception for a transaction to be
     * considered valid.
     */
    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands.Create>()
        requireThat {
            // Generic constraints around the transaction.
            "No inputs should be consumed when issuing an." using (tx.inputs.isEmpty())
            "Only one output state should be created." using (tx.outputs.size == 1)
            val out = tx.outputsOfType<TransactionState>().single()
            "The lender and the borrower cannot be the same entity." using (out.lender != out.borrower)
            "All of the participants must be signers." using (command.signers.containsAll(out.participants.map { it.owningKey }))

            // IOU-specific constraints.
            "The IOU's value must be non-negative." using (out.value > 0)
        }
    }

    /**
     * This contract only implements one command, Create.
     */
    interface Commands : CommandData {
        class Create : Commands
    }
}
