package com.vairavans.ads

import com.google.android.gms.ads.AdRequest

internal inline fun adRequest( crossinline block : AdRequest.Builder.() -> Unit ) : AdRequest {

    return with( AdRequest.Builder() ) {
        block()
        build()
    }

}
