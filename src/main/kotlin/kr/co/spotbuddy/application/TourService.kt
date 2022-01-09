package kr.co.spotbuddy.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.spotbuddy.domain.*
import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import kr.co.spotbuddy.interfaces.request.TourRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TourService(
    private val tourRepository: TourRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val memberService: MemberService,
    private val tourThemeService: TourThemeService,
) {
    fun getTourById(id: Long): Tour {
        return tourRepository.findByIdOrNull(id) ?: throw CustomException(ExceptionDefinition.NOT_FOUND_TOUR)
    }

    fun isTempSavedAndMemberId(isTempSaved: Boolean, member: Member): Boolean {
        return tourRepository.existsByIsTempSavedAndMember(isTempSaved, member)
    }

    fun getLatestTempTour(member: Member): Tour {
        return tourRepository.findFirstByIsTempSavedAndMemberOrderByIdDesc(true, member)
    }

    fun getTourDetail(id: Long) {
        val tour = getTourById(id)

        CoroutineScope(Dispatchers.IO).launch {
            applicationEventPublisher.publishEvent(TourLookUpEvent.fire(tour))
        }
    }

    fun getUsersTemporarySavedTours(member: Member): List<Tour> {
        return tourRepository.findAllByIsTempSavedAndMember(true, member)
    }

    @Transactional
    fun deleteAllTemporarySavedTours(memberId: Long) {
        val member = memberService.getById(memberId)
        val temporarySavedTours = getUsersTemporarySavedTours(member)

        tourRepository.deleteAllInBatch(temporarySavedTours)
    }

    @Transactional
    fun modifyTour(tourRequest: TourRequest, member: Member) {
        val tour = tourRequest.id?.let { getTourById(it) } ?: throw CustomException(ExceptionDefinition.BAD_REQUEST)

        tour.apply {
            this.update(tourRequest)
        }
        // TODO : tourThemes 업데이트 처리
    }

    @Transactional
    fun saveTour(tourRequest: TourRequest, memberId: Long): Pair<Tour, List<String>?> {
        val member = memberService.getById(memberId)

        when {
            isTempSavedAndMemberId(tourRequest.isTempSaved, member) -> {
                modifyTour(tourRequest, member)
            }
        }

        val tour = tourRepository.save(Tour.of(tourRequest, member))

        val themes = tourRequest.tourThemes
            ?.let { theme -> tourThemeService.saveTourThemes(theme, tour.id!!).map { it.theme } }

        return Pair(tour, themes)
    }
}