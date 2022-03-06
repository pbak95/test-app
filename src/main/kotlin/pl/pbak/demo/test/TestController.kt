package pl.pbak.demo.test

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/test")
class TestController {

    val logger: Logger = LoggerFactory.getLogger(TestController::class.java)

    @GetMapping(value = ["/uuid"], produces = ["application/json"])
    @Operation(
            operationId = "generateRandomUuid",
            summary = "Generates random UUID",
            tags = ["auth"])
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "new UUID was generated"),
    ])
    fun generateUuid(): UUID {
        val uuid = UUID.randomUUID()
        logger.info("Generate uuid: $uuid")
        return uuid
    }
}