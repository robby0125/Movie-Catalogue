package com.robby.moviecatalogue.utils

import com.robby.moviecatalogue.data.Content

object DataDummy {

    fun getAllDummyContents(): ArrayList<Content> = ArrayList<Content>().apply {
        addAll(getDummyMovies())
        addAll(getDummyTvShows())
    }

    fun getDummyMovies(): ArrayList<Content> = ArrayList<Content>().apply {
        add(
            Content(
                "m01",
                "Godzilla vs. Kong",
                "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
                "03/31/2021",
                "Action, Science Fiction",
                "Released",
                "7.8",
                "https://image.tmdb.org/t/p/w500/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
                "https://image.tmdb.org/t/p/w500/iopYFB1b6Bh7FWZh3onQhph1sih.jpg"
            )
        )

        add(
            Content(
                "m02",
                "Raya and the Last Dragon",
                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people.",
                "03/05/2021",
                "Animation, Adventure, Fantasy, Family, Action",
                "Released",
                "8.0",
                "https://image.tmdb.org/t/p/w500/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
                "https://image.tmdb.org/t/p/w500/rcUcYzGGicDvhDs58uM44tJKB9F.jpg"
            )
        )

        add(
            Content(
                "m03",
                "Tom & Jerry",
                "Tom the cat and Jerry the mouse get kicked out of their home and relocate to a fancy New York hotel, where a scrappy employee named Kayla will lose her job if she can’t evict Jerry before a high-class wedding at the hotel. Her solution? Hiring Tom to get rid of the pesky mouse.",
                "02/26/2021",
                "Comedy, Family, Animation",
                "Released",
                "6.1",
                "https://image.tmdb.org/t/p/w500/6KErczPBROQty7QoIsaa6wJYXZi.jpg",
                "https://image.tmdb.org/t/p/w500/smgQgcw6kRaaoL9iNKweAGAyjCx.jpg"
            )
        )

        add(
            Content(
                "m04",
                "Monster Hunter",
                "A portal transports Cpt. Artemis and an elite unit of soldiers to a strange world where powerful monsters rule with deadly ferocity. Faced with relentless danger, the team encounters a mysterious hunter who may be their only hope to find a way home.",
                "12/18/2020",
                "Fantasy, Action, Adventure",
                "Released",
                "6.9",
                "https://image.tmdb.org/t/p/w500/1UCOF11QCw8kcqvce8LKOO6pimh.jpg",
                "https://image.tmdb.org/t/p/w500/6Vz2tb0heRgd12ceuyUnZIXQu3F.jpg"
            )
        )

        add(
            Content(
                "m05",
                "Wrong Turn",
                "Jen and a group of friends set out to hike the Appalachian Trail. Despite warnings to stick to the trail, the hikers stray off course—and cross into land inhabited by The Foundation, a hidden community of mountain dwellers who use deadly means to protect their way of life.",
                "01/26/2021",
                "Horror, Thriller",
                "Released",
                "6.6",
                "https://image.tmdb.org/t/p/w500/4U1SBHmwHkNA0eHZ2n1CuiC1K1g.jpg",
                "https://image.tmdb.org/t/p/w500/u5WUCO6irZoq27qbYYrtLUrCGDV.jpg"
            )
        )

        add(
            Content(
                "m06",
                "The Croods: A New Age",
                "Searching for a safer habitat, the prehistoric Crood family discovers an idyllic, walled-in paradise that meets all of its needs. Unfortunately, they must also learn to live with the Bettermans -- a family that's a couple of steps above the Croods on the evolutionary ladder. As tensions between the new neighbors start to rise, a new threat soon propels both clans on an epic adventure that forces them to embrace their differences, draw strength from one another, and survive together.",
                "11/25/2020",
                "Family, Fantasy, Animation, Comedy",
                "Released",
                "7.8",
                "https://image.tmdb.org/t/p/w500/tbVZ3Sq88dZaCANlUcewQuHQOaE.jpg",
                "https://image.tmdb.org/t/p/w500/rCxdJkk5PMCWIzRWcpqIxUaWnf1.jpg"
            )
        )

        add(
            Content(
                "m07",
                "Space Sweepers",
                "When the crew of a space junk collector ship called The Victory discovers a humanoid robot named Dorothy that's known to be a weapon of mass destruction, they get involved in a risky business deal which puts their lives at stake.",
                "02/05/2021",
                "Drama, Fantasy, Science Fiction",
                "Released",
                "7.2",
                "https://image.tmdb.org/t/p/w500/bmemsraCG1kIthY74NjDnnLRT2Q.jpg",
                "https://image.tmdb.org/t/p/w500/11Lcr8MGnV4DPmSY9QmlH0ebdKq.jpg"
            )
        )

        add(
            Content(
                "m08",
                "My Hero Academia: Heroes Rising",
                "Class 1-A visits Nabu Island where they finally get to do some real hero work. The place is so peaceful that it's more like a vacation … until they're attacked by a villain with an unfathomable Quirk! His power is eerily familiar, and it looks like Shigaraki had a hand in the plan. But with All Might retired and citizens' lives on the line, there's no time for questions. Deku and his friends are the next generation of heroes, and they're the island's only hope.",
                "02/26/2020",
                "Animation, Action, Fantasy, Adventure",
                "Released",
                "8.9",
                "https://image.tmdb.org/t/p/w500/zGVbrulkupqpbwgiNedkJPyQum4.jpg",
                "https://image.tmdb.org/t/p/w500/ryY0vB4hrIt4l5gjhMDiEDHScvY.jpg"
            )
        )

        add(
            Content(
                "m09",
                "Mortal Kombat",
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                "04/23/2021",
                "Fantasy, Action, Adventure",
                "Released",
                "5.5",
                "https://image.tmdb.org/t/p/w500/8yhtzsbBExY8mUct2GOk4LDDuGH.jpg",
                "https://image.tmdb.org/t/p/w500/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg"
            )
        )

        add(
            Content(
                "m10",
                "Demon Slayer the Movie: Mugen Train",
                "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
                "04/23/2021",
                "Animation, Action, Adventure, Fantasy, Drama",
                "Released",
                "8.5",
                "https://image.tmdb.org/t/p/w500/yF45egpHwaYLn4jTyZAgk0Cmug9.jpg",
                "https://image.tmdb.org/t/p/w500/d1sVANghKKMZNvqjW0V6y1ejvV9.jpg"
            )
        )
    }


    fun getDummyTvShows(): ArrayList<Content> = ArrayList<Content>().apply {
        add(
            Content(
                "tv01",
                "The Falcon and the Winter Soldier",
                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                "19/05/2021",
                "Sci-Fi & Fantasy, Action & Adventure, Drama",
                "Returning Series",
                "7.7",
                "https://image.tmdb.org/t/p/w500/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
                "https://image.tmdb.org/t/p/w500/JB17sIsU53NuWVUecOwrCA0CUp.jpg"
            )
        )

        add(
            Content(
                "tv02",
                "Jujutsu Kaisen",
                "With his days numbered, high schooler Yuji decides to hunt down and consume the remaining 19 fingers of a deadly curse so it can die with him.",
                "03/10/2020",
                "Animation, Action & Adventure, Sci-Fi & Fantasy",
                "Ended",
                "8.3",
                "https://image.tmdb.org/t/p/w500/g1rK2nRXSidcMwNliWDIroWWGTn.jpg",
                "https://image.tmdb.org/t/p/w500/lthkKBLe1rX6iThgVFg22O02sJw.jpg"
            )
        )

        add(
            Content(
                "tv03",
                "Attack on Titan",
                "Several hundred years ago, humans were nearly exterminated by Titans. Titans are typically several stories tall, seem to have no intelligence, devour human beings and, worst of all, seem to do it for the pleasure rather than as a food source. A small percentage of humanity survived by walling themselves in a city protected by extremely high walls, even taller than the biggest Titans. Flash forward to the present and the city has not seen a Titan in over 100 years. Teenage boy Eren and his foster sister Mikasa witness something horrific as the city walls are destroyed by a Colossal Titan that appears out of thin air. As the smaller Titans flood the city, the two kids watch in horror as their mother is eaten alive. Eren vows that he will murder every single Titan and take revenge for all of mankind.",
                "06/04/2013",
                "Sci-Fi & Fantasy, Animation, Action & Adventure",
                "Returning Series",
                "8.4",
                "https://image.tmdb.org/t/p/w500/aiy35Evcofzl7hASZZvsFgltHTX.jpg",
                "https://image.tmdb.org/t/p/w500/3zt7xPU8pn5wpc17OScVDrGgV5X.jpg"
            )
        )

        add(
            Content(
                "tv04",
                "Game of Thrones",
                "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                "05/12/2010",
                "Sci-Fi & Fantasy, Drama, Action & Adventure",
                "Ended",
                "8.4",
                "https://image.tmdb.org/t/p/w500/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                "https://image.tmdb.org/t/p/w500/suopoADq0k8YZr4dQXcU6pToj6s.jpg"
            )
        )

        add(
            Content(
                "tv05",
                "The Rising of the Shield Hero",
                "Iwatani Naofumi was summoned into a parallel world along with 3 other people to become the world's Heroes. Each of the heroes respectively equipped with their own legendary equipment when summoned, Naofumi received the Legendary Shield as his weapon. Due to Naofumi's lack of charisma and experience he's labeled as the weakest, only to end up betrayed, falsely accused, and robbed by on the third day of adventure. Shunned by everyone from the king to peasants, Naofumi's thoughts were filled with nothing but vengeance and hatred. Thus, his destiny in a parallel World begins...",
                "09/01/2019",
                "Animation, Action & Adventure, Sci-Fi & Fantasy, Drama",
                "Returning Series",
                "9.5",
                "https://image.tmdb.org/t/p/w500/VSxgt1WBqkI1Wf65zQNiU32xHv.jpg",
                "https://image.tmdb.org/t/p/w500/VSxgt1WBqkI1Wf65zQNiU32xHv.jpg"
            )
        )

        add(
            Content(
                "tv06",
                "Dr. Stone",
                "One fateful day, all of humanity was petrified by a blinding flash of light. After several millennia, high schooler Taiju awakens and finds himself lost in a world of statues. However, he's not alone! His science-loving friend Senku's been up and running for a few months and he's got a grand plan in mind, to kickstart civilization with the power of science!",
                "05/07/2019",
                "Action & Adventure, Animation",
                "Returning Series",
                "8.4",
                "https://image.tmdb.org/t/p/w500/dLlnzbDCblBXcJqFLXyvN43NIwp.jpg",
                "https://image.tmdb.org/t/p/w500/ty7JiHOBnD0UV88m1eudJvPQNXg.jpg"
            )
        )

        add(
            Content(
                "tv07",
                "Made In Abyss",
                "Located in the center of a remote island, the Abyss is the last unexplored region, a huge and treacherous fathomless hole inhabited by strange creatures where only the bravest adventurers descend in search of ancient relics. In the upper levels of the Abyss, Riko, a girl who dreams of becoming an explorer, stumbles upon a mysterious little boy.",
                "07/07/2017",
                "Animation, Drama, Action & Adventure, Sci-Fi & Fantasy",
                "Ended",
                "8.0",
                "https://image.tmdb.org/t/p/w500/5ICCEOKdqHFGp03zNMZmi95q9WB.jpg",
                "https://image.tmdb.org/t/p/w500/uzp513qTcHsAavlCJ58x5d73bzy.jpg"
            )
        )

        add(
            Content(
                "tv08",
                "Who Killed Sara?",
                "Hell-bent on exacting revenge and proving he was framed for his sister's murder, Álex sets out to unearth much more than the crime's real culprit.",
                "24/03/2021",
                "Drama, Crime, Mystery",
                "Returning Series",
                "7.8",
                "https://image.tmdb.org/t/p/w500/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg",
                "https://image.tmdb.org/t/p/w500/ufkXdR5FNsXfe76VOydT74bRrP7.jpg"
            )
        )

        add(
            Content(
                "tv09",
                "Dororo",
                "A samurai lord has bartered away his newborn son's organs to forty-eight demons in exchange for dominance on the battlefield. Yet, the abandoned infant survives thanks to a medicine man who equips him with primitive prosthetics—lethal ones with which the wronged son will use to hunt down the multitude of demons to reclaim his body one piece at a time, before confronting his father. On his journeys the young hero encounters an orphan who claims to be the greatest thief in Japan.",
                "07/01/2019",
                "Animation, Action & Adventure",
                "Ended",
                "9.0",
                "https://image.tmdb.org/t/p/w500/jyFHtqYeVvc2A9nLhFGvM49xGPg.jpg",
                "https://image.tmdb.org/t/p/w500/xXY9WxE3KxwxftTmJIU8LwY5ojD.jpg"
            )
        )

        add(
            Content(
                "tv10",
                "The Flash",
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "07/10/2014",
                "Drama, Sci-Fi & Fantasy",
                "Returning Series",
                "8.4",
                "https://image.tmdb.org/t/p/w500/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                "https://image.tmdb.org/t/p/w500/8s7WmphQS3jdUr3z6HvTLmet9Bk.jpg"
            )
        )
    }

}