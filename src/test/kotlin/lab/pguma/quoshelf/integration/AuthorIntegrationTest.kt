package lab.pguma.quoshelf.integration

import com.fasterxml.jackson.databind.ObjectMapper
import lab.pguma.quoshelf.presentation.api.author.AuthorController.Companion.API_AUTHOR_ENDPOINT
import lab.pguma.quoshelf.presentation.api.author.AuthorCreateRequest
import lab.pguma.quoshelf.presentation.api.author.AuthorUpdateRequest
import lab.pguma.quoshelf.usecase.author.authorDetailsBaseFixture
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class AuthorIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val mapper = ObjectMapper()

    @Order(1)
    @Test
    @DisplayName("著者詳細を正常に取得できること")
    fun show() {
        mockMvc.perform(get("${API_AUTHOR_ENDPOINT}/1"))
            .andExpect { status().isOk }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect { content().json(mapper.writeValueAsString(authorDetailsBaseFixture[0])) }
    }

    @Order(2)
    @Test
    @DisplayName("著者の新規登録が正常にできること")
    fun create() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(API_AUTHOR_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(AuthorCreateRequest("山田四郎")))
        )
            .andExpect { status().isCreated }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect { jsonPath("$.authorName").value("山田四郎") }
    }

    @Order(3)
    @Test
    @DisplayName("著者の更新が正常にできること")
    fun update() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("${API_AUTHOR_ENDPOINT}/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(AuthorUpdateRequest("山田三郎（更新）")))
        )
            .andExpect { status().isOk }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect {
                jsonPath("$.authorId").value(3)
                jsonPath("$.authorName").value("山田三郎（更新）")
            }
    }

    @Order(4)
    @Test
    @DisplayName("著者の検索が正常にできること")
    fun find() {
        mockMvc.perform(get("${API_AUTHOR_ENDPOINT}/search").param("keyword", "Kotlin"))
            .andExpect { status().isOk }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect {
                jsonPath("$").isNotEmpty
            }
    }
}