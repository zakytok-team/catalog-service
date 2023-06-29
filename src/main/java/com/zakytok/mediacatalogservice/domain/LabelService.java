package com.zakytok.mediacatalogservice.domain;

import com.zakytok.mediacatalogservice.web.LabelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelDto create(LabelDto label) {
        Label toSave = Label.of(label.name(), label.yearCreated());
        Label created = labelRepository.save(toSave);
        return mapToDto(created);
    }

    public List<LabelDto> getAll() {
        List<LabelDto> res = new ArrayList<>();
        labelRepository.findAll().forEach(el -> res.add(mapToDto(el)));
        return res;
    }

    private static LabelDto mapToDto(Label created) {
        return LabelDto.of(created.getId(), created.getName(), created.getYearCreated());
    }

    public void delete(UUID id) {
        Label toDelete = find(id);
        labelRepository.delete(toDelete);
    }

    Label find(UUID id) {
        return labelRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Label with id " + id + " does not exist."));
    }

}
