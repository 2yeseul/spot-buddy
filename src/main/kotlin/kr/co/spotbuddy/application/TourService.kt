package kr.co.spotbuddy.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.spotbuddy.domain.*
import kr.co.spotbuddy.domain.repository.TourQueryRepository
import kr.co.spotbuddy.domain.repository.TourRepository
import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import kr.co.spotbuddy.interfaces.request.TourRequest
import kr.co.spotbuddy.interfaces.request.TourSortType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TourService(
    private val tourRepository: TourRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val memberService: MemberService,
    private val tourThemeService: TourThemeService,
    private val blockService: BlockService,
    private val tourQueryRepository: TourQueryRepository,
) {
    fun getTourById(id: Long): Tour =
        tourRepository.findByIdOrNull(id) ?: throw CustomException(ExceptionDefinition.NOT_FOUND_TOUR)

    fun isTempSavedAndMemberId(isTempSaved: Boolean, memberId: Long): Boolean {
        val member = memberService.getById(memberId)
        return tourRepository.existsByIsTempSavedAndMember(isTempSaved, member)
    }

    fun getLatestTempTour(member: Member): Tour =
        tourRepository.findFirstByIsTempSavedAndMemberOrderByIdDesc(true, member)

    fun getTourDetail(id: Long) {
        val tour = getTourById(id)

        CoroutineScope(Dispatchers.IO).launch {
            applicationEventPublisher.publishEvent(TourLookUpEvent.fire(tour))
        }
    }

    fun getUsersTemporarySavedTours(member: Member): List<Tour> =
        tourRepository.findAllByIsTempSavedAndMember(true, member)

    @Transactional
    fun deleteAllTemporarySavedTours(memberId: Long) {
        val member = memberService.getById(memberId)
        val temporarySavedTours = getUsersTemporarySavedTours(member)

        tourRepository.deleteAllInBatch(temporarySavedTours)
    }

    @Transactional
    fun deleteById(memberId: Long, tourId: Long) {
        val tour = getTourById(tourId)

        tour.member.id.let {
            if (tour.member.id != memberId) {
                throw CustomException(ExceptionDefinition.BAD_REQUEST)
            }
        }

        tourRepository.delete(tour)
    }

    @Transactional
    fun modifyTour(tourRequest: TourRequest, memberId: Long) {
        val tour = tourRequest.id?.let { getTourById(it) } ?: throw CustomException(ExceptionDefinition.BAD_REQUEST)

        tour.apply {
            this.update(tourRequest)
        }

        tourRequest.tourThemes?.let { tourThemeService.modifyThemes(it, tour.id!!) }
    }

    @Transactional
    fun saveTour(tourRequest: TourRequest, memberId: Long): Pair<Tour, List<String>?> {
        when {
            isTempSavedAndMemberId(tourRequest.isTempSaved, memberId) -> {
                modifyTour(tourRequest, memberId)
            }
        }

        val member = memberService.getById(memberId)
        val tour = tourRepository.save(Tour.of(tourRequest, member))

        val themes = tourRequest.tourThemes
            ?.let { theme -> tourThemeService.saveTourThemes(theme, tour.id!!).map { it.theme } }

        return Pair(tour, themes)
    }

    fun getTours(memberId: Long?, pageable: Pageable, sortType: TourSortType): List<Tour> {
        val getBlockedMemberIds =
            if (memberId != null) blockService.getMemberBlocks(memberId).map(BlockResponse::getBlocked) else listOf()
        when (sortType) {
            TourSortType.LATEST -> tourQueryRepository.getFilteredLatestTours(getBlockedMemberIds, pageable)
            TourSortType.SCRAPS -> tourQueryRepository.getFilteredPopularList(getBlockedMemberIds, pageable)
            TourSortType.MY_AGE -> {
                val memberAge = if (memberId != null) {
                    memberService.getById(memberId).getMemberAge()
                } else throw CustomException(ExceptionDefinition.UNAUTHORIZED)
                return tourQueryRepository.getAllToursSortedByMemberAge(memberAge, getBlockedMemberIds, pageable)
            }
            TourSortType.WEATHER, TourSortType.ENDS_AT, TourSortType.MY_GENDER -> {
                if (memberId == null) throw CustomException(ExceptionDefinition.UNAUTHORIZED)
                tourQueryRepository.getAllToursSortedBy(
                    getBlockedMemberIds,
                    pageable,
                    sortType)
            }
        }
        return listOf()
    }

    fun getMemberTours(memberId: Long, pageable: Pageable): List<Tour> =
        tourQueryRepository.getMemberTours(memberId, pageable)

    @Transactional
    fun closeTour(memberId: Long, id: Long) {
        val member = memberService.getById(memberId)
        val tour = getTourById(id)

        tour.member.id?.let {
            if (it == memberId) {
                tour.apply {
                    this.close()
                }
            }
        }
    }
}