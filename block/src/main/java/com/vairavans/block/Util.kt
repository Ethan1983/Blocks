package com.vairavans.block

/**
 * Repeat specified [block] forever. [block] receives a zero based index representing the iteration count
 */
inline fun repeatForever( block : (Int) -> Unit ) {

    var index = 0

    while( true ) {
        block(index++)
    }
}

/**
 * Repeat specified [block] until it returns false or specified number of [times].
 * Use this to break out of a repeat.
 */
inline fun repeatUntil( times: Int, block: (Int) -> Boolean ) {

    for( index in 0 until times ) {
        if( !block(index) ) {
            break
        }
    }
}

/**
 * Repeat specified [block] until it returns false. [block] receives a zero based index representing the iteration
 * count. Use this to break out of a repeat.
 */
inline fun repeatUntil( block: (Int) -> Boolean ) {

    var index = 0

    while( block(index++) );
}
