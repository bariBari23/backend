package store.baribari.demo.controller

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.CreateReviewResponseDto
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.ReviewService
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
    fun createReview(
        @LoginUser loginUser: User,
        @Positive orderItemId: Long,
    ): ApiResponse<CreateReviewResponseDto> {

        val data = reviewService.createReview(username = loginUser.username, orderItemId = orderItemId)

        return ApiResponse.success(data)
    }

    /**
     * 해당 가게에 달린 모든 리뷰를 보여준다.
     * 나중에 리뷰를 페이징 처리 할 수도?
     * 또한
     */
    // r
    fun readAllReview() {

    }

    fun readOneReview() {

    }


    // d
    fun deleteReview() {

    }

}