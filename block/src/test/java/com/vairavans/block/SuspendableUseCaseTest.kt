package com.vairavans.block

import com.vairavans.block.usecase.SuspendableUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SuspendableUseCaseTest {

    private class GetSameNumberUseCase : SuspendableUseCase<Int, Int>() {

        var executed = false

        override suspend fun execute(parameter: Int): Int {
            executed = true
            return parameter
        }

    }

    @Test
    fun `GetSameNumberUseCase is executed as expected`() {

        val useCase = GetSameNumberUseCase()

        runBlocking {
            val output = useCase( 10 )

            assert( useCase.executed ) {
                "UseCase is not executed"
            }

            assert( output == 10 ) {
                "UseCase executed as expected"
            }

            assert( useCase( 20 ) == 20 ) {
                "UseCase executed as expected on second attempt with different parameters"
            }
        }

    }
}