package lab.pguma.quoshelf.infrastructure.author

import lab.pguma.quoshelf.domain.author.AuthorId
import lab.pguma.quoshelf.domain.author.AuthorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

/*
 NOTE: JooqAuthorRepositoryと観点が被るようなテストは省略しています。
 */

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class JooqAuthorRepositoryTest @Autowired constructor(private val authorRepository: AuthorRepository) {

    @Test
    @DisplayName("登録済みの著者詳細が取得できること")
    @Transactional(readOnly = true)
    fun getExistsAuthorDetail() {
        val authorDetail = authorRepository.detail(AuthorId.of(1))
        Assertions.assertNotNull(authorDetail)
        authorDetail?.run {
            Assertions.assertEquals(1, authorId)
            Assertions.assertEquals("山田一郎", authorName)
            authorDetail.books.forEach { bookDetail ->
                Assertions.assertEquals("Kotlin Ultimania vol.1", bookDetail.bookTitle)
            }
        }
    }
}