package com.ssafy.ibalance.child.controller;

import com.ssafy.ibalance.child.dto.annotation.CheckFile;
import com.ssafy.ibalance.child.dto.request.ModifyChildRequest;
import com.ssafy.ibalance.child.dto.request.RegistChildRequest;
import com.ssafy.ibalance.child.dto.response.*;
import com.ssafy.ibalance.child.dto.response.heightweight.page.HeightPageResponse;
import com.ssafy.ibalance.child.dto.response.heightweight.page.WeightPageResponse;
import com.ssafy.ibalance.child.service.ChildService;
import com.ssafy.ibalance.member.entity.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 박성진, 김주이
 */
@RestController
@RequestMapping("/child")
@RequiredArgsConstructor
@Validated
public class ChildController {

    private final ChildService childService;

    /**
     * 자녀 목록 조회
     *
     * @param member 로그인 한 멤버
     * @return 자녀 목록 (자녀 아이디, 이름, 프로필 이미지 url, 성별)
     */
    @GetMapping("")
    public List<ChildInfoResponse> getChildList(@AuthenticationPrincipal Member member) {

        return childService.getChildList(member.getId());
    }

    /**
     * 자녀 등록
     *
     * @param registChildRequest 자녀 이름, 생년월일, 성별, 키, 몸무게, 알러지
     * @param member             로그인 한 멤버
     * @return 등록된 자녀 정보 (자녀 아이디, 이름, 생년월일, 성별, 키, 몸무게, 프로필 이미지 url, 멤버 아이디)
     */
    @PostMapping("")
    public RegistChildResponse registChild(@Valid @RequestBody RegistChildRequest registChildRequest,
                                           @AuthenticationPrincipal Member member) {

        return childService.registChild(registChildRequest, member);
    }

    /**
     * 자녀 삭제
     *
     * @param member  로그인 한 멤버
     * @param childId 자녀 아이디
     * @return 삭제된 자녀 정보 (자녀 아이디, 이름, 멤버 아이디)
     */
    @DeleteMapping("/{childId}")
    public DeleteChildResponse deleteChild(@AuthenticationPrincipal Member member,
                                           @PathVariable @Min(value = 1, message = "자녀 아이디는 1 이상이어야 합니다.") Integer childId) {

        return childService.deleteChild(member, childId);
    }

    /**
     * 메인 페이지에서 자녀 정보와 오늘의 식단 조회
     *
     * @param childId 자녀 아이디
     * @param member  로그인 한 멤버
     * @return 자녀 정보 (자녀 아이디, 프로필 이미지 url, 이름, 생년월일, 성별, 키, 몸무게, 마지막 업데이트 날짜)와
     * 오늘의 식단 (식단 아이디, 식단 날짜, 순서, 메뉴 목록)
     */
    @GetMapping("/main/{childId}")
    public ChildDietResponse getMain(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                     @AuthenticationPrincipal Member member) {
        return childService.getMain(childId, member);
    }

    /**
     * 키 성장 데이터 조회
     *
     * @param childId  자녀 아이디
     * @param pageable 현재 조회하고 있는 페이지. page, size=4
     * @param member   로그인 한 멤버
     * @return 자녀의 성장 데이터 (성별, 생년월일, 개월 수, 기록일, 기록일 기준 일주일의 시작일과 종료일, 키)와
     * 평균 데이터 (개월 수, 평균 키, 평균 몸무게)
     */
    @GetMapping("/height/{childId}")
    public HeightPageResponse getHeightList(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                            Pageable pageable,
                                            @AuthenticationPrincipal Member member) {
        return childService.getHeightList(childId, pageable, member);
    }

    /**
     * 몸무게 성장 데이터 조회
     *
     * @param childId  자녀 아이디
     * @param pageable 현재 조회하고 있는 페이지. page, size=4
     * @param member   로그인 한 멤버
     * @return 자녀의 성장 데이터 (성별, 생년월일, 개월 수, 기록일, 기록일 기준 일주일의 시작일과 종료일, 몸무게)와
     * 평균 데이터 (개월 수, 평균 키, 평균 몸무게)
     */
    @GetMapping("/weight/{childId}")
    public WeightPageResponse getWeightList(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                            Pageable pageable,
                                            @AuthenticationPrincipal Member member) {
        return childService.getWeightList(childId, pageable, member);
    }

    /**
     * 자녀 상세 정보 조회
     *
     * @param childId 자녀 아이디
     * @param member  로그인 한 멤버
     * @return 자녀의 상세 정보 (자녀 아이디, 이미지, 이름, 생년월일, 성별, 키, 몸무게, 알러지 목록)
     */
    @GetMapping("/{childId}")
    public ChildDetailResponse getChildDetail(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                              @AuthenticationPrincipal Member member) {
        return childService.getChildDetail(childId, member);
    }

    /**
     * 자녀 정보 변경 (키, 몸무게, 알러지)
     *
     * @param childId            자녀 아이디
     * @param modifyChildRequest 변경된 자녀 정보
     * @param member             로그인 한 멤버
     * @return 변경된 자녀 정보 (자녀 아이디, 이미지, 이름, 생년월일, 성별, 키, 몸무게, 알러지 목록)
     */
    @PutMapping("/{childId}")
    public ChildDetailResponse modifyChild(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                           @RequestBody @Valid ModifyChildRequest modifyChildRequest,
                                           @AuthenticationPrincipal Member member) {
        return childService.modifyChild(childId, modifyChildRequest, member);
    }

    /**
     * 자녀 프로필 변경
     *
     * @param childId 자녀 아이디
     * @param image   이미지 파일
     * @param member  로그인 한 멤버
     * @return 변경된 자녀 정보
     */
    @PutMapping("/profile/{childId}")
    public ChildInfoResponse saveProfileImage(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                              @RequestPart(value = "image") @CheckFile MultipartFile image,
                                              @AuthenticationPrincipal Member member) {
        return childService.saveProfileImage(childId, image, member);
    }

    /**
     * 자녀 프로필 사진 삭제 (기본 이미지로 변경)
     *
     * @param childId 자녀 아이디
     * @param member  로그인 한 멤버
     * @return 기본 이미지로 변경된 자녀 정보
     */
    @DeleteMapping("/profile/{childId}")
    public ChildInfoResponse deleteProfileImage(@PathVariable @Min(value = 1, message = "자녀 ID 는 1 이상이어야 합니다.") Integer childId,
                                                @AuthenticationPrincipal Member member) {
        return childService.deleteProfileImage(childId, member);
    }
}
