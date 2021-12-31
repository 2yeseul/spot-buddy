package kr.co.spotbuddy.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.spotbuddy.domain.Member
import kr.co.spotbuddy.domain.Tour
import kr.co.spotbuddy.domain.TourLookUpEvent
import kr.co.spotbuddy.domain.TourRepository
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

    @Transactional
    fun modifyTour(tourRequest: TourRequest, member: Member) {
        val tour = tourRequest.id?.let { getTourById(it) } ?: throw CustomException(ExceptionDefinition.BAD_REQUEST)

        tour.apply {
            this.update(tourRequest)
        }
        // TODO : tourThemes 업데이트 처리
    }

    fun uploadTour(tourRequest: TourRequest, member: Member) {
        when {
            isTempSavedAndMemberId(tourRequest.isTempSaved, member) -> {
                modifyTour(tourRequest, member)
            }
        }
    }
}