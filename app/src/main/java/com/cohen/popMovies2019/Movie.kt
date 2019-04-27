package com.cohen.popMovies2019


import android.os.Parcel
import android.os.Parcelable

class Movie : Parcelable {
    var poster_path: String? = null
    var overview: String? = null
    var adult: Boolean = false
    var release_date: String? = null
    var id: Int = 0
    var original_title: String? = null
    var original_language: String? = null
    var title: String? = null
    var backdrop_path: String? = null
    var popularity: Float = 0.toFloat()
    var vote_count: Int = 0
    var video: Boolean = false
    var vote_average: Float = 0.toFloat()

    constructor(
        poster_path: String,
        overview: String,
        adult: Boolean,
        release_date: String,
        id: Int,
        original_title: String,
        original_language: String,
        title: String,
        backdrop_path: String,
        popularity: Float,
        vote_count: Int,
        video: Boolean,
        vote_average: Float
    ) {
        this.poster_path = poster_path
        this.overview = overview
        this.adult = adult
        this.release_date = release_date
        this.id = id
        this.original_title = original_title
        this.original_language = original_language
        this.title = title
        this.backdrop_path = backdrop_path
        this.popularity = popularity
        this.vote_count = vote_count
        this.video = video
        this.vote_average = vote_average
    }

    protected constructor(`in`: Parcel) {
        poster_path = `in`.readString()
        overview = `in`.readString()
        release_date = `in`.readString()
        id = `in`.readInt()
        original_title = `in`.readString()
        original_language = `in`.readString()
        title = `in`.readString()
        backdrop_path = `in`.readString()
        popularity = `in`.readFloat()
        vote_count = `in`.readInt()
        vote_average = `in`.readFloat()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(poster_path)
        dest.writeString(overview)
        dest.writeString(release_date)
        dest.writeInt(id)
        dest.writeString(original_title)
        dest.writeString(original_language)
        dest.writeString(title)
        dest.writeString(backdrop_path)
        dest.writeFloat(popularity)
        dest.writeInt(vote_count)
        dest.writeFloat(vote_average)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(`in`: Parcel): Movie {
                return Movie(`in`)
            }

            override fun newArray(size: Int): Array<Movie?> {
                return arrayOfNulls(size)
            }
        }
    }
}