package com.SEACORP.EastSea.Config;

import com.SEACORP.EastSea.Models.EastSeaRecord.EastSeaRecord;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
public class EastSeaConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Resource resource;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<EastSeaRecord> itemReader, ItemProcessor<EastSeaRecord, EastSeaRecord> itemProcessor, ItemWriter<EastSeaRecord> itemWriter){

        Step step = stepBuilderFactory.get("EastSea-Record-Load")
                .<EastSeaRecord, EastSeaRecord>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("EastSea-Record")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();

    }

    @Bean
    public FlatFileItemReader<EastSeaRecord> recordItemReader(@Value("${input}") Resource resource){
        FlatFileItemReader<EastSeaRecord> reader = new FlatFileItemReader<>();
        reader.setResource(resource);
        reader.setName("EastSea-Reader");
        reader.setLinesToSkip(1); //skips the line explaining the data types
        reader.setLineMapper(lineMapper());

        return reader;
    }

    @Bean
    LineMapper<EastSeaRecord> lineMapper(){
        DefaultLineMapper<EastSeaRecord> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames("year", "month", "day", "lat", "lon", "vgs", "ugs", "adts");

        defaultLineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<EastSeaRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(EastSeaRecord.class);

        return defaultLineMapper;
    }

  /*  @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public JdbcBatchItemWriter<EastSeaRecord> recordItemWriter(){
        JdbcBatchItemWriter<EastSeaRecord> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);

        itemWriter.setSql("Insert into eastsea_record values(:year, :month, :day, :lat, :log, :vgs, :ugs, :adts)"); // TODO: 6/4/2022 Create database with this table

        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }*/

   /* @Bean
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
    }*/

}
