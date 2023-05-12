package GooRoom.projectgooroom.homepost.service;

import GooRoom.projectgooroom.global.exception.HomePostException;
import GooRoom.projectgooroom.global.exception.HomePostExceptionType;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.dto.EditHomePostDto;
import GooRoom.projectgooroom.member.domain.Member;
import GooRoom.projectgooroom.homepost.repository.HomePostRepository;
import GooRoom.projectgooroom.member.repository.MemberRepository;
import GooRoom.projectgooroom.homepost.dto.HomePostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HomePostService {

    private final HomePostRepository homePostRepository;
    private final MemberRepository memberRepository;

    /**
     * HomePost 생성
     *
     * @param memberId
     * @param homePostDto
     * @return HomePost
     */
    @Transactional
    public Long createHomePost(Long memberId, HomePostDto homePostDto, String roomImagePath) {
        Member member = memberRepository.findMemberById(memberId).get();
        HomePost post = homePostDto.toEntity();
        post.addMember(member);
        post.addRoomImage(roomImagePath);
        post.addLastEditTime();

        return homePostRepository.save(post).getId();
    }

    /**
     * Member가 작성한 전체 HomePost 페이징 조회
     *
     * @param memberId
     * @param pageable
     * @return
     */
    public Page<HomePost> findAllPostsByMember(Long memberId, Pageable pageable) {
        return homePostRepository.findHomePostsByMemberId(memberId, pageable);
    }

    /**
     * 전체 HomePost 페이징 조회
     *
     * @param pageable
     * @return
     */
    public Page<HomePost> findAllPosts(Pageable pageable) {
        return homePostRepository.findAll(pageable);
    }

    /**
     * ID를 통해 HomePost 조회
     *
     * @param postId
     * @return
     */
    public HomePost findOne(Long postId) {
        return homePostRepository.findHomePostById(postId);
    }

    /**
     * ID를 통해 HomePost 수정
     * 제목, 글 상태, rentType, 가격, 주소, 상세설명, 사진 수정
     * @param postId
     * @param homePostDto
     * @return
     */
    @Transactional
    public Long updateHomePost(Long postId, String email, EditHomePostDto homePostDto, String filePath) {
        checkWriter(postId, email);
        HomePost homePost = homePostRepository.findHomePostById(postId);
        homePost.editHomePost(homePostDto);
        homePost.addRoomImage(filePath);
        return homePost.getId();
    }

    /**
     * ID를 통해 HomePost 삭제
     * @param postId
     * @param email
     */
    @Transactional
    public void deleteHomePost(Long postId, String email){
        Member member = checkWriter(postId, email);
        homePostRepository.deleteById(postId);
        member.getHomePostList().removeIf(homePost -> homePost.getId() == postId);
    }

    /**
     * 작성자 확인
     * @param postId
     * @param email
     * @return
     */
    private Member checkWriter(Long postId, String email) {
        Member member = memberRepository.findMemberByEmail(email).get();
        if(homePostRepository.findHomePostById(postId).getMember().getId()
                !=
                member.getId()){
            throw new HomePostException(HomePostExceptionType.AUTHOR_MISMATCH);
        }
        else{
            return member;
        }
    }
}