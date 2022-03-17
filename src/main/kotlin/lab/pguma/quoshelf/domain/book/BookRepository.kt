package lab.pguma.quoshelf.domain.book

import lab.pguma.quoshelf.usecase.book.BookDetail
import lab.pguma.quoshelf.usecase.book.BookSearchCriteria

interface BookRepository {
    fun get(bookId: BookId): Book?
    fun save(book: Book): Book
    fun update(book: Book): Book

    fun detail(bookId: BookId): BookDetail
    fun findBy(criteria: BookSearchCriteria): List<BookDetail>
}