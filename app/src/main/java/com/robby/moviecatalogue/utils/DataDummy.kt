package com.robby.moviecatalogue.utils

import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.data.source.remote.response.Movie
import com.robby.moviecatalogue.data.source.remote.response.TvShow

object DataDummy {

    fun getDummyMovies(): List<Movie> = ArrayList<Movie>().apply {
        add(
            Movie(
                1,
                "/iopYFB1b6Bh7FWZh3onQhph1sih.jpg",
                "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
                "en",
                "03/31/2021",
                7.8,
                "Godzilla vs. Kong",
                emptyList(),
                "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg"
            )
        )

        add(
            Movie(
                2,
                "/rcUcYzGGicDvhDs58uM44tJKB9F.jpg",
                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people.",
                "en",
                "03/05/2021",
                8.0,
                "Raya and the Last Dragon",
                emptyList(),
                "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg"
            )
        )

        add(
            Movie(
                3,
                "/smgQgcw6kRaaoL9iNKweAGAyjCx.jpg",
                "Comedy, Family, Animation",
                "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel, where a scrappy employee named Kayla will lose her job if she can’t evict Jerry before a high-class wedding at the hotel. Her solution? Hiring Tom to get rid of the pesky mouse.",
                "02/26/2021",
                6.1,
                "Tom & Jerry",
                emptyList(),
                "/6KErczPBROQty7QoIsaa6wJYXZi.jpg"
            )
        )

        add(
            Movie(
                4,
                "/6Vz2tb0heRgd12ceuyUnZIXQu3F.jpg",
                "A portal transports Cpt. Artemis and an elite unit of soldiers to a strange world where powerful monsters rule with deadly ferocity. Faced with relentless danger, the team encounters a mysterious hunter who may be their only hope to find a way home.",
                "en",
                "12/18/2020",
                6.9,
                "Monster Hunter",
                emptyList(),
                "/1UCOF11QCw8kcqvce8LKOO6pimh.jpg"
            )
        )

        add(
            Movie(
                5,
                "/u5WUCO6irZoq27qbYYrtLUrCGDV.jpg",
                "Jen and a group of friends set out to hike the Appalachian Trail. Despite warnings to stick to the trail, the hikers stray off course—and cross into land inhabited by The Foundation, a hidden community of mountain dwellers who use deadly means to protect their way of life.",
                "en",
                "01/26/2021",
                6.6,
                "Horror, Thriller",
                emptyList(),
                "/4U1SBHmwHkNA0eHZ2n1CuiC1K1g.jpg"
            )
        )
    }

    fun getDummyTvShows(): List<TvShow> = ArrayList<TvShow>().apply {
        add(
            TvShow(
                11,
                "/JB17sIsU53NuWVUecOwrCA0CUp.jpg",
                "19/05/2021",
                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                "en",
                7.7,
                "The Falcon and the Winter Soldier",
                emptyList(),
                "/6kbAMLteGO8yyewYau6bJ683sw7.jpg"
            )
        )

        add(
            TvShow(
                12,
                "/lthkKBLe1rX6iThgVFg22O02sJw.jpg",
                "03/10/2020",
                "With his days numbered, high schooler Yuji decides to hunt down and consume the remaining 19 fingers of a deadly curse so it can die with him.",
                "jp",
                8.3,
                "Jujutsu Kaisen",
                emptyList(),
                "/g1rK2nRXSidcMwNliWDIroWWGTn.jpg"
            )
        )

        add(
            TvShow(
                13,
                "/3zt7xPU8pn5wpc17OScVDrGgV5X.jpg",
                "06/04/2013",
                "Several hundred years ago, humans were nearly exterminated by Titans. Titans are typically several stories tall, seem to have no intelligence, devour human beings and, worst of all, seem to do it for the pleasure rather than as a food source. A small percentage of humanity survived by walling themselves in a city protected by extremely high walls, even taller than the biggest Titans. Flash forward to the present and the city has not seen a Titan in over 100 years. Teenage boy Eren and his foster sister Mikasa witness something horrific as the city walls are destroyed by a Colossal Titan that appears out of thin air. As the smaller Titans flood the city, the two kids watch in horror as their mother is eaten alive. Eren vows that he will murder every single Titan and take revenge for all of mankind.",
                "jp",
                8.4,
                "Attack on Titan",
                emptyList(),
                "/aiy35Evcofzl7hASZZvsFgltHTX.jpg"
            )
        )

        add(
            TvShow(
                14,
                "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
                "05/12/2010",
                "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                "en",
                8.4,
                "Game of Thrones",
                emptyList(),
                "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
            )
        )

        add(
            TvShow(
                15,
                "/VSxgt1WBqkI1Wf65zQNiU32xHv.jpg",
                "09/01/2019",
                "Iwatani Naofumi was summoned into a parallel world along with 3 other people to become the world's Heroes. Each of the heroes respectively equipped with their own legendary equipment when summoned, Naofumi received the Legendary Shield as his weapon. Due to Naofumi's lack of charisma and experience he's labeled as the weakest, only to end up betrayed, falsely accused, and robbed by on the third day of adventure. Shunned by everyone from the king to peasants, Naofumi's thoughts were filled with nothing but vengeance and hatred. Thus, his destiny in a parallel World begins...",
                "jp",
                9.5,
                "The Rising of the Shield Hero",
                emptyList(),
                "/VSxgt1WBqkI1Wf65zQNiU32xHv.jpg",
            )
        )
    }

    fun getDummyMoviesAsContent(): List<ContentEntity> {
        val movieList = ArrayList<ContentEntity>()

        getDummyMovies().forEach {
            with(it) {
                val movie = ContentEntity(
                    id,
                    ContentType.MOVIE,
                    title,
                    "Dummy Genre",
                    releaseDate,
                    voteAverage,
                    originalLanguage,
                    overview,
                    posterPath,
                    backdropPath
                )
                movieList.add(movie)
            }
        }

        return movieList
    }

    fun getDummyTvShowsAsContent(): List<ContentEntity> {
        val tvList = ArrayList<ContentEntity>()

        getDummyTvShows().forEach {
            with(it) {
                val tv = ContentEntity(
                    id,
                    ContentType.MOVIE,
                    name,
                    "Dummy Genre",
                    firstAirDate,
                    voteAverage,
                    originalLanguage,
                    overview,
                    posterPath,
                    backdropPath
                )
                tvList.add(tv)
            }
        }

        return tvList
    }
}