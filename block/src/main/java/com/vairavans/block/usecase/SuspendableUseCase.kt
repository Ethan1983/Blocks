package com.vairavans.block.usecase

abstract class SuspendableUseCase<in P, out R> {

    suspend operator fun invoke( parameter: P ) = execute( parameter )

    abstract suspend fun execute( parameter : P ) : R
}
