package com.programming.dmaker.controller;

import com.programming.dmaker.dto.*;
import com.programming.dmaker.exception.DMakerException;
import com.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    //Controller는 Presentation Layer라고 한다. 비즈니스 로직이 포함되면 안좋을 수 있다.
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET deverlopers HTTP/1.1");
        return dMakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ) {
        log.info("GET deverloper HTTP/1.1");
        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
    ) {
        log.info("request : {} ", request);
        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDevelopers(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request
    ) {
        log.info("GET deverloper HTTP/1.1");
        return dMakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDevelopers(
            @PathVariable String memberId
    ) {
        log.info("GET deverloper HTTP/1.1");
        return dMakerService.deleteDeveloper(memberId);
    }

    // controller 내에서 발생하는 DMaker Exception은 여기서 처리한다.
    // 실무에서 : 에러 코드나 에러메세지에 정확한 정보를 담고 Status는 그냥 200으로 처리하는 경우도 많다고 한다.
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(
            DMakerException e,
            HttpServletRequest request
            ) {
        log.error("errorCode: {}, url : {}, message : {}", e.getDMakerErrorCode(),request.getRequestURI() , e.getDetailMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

}
