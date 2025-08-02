package com.deepzub.footify.data.mapper

import com.deepzub.footify.data.remote.dto.clubs.ResponseClub
import com.deepzub.footify.data.room.ClubEntity
import com.deepzub.footify.domain.model.Club
import com.deepzub.footify.domain.model.Country

fun ResponseClub.toDomain(countries: List<Country>): Club {

    val flag = countries.find { it.name.equals(team.country, ignoreCase = true) }?.flag ?: ""
    val coordinates = clubCoordinates[team.name]

    return Club(
        id = team.id,
        name = team.name,
        country = team.country,
        founded = team.founded,
        logoUrl = team.logo,
        stadiumName = venue.name,
        stadiumCapacity = venue.capacity,
        flagUrl = flag,
        latitude = coordinates?.first,
        longitude = coordinates?.second
    )
}

fun ClubEntity.toDomain(): Club {
    return Club(
        id = id,
        name = name,
        country = country,
        founded = founded,
        logoUrl = logoUrl,
        stadiumName = stadiumName,
        stadiumCapacity = stadiumCapacity,
        flagUrl = flagUrl,
        latitude = latitude,
        longitude = longitude
    )
}

fun Club.toEntity(): ClubEntity {
    return ClubEntity(
        id = id,
        name = name,
        country = country,
        founded = founded,
        logoUrl = logoUrl,
        stadiumName = stadiumName,
        stadiumCapacity = stadiumCapacity,
        flagUrl = flagUrl,
        latitude = latitude,
        longitude = longitude
    )
}

private val clubCoordinates = mapOf(

    //England
    "Manchester United" to (53.4635 to -2.2923),
    "Newcastle" to (54.9756 to -1.6216),
    "Bournemouth" to (50.7345 to -1.8363),
    "Fulham" to (51.4749 to -0.2216),
    "Wolves" to (52.5904 to -2.1309),
    "Liverpool" to (53.4308 to -2.9614),
    "Arsenal" to (51.5549 to -0.1091),
    "Burnley" to (53.7891 to -2.2306),
    "Everton" to (53.4388 to -2.9664),
    "Tottenham" to (51.6043 to -0.0665),
    "West Ham" to (51.5386 to 0.0166),
    "Chelsea" to (51.4816 to -0.1910),
    "Manchester City" to (53.4831 to -2.2004),
    "Brighton" to (50.8615 to -0.0839),
    "Crystal Palace" to (51.3983 to -0.0857),
    "Brentford" to (51.4890 to -0.2880),
    "Sheffield Utd" to (53.3703 to -1.4700),
    "Nottingham Forest" to (52.9394 to -1.1321),
    "Aston Villa" to (52.5092 to -1.8849),
    "Luton" to (51.8840 to -0.4317),

    //France
    "Lille" to (50.6113 to 3.1282),
    "Lyon" to (45.7677 to 4.8357),
    "Marseille" to (43.2708 to 5.3955),
    "Montpellier" to (43.6111 to 3.8767),
    "Nantes" to (47.2184 to -1.5536),
    "Nice" to (43.7102 to 7.2620),
    "Paris Saint Germain" to (48.8415 to 2.2526),
    "Monaco" to (43.7275 to 7.4158),
    "Reims" to (49.2583 to 4.0316),
    "Rennes" to (48.1147 to -1.6809),
    "Strasbourg" to (48.5839 to 7.7455),
    "Toulouse" to (43.6045 to 1.4432),
    "Lorient" to (47.7508 to -3.3662),
    "Clermont Foot" to (45.7781 to 3.0870),
    "Stade Brestois 29" to (48.3904 to -4.4861),
    "Le Havre" to (49.4944 to 0.1079),
    "Metz" to (49.1193 to 6.1757),
    "Lens" to (50.4300 to 2.8200),
    "Saint Etienne" to (45.4446 to 4.3890),

    //Germany
    "Bayern München" to (48.2188 to 11.6247),
    "Fortuna Düsseldorf" to (51.2383 to 6.7358),
    "SC Freiburg" to (47.9886 to 7.8939),
    "VfL Wolfsburg" to (52.4328 to 10.7866),
    "Werder Bremen" to (53.0664 to 8.8376),
    "Borussia Mönchengladbach" to (51.1745 to 6.3856),
    "FSV Mainz 05" to (49.9834 to 8.2475),
    "Borussia Dortmund" to (51.4926 to 7.4517),
    "1899 Hoffenheim" to (49.2381 to 8.8875),
    "Bayer Leverkusen" to (51.0381 to 7.0026),
    "Eintracht Frankfurt" to (50.0685 to 8.6456),
    "FC Augsburg" to (48.3230 to 10.9318),
    "VfB Stuttgart" to (48.7928 to 9.2320),
    "RB Leipzig" to (51.3452 to 12.3484),
    "VfL Bochum" to (51.4860 to 7.2168),
    "1. FC Heidenheim" to (48.6830 to 10.1510),
    "SV Darmstadt 98" to (49.8622 to 8.6561),
    "Union Berlin" to (52.4574 to 13.5681),
    "1.FC Köln" to (50.9335 to 6.8750),

    //Spain
    "Barcelona" to (41.3809 to 2.1228),
    "Atletico Madrid" to (40.4362 to -3.5995),
    "Athletic Club" to (43.2641 to -2.9497),
    "Valencia" to (39.4749 to -0.3584),
    "Villarreal" to (39.9437 to -0.1030),
    "Las Palmas" to (28.1002 to -15.4165),
    "Sevilla" to (37.3841 to -5.9707),
    "Celta Vigo" to (42.2118 to -8.7392),
    "Real Madrid" to (40.4531 to -3.6884),
    "Alaves" to (42.8461 to -2.6885),
    "Real Betis" to (37.3564 to -5.9816),
    "Getafe" to (40.3259 to -3.7146),
    "Girona" to (41.9610 to 2.8095),
    "Real Sociedad" to (43.3017 to -1.9734),
    "Granada CF" to (37.2023 to -3.6032),
    "Almeria" to (36.8401 to -2.4350),
    "Cadiz" to (36.5024 to -6.2731),
    "Osasuna" to (42.7990 to -1.6371),
    "Rayo Vallecano" to (40.3919 to -3.6584),
    "Mallorca" to (39.5892 to 2.6505),

    //Italy
    "Lazio" to (41.9339 to 12.4544),
    "Sassuolo" to (44.5469 to 10.9254),
    "AC Milan" to (45.4781 to 9.1240),
    "Cagliari" to (39.2468 to 9.1316),
    "Napoli" to (40.8270 to 14.1930),
    "Udinese" to (46.0830 to 13.2007),
    "Genoa" to (44.4164 to 8.9526),
    "Juventus" to (45.1096 to 7.6413),
    "AS Roma" to (41.9339 to 12.4544),
    "Atalanta" to (45.6951 to 9.6785),
    "Bologna" to (44.4939 to 11.3095),
    "Fiorentina" to (43.7809 to 11.2826),
    "Torino" to (45.0418 to 7.6528),
    "Verona" to (45.4171 to 10.9782),
    "Inter" to (45.4781 to 9.1240),
    "Empoli" to (43.7177 to 10.9545),
    "Frosinone" to (41.6426 to 13.3419),
    "Salernitana" to (40.6458 to 14.8231),
    "Lecce" to (40.3600 to 18.1720),
    "Monza" to (45.5831 to 9.2982),

)