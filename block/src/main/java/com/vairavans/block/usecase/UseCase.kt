package com.vairavans.block.usecase

abstract class UseCase<in P, out R> {

    operator fun invoke( parameter: P ) = execute( parameter )

    abstract fun execute( parameter : P ) : R
}
