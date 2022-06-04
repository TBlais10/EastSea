package com.SEACORP.EastSea.Config;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private DataSource dataSource;
//    @Value("assets/East Sea/data_for_vis.csv")
//    private Resource eastSeaCsv;

    @Bean
    public FlatFileItemReader<?> recordItemReader(){
        FlatFileItemReader<?> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1); //skips the line explaining the data types
        reader.setResource(new ClassPathResource("/East Sea/data_for_vis.csv"));

        DefaultLineMapper<?> recordLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] {"year", "month", "day", "lat", "lon", "vgs", "adts"});

        recordLineMapper.setLineTokenizer(tokenizer);

        recordLineMapper.setFieldSetMapper(/*Find the set mapper according to the data type needed*/);

        recordLineMapper.afterPropertiesSet();

        reader.setLineMapper(recordLineMapper);

        return reader;
    }

}
