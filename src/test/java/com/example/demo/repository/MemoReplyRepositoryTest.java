package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.entity.Memo;
import com.example.demo.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoReplyRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;
    @Test
    public void insertTest(){

        UserEntity userEntity = userRepository.findById(1L).get();

        Memo memo = new Memo();
        memo.setTitle("안녕 이건 제목이야");
        memo.setContent("안녕 이건 내용이야");
        memo.setUserEntity(userEntity);

        memoRepository.save(memo);

    }

    @Test
    public void insertTest2(){
        UserEntity userEntity = userRepository.findById(1L).get();

        Board board = new Board();
        board.setTitle("안녕 이건 제목이야");
        board.setContent("안녕 이건 내용이야");
        board.setUserEntity(userEntity);

        boardRepository.save(board);
    }


    @Test
    public void readTest(){
        //글찾아오기 pk로 찾아오기

        Optional<Memo> optionalMemo =
                memoRepository.findById(1L);


        System.out.println(optionalMemo.get());

        Memo memo = optionalMemo.get();
        System.out.println("meme의 데이터" + memo);

        System.out.println("memo가져온 데이터의 작성자 참조된 유저내용" + memo.getUserEntity());

        System.out.println("그래서 작성자는 ?? " + memo.getUserEntity().getEmail());


    }

    @Test
    public void inserttestx100(){

        for(int i = 0 ;  i< 100 ; i++){
            UserEntity userEntity = userRepository.findById(1L).get();

            Memo memo = new Memo();
            memo.setTitle("안녕 이건 제목이야" + i);
            memo.setContent("안녕 이건 내용이야" + i);
            memo.setUserEntity(userEntity);

            memoRepository.save(memo);
        }

    }

    @Test
    public void findbyidTest(){
        //메모에 저장되어있는 데이터중 1번과 3번을 가져올 예정

        Optional<Memo> optionalMemo = memoRepository.findById(3L);

        System.out.println(optionalMemo);

        Memo memo = optionalMemo.get();

        System.out.println(memo);
    }

    @Test
    public void findAllTest(){

        List<Memo> memoList = memoRepository.findAll();

        for (Memo m : memoList) {
            System.out.println(m);
        }

    }
    @Test
    public void queryTest(){

        List<Memo> memoList =
        memoRepository.selectTitle("1");

        for(Memo m : memoList)        {
            System.out.println(m);
        }
    }

    @Test
    public void querytest2(){

        List<Memo> memoList =
                memoRepository.selectTitle2("1");

        for(Memo m : memoList)        {
            System.out.println(m);
        }

    }

    @Test
    public void querytest3(){

        List<Memo> memoList =
                memoRepository.findByTitleContains("1");

        for(Memo m : memoList)        {
            System.out.println(m);
        }

    }

    //업데이트 테스트
    @Test
    public void updateTest(){

        //findbyid를 통해 찾아와서 변경해서 저장한다.

        Memo memo = memoRepository.findById(1L).get();
        memo.setTitle("zzzzzzzzzz");
        memo.setContent("zzzzzzzzzzzzz");

        memoRepository.save(memo);


    }

    @Test
    public void delTest(){

        try {
            Memo memo = memoRepository.findById(3L).get();
            memoRepository.deleteById(memo.getMno());
            //memoRepository.delete(memo);
        }catch (NoSuchElementException e) {
            System.out.println("글이 없음");
        }





    }








}