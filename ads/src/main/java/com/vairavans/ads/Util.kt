package com.vairavans.ads

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.doubleclick.PublisherAdRequest

inline fun adRequest( crossinline block : AdRequest.Builder.() -> Unit ) : AdRequest {

    return with( AdRequest.Builder() ) {
        block()
        build()
    }

}

inline fun publisherAdRequest( crossinline block : PublisherAdRequest.Builder.() -> Unit ) : PublisherAdRequest {

    return with( PublisherAdRequest.Builder() ) {
        block()
        build()
    }

}
