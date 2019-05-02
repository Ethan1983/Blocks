package com.vairavans.block

import io.reactivex.Observable

/**
 * [Observable.doOnNext] only when [predicate] is true
 */
fun <T> Observable<T>.doOnNextIf( predicate : Boolean, block : (T) -> Unit ) : Observable<T> {

    return if( predicate ) {
        doOnNext { data ->
            block( data )
        }
    } else {
        this
    }
}

/**
 * [Observable.doOnNext] only when [predicate] returns true
 */
fun <T> Observable<T>.doOnNextIf( predicate : () -> Boolean, block : (T) -> Unit ) : Observable<T> {

    return if( predicate() ) {
        doOnNext { data ->
            block( data )
        }
    } else {
        this
    }
}
