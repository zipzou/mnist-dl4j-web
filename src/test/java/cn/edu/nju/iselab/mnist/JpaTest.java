package cn.edu.nju.iselab.mnist;

import cn.edu.nju.iselab.mnist.dt.ImageDto;
import cn.edu.nju.iselab.mnist.dt.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {

    @Autowired private ImageRepository repository;

    @Transactional
    @Test
    public void testInsert() {
        ImageDto imageDto = new ImageDto();
        imageDto.setName("test");
        imageDto.setPath("null");
        imageDto.setTrained(false);
        repository.save(imageDto);
    }

}
