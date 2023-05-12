package GooRoom.projectgooroom.homepost.domain;

import GooRoom.projectgooroom.global.embedded.Address;
import GooRoom.projectgooroom.homepost.dto.EditHomePostDto;
import GooRoom.projectgooroom.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class HomePost {

    @Id
    @GeneratedValue
    @Column(name = "home_post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String title;
    @NotNull
    private Boolean hasHome;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    private LocalDateTime lastEditTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ResidenceType residenceType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RentType rentType;

    @NotNull
    private int roomPrice;

    @Embedded
    @NotNull
    private Address address;

    @Lob
    private String content;

    private String roomImage;

    /**
     * 연관관계 생성 메서드
     * @param member
     */
    public void addMember(Member member){
        this.member = member;
        member.getHomePostList().add(this);
    }

    public void addRoomImage(String roomImagePath){
        this.roomImage = roomImagePath;
    }

    public void addLastEditTime(){
        this.lastEditTime = LocalDateTime.now();
    }

    public void editHomePost(EditHomePostDto homePostDto){
        this.title = homePostDto.title();
        this.postStatus = homePostDto.postStatus();
        this.rentType = homePostDto.rentType();
        this.roomPrice = homePostDto.roomPrice();
        this.address = homePostDto.address();
        this.content = homePostDto.content();
    }
}
