package com.vairavans.block

import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.reflect.KClass

@RunWith(JUnit4::class)
class AbsObservableViewModelTest {

    data class TestViewState(
        val showLoading : Boolean = false,
        val data : List<String> = emptyList() ) : ViewState()

    sealed class TestEvent : Event() {
        object TestOneEvent : TestEvent()
        object TestTwoEvent : TestEvent()
    }

    sealed class TestResult : Result() {
        data class TestOneResult( val data : List<String> ) : TestResult()
        object TestTwoResult : TestResult()
    }

    sealed class TestViewEffect : ViewEffect() {
        object TestTwoLoadingEffect : TestViewEffect()
        object TestTwoLoadedEffect : TestViewEffect()
        object TestTwoErrorEffect : TestViewEffect()
        object DefaultEffect : TestViewEffect()
    }

    abstract class AbsTestViewModel : AbsObservableViewModel<TestViewState, TestViewEffect, TestEvent, TestResult >() {

        lateinit var initialState : TestViewState

        lateinit var loadedState : TestViewState
        lateinit var loadingState : TestViewState
        lateinit var loadedEffect : TestViewEffect
        lateinit var loadingEffect : TestViewEffect
        lateinit var errorState : TestViewState
        lateinit var errorEffect : TestViewEffect

        override fun getInitialViewState(): TestViewState = TestViewState( showLoading = false ).also {
            initialState = it
        }

        override fun getEventToResultObservableList(eventObservable: Observable<out TestEvent>)
                : List<Observable<out Lce<out TestResult>>> {

            return listOf(
                eventObservable.ofType(TestEvent.TestOneEvent::class.java).onTestOne(),
                eventObservable.ofType(TestEvent.TestTwoEvent::class.java).onTestTwo()
            )
        }

        override fun getLoadingViewState(currentViewState: TestViewState, type: KClass<out TestResult>): TestViewState {
            return if( type == TestResult.TestOneResult::class ) {
                currentViewState.copy( showLoading = true )
            } else {
                currentViewState
            }.also {
                loadingState = it
            }
        }

        override fun getLoadedViewState(currentViewState: TestViewState, dataContent: TestResult): TestViewState {
            return when( dataContent ) {
                is TestResult.TestOneResult -> {
                    currentViewState.copy( showLoading = false, data = dataContent.data )
                }
                is TestResult.TestTwoResult -> {
                    currentViewState
                }
            }.also {
                loadedState = it
            }
        }

        override fun getErrorViewState(currentViewState: TestViewState, errorContent: TestResult): TestViewState {
            return when( errorContent ) {
                is TestResult.TestOneResult -> {
                    currentViewState.copy( showLoading = false, data = errorContent.data )
                }
                is TestResult.TestTwoResult -> {
                    currentViewState
                }
            }.also {
                errorState = it
            }
        }

        override fun getLoadingViewEffect(type: KClass<out TestResult>): TestViewEffect {
            return if( type == TestResult.TestTwoResult::class ) {
                TestViewEffect.TestTwoLoadingEffect
            } else {
                TestViewEffect.DefaultEffect
            }.also {
                loadingEffect = it
            }
        }

        override fun getLoadedViewEffect(dataContent: TestResult): TestViewEffect {
            return when( dataContent ) {
                is TestResult.TestOneResult -> {
                    TestViewEffect.DefaultEffect
                }
                is TestResult.TestTwoResult -> {
                    TestViewEffect.TestTwoLoadedEffect
                }
            }.also {
                loadedEffect = it
            }
        }

        override fun getErrorViewEffect(errorContent: TestResult): TestViewEffect {
            return when( errorContent ) {
                is TestResult.TestOneResult -> {
                    TestViewEffect.DefaultEffect
                }
                is TestResult.TestTwoResult -> {
                    TestViewEffect.TestTwoErrorEffect
                }
            }.also {
                errorEffect = it
            }
        }

        open fun Observable<TestEvent.TestOneEvent>.onTestOne() : Observable<Lce<TestResult.TestOneResult>> {
            return switchMap {
                Observable.just(1)
                    .map {
                        Lce.Content(TestResult.TestOneResult( listOf( "1", "2", "3" ) ) ) as Lce<TestResult.TestOneResult>
                    }
                    .startWith( Lce.Loading(TestResult.TestOneResult::class ) )
            }
        }

        open fun Observable<TestEvent.TestTwoEvent>.onTestTwo() : Observable<Lce<TestResult.TestTwoResult>> {
            return switchMap {
                Observable.just(1)
                    .map {
                        Lce.Content( TestResult.TestTwoResult ) as Lce<TestResult.TestTwoResult>
                    }
                    .startWith( Lce.Loading(TestResult.TestTwoResult::class ) )
            }
        }
    }

    class LoadingViewModel : AbsTestViewModel() {
        override val enableLog: Boolean = true

        override fun Observable<TestEvent.TestOneEvent>.onTestOne(): Observable<Lce<TestResult.TestOneResult>> {

            return switchMap {
                Observable.just(1)
                    .map {
                        Lce.Loading(TestResult.TestOneResult::class )
                    }
            }
        }

        override fun Observable<TestEvent.TestTwoEvent>.onTestTwo(): Observable<Lce<TestResult.TestTwoResult>> {
            return switchMap {
                Observable.just(1)
                    .map {
                        Lce.Loading(TestResult.TestTwoResult::class )
                    }
            }
        }
    }

    class FailingViewModel : AbsTestViewModel() {
        override val enableLog: Boolean = true

        override fun Observable<TestEvent.TestOneEvent>.onTestOne(): Observable<Lce<TestResult.TestOneResult>> {

            return switchMap {
                Observable.just(1)
                    .map {
                        Lce.Error(TestResult.TestOneResult( emptyList() ) )
                    }
            }
        }

        override fun Observable<TestEvent.TestTwoEvent>.onTestTwo(): Observable<Lce<TestResult.TestTwoResult>> {
            return switchMap {
                Observable.just(1)
                    .map {
                        Lce.Error(TestResult.TestTwoResult )
                    }
            }
        }
    }

    abstract class LoggingTestViewModel : AbsTestViewModel() {
        var logInvoked = false

        override fun log(msg: String) {
            logInvoked = true
        }
    }

    class LoggingEnabledTestViewModel : LoggingTestViewModel() {
        override val enableLog: Boolean = true
    }

    class LoggingDisabledTestViewModel : LoggingTestViewModel() {
        override val enableLog: Boolean = false
    }

    @Test
    fun `ViewModel invokes Log when requested upon subscription with initial state`() {

        val viewModel = LoggingEnabledTestViewModel()

        viewModel.viewState
            .subscribe()

        viewModel.viewEffect
            .subscribe()

        assert(viewModel.logInvoked) {
            "log is not invoked"
        }
    }

    @Test
    fun `ViewModel ignores Log when not subscribed`() {

        val viewModel = LoggingEnabledTestViewModel()

        assert(!viewModel.logInvoked) {
            "log is invoked"
        }
    }

    @Test
    fun `ViewModel ignores Log when subscribed to only Effects`() {

        val viewModel = LoggingEnabledTestViewModel()

        viewModel.viewEffect
            .subscribe()

        assert(!viewModel.logInvoked) {
            "log is invoked"
        }
    }

    @Test
    fun `ViewModel ignores Log when not requested upon subscription with initial state`() {

        val viewModel = LoggingDisabledTestViewModel()

        viewModel.viewState
            .subscribe()

        viewModel.viewEffect
            .subscribe()

        assert(!viewModel.logInvoked) {
            "log is invoked"
        }
    }

    @Test
    fun `ViewModel returns expected initial state`() {

        val viewModel = LoggingDisabledTestViewModel()

        lateinit var currentViewState : TestViewState

        viewModel.viewState
            .subscribe { vs ->
                currentViewState = vs
            }

        assert( currentViewState == viewModel.initialState ) {
            "Unexpected initial state"
        }
    }

    @Test
    fun `ViewModel returns expected loaded state on TestOneEvent`() {

        val viewModel = LoggingDisabledTestViewModel()

        lateinit var currentViewState : TestViewState

        viewModel.viewState
            .subscribe { vs ->
                currentViewState = vs
            }

        viewModel.processEvent( TestEvent.TestOneEvent )

        assert( currentViewState == viewModel.loadedState ) {
            "Unexpected loaded state for TestOneEvent"
        }
    }

    @Test
    fun `ViewModel returns expected loaded state on TestTwoEvent`() {

        val viewModel = LoggingDisabledTestViewModel()

        lateinit var currentViewState : TestViewState

        viewModel.viewState
            .subscribe { vs ->
                currentViewState = vs
            }

        viewModel.processEvent( TestEvent.TestTwoEvent )

        assert( currentViewState == viewModel.loadedState ) {
            "Unexpected loaded state for TestTwoEvent"
        }
    }

    @Test
    fun `ViewModel returns expected loaded effect on TestTwoEvent`() {

        val viewModel = LoggingDisabledTestViewModel()

        lateinit var viewEffect : TestViewEffect

        viewModel.viewEffect
            .subscribe { vs ->
                viewEffect = vs
            }

        viewModel.processEvent( TestEvent.TestTwoEvent )

        assert( viewEffect == viewModel.loadedEffect ) {
            "Unexpected loaded effect for TestTwoEvent"
        }
    }

    @Test
    fun `ViewModel returns expected loaded effect on TestOneEvent`() {

        val viewModel = LoggingDisabledTestViewModel()

        lateinit var viewEffect : TestViewEffect

        viewModel.viewEffect
            .subscribe { vs ->
                viewEffect = vs
            }

        viewModel.processEvent( TestEvent.TestOneEvent )

        assert( viewEffect == viewModel.loadedEffect ) {
            "Unexpected loaded effect for TestOneEvent"
        }
    }

    @Test
    fun `ViewModel returns expected loading state on TestOneEvent`() {

        val viewModel = LoadingViewModel()

        lateinit var currentViewState : TestViewState

        viewModel.viewState
            .subscribe { vs ->
                currentViewState = vs
            }

        viewModel.processEvent( TestEvent.TestOneEvent )

        assert( currentViewState == viewModel.loadingState ) {
            "Unexpected loading state for TestOneEvent"
        }
    }

    @Test
    fun `ViewModel returns expected loading state on TestTwoEvent`() {

        val viewModel = LoadingViewModel()

        lateinit var currentViewState : TestViewState

        viewModel.viewState
            .subscribe { vs ->
                currentViewState = vs
            }

        viewModel.processEvent( TestEvent.TestTwoEvent )

        assert( currentViewState == viewModel.loadingState ) {
            "Unexpected loading state for TestTwoEvent"
        }
    }

    @Test
    fun `ViewModel returns expected loading effect on TestTwoEvent`() {

        val viewModel = LoadingViewModel()

        lateinit var viewEffect : TestViewEffect

        viewModel.viewEffect
            .subscribe { vs ->
                viewEffect = vs
            }

        viewModel.processEvent( TestEvent.TestTwoEvent )

        assert( viewEffect == viewModel.loadingEffect ) {
            "Unexpected loading effect for TestTwoEvent"
        }
    }

    @Test
    fun `ViewModel returns expected loading effect on TestOneEvent`() {

        val viewModel = LoadingViewModel()

        lateinit var viewEffect : TestViewEffect

        viewModel.viewEffect
            .subscribe { vs ->
                viewEffect = vs
            }

        viewModel.processEvent( TestEvent.TestOneEvent )

        assert( viewEffect == viewModel.loadingEffect ) {
            "Unexpected loading effect for TestOneEvent"
        }
    }


    @Test
    fun `ViewModel returns expected error state on TestOneEvent`() {

        val viewModel = FailingViewModel()

        lateinit var currentViewState : TestViewState

        viewModel.viewState
            .subscribe { vs ->
                currentViewState = vs
            }

        viewModel.processEvent( TestEvent.TestOneEvent )

        assert( currentViewState == viewModel.errorState ) {
            "Unexpected error state for TestOneEvent"
        }
    }

    @Test
    fun `ViewModel returns expected error state on TestTwoEvent`() {

        val viewModel = FailingViewModel()

        lateinit var currentViewState : TestViewState

        viewModel.viewState
            .subscribe { vs ->
                currentViewState = vs
            }

        viewModel.processEvent( TestEvent.TestTwoEvent )

        assert( currentViewState == viewModel.errorState ) {
            "Unexpected error state for TestTwoEvent"
        }
    }

    @Test
    fun `ViewModel returns expected error effect on TestTwoEvent`() {

        val viewModel = FailingViewModel()

        lateinit var viewEffect : TestViewEffect

        viewModel.viewEffect
            .subscribe { vs ->
                viewEffect = vs
            }

        viewModel.processEvent( TestEvent.TestTwoEvent )

        assert( viewEffect == viewModel.errorEffect ) {
            "Unexpected error effect for TestTwoEvent"
        }
    }

    @Test
    fun `ViewModel returns expected error effect on TestOneEvent`() {

        val viewModel = FailingViewModel()

        lateinit var viewEffect : TestViewEffect

        viewModel.viewEffect
            .subscribe { vs ->
                viewEffect = vs
            }

        viewModel.processEvent( TestEvent.TestOneEvent )

        assert( viewEffect == viewModel.errorEffect ) {
            "Unexpected error effect for TestOneEvent"
        }
    }
}