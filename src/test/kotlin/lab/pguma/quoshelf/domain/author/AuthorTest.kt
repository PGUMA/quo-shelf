package lab.pguma.quoshelf.domain.author

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class AuthorTest {
    @Test
    @DisplayName("新規にドメインインスタンスを取得した場合にAuthorIdが未払いだし状態であること")
    fun newDomainInstance() {
        val author = Author.new("test name")
        assertEquals(true, author.id.isNotIssued())
    }

    @Test
    @DisplayName("ドメインインスタンスを再構築できること（有効値）")
    fun getIssuedAuthorIdWithValidValue() {
        val author = Author.reconstruct(
            id = AuthorId.of(10),
            name = "test name"
        )

        assertEquals(AuthorId.of(10), author.id)
        assertEquals("test name", author.name)
    }

    @Test
    @DisplayName("ドメインインスタンスを再構築（無効値）すると例外が送出されること")
    fun getIssuedAuthorIdWithInValidValue() {
        assertThrows(IllegalArgumentException::class.java) {
            Author.reconstruct(
                id = AuthorId.none(),
                name = "test name"
            )
        }
    }
}