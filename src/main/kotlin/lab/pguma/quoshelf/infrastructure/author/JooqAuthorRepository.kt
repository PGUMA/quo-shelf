package lab.pguma.quoshelf.infrastructure.author

import lab.pguma.quoshelf.domain.author.Author
import lab.pguma.quoshelf.domain.author.AuthorId
import lab.pguma.quoshelf.domain.author.AuthorRepository
import lab.pguma.quoshelf.infrastructure.jooq.models.Keys
import lab.pguma.quoshelf.infrastructure.jooq.models.tables.Authors.AUTHORS
import lab.pguma.quoshelf.infrastructure.jooq.models.tables.records.AuthorsRecord
import lab.pguma.quoshelf.usecase.author.AuthorDetail
import lab.pguma.quoshelf.usecase.author.AuthorSearchCriteria
import lab.pguma.quoshelf.usecase.book.BookDetail
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class JooqAuthorRepository(private val jooq: DSLContext) : AuthorRepository {
    override fun get(authorId: AuthorId): Author? {
        return jooq
            .selectFrom(AUTHORS)
            .where(AUTHORS.AUTHOR_ID.eq(authorId.value))
            .fetchOne()
            ?.toDomain()
    }

    override fun save(author: Author): Author {
        require(author.id.isNotIssued()) { "IDが払い出し済みの著者は新規作成できません。id=${author.id}" }

        val authorRecord = jooq.newRecord(AUTHORS).apply {
            set(AUTHORS.AUTHOR_NAME, author.name)
        }

        authorRecord.store()
        return authorRecord.toDomain()
    }

    override fun update(author: Author): Author {
        val authorRecord = jooq
            .fetchOptional(AUTHORS, AUTHORS.AUTHOR_ID.eq(author.id.value))
            .orElseThrow { IllegalArgumentException("更新対象レコードが存在しません。id=${author.id}") }

        authorRecord.values(author.id.value, author.name).store()
        return authorRecord.toDomain()
    }

    override fun detail(authorId: AuthorId): AuthorDetail {
        return jooq
            .fetchOptional(AUTHORS, AUTHORS.AUTHOR_ID.eq(authorId.value))
            .map(AuthorsRecord::toAuthorDetail)
            .orElseThrow { throw IllegalArgumentException("対象のIDに紐付くデータが存在しません。id=${authorId}") }
    }

    override fun findBy(criteria: AuthorSearchCriteria): List<AuthorDetail> {
        return jooq
            .selectFrom(AUTHORS)
            .where(AUTHORS.AUTHOR_NAME.contains(criteria.keyword))
            .orderBy(AUTHORS.AUTHOR_ID)
            .map(AuthorsRecord::toAuthorDetail)
    }
}

private fun AuthorsRecord.toAuthorDetail(): AuthorDetail {
    return AuthorDetail(
        authorId = authorId,
        authorName = authorName,
        books = fetchChildren(Keys.BOOK_WRITING_AUTHOR)
            .map { bookRecord ->
                BookDetail(
                    bookId = bookRecord.bookId,
                    bookTitle = bookRecord.bookTitle,
                    authorName = authorName
                )
            }
    )
}

private fun AuthorsRecord.toDomain(): Author {
    return Author.reconstruct(
        id = AuthorId.of(authorId),
        name = authorName
    )
}