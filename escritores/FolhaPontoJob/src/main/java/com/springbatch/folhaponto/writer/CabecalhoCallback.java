package com.springbatch.folhaponto.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.stereotype.Component;

import com.springbatch.folhaponto.dominio.FolhaPonto;

@Component
public class CabecalhoCallback implements FlatFileHeaderCallback{

	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s\n\n",
				new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
		writer.append(String.format("MÓDULO: RH \t\t\t\t\t\t\t\t HORA: %s\n\n",
				new SimpleDateFormat("HH:MM").format(new Date())));
		writer.append(String.format("\t\t\t\tFOLHA DE PONTO\n\n"));
	}

	@BeforeWrite
	public void beforeWrite(List<FolhaPonto> grupos) {}
	
	@AfterChunk
	public void afterChunk(ChunkContext context) {}
}
