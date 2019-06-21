package cn.edu.nju.iselab.mnist.dt;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<ImageDto, Integer> {

    /**
     * Find the image by its name, the name is unique column
     * @param name the name of image
     * @return image information
     */
    public ImageDto findByName(String name);

}
