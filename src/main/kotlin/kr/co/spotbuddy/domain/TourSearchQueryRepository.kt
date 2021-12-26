package kr.co.spotbuddy.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.spotbuddy.domain.QTour.tour
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TourSearchQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory
) {

    fun searchByKeywordAtHome(keyword: String): List<Tour> {
        return jpaQueryFactory.selectFrom(tour)
            .where(tour.tourTitle.contains(keyword).or(tour.tourContent.contains(keyword)))
            .orderBy(tour.id.desc())
            .fetch()
    }

    fun searchByKeyword(keyword: String): List<Tour> {
        return jpaQueryFactory.selectFrom(tour)
            .where(tour.tourLocation.contains(keyword).or(tour.tourTeam.contains(keyword)))
            .orderBy(tour.id.desc())
            .fetch()
    }

    fun searchByLocationAndDate(location: String, startDate: LocalDate, endDate: LocalDate): List<Tour> {
        if (startDate == endDate) {
            return jpaQueryFactory.selectFrom(tour)
                .where(tour.tourLocation.eq(location).and(tour.startDate.loe(startDate)).and(tour.endDate.goe(endDate)))
                .orderBy(tour.id.desc())
                .fetch()
        }

        return jpaQueryFactory.selectFrom(tour)
            .where(tour.tourLocation.contains(location).and(tour.startDate.goe(startDate)).and(tour.endDate.loe(endDate)))
            .orderBy(tour.id.desc())
            .fetch()
    }

}