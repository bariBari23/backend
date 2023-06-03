package store.baribari.demo.controller

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.dto.review.request.CreateReviewRequestDto
import store.baribari.demo.dto.review.response.CreateReviewResponseDto
import store.baribari.demo.dto.review.response.ReadOneReviewResponseDto
import store.baribari.demo.service.ReviewService
import java.security.Principal
import javax.validation.constraints.Positive

@Validated
@RestController
@RequestMapping("/api/v1/review")
class ReviewController(
    private val reviewService: ReviewService
) {

    //c


    /**
     * user 와
     * orderItem 을 받아서 review 를 생성한다.
     * 후에 review에 댓글 생성 가능할 수도?
     * 현재 까지는 review는 수정이 불가능하다.
     */
    @PostMapping("")
    fun createReview(
        @LoginUser loginUser: User,
        createReviewResponseDto: CreateReviewRequestDto
    ): ApiResponse<CreateReviewResponseDto> {

        val data = reviewService.createReview(
            username = loginUser.username,
            createReviewRequestDto = createReviewResponseDto
        )

        return ApiResponse.success(data)
    }

    /**
     * 해당 가게에 달린 모든 리뷰를 보여준다.
     * 나중에 리뷰를 페이징 처리 할 수도?
     * 또한
     */
    // r
    // 어떻게 구분하지?
    @GetMapping("store/{storeId}")
    fun readReviewByStore(
        principal: Principal?,
        @PathVariable @Positive storeId: Long
    ) {
        // 상점에 있는 리뷰를 읽는다.
    }

    // 상품에 따라서 리뷰를 읽는 기능도 나중에 프론트가 요구하면 넣는거로

    // r
    @GetMapping("/{reviewId}")
    fun readOneReview(
        principal: Principal?,
        @PathVariable @Positive reviewId: Long
    ): ApiResponse<ReadOneReviewResponseDto> {
        val data = reviewService.readOneReview(principal?.name ,reviewId)

        return ApiResponse.success(data)
    }


    // d
    // 현재 주요 기능은 아닌거 같음
    fun deleteReview() {

    }

}