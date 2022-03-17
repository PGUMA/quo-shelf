package lab.pguma.quoshelf.usecase.book

import lab.pguma.quoshelf.domain.author.AuthorId
import lab.pguma.quoshelf.domain.book.Book
import lab.pguma.quoshelf.domain.book.BookId
import lab.pguma.quoshelf.domain.book.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookUseCaseImpl(private val bookRepository: BookRepository) : BookUseCase {

    @Transactional(readOnly = true)
    override fun getBookDetail(id: Int): BookDetail {
        return bookRepository.detail(BookId.of(id))
    }

    @Transactional
    override fun createBook(resource: BookCreateResource): BookDetail {
        val book = bookRepository.save(Book.new(title = resource.bookTitle, author = AuthorId.of(resource.authorId)))
        return bookRepository.detail(book.id)
    }

    @Transactional
    override fun updateBook(resource: BookUpdateResource): BookDetail {
        val book = bookRepository.update(
            Book.reconstruct(
                id = BookId.of(resource.bookId),
                title = resource.bookTitle,
                author = AuthorId.of(resource.authorId)
            )
        )
        return bookRepository.detail(book.id)
    }

    @Transactional(readOnly = true)
    override fun searchBooks(criteria: BookSearchCriteria): List<BookSearchResultRecord> {
        return bookRepository.findBy(criteria)
            .map { BookSearchResultRecord(bookId = it.bookId, bookTitle = it.bookTitle) }
    }
}