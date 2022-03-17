package lab.pguma.quoshelf.presentation.api.book

import lab.pguma.quoshelf.presentation.api.book.BookController.Companion.API_BOOK_ENDPOINT
import lab.pguma.quoshelf.usecase.book.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(API_BOOK_ENDPOINT)
class BookController(private val bookUseCase: BookUseCase) {

    companion object {
        const val API_BOOK_ENDPOINT = "/api/book"
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: BookCreateRequest): BookDetail {
        return bookUseCase.createBook(BookCreateResource(bookTitle = request.bookTitle, authorId = request.authorId))
    }

    @PutMapping("/{id}")
    @ResponseBody
    fun update(
        @PathVariable id: Int,
        @RequestBody request: BookUpdateRequest
    ): BookDetail {
        return bookUseCase.updateBook(
            BookUpdateResource(
                bookId = id,
                bookTitle = request.bookTitle,
                authorId = request.authorId
            )
        )
    }

    @GetMapping("/search")
    @ResponseBody
    fun find(@RequestParam keyword: String): List<BookSearchResultRecord> {
        return bookUseCase.searchBooks(criteria = BookSearchCriteria(keyword = keyword))
    }
}