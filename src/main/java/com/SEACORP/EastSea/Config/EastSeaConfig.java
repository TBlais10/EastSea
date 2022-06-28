package com.SEACORP.EastSea.Config;

import com.SEACORP.EastSea.Models.EastSeaRecord.EastSeaRecord;
import com.SEACORP.EastSea.Processor.EastSeaProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@EnableBatchProcessing
public class EastSeaConfig {
    @Autowired
    public JobBuilderFactory jobs;

    @Autowired
    public StepBuilderFactory steps;

    @Value("${data_for_vis.csv}")
    private String fileInput;
    /*@Autowired
    private DataSource dataSource;

    @Autowired
    private Resource resource;*/

    @Bean
    public FlatFileItemReader<EastSeaRecord> reader(){
        return new FlatFileItemReaderBuilder<EastSeaRecord>().name("eastSeaItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(new String[] {"year", "month", "day", "lat", "lon", "vgs", "ugs", "adts"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
                    setTargetType(EastSeaRecord.class);
                }})
                .build();
        }

        @Bean
    public JdbcBatchItemWriter writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<EastSeaRecord>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("insert into eastsea (year, month, day, lat, lon, vgs, ugs, adts) values (:year, :month, :day, :lat, :lon, :vgs, :ugs, :adts")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JmsProperties.Listener listener, Step step1){ // TODO: 6/27/2022 Find replacement for JobCompletionNotifcationListener
        return jobs.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener) //listener may not work.
                .flow(step1)
                .end()
                .build();
    }

    @Bean Step step1(JdbcBatchItemWriter writer){
        return steps.get("step1")
                .<EastSeaRecord, EastSeaRecord> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public EastSeaProcessor processor(){
        return new EastSeaProcessor();
    }

//    @Bean
//    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<EastSeaRecord> itemReader, ItemProcessor<EastSeaRecord, EastSeaRecord> itemProcessor, ItemWriter<EastSeaRecord> itemWriter){
//
//        Step step = stepBuilderFactory.get("EastSea-Record-Load")
//                .<EastSeaRecord, EastSeaRecord>chunk(100)
//                .reader(itemReader)
//                .processor(itemProcessor)
//                .writer(itemWriter)
//                .build();
//
//        return jobBuilderFactory.get("EastSea-Record")
//                .incrementer(new RunIdIncrementer())
//                .start(step)
//                .build();
//
//    }
//
//    @Bean
//    public FlatFileItemReader<EastSeaRecord> recordItemReader(@Value("${input}") Resource resource){
//        FlatFileItemReader<EastSeaRecord> reader = new FlatFileItemReader<>();
//        reader.setResource(resource);
//        reader.setName("EastSea-Reader");
//        reader.setLinesToSkip(1); //skips the line explaining the data types
//        reader.setLineMapper(lineMapper());
//
//        return reader;
//    }
//
//    @Bean
//    LineMapper<EastSeaRecord> lineMapper(){
//        DefaultLineMapper<EastSeaRecord> defaultLineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//
//        tokenizer.setDelimiter(",");
//        tokenizer.setStrict(false);
//        tokenizer.setNames("year", "month", "day", "lat", "lon", "vgs", "ugs", "adts");
//
//        defaultLineMapper.setLineTokenizer(tokenizer);
//
//        BeanWrapperFieldSetMapper<EastSeaRecord> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(EastSeaRecord.class);
//
//        return defaultLineMapper;
//    }

}
