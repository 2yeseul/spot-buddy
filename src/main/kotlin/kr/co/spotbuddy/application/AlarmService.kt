package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.Alarm
import kr.co.spotbuddy.domain.Member
import kr.co.spotbuddy.domain.repository.AlarmRepository
import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import kr.co.spotbuddy.interfaces.request.AlarmRequest
import org.springframework.data.domain.Pageable
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
    fun saveAlarm(alarmRequest: AlarmRequest) {
        val member = memberService.getById(alarmRequest.memberId)

        alarmRepository.save(Alarm.from(alarmRequest, member))
    }

    @Transactional
    fun deleteAllAlarmsByMember(memberId: Long) {
        val member = memberService.getById(memberId)
        alarmRepository.deleteAllByMember(member)
    }

    @Transactional
    fun changeAlarmStatusToRead(id: Long) {
        val alarm = getAlarmById(id)

        alarm.changeStatusToRead()
    }

    fun getMemberAlarms(memberId: Long, pageable: Pageable): Pair<List<Alarm>, Member> {
        val member = memberService.getById(memberId)
        return Pair(alarmRepository.findAllByMemberOrderByIdDesc(member, pageable).content, member)
    }

    @Transactional
    fun deleteSingleAlarm(id: Long) {
        val alarm = getAlarmById(id)

        alarmRepository.delete(alarm)
    }

    // TODO: Chat 구현 뒤 고치기
    fun isAnyAlarmExists(memberId: Long) {

    }
}