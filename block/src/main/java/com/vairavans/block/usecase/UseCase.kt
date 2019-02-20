package com.vairavans.block.usecase

/**
 * Abstraction for a use case with configurable input and output
 */
abstract class UseCase<in P, out R> {

    operator fun invoke( parameter: P ) = execute( parameter )

    abstract fun execute( parameter : P ) : R
}
