package lab.pguma.quoshelf.usecase.author

import lab.pguma.quoshelf.domain.author.Author
import lab.pguma.quoshelf.domain.author.AuthorId
import lab.pguma.quoshelf.usecase.book.BookDetail

internal val authorDetailsBaseFixture = listOf(
    AuthorDetail(
        authorId = 1,
        authorName = "山田一郎",
        books = listOf(BookDetail(bookId = 1, bookTitle = "Kotlin Ultimania vol.1", authorName = "山田一郎"))
    ),
    AuthorDetail(
        authorId = 2,
        authorName = "テスト花子",
        books = listOf(BookDetail(bookId = 1, bookTitle = "Kotlin master vol2", authorName = "テスト花子"))
    )
)

internal val authorSearchResultRecordBaseFixture =
    authorDetailsBaseFixture
        .map { AuthorSearchResultRecord(authorId = it.authorId, authorName = it.authorName) }

internal val authorBaseFixture = Author.reconstruct(
    id = AuthorId.of(1),
    name = "山田一郎",
)