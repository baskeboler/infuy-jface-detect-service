package uy.com.infuy.jfacedetect.service.mapper;

import org.mapstruct.Mapper;
import uy.com.infuy.jfacedetect.domain.FaceDetection;
import uy.com.infuy.jfacedetect.service.dto.FaceDetectionDTO;

@Mapper(componentModel = "spring", uses = {})
public interface FaceDetectionMapper extends EntityMapper<FaceDetectionDTO, FaceDetection> {

    default FaceDetection fromId(Long id) {
        if (id == null) {
            return null;
        }
        FaceDetection fd = new FaceDetection();
        fd.setId(id);
        return fd;
    }
}
