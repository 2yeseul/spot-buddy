package kr.co.spotbuddy.domain.repository

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.spotbuddy.domain.QBlock.block
import kr.co.spotbuddy.domain.QTour.tour
import kr.co.spotbuddy.domain.Tour
import kr.co.spotbuddy.interfaces.request.TourSortType
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class TourQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun getFilteredLatestTours(getBlockedIds: List<Long>, pageable: Pageable): List<Tour> {
        return jpaQueryFactory.selectFrom(tour)
            .where(tour.member.id.notIn(getBlockedIds).and(tour.isTempSaved.isFalse).and(tour.isEnded.isFalse))
            .orderBy(tour.id.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()
    }

    fun getFilteredPopularList(getBlockedIds: List<Long>, pageable: Pageable): List<Tour> {
        return jpaQueryFactory.selectFrom(tour)
            .where(tour.member.id.notIn(getBlockedIds).and(tour.isTempSaved.isFalse).and(tour.isEnded.isFalse))
            .orderBy(tour.scrapCount.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()
    }


    // 내 연령대 조회
    // tour.minAge <= 내 나이 <= tour.max
    fun getAllToursSortedByMemberAge(memberAge: Int, getBlockedIds: List<Long>?, pageable: Pageable): List<Tour> {
        return jpaQueryFactory.selectFrom(tour)
            .where(tour.isEnded.not()
                .and(tour.isTempSaved.not())
                .and(tour.minimumAge.loe(memberAge))
                .and(tour.maximumAge.goe(memberAge))
                .and(tour.member.id.notIn(getBlockedIds))
            )
            .orderBy(tour.id.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()
    }

    // 마감일 순, 성별, 날씨 정렬
    fun getAllToursSortedBy(getBlockedIds: List<Long>?, pageable: Pageable, sortType: TourSortType): List<Tour> {
        return jpaQueryFactory.selectFrom(tour)
            .where(tour.isEnded.isFalse
                .and(tour.isTempSaved.isFalse)
                .and(tour.member.id.notIn(getBlockedIds))
            )
            .orderBy(sortType.field)
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()
    }

}