package lab.pguma.quoshelf.usecase.book

interface BookUseCase {
    fun getBookDetail(id: Int): BookDetail
    fun createBook(resource: BookCreateResource): BookDetail
    fun updateBook(resource: BookUpdateResource): BookDetail
    fun searchBooks(criteria: BookSearchCriteria): List<BookSearchResultRecord>
}

data class BookCreateResource(
    val bookTitle: String,
    val authorId: Int
)

data class BookUpdateResource(
    val bookId: Int,
    val bookTitle: String,
    val authorId: Int
)

data class BookDetail(
    val bookId: Int,
    val bookTitle: String,
    val authorName: String
)

data class BookSearchCriteria(
    val keyword: String?
)

data class BookSearchResultRecord(
    val bookId: Int,
    val bookTitle: String,
)