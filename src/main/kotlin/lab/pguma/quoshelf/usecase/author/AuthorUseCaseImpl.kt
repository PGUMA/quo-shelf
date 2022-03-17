package lab.pguma.quoshelf.usecase.author

import lab.pguma.quoshelf.domain.author.Author
import lab.pguma.quoshelf.domain.author.AuthorId
import lab.pguma.quoshelf.domain.author.AuthorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthorUseCaseImpl(private val authorRepository: AuthorRepository) : AuthorUseCase {

    @Transactional(readOnly = true)
    override fun getDetail(id: Int): AuthorDetail {
        return authorRepository.detail(AuthorId.of(id))
    }

    @Transactional
    override fun createAuthor(resource: AuthorCreateResource): AuthorDetail {
        val author = authorRepository.save(Author.new(name = resource.authorName))
        return authorRepository.detail(author.id)
    }

    @Transactional
    override fun updateAuthor(resource: AuthorUpdateResource): AuthorDetail {
        val author = authorRepository.update(
            Author.reconstruct(
                id = AuthorId.of(resource.authorId),
                name = resource.authorName
            )
        )
        return authorRepository.detail(author.id)
    }

    @Transactional(readOnly = true)
    override fun searchAuthors(criteria: AuthorSearchCriteria): List<AuthorSearchResultRecord> {
        return authorRepository.findBy(criteria)
            .map { AuthorSearchResultRecord(authorId = it.authorId, authorName = it.authorName) }
    }
}