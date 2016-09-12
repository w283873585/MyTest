package vr.com.pojo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class InterfaceParamCodec implements Codec<InterfaceParam>{

	@Override
	public void encode(BsonWriter writer, InterfaceParam value, 
			EncoderContext encoderContext) {
		writer.writeStartDocument();
			writer.writeName("key");
			writer.writeString(value.getKey());
			writer.writeName("desc");
			writer.writeString(value.getDesc());
			writer.writeName("constraint");
			writer.writeString(value.getConstraint());
		writer.writeEndDocument();
	}

	@Override
	public Class<InterfaceParam> getEncoderClass() {
		return InterfaceParam.class;
	}

	@Override
	public InterfaceParam decode(BsonReader reader, DecoderContext decoderContext) {
		InterfaceParam result = new InterfaceParam();
		reader.readStartDocument();
			reader.readName();
			result.setConstraint(reader.readString());
			reader.readName();
			result.setDesc(reader.readString());
			reader.readName();
			result.setKey(reader.readString());
		reader.readEndDocument();
		return result;
	}
}
