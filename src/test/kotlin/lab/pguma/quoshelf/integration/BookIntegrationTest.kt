package lab.pguma.quoshelf.integration

import com.fasterxml.jackson.databind.ObjectMapper
import lab.pguma.quoshelf.presentation.api.book.BookController.Companion.API_BOOK_ENDPOINT
import lab.pguma.quoshelf.presentation.api.book.BookCreateRequest
import lab.pguma.quoshelf.presentation.api.book.BookUpdateRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val mapper = ObjectMapper()

    @Order(1)
    @Test
    @DisplayName("書籍の新規登録が正常にできること")
    fun create() {
        mockMvc.perform(
            post(API_BOOK_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BookCreateRequest("Kotlin In Action", authorId = 1)))
        )
            .andExpect { status().isCreated }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect {
                jsonPath("$.bookTitle").value("Kotlin In Action")
                jsonPath("$.authorName").value("山田一郎")
            }
    }

    @Order(2)
    @Test
    @DisplayName("書籍の更新が正常にできること")
    fun update() {
        mockMvc.perform(
            put("${API_BOOK_ENDPOINT}/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BookUpdateRequest("Kotlin Ultimania vol.3（改）", 2)))
        )
            .andExpect { status().isOk }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect {
                jsonPath("$.bookId").value(3)
                jsonPath("$.bookTitle").value("Kotlin Ultimania vol.3（改）")
                jsonPath("$.authorName").value("山田次郎")
            }
    }

    @Order(4)
    @Test
    @DisplayName("書籍の検索が正常にできること")
    fun find() {
        mockMvc.perform(get("${API_BOOK_ENDPOINT}/search").param("keyword", "Kotlin"))
            .andExpect { status().isOk }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect {
                jsonPath("$").isNotEmpty
            }
    }
}