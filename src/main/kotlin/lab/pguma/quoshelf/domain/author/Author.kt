package lab.pguma.quoshelf.domain.author

@JvmInline
value class AuthorId private constructor(val value: Int) {
    fun isNotIssued() = value == 0
    fun isIssued() = isNotIssued().not()

    companion object {
        fun none(): AuthorId = AuthorId(0)
        fun of(value: Int): AuthorId {
            require(value > 0) { "AuthorIdは０より大きい値でないと外部からは初期化できません" }
            return AuthorId(value)
        }
    }
}

class Author private constructor(
    val id: AuthorId,
    val name: String,
    // etc....
) {
    companion object {
        fun new(name: String): Author = Author(id = AuthorId.none(), name = name)
        fun reconstruct(id: AuthorId, name: String): Author {
            require(id.isIssued()) { "AuthorIdの値が不正です。id=${id}" }
            return Author(id = id, name = name)
        }
    }
}