package com.example.weblibrary.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
	@Autowired
	private Cloudinary cloudinary;

	public CloudinaryService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	public String uploadImage(MultipartFile file) throws IOException {
		Map<String, String> params = ObjectUtils.asMap("public_id", "my_image", "overwrite", "true");
		return cloudinary.uploader().upload(file.getBytes(), params).get("url").toString();
	}
}