package uy.com.infuy.jfacedetect.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uy.com.infuy.jfacedetect.domain.FaceDetection;
import uy.com.infuy.jfacedetect.domain.Image;
import uy.com.infuy.jfacedetect.repository.FaceDetectionRepository;
import uy.com.infuy.jfacedetect.repository.ImageRepository;
import uy.com.infuy.jfacedetect.service.ImageService;
import uy.com.infuy.jfacedetect.service.dto.FaceDetectionDTO;
import uy.com.infuy.jfacedetect.service.dto.ImageDTO;
import uy.com.infuy.jfacedetect.service.errors.AzureFaceApiException;
import uy.com.infuy.jfacedetect.service.mapper.FaceDetectionMapper;
import uy.com.infuy.jfacedetect.service.mapper.ImageMapper;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Image.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageRepository imageRepository;
    private final FaceDetectionRepository faceDetectionRepository;
    private final ImageMapper imageMapper;
    private final FaceDetectionMapper faceDetectionMapper;

    public ImageServiceImpl(ImageRepository imageRepository, FaceDetectionRepository faceDetectionRepository, ImageMapper imageMapper, FaceDetectionMapper faceDetectionMapper) {
        this.imageRepository = imageRepository;
        this.faceDetectionRepository = faceDetectionRepository;
        this.imageMapper = imageMapper;
        this.faceDetectionMapper = faceDetectionMapper;
    }

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);
        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    /**
     * Get all the images.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Images");
        return imageRepository.findAll(pageable)
            .map(imageMapper::toDto);
    }

    /**
     * Get one image by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ImageDTO findOne(Long id) {
        log.debug("Request to get Image : {}", id);
        Image image = imageRepository.findOne(id);
        return imageMapper.toDto(image);
    }

    /**
     * Delete the image by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.delete(id);
    }

    @Override
    public void processImage(File f) throws IOException {

        BufferedImage read = ImageIO.read(f);
        Graphics2D graphics = read.createGraphics();
        graphics.setColor(Color.RED);
//        graphics.setStroke(new BasicStroke(3));
        graphics.drawRect(10, 10, 100, 100);
        ImageIO.write(read, "jpg", new File(f.getAbsolutePath() + "-processed"));
    }

    @Override
    public void addFaceDetection(Long imageId, Long faceDetectionId) {
        Image image = imageRepository.findOne(imageId);
        FaceDetection faceDetection = faceDetectionRepository.findById(faceDetectionId).orElseThrow(AzureFaceApiException::new);

        image.getFaces().add(faceDetection);
        imageRepository.save(image);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageDTO> findByPathUuid(String uuid) {
        return imageRepository.findImageIdByFilenameUuid(uuid)
            .flatMap(imageRepository::findOneById)
            .map(imageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FaceDetectionDTO> getFacesDetected(Long imageId) {
        Image image = imageRepository.findOneById(imageId).orElseThrow(RuntimeException::new);
        return image.getFaces()
            .stream()
            .map(faceDetectionMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<BufferedImage> getImageFile(Long id) {
        return imageRepository.findOneById(id)
            .flatMap(im -> {
                if (StringUtils.isEmpty(im.getPath())) return Optional.empty();
                File file = new File(im.getPath());
                if (!file.exists()) return Optional.empty();
                BufferedImage imageBuffer = null;
                try {
                    imageBuffer = ImageIO.read(file);
                    return Optional.of(imageBuffer);
                } catch (IOException e) {
                    log.warn("File for image {} does not exist", im.getId());
                }
                return Optional.empty();
            });
    }
}
