package com.springbatch.folhaponto.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import com.springbatch.folhaponto.dominio.FolhaPonto;

@Component
public class RodapeCallback implements FlatFileFooterCallback {

	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.append(String.format("\n\t\t\t\t\t\t\t  Código de Autenticação: %s\n", "fkyew6868fewjfhjjewf"));
	}

	@BeforeWrite
	public void beforeWrite(List<FolhaPonto> grupos) {}
	
	@AfterChunk
	public void afterChunk(ChunkContext context) {}
}
