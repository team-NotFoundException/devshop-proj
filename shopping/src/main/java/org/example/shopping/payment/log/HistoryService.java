package org.example.shopping.payment.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public List<HistoryResponse.ListDTO> findAll() {
        List<History> histories = historyRepository.findAll();
        return histories.stream().map(HistoryResponse.ListDTO::new).toList();
    }
}
