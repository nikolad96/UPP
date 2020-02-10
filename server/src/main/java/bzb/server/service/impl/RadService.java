package bzb.server.service.impl;

import bzb.server.model.Rad;
import bzb.server.repository.RadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class RadService {

    @Autowired
    RadRepository radRepository;


    public Rad savePdf(MultipartFile file, Rad rad){
        try {
            rad.setPdf(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return radRepository.save(rad);
    }
}
