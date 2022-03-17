package lab.pguma.quoshelf.domain.author

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


internal class AuthorIdTest {

    @Test
    @DisplayName("未払いだし状態を表現したAuthorIdが取得できること")
    fun getUnIssuedAuthorId() {
        assertEquals(true, AuthorId.none().isNotIssued())
    }

    @Test
    @DisplayName("指定したID（有効値）でAuthorIdが取得できること")
    fun getIssuedAuthorIdWithValidValue() {
        val authorId = AuthorId.of(5)
        assertEquals(5, authorId.value)
    }

    @Test
    @DisplayName("指定したID（無効値）でAuthorIdを取得しようとすると例外が送出されること")
    fun getIssuedAuthorIdWithInValidValue() {
        assertThrows(IllegalArgumentException::class.java) { AuthorId.of(0) }
    }

}