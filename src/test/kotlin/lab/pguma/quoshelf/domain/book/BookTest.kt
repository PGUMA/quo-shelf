package lab.pguma.quoshelf.domain.book

import lab.pguma.quoshelf.domain.author.AuthorId
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/*
 NOTE: Authorと観点が被るようなテストは省略しています。
 */

internal class BookTest {
    @Test
    @DisplayName("ドメインインスタンスを取得する場合に著者が未登録の場合例外が送出されること")
    fun newDomainInstance() {
        assertThrows(IllegalArgumentException::class.java) {
            Book.new(title = "test title", author = AuthorId.none())
        }
    }
}