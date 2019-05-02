package com.vairavans.block

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlin.reflect.KClass

/**
 * Base class representing an Event. It is recommended for concrete implementations to be a top level sealed class
 * and specific events to be child of that sealed class to leverage exhaustive checks of when.
 */
abstract class Event

/**
 * Base class representing a Result. It is recommended for concrete implementations to be a top level sealed class
 * and specific results to be child of that sealed class to leverage exhaustive checks of when.
 */
abstract class Result

/**
 * Base class representing a ViewState.
 */
abstract class ViewState

/**
 * Base class representing a ViewEffect. It is recommended for concrete implementations to be a top level sealed class
 * and specific effects to be child of that sealed class to leverage exhaustive checks of when.
 */
abstract class ViewEffect

/**
 * Data structure representing Loading, Loaded and Error states associated with a [Result]
 */
sealed class Lce<T : Result> {
    class Loading<T : Result>(val type : KClass<T>) : Lce<T>()
    data class Content<T : Result>(val dataContent: T) : Lce<T>()
    data class Error<T : Result>(val errorContent: T) : Lce<T>()
}

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class AbsObservableViewModel<VS : ViewState, VE : ViewEffect, E : Event, R : Result> : ViewModel() {

    private val events : PublishSubject<E> = PublishSubject.create()
    private lateinit var viewStateDisposable : Disposable

    /**
     * Concrete implementations need to implement [log] when this is set to true
     */
    abstract val enableLog : Boolean

    val viewState : Observable<VS>
    val viewEffect : Observable<VE>

    init {

        events
            .doOnNext { logInternal( "Processing Event $it") }
            .eventToResult()
            .doOnNext { logInternal( "Result $it") }
            .share()
            .also { resultObservable ->

                viewState = resultObservable
                    .resultToViewState()
                    .doOnNext { logInternal( "View State $it") }
                    .replay(1)
                    .autoConnect(1) {
                        viewStateDisposable = it
                    }

                viewEffect = resultObservable
                    .doOnNext { logInternal( "View Effect $it") }
                    .resultToViewEffect()
            }
    }

    override fun onCleared() {
        super.onCleared()
        viewStateDisposable.dispose()
    }

    fun processEvent( event : E ) {
        events.onNext( event )
    }

    private fun Observable<out E>.eventToResult() : Observable<out Lce<out R>> {
        return publish { eventObservable ->
            Observable.merge( getEventToResultObservableList( eventObservable ) )
        }
    }

    private fun Observable<out Lce<out R>>.resultToViewState() : Observable<VS> {

        return scan( getInitialViewState() ) { vs, result ->

            when( result ) {
                is Lce.Loading -> {
                    getLoadingViewState( vs, result.type )
                }
                is Lce.Content -> {
                    getLoadedViewState( vs, result.dataContent )
                }
                is Lce.Error -> {
                    getErrorViewState( vs, result.errorContent )
                }
            }
        }
            .distinctUntilChanged()
    }

    private fun Observable<out Lce<out R>>.resultToViewEffect() : Observable<VE> {
        return map { result ->

            when( result ) {
                is Lce.Loading -> {
                    getLoadingViewEffect( result.type )
                }
                is Lce.Content -> {
                    getLoadedViewEffect( result.dataContent )
                }
                is Lce.Error -> {
                    getErrorViewEffect( result.errorContent )
                }
            }
        }
    }

    private fun logInternal( msg : String ) {
        if( enableLog ) {
            log( msg )
        }
    }

    /**
     * Concrete implementations can use any logging mechanism. This is called only when log is enabled using
     * [enableLog]
     */
    open fun log( msg : String ) {
        // Empty Implementations
    }

    /**
     * Return a list of Observable of [Lce<[Result]>] given a stream of events as Observable of [Event]
     */
    abstract fun getEventToResultObservableList( eventObservable : Observable<out E> ) :
            List<Observable<out Lce<out R>>>

    /**
     * Called only once upon first subscription to [viewState]
     */
    abstract fun getInitialViewState() : VS

    /**
     * Return [ViewState] for Loading status
     */
    abstract fun getLoadingViewState( currentViewState: VS, type : KClass<out R> ) : VS

    /**
     * Return [ViewState] for Loaded status
     */
    abstract fun getLoadedViewState( currentViewState: VS, dataContent : R ) : VS

    /**
     * Return [ViewState] for Error status
     */
    abstract fun getErrorViewState( currentViewState: VS, errorContent : R ) : VS

    /**
     * Return [ViewEffect] for Loading status
     */
    abstract fun getLoadingViewEffect( type : KClass<out R> ) : VE

    /**
     * Return [ViewEffect] for Loaded status
     */
    abstract fun getLoadedViewEffect( dataContent : R ) : VE

    /**
     * Return [ViewEffect] for Error status
     */
    abstract fun getErrorViewEffect( errorContent : R ) : VE
}
