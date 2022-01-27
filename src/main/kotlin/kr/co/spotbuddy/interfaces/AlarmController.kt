package kr.co.spotbuddy.interfaces

import kr.co.spotbuddy.application.AlarmService
import kr.co.spotbuddy.interfaces.response.AlarmView
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/alarms")
class AlarmController(
    private val alarmService: AlarmService,
) {
    @GetMapping("/")
    fun getMemberAlarms(memberId: Long, pageRequest: PageRequest): ResponseEntity<List<AlarmView>> {
        val alarms = alarmService.getMemberAlarms(memberId, pageRequest)

        return ResponseEntity.ok(
            alarms.first.map {
                AlarmView.of(it, alarms.second)
            }
        )
    }

    @DeleteMapping("/all")
    fun deleteAllAlarms(memberId: Long): ResponseEntity<HttpStatus> {
        alarmService.deleteAllAlarmsByMember(memberId)

        return ResponseEntity.ok(HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/{id}")
    fun deleteSingleAlarm(@PathVariable id: Long): ResponseEntity<HttpStatus> {
        alarmService.deleteSingleAlarm(id)

        return ResponseEntity.ok(HttpStatus.ACCEPTED)
    }
}