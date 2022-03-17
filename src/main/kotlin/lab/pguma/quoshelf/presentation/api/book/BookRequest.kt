package lab.pguma.quoshelf.presentation.api.book

data class BookCreateRequest(
    val bookTitle: String,
    val authorId: Int
)

data class BookUpdateRequest(
    val bookTitle: String,
    val authorId: Int
)