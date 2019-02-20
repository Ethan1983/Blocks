package com.vairavans.block.usecase

/**
 * Abstraction for a suspendable use case with configurable input and output
 */
abstract class SuspendableUseCase<in P, out R> {

    suspend operator fun invoke( parameter: P ) = execute( parameter )

    abstract suspend fun execute( parameter : P ) : R
}
