package kr.co.spotbuddy.common

enum class KboTeams(
    val location: String
) {
    LG("잠실"),
    DOOSAN("잠실"),
    KIWOOM("고척"),
    SSG("문학"),
    KT("수원"),
    HANHWA("대전"),
    KIA("광주"),
    LOTTE("사직"),
    SAMSUNG("대구"),
    NC("창원");

    fun getAllLocations(): List<String> {
        return KboTeams.values()
            .map { location }
    }
}