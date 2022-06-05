package com.SEACORP.EastSea.Config;

import com.SEACORP.EastSea.Models.EastSeaRecord.EastSeaRecord;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

public class EastSeaConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private DataSource dataSource;

    @Bean
    public FlatFileItemReader<EastSeaRecord> recordItemReader(){
        FlatFileItemReader<EastSeaRecord> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1); //skips the line explaining the data types
        reader.setResource(new ClassPathResource("/East Sea/data_for_vis.csv"));

        DefaultLineMapper<EastSeaRecord> recordLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] {"year", "month", "day", "lat", "lon", "vgs", "adts"});

        recordLineMapper.setLineTokenizer(tokenizer);

        recordLineMapper.setFieldSetMapper(new EastSeaSetMapper());

        recordLineMapper.afterPropertiesSet();

        reader.setLineMapper(recordLineMapper);

        return reader;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public JdbcBatchItemWriter<EastSeaRecord> recordItemWriter(){
        JdbcBatchItemWriter<EastSeaRecord> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);

        itemWriter.setSql("Insert into eastsea_record values(:year, :month, :day, :lat, :log, :vgs, :adts)"); // TODO: 6/4/2022 Create database with this table

        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }

    @Bean
    public Job processJob() {
        return jobs.get("job")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1(){
        return steps.get("step1")
                .<EastSeaRecord, EastSeaRecord> chunk(10)
                .reader(recordItemReader())
                .writer(recordItemWriter())
                .build();
    }

}
