package com.example.demo.service;

import com.example.demo.dto.MemoDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Memo;
import com.example.demo.entity.Reply;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.MemoRepository;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void register(ReplyDTO replyDTO , String email) {
        //등록
        //dto>entity , 작성자를 작성해야한다. email
        UserEntity userEntity =
        userRepository.findByEmail(email);

        Reply reply = modelMapper.map(replyDTO, Reply.class);

        //부모 게시글과 , email이용해서 작성자 지정 작성자는 좀 나중에
        Memo memo =
        memoRepository.findById(  replyDTO.getMno() )
                .orElseThrow(EntityNotFoundException::new);

        reply.setMemo(memo);
        reply.setUserEntity(userEntity);

        replyRepository.save(reply);

    }

    @Override
    public List<ReplyDTO> list(int page , Long mno) {

        Pageable pageable =
                PageRequest.of(page-1 , 10, Sort.by("rno").descending());

        Page<Reply> replyPage =
         replyRepository.selectBymno( mno , pageable);

        //list<>형태로 변환
        List<Reply> replyList = replyPage.getContent();

        //dto
        List<ReplyDTO > replyDTOList = new ArrayList<>();
        for(Reply reply : replyList) {

            ReplyDTO replyDTO = modelMapper.map( reply , ReplyDTO.class  );
            replyDTO.setMemoDTO(  modelMapper.map( reply.getMemo() , MemoDTO.class));
            replyDTO.setUserDTO(  modelMapper.map( reply.getUserEntity() , UserDTO.class));
            replyDTO.getUserDTO().setPassword("");

            replyDTOList.add(replyDTO);

        }


        return replyDTOList;
    }

    @Override
    public ReplyDTO read(Long rno) {

        //데이터가져오기
        Reply reply =
        replyRepository.findById(rno).orElseThrow(EntityNotFoundException::new);


        //dto변환
        ReplyDTO replyDTO = modelMapper.map(reply , ReplyDTO.class);
        //부모값인 memo, userEntity도 dto로 변경해서 넣어준다.
        replyDTO.setMemoDTO(  modelMapper.map(reply.getMemo()  , MemoDTO.class)   );

        return replyDTO;
    }

    @Override
    public Long update(ReplyDTO replyDTO) {
        //데이터 가져오기
        Reply reply =
        replyRepository.findById(replyDTO.getRno()).orElseThrow(EntityNotFoundException::new);
        
        //변경하기

        reply.setContent( replyDTO.getContent() );
        
        //저장
        reply = replyRepository.save(reply);
        
        //저장한값을 돌려받을수 있다. 그값에서 pk 반환
        
        
        return reply.getRno();
    }

    @Override
    public void del(Long rno) {

        //데이터 찾아오기
        Reply reply =
                replyRepository.findById(rno).orElseThrow(EntityNotFoundException::new);


        //데이터 삭제
        replyRepository.delete(reply);
    }
}
