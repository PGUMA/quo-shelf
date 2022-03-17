package lab.pguma.quoshelf.domain.book

import lab.pguma.quoshelf.domain.author.AuthorId

@JvmInline
value class BookId private constructor(val value: Int) {
    fun isNotIssued() = value == 0
    fun isIssued() = isNotIssued().not()

    companion object {
        fun none(): BookId = BookId(0)
        fun of(value: Int): BookId {
            require(value > 0) { "BookIdは０より大きい値でないと外部からは初期化できません" }
            return BookId(value)
        }
    }
}

class Book private constructor(
    val id: BookId,
    val title: String,
    // etc....
    val author: AuthorId
) {
    init {
        require(author.isIssued()) { "登録済みの著者でなければ初期化できません。" }
    }

    companion object {
        fun new(title: String, author: AuthorId) = Book(id = BookId.none(), title = title, author = author)
        fun reconstruct(id: BookId, title: String, author: AuthorId): Book {
            require(id.isIssued()) { "BookIdの値が不正です。id=${id}" }
            return Book(id = id, title = title, author = author)
        }
    }
}