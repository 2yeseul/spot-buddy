package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.Alarm
import kr.co.spotbuddy.domain.repository.AlarmRepository
import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlarmService(
    private val alarmRepository: AlarmRepository,
    private val memberService: MemberService,
) {
    fun getAlarmById(id: Long): Alarm =
        alarmRepository.findByIdOrNull(id) ?: throw CustomException(ExceptionDefinition.BAD_REQUEST)

    @Transactional
    fun deleteAllAlarmsByMember(memberId: Long) {
        val member = memberService.getById(memberId)
        alarmRepository.deleteAllByMember(member)
    }

    @Transactional
    fun deleteSingleAlarm(id: Long) {
        val alarm = getAlarmById(id)

        alarmRepository.delete(alarm)
    }

}