package br.com.suleimanmoraes.primeiroprojetospringbatch.batch;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class ParImparBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job imprimirParImparJob() {
		return jobBuilderFactory.get("imprimirParImparJob").start(imprimirParImparStep()).incrementer(new RunIdIncrementer()).build();
	}

	private Step imprimirParImparStep() {
		return stepBuilderFactory.get("imprimirParImparStep").
				<Integer, String>chunk(1).reader(contaAteDezReader()).processor(parOuImparProcessor()).writer(imprimirWriter()).build();
	}
	
	private IteratorItemReader<Integer> contaAteDezReader() {
		List<Integer> numerosDeUmAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		return new IteratorItemReader<Integer>(numerosDeUmAteDez.iterator());
	}
	
	private FunctionItemProcessor<Integer, String> parOuImparProcessor() {
		return new FunctionItemProcessor<>(item -> item % 2 == 0 ? String.format("Item %s é Par", item) : String.format("Item %s é Ímpar", item));
	}
	
	private ItemWriter<String> imprimirWriter() {
		return itens -> itens.forEach(System.out::println);
	}
}
