package lab.pguma.quoshelf.presentation.api.author

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import lab.pguma.quoshelf.presentation.api.author.AuthorController.Companion.API_AUTHOR_ENDPOINT
import lab.pguma.quoshelf.usecase.author.AuthorUseCase
import lab.pguma.quoshelf.usecase.author.authorDetailsBaseFixture
import lab.pguma.quoshelf.usecase.author.authorSearchResultRecordBaseFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class AuthorControllerTest {

    @MockkBean
    private lateinit var authorUseCase: AuthorUseCase

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val mapper = ObjectMapper()

    @BeforeEach
    fun setup() {
        every { authorUseCase.getDetail(any()) } returns authorDetailsBaseFixture[0]
        every { authorUseCase.createAuthor(any()) } returns authorDetailsBaseFixture[0]
        every { authorUseCase.updateAuthor(any()) } returns authorDetailsBaseFixture[0]
        every { authorUseCase.searchAuthors(any()) } returns authorSearchResultRecordBaseFixture
    }

    @Test
    @DisplayName("著者詳細を取得時のI/F形式が想定通りであること")
    fun show() {
        mockMvc.perform(get("${API_AUTHOR_ENDPOINT}/1"))
            .andExpect { status().isOk }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect { content().json(mapper.writeValueAsString(authorDetailsBaseFixture[0])) }

        verify {
            authorUseCase.getDetail(any())
        }
    }

    @Test
    @DisplayName("著者を新規作成時のI/F形式が想定通りであること")
    fun create() {
        mockMvc.perform(
            post(API_AUTHOR_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(AuthorCreateRequest("テスト太郎")))
        )
            .andExpect { status().isCreated }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect { content().json(mapper.writeValueAsString(authorDetailsBaseFixture[0])) }

        verify {
            authorUseCase.createAuthor(any())
        }
    }

    @Test
    @DisplayName("著者を更新時のI/F形式が想定通りであること")
    fun update() {
        mockMvc.perform(
            put("${API_AUTHOR_ENDPOINT}/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(AuthorUpdateRequest("テスト太郎")))
        )
            .andExpect { status().isOk }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect { content().json(mapper.writeValueAsString(authorDetailsBaseFixture[0])) }

        verify {
            authorUseCase.updateAuthor(any())
        }
    }

    @Test
    @DisplayName("著者を検索時のI/F形式が想定通りであること")
    fun find() {
        mockMvc.perform(get("${API_AUTHOR_ENDPOINT}/search").param("keyword", "kotlin"))
            .andExpect { status().isOk }
            .andExpect { content().contentType(MediaType.APPLICATION_JSON) }
            .andExpect { content().json(mapper.writeValueAsString(authorSearchResultRecordBaseFixture)) }

        verify {
            authorUseCase.searchAuthors(any())
        }
    }
}