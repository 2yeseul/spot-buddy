package kr.co.spotbuddy.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.co.spotbuddy.domain.Post
import kr.co.spotbuddy.domain.QPost.post
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class PostQueryRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    // batch..
    fun resetTodayView() {
        jpaQueryFactory.update(post).set(post.todayViewCount, 0)
    }

    fun findAllByMemberId(memberId: Long): List<Post> {
        return jpaQueryFactory.selectFrom(post)
            .where(post.member.id.eq(memberId))
            .fetch()
    }

    fun searchByKeywordsAtHome(keyword: String, pageRequest: PageRequest): List<Post> {
        return jpaQueryFactory.selectFrom(post)
            .where(post.title.contains(keyword).or(post.content.contains(keyword)))
            .orderBy(post.id.desc())
            .limit(pageRequest.pageSize.toLong())
            .offset(pageRequest.offset)
            .fetch()
    }

    fun getFilteredPosts() {

    }

}