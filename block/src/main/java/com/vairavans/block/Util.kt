package com.vairavans.block

inline fun repeatForever( block : (Int) -> Unit ) {

    var index = 0

    while( true ) {
        block(index++)
    }
}
