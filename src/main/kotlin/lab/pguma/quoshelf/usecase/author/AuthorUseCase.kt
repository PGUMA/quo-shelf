package lab.pguma.quoshelf.usecase.author

import lab.pguma.quoshelf.usecase.book.BookDetail

interface AuthorUseCase {
    fun getDetail(id: Int): AuthorDetail
    fun createAuthor(resource: AuthorCreateResource): AuthorDetail
    fun updateAuthor(resource: AuthorUpdateResource): AuthorDetail
    fun searchAuthors(criteria: AuthorSearchCriteria): List<AuthorSearchResultRecord>
}

data class AuthorCreateResource(
    val authorName: String
)

data class AuthorUpdateResource(
    val authorId: Int,
    val authorName: String
)

data class AuthorDetail(
    val authorId: Int,
    val authorName: String,
    val books: List<BookDetail> = emptyList()
)

data class AuthorSearchCriteria(
    val keyword: String?
)

data class AuthorSearchResultRecord(
    val authorId: Int,
    val authorName: String
)