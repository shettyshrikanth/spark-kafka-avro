package com.data.avro;

import java.io.EOFException;
import java.io.IOException;

import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

/**
 * Created by Shrikanth Shetty on 28/10/2018.
 */
public class LogLineDeserializer {
	public LogLineDeserializer() {
	}

	public LogLine DeserializeEvent(byte[] event) throws Exception {
		SpecificDatumReader<LogLine> reader = new SpecificDatumReader<LogLine>(LogLine.getClassSchema());
		Decoder decoder = null;
		try {
			System.out.println(event);
			decoder = DecoderFactory.get().binaryDecoder(event, null);
			return reader.read(null, decoder);
		} catch (EOFException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return null;
	}
}
