package com.vairavans.block

import com.vairavans.block.usecase.UseCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UseCaseTest {

    private class GetSameNumberUseCase : UseCase<Int, Int>() {

        var executed = false

        override fun execute(parameter: Int): Int {
            executed = true
            return parameter
        }

    }

    @Test
    fun `GetSameNumberUseCase is executed as expected`() {

        val useCase = GetSameNumberUseCase()
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