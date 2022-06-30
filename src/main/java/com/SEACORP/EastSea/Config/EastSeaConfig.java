package com.SEACORP.EastSea.Config;

import com.SEACORP.EastSea.Models.EastSeaRecord.EastSeaRecord;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class EastSeaConfig {
    @Autowired
    public JobBuilderFactory jobs;

    @Autowired
    public StepBuilderFactory steps;

    @Autowired
    public DataSource dataSource;

   @Bean
    public FlatFileItemReader<EastSeaRecord> itemReader() {
       FlatFileItemReader<EastSeaRecord> reader = new FlatFileItemReader<>();
       reader.setLinesToSkip(1);
       reader.setResource(new ClassPathResource("/assets/East Sea/data_for_vis.csv")); // TODO: 6/28/2022 Check to see if the csv is being read. Having issue of columns not matching.

       DefaultLineMapper<EastSeaRecord> customLineMapper = new DefaultLineMapper<>();

       DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
       tokenizer.setNames("year", "month", "day", "lat", "lon", "vgs", "ugs", "adts");

       customLineMapper.setLineTokenizer(tokenizer);
       customLineMapper.setFieldSetMapper(new EastSeaSetMapper());
       customLineMapper.afterPropertiesSet();
       reader.setLineMapper(customLineMapper);
       return reader;
   }

   @SuppressWarnings({"rawtypes", "unchecked"})
   @Bean
    public JdbcBatchItemWriter<EastSeaRecord> itemWriter(){
       JdbcBatchItemWriter<EastSeaRecord> writer = new JdbcBatchItemWriter<>();

       writer.setDataSource(dataSource);
       writer.setSql("INSERT INTO eastsea_record VALUES (:year, :month, :day, :lat, :lon, :vgs, :ugs, :adts)");
       writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
       writer.afterPropertiesSet();

       return writer;
   }

   @Bean
   public Step step1(){
       return steps.get("step1")
               .<EastSeaRecord, EastSeaRecord>chunk(10)
               .reader(itemReader())
               .writer(itemWriter())
               .build();
   }

   @Bean
    public Job job(){
       return jobs.get("job")
               .start(step1())
               .build();
   }

}
