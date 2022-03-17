package lab.pguma.quoshelf.infrastructure.book

import lab.pguma.quoshelf.domain.author.AuthorId
import lab.pguma.quoshelf.domain.book.Book
import lab.pguma.quoshelf.domain.book.BookId
import lab.pguma.quoshelf.domain.book.BookRepository
import lab.pguma.quoshelf.usecase.book.BookSearchCriteria
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class JooqBookRepositoryTest @Autowired constructor(private val bookRepository: BookRepository) {

    @Test
    @DisplayName("登録済みの書籍が取得できること")
    @Transactional(readOnly = true)
    fun getExistsBook() {
        val book = bookRepository.get(BookId.of(1))
        assertNotNull(book)
        book?.run {
            assertEquals("Kotlin Ultimania vol.1", title)
            assertEquals(AuthorId.of(1), author)
        }
    }

    @Test
    @DisplayName("登録済みの書籍詳細が取得できること")
    @Transactional(readOnly = true)
    fun getExistsBookDetail() {
        val bookDetail = bookRepository.detail(BookId.of(1))
        assertNotNull(bookDetail)
        bookDetail?.run {
            assertEquals(1, bookId)
            assertEquals("Kotlin Ultimania vol.1", bookTitle)
            assertEquals("山田一郎", authorName)
        }
    }

    @Test
    @DisplayName("未登録の書籍はNullが返ること")
    @Transactional(readOnly = true)
    fun getNotExistsBook() {
        val book = bookRepository.get(BookId.of(99))
        assertNull(book)
    }

    @Test
    @DisplayName("新規の書籍の登録が永続化できること")
    @Transactional
    fun saveNewBook() {
        val book = Book.new("new book", AuthorId.of(1))
        bookRepository.save(book).run {
            assertNotNull(bookRepository.get(id))
        }
    }

    @Test
    @DisplayName("登録済み書籍の更新が永続化できること")
    @Transactional
    fun updateBook() {
        val book = Book.reconstruct(BookId.of(2), "update book", AuthorId.of(1))
        bookRepository.update(book).run {
            assertEquals(BookId.of(2), id)
            assertEquals("update book", title)
            assertEquals(AuthorId.of(1), author)
        }
    }

    @Test
    @DisplayName("登録済みの書籍検索ができること")
    @Transactional(readOnly = true)
    fun searchBooks() {
        val bookDetails = bookRepository.findBy(BookSearchCriteria("Kotlin"))
        assertNotNull(bookDetails)
        assertTrue(bookDetails.isNotEmpty()) // FIXME なぜかdata.sqlによるデータ投入が２回行われている模様・・・
    }
}