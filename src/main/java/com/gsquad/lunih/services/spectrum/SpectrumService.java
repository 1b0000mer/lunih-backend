package com.gsquad.lunih.services.spectrum;

import com.gsquad.lunih.dtos.categories.SpectrumDTO;
import com.gsquad.lunih.entities.categories.Spectrum;

import java.util.List;

public interface SpectrumService {

    //Page<PostType> listAllPaging(String search, int page, int size, String sort, String column);

    List<Spectrum> listAll();

    Spectrum get(int id);

    Spectrum create(SpectrumDTO dto);

    Spectrum update(int id, SpectrumDTO dto);

    Spectrum changeStatus(int id);

    Spectrum delete(int id);
}
