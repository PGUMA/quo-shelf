package lab.pguma.quoshelf.usecase.author

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import lab.pguma.quoshelf.domain.author.AuthorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class AuthorUseCaseImplTest {

    @Autowired
    private lateinit var authorUseCase: AuthorUseCase

    @MockkBean
    private lateinit var authorRepository: AuthorRepository

    @Test
    @DisplayName("著者詳細取得結果のモデルを適切にユースケースに沿ったレスポンスに変換できていること")
    fun getDetail() {
        every { authorRepository.detail(any()) } returns authorDetailsBaseFixture[0]

        authorUseCase.getDetail(1)
            .run {
                assertEquals(1, authorId)
                assertEquals("山田一郎", authorName)
                books.forEach { bookDetail ->
                    assertEquals(1, bookDetail.bookId)
                    assertEquals("Kotlin Ultimania vol.1", bookDetail.bookTitle)
                    assertEquals("山田一郎", bookDetail.authorName)
                }
            }

        verify {
            authorRepository.detail(any())
        }
    }

    @Test
    @DisplayName("著者新規作成結果のモデルを適切にユースケースに沿ったレスポンスに変換できていること")
    fun createAuthor() {
        every { authorRepository.detail(any()) } returns authorDetailsBaseFixture[0]
        every { authorRepository.save(any()) } returns authorBaseFixture
        authorUseCase.createAuthor(AuthorCreateResource(authorName = "山田一郎"))
            .run {
                assertEquals(1, authorId)
                assertEquals("山田一郎", authorName)
                books.forEach { bookDetail ->
                    assertEquals(1, bookDetail.bookId)
                    assertEquals("Kotlin Ultimania vol.1", bookDetail.bookTitle)
                    assertEquals("山田一郎", bookDetail.authorName)
                }
            }

        verify {
            authorRepository.save(any())
            authorRepository.detail(any())
        }
    }

    @Test
    @DisplayName("著者更新作成結果のモデルを適切にユースケースに沿ったレスポンスに変換できていること")
    fun updateAuthor() {
        every { authorRepository.detail(any()) } returns authorDetailsBaseFixture[0]
        every { authorRepository.update(any()) } returns authorBaseFixture
        authorUseCase.updateAuthor(AuthorUpdateResource(authorId = 1, authorName = "山田一郎"))
            .run {
                assertEquals(1, authorId)
                assertEquals("山田一郎", authorName)
                books.forEach { bookDetail ->
                    assertEquals(1, bookDetail.bookId)
                    assertEquals("Kotlin Ultimania vol.1", bookDetail.bookTitle)
                    assertEquals("山田一郎", bookDetail.authorName)
                }
            }

        verify {
            authorRepository.update(any())
            authorRepository.detail(any())
        }
    }

    @Test
    @DisplayName("著者検索結果のモデルを適切にユースケースに沿ったレスポンスに変換できていること")
    fun searchAuthors() {
        every { authorRepository.findBy(any()) } returns authorDetailsBaseFixture
        authorUseCase.searchAuthors(AuthorSearchCriteria("Kotlin"))
            .run {
                assertEquals(2, size)
            }

        verify {
            authorRepository.findBy(any())
        }
    }
}