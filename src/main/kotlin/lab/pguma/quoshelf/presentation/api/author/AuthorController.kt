package lab.pguma.quoshelf.presentation.api.author

import lab.pguma.quoshelf.presentation.api.author.AuthorController.Companion.API_AUTHOR_ENDPOINT
import lab.pguma.quoshelf.usecase.author.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(API_AUTHOR_ENDPOINT)
class AuthorController(private val authorUseCase: AuthorUseCase) {

    companion object {
        const val API_AUTHOR_ENDPOINT = "/api/author"
    }

    @GetMapping("/{id}")
    @ResponseBody
    fun show(@PathVariable id: Int): AuthorDetail {
        return authorUseCase.getDetail(id)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: AuthorCreateRequest): AuthorDetail {
        return authorUseCase.createAuthor(AuthorCreateResource(authorName = request.authorName))
    }

    @PutMapping("/{id}")
    @ResponseBody
    fun update(
        @PathVariable id: Int,
        @RequestBody request: AuthorUpdateRequest
    ): AuthorDetail {
        return authorUseCase.updateAuthor(AuthorUpdateResource(authorId = id, authorName = request.authorName))
    }

    @GetMapping("/search")
    @ResponseBody
    fun find(@RequestParam keyword: String): List<AuthorSearchResultRecord> {
        return authorUseCase.searchAuthors(AuthorSearchCriteria(keyword))
    }
}