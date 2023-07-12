package com.sandro.config;

import com.sandro.repository.ItemRepository;
import com.sandro.repository.memory.MemoryItemRepository;
import com.sandro.service.ItemService;
import com.sandro.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

}
