package com.vairavans.ads

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.doubleclick.PublisherAdRequest

/**
 * DSL for creating an [AdRequest]
 */
inline fun adRequest( crossinline block : AdRequest.Builder.() -> Unit ) : AdRequest {

    return with( AdRequest.Builder() ) {
        block()
        build()
    }

}

/**
 * DSL for creating a [PublisherAdRequest]
 */
inline fun publisherAdRequest( crossinline block : PublisherAdRequest.Builder.() -> Unit ) : PublisherAdRequest {

    return with( PublisherAdRequest.Builder() ) {
        block()
        build()
    }

}
