package com.data.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

/**
 * Created by Shrikanth Shetty on 28/10/2018.
 */
public class LogLineSerializer {

	public LogLineSerializer() throws IOException {
	}

	public byte[] serializeMessage(LogLine eventMessage) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		DatumWriter<LogLine> writer = new SpecificDatumWriter<LogLine>(LogLine.getClassSchema());
		writer.write(eventMessage, encoder);
		encoder.flush();
		out.close();
		return out.toByteArray();
	}
}
