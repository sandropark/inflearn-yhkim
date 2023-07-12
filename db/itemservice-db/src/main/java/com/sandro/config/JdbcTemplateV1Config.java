package com.sandro.config;

import com.sandro.repository.ItemRepository;
import com.sandro.repository.jdbctemplate.JdbcTemplateItemRepository;
import com.sandro.repository.memory.MemoryItemRepository;
import com.sandro.service.ItemService;
import com.sandro.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
public class JdbcTemplateV1Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepository(dataSource);
    }
}
