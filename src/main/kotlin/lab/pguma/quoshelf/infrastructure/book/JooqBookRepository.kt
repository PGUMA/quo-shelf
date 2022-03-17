package lab.pguma.quoshelf.infrastructure.book

import lab.pguma.quoshelf.domain.author.AuthorId
import lab.pguma.quoshelf.domain.book.Book
import lab.pguma.quoshelf.domain.book.BookId
import lab.pguma.quoshelf.domain.book.BookRepository
import lab.pguma.quoshelf.infrastructure.jooq.models.tables.Authors.AUTHORS
import lab.pguma.quoshelf.infrastructure.jooq.models.tables.Books.BOOKS
import lab.pguma.quoshelf.infrastructure.jooq.models.tables.records.BooksRecord
import lab.pguma.quoshelf.usecase.book.BookDetail
import lab.pguma.quoshelf.usecase.book.BookSearchCriteria
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository

@Repository
class JooqBookRepository(private val jooq: DSLContext) : BookRepository {

    override fun get(bookId: BookId): Book? {
        return jooq
            .selectFrom(BOOKS)
            .where(BOOKS.BOOK_ID.eq(bookId.value))
            .fetchOne()?.toDomain()
    }

    override fun save(book: Book): Book {
        require(book.id.isNotIssued()) { "IDが払い出し済みの書籍は新規作成できません。id=${book.id}" }

        val bookRecord = jooq.newRecord(BOOKS).apply {
            set(BOOKS.BOOK_TITLE, book.title)
            set(BOOKS.AUTHOR_ID, book.author.value)
        }

        bookRecord.store()
        return bookRecord.toDomain()
    }

    override fun update(book: Book): Book {
        val bookRecord = jooq
            .fetchOptional(BOOKS, BOOKS.BOOK_ID.eq(book.id.value))
            .orElseThrow { IllegalArgumentException("更新対象レコードが存在しません。id=${book.id}") }

        bookRecord.values(book.id.value, book.title, book.author.value).store()
        return bookRecord.toDomain()
    }

    override fun detail(bookId: BookId): BookDetail {
        return jooq
            .select()
            .from(BOOKS)
            .join(AUTHORS).on(BOOKS.AUTHOR_ID.eq(AUTHORS.AUTHOR_ID))
            .where(BOOKS.BOOK_ID.eq(bookId.value))
            .fetchOptional()
            .map(Record::toBookDetail)
            .orElseThrow { throw IllegalArgumentException("対象のIDに紐付くデータが存在しません。id=${bookId}") }
    }

    override fun findBy(criteria: BookSearchCriteria): List<BookDetail> {
        return jooq
            .select()
            .from(BOOKS)
            .join(AUTHORS).on(BOOKS.AUTHOR_ID.eq(AUTHORS.AUTHOR_ID))
            .where(BOOKS.BOOK_TITLE.contains(criteria.keyword))
            .fetch()
            .map(Record::toBookDetail)
            .toList()
    }
}

private fun Record.toBookDetail(): BookDetail {
    val bookRecord = into(BOOKS)
    val authorRecord = into(AUTHORS)
    return BookDetail(
        bookId = bookRecord.bookId,
        bookTitle = bookRecord.bookTitle,
        authorName = authorRecord.authorName
    )
}

private fun BooksRecord.toDomain(): Book {
    return Book.reconstruct(
        id = BookId.of(bookId),
        title = bookTitle,
        author = AuthorId.of(authorId)
    )
}