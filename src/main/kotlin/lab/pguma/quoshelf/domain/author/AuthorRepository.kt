package lab.pguma.quoshelf.domain.author

import lab.pguma.quoshelf.usecase.author.AuthorDetail
import lab.pguma.quoshelf.usecase.author.AuthorSearchCriteria

interface AuthorRepository {
    fun get(authorId: AuthorId): Author?
    fun save(author: Author): Author
    fun update(author: Author): Author

    fun detail(authorId: AuthorId): AuthorDetail
    fun findBy(criteria: AuthorSearchCriteria): List<AuthorDetail>
}