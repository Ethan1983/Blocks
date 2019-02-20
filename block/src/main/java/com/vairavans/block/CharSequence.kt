package com.vairavans.block

/**
 * Extension of [CharSequence] to convert into a array of [CharSequence] split by space character.
 */
fun CharSequence.toTypedArray() : Array<CharSequence> = split( " " ).toTypedArray()
