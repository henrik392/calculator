package edu.ntnu.idi.idatt.server.controller

import edu.ntnu.idi.idatt.server.service.CalculateService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/calculate")
class CalculateController(private val calculateService: CalculateService) {
    private val logger = LoggerFactory.getLogger(CalculateController::class.java)

    @PostMapping
    fun calculate(@RequestBody input: String): Double {
        logger.info("Calculating the result of the input: $input")
        return calculateService.calculate(input);
    }
}